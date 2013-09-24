package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Predator policy that uses an epsilon greedy method to select action 
 */
public class EpsilonGreedyPredatorPolicy extends LearningPredatorPolicy {
	
	/** Greedy factor */
	private double epsilon;

	public EpsilonGreedyPredatorPolicy(double epsilon) {
		super();
		this.epsilon = epsilon;
	}

	/**
	 * Sets the epsilon-greedy action in a certain state
	 */
	public void setAction(HuntState state, Position action){
		HashMap<Position, Double> dist = this.probabilities.get(state);
		
		int countActions = dist.size();
		for (Position key: dist.keySet()) {
			// Divide epsilon over all actions minus the greedy action
			dist.put(key, epsilon / (countActions - 1));
		}
		dist.put(action, 1.0 - epsilon);
	}

	@Override
	public void setProbabilitiesWithQ(HuntState state,
			Map<Position, Double> QValues) {
		
		Set<Position> actions=QValues.keySet();
		Position bestAction=Move.WAIT;
		double max=-10000;
		for(Position action: actions)
		{
			double Qval=QValues.get(action);
			if(Qval>max)
				bestAction=action;
		}
		this.setAction(state, bestAction);
		
	}

}

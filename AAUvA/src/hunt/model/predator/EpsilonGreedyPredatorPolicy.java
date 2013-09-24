package hunt.model.predator;

import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.HashMap;
import java.util.Map;

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
		// TODO Auto-generated method stub
		
	}

}

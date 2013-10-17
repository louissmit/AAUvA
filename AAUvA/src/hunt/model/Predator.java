package hunt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.PredatorPolicy;
import hunt.model.predator.SoftmaxPredatorPolicy;

/**
 * Predator object
 */
public class Predator {

	/** Action selection policy */
	protected PredatorPolicy policy;
	/** Learning algorithm */
	protected LearningAlgorithm learningAlg;
	/** Predator identifier */
	protected String name;
	/**
	 * Latest version of qtable from learning algorithm
	 */
	protected QTable latestQtable;

	/** Most recently seen state and the action taken in that state */
	private StateActionPair lastStateActionPair;
	
	public void UpdateLastStateActionPair(StateActionPair state)
	{
		PredatorInternalState localState = this.convertState((BasicMPState)state.getState());
		this.lastStateActionPair=new StateActionPair(localState,state.getAction());
	}
	
	/**
	 * Create a predator
	 * @param name - predator identifier
	 * @param policy - action selection policy
	 * @param alg - learning algorithm
	 */
	public Predator(String name, PredatorPolicy policy, LearningAlgorithm alg) {
		this.name = name;
		this.policy = policy;
		this.learningAlg = alg;
	}
	
	public Predator(String name, PredatorPolicy policy) {
		this.name = name;
		this.policy = policy;
		this.learningAlg = null;
		
	}

	/**
	 * Get an action for the given state 
	 * @param state
	 * @return
	 */
	public Position getAction(BasicMPState state) {
		PredatorInternalState localState = this.convertState(state);
		return policy.getAction(localState);
	}
	
	/**
	 * Give this predator an observation and update the Q-table and policy.
	 * @param observation
	 */
	public void giveObservation(StateAndRewardObservation observation) {
		if (this.learningAlg != null && lastStateActionPair != null) {
			// TODO: convert returned state to local state ??
			QTable qtable = this.learningAlg.update(lastStateActionPair, observation);
			this.latestQtable=qtable;
			//if (this.policy instanceof LearningPredatorPolicy) {
			//	((LearningPredatorPolicy) this.policy).setProbabilitiesWithQ(qtable);
			//}
		}
	}
	
	public void updatePolicy()
	{
		if (this.policy instanceof LearningPredatorPolicy) {
			((LearningPredatorPolicy) this.policy).setProbabilitiesWithQ(this.latestQtable);
		}
	}
	
	/**
	 * Convert a global state to a state from this predator's point of view
	 * @param relativeState "Positions of predators relative to prey"
	 * @return
	 */
	public hunt.model.PredatorInternalState convertState(BasicMPState relativeState) {
		Map<String, Position> predators = relativeState.getPredatorPositions();
		Position myPos = predators.get(this.name);
		Position prey = myPos.copy().negate();  


		ArrayList<Position> res = new ArrayList<Position>();
		for(Map.Entry<String, Position> pred : predators.entrySet()) {
			if(pred.getKey().equals(this.name)) continue;
			Position newPos = pred.getValue().subtract(myPos);
			res.add(newPos);
		}
		PredatorInternalState state = new PredatorInternalState(prey, res);
		return state;
	}

	public String getName() {
		return this.name;
	}
	

}

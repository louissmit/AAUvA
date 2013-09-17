package hunt.scripts;

import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Evaluates the values for all states in a given predator policy
 */
public class PolicyEvaluator {
	
	/**
	 * The calculated values for the states
	 */
	protected Map<HuntState, Double> values;
	/**
	 * The policy determining the predator moves
	 */
	protected PredatorPolicy policy;
	
	/**
	 * The amount of iterations required for convergence
	 */
	protected int iterations;
	
	/**
	 * The minimal difference required for another iteration
	 */
	public static final double THRESHOLD = 0.001;
	/**
	 * The discount factor
	 */
	public static final double GAMMA = 0.8;
	
	/**
	 * Initialize the evaluator with a given policy
	 * @param policy - the policy to evaluate
	 */
	public PolicyEvaluator(PredatorPolicy policy) {
		values = new HashMap<HuntState, Double>();
		this.policy = policy;
	}

	/**
	 * Perform policy evaluation
	 * Note that this makes use of the result of a previously found run, if any.
	 */
	public void run() {
		// Setup
		this.init();
		double difference = 1000;
		this.iterations = 0;
		
		// Sweeps
//		double debug;
//		HuntState debugState = new HuntState(new Position(0,0), new Position(0,1));
		while (difference > THRESHOLD) {
			iterations++;
			difference = 0;
			
			// Loop over all states
			for (Entry<HuntState, Double> entry: values.entrySet()) {
				HuntState oldState = entry.getKey();
				Double oldValue = entry.getValue();
				
				// Loop over all actions possible in the current state
				double updatedValue = 0;
				for (Position action : policy.getActions(oldState)) {
					double actionProbability = policy.getActionProbability(oldState, action);
					double actionValue = 0;
					
					// Loop over all states that may be reached by the given combination of old state and action
					for (HuntState newState : policy.getNextStates(oldState, action)) {
						double transitionProbability = policy.getTransitionProbability(oldState, newState, action);
						double transitionReward = policy.getReward(oldState, newState, action);
						double newStateValue = values.get(newState);
												
						actionValue += transitionProbability * (transitionReward + GAMMA * newStateValue); 

						if (newState.isTerminal()) {
							System.out.println(actionValue);
						}
					}
					
					updatedValue += actionProbability * actionValue;
				}
				
				this.values.put(oldState, updatedValue);
				
				difference = Math.max(difference, Math.abs(oldValue - updatedValue));
			}
			
//			debug = this.values.get(debugState);
//			System.out.println(debug);
		}
	}
	
	/**
	 * Make sure the state space contains all possible states
	 */
	public void init() {
		for (HuntState state : policy.getAllStates()) {
			if (!values.containsKey(state)) { 
				values.put(state, new Double(0));
			}
		}
	}
	
	/**
	 * The values for all states in this policy
	 * @return a map with all states and their corresponding values
	 */
	public Map<HuntState, Double> getValues() {
		return this.values;
	}
	
	/**
	 * The amount of iterations on the most recent invocation of run()
	 * @return
	 */
	public int getIterations() {
		return this.iterations;
	}
}

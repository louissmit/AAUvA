/**
 * 
 */
package hunt.scripts;

import java.util.Map;

import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PlannerPredatorPolicy;

/**
 * Wraps around PolicyEvaluator to improve the policy and evaluate again.
 *
 */
public class PolicyIterator {

	private PolicyEvaluator eval;
	private PlannerPredatorPolicy policy;

	public PolicyIterator(PolicyEvaluator eval){
		this.eval = eval;
		this.policy = eval.policy;
	}

	/**
	 * Executes policy iteration algorithm.
	 */
	public void run(){
		Map<HuntState, Double> values = this.eval.getValues();

		boolean stable = true;
		while(stable){
			for (HuntState currentState: values.keySet()) {

				// Loop over all actions possible in the current state and selects the best action
				double bestActionValue = -1; //all action values => 0
				Position bestAction = null;
				for (Position action : policy.getActions(currentState)) {
					double actionProbability = policy.getActionProbability(currentState, action);
					double actionValue = eval.evaluateAction(currentState, action);
					if((actionProbability * actionValue) > bestActionValue) {
						bestAction = action;
						stable = false;
						bestActionValue = actionProbability * actionValue;
					}
				}
				policy.setAction(currentState, bestAction);
			}
			if(!stable) {
				eval.run();
			}
		}
	}

}

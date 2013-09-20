/**
 * 
 */
package hunt.scripts;

import java.util.Map;
import java.util.Map.Entry;

import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PlannerPredatorPolicy;

/**
 * @author louissmit
 *
 */
public class PolicyImprover {

	private PolicyEvaluator eval;
	private PlannerPredatorPolicy policy;

	public PolicyImprover(PolicyEvaluator eval){
		this.eval = eval;
		this.policy = eval.policy;
	}

	public void run(){
		Map<HuntState, Double> values = this.eval.getValues();

		boolean stable = true;
		while(stable){
			for (Entry<HuntState, Double> entry: values.entrySet()) {
				HuntState oldState = entry.getKey();

				// Loop over all actions possible in the current state
				double bestActionValue = 0;
				Position bestAction = null;
				for (Position action : policy.getActions(oldState)) {
					double actionProbability = policy.getActionProbability(oldState, action);
					double actionValue = eval.evaluateAction(oldState, action);
					if((actionProbability * actionValue) > bestActionValue) {
						bestAction = action;
						stable = false;
						bestActionValue = actionProbability * actionValue;
					}
				}
				policy.setAction(oldState, bestAction);
			}
			if(!stable) {
				eval.run();
			}
		}
	}

}

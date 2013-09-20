/**
 * 
 */
package hunt.scripts;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PlannerPredatorPolicy;
import hunt.model.predator.PredatorPolicy;

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

	public PolicyImprover run(){
		Map<HuntState, Double> values = this.eval.getValues();

		boolean stable = true;
		while(stable){
			eval.run();
			for (Entry<HuntState, Double> entry: values.entrySet()) {
				HuntState oldState = entry.getKey();
				Double oldValue = entry.getValue();

				// Loop over all actions possible in the current state
				double bestActionValue = 0;
				Position bestAction = null;
				for (Position action : policy.getActions(oldState)) {
					double actionProbability = policy.getActionProbability(oldState, action);
					double actionValue = eval.evaluateAction(oldState, action);
					if((actionProbability * actionValue) > bestActionValue) {
						bestAction = action;
						stable = false;
						HashMap<Position, Double> dist = new HashMap<Position, Double>();
						policy.setAction(oldState, bestAction);
					}
				}
			}
		}
		return this;
	}

}

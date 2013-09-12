package hunt.scripts;

import hunt.model.Predator;
import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PolicyEvaluator {
	
	protected Map<HuntState, Double> values;
	protected PredatorPolicy policy;
	
	protected int iterations;
	
	public static final double THRESHOLD = 0.001;
	public static final double GAMMA = 0.8;
	
	public PolicyEvaluator(PredatorPolicy policy) {
		values = new HashMap<HuntState, Double>();
		this.policy = policy;
	}

	public void run() {
		this.init();
		
		double difference = 1000;
		this.iterations = 0;
		while (difference > THRESHOLD) {
			iterations++;
			difference = 0;
			
			for (Entry<HuntState, Double> entry: values.entrySet()) {
				HuntState oldState = entry.getKey();
				Double oldValue = entry.getValue();
				
				double updatedValue = 0;
				for (Position action : policy.getActions(oldState)) {
					double actionProbability = policy.getActionProbability(oldState, action);
					double actionValue = 0;
					
					for (HuntState newState : policy.getNextStates(oldState, action)) {
						double transitionProbability = policy.getTransitionProbability(oldState, newState, action);
						double transitionReward = policy.getReward(oldState, newState, action);
						double newStateValue = values.get(newState);
						
						actionValue += transitionProbability * (transitionReward + GAMMA * newStateValue); 
					}
					
					updatedValue += actionProbability * actionValue;
				}
				
				this.values.put(oldState, updatedValue);
				
				difference = Math.max(difference, Math.abs(oldValue - updatedValue));
			}
		}
	}
	
	public void init() {
		for (HuntState state : policy.getAllStates()) {
			if (!values.containsKey(state)) { 
				values.put(state, new Double(0));
			}
		}
	}
	
	public Map<HuntState, Double> getValues() {
		return this.values;
	}
	
	public int getIterations() {
		return this.iterations;
	}
}

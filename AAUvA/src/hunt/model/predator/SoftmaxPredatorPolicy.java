package hunt.model.predator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hunt.model.HuntState;
import hunt.model.QTable;
import hunt.model.board.Position;

/**
 * Predator agent using softmax 
 */
public class SoftmaxPredatorPolicy extends LearningPredatorPolicy {
	
	/** Softmax temperature */
	private double temperature;

	/**
	 * Initialization
	 * @param temperature - the temperature to use
	 */
	public SoftmaxPredatorPolicy(double temperature) {
		this.temperature = temperature;
		// Set random policy
		this.probabilities = new HashMap<HuntState, HashMap<Position, Double>>();
		for (HuntState state : this.getAllStates()) {
			HashMap<Position, Double> distribution = new HashMap<Position, Double>();
			
			List<Position> actions = this.getActions(state);
			for (Position action : actions) {
				distribution.put(action, new Double(((double) 1) / actions.size()));
			}
			
			this.probabilities.put(state, distribution);
		}
	}
	
	@Override
	public void setProbabilitiesWithQ(HuntState state, Map<Position, Double> QValues) {
		// Calculation of relative weights
		HashMap<Position, Double> probabilities = new HashMap<Position, Double>();
		double totalProbability = 0;
		for (Position action : QValues.keySet()) {
			Double rawProbability = Math.pow(Math.E,  (QValues.get(action) / temperature));
			probabilities.put(action, rawProbability);
			totalProbability += rawProbability;
		}
		
		// Marginalization
		for (Position action: probabilities.keySet()) {
			probabilities.put(action, probabilities.get(action) / totalProbability);
		}
		
		this.probabilities.put(state, probabilities);
	}

	@Override
	public void setProbabilitiesWithQ(QTable qtable) {
		// TODO Auto-generated method stub
		
	}

}

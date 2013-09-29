package hunt.model.predator;

import java.util.HashMap;
import java.util.Map;

import hunt.model.HuntState;
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
	}

}

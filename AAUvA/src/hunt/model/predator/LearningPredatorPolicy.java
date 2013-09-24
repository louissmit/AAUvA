package hunt.model.predator;

import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.List;
import java.util.Map;

public abstract class LearningPredatorPolicy extends PredatorPolicy {

	/**
	 * Update the probability distribution using qvalues for actions in a state
	 * @param state - the state to update the distribution for
	 * @param QValues - the Q-values for the actions in the given state
	 */
	public abstract void setProbabilitiesWithQ(HuntState state, Map<Position, Double> QValues);
	
	@Override
	public List<HuntState> getAllStates() {
		// TODO
		return null;
	}
	
}

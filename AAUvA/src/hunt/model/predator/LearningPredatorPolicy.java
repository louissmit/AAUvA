package hunt.model.predator;

import hunt.model.HuntState;
import hunt.model.QTable;
import hunt.model.RelativeState;
import hunt.model.board.Position;

import java.util.ArrayList;
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
		List<HuntState> result = new ArrayList<HuntState>();
		
		// Predator position
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				result.add(new RelativeState(new Position(i,j)));
			}
		}
		
		return result;
	}

	public abstract void setProbabilitiesWithQ(QTable qtable);
	
}

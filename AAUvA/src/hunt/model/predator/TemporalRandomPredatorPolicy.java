package hunt.model.predator;

import hunt.model.HuntState;
import hunt.model.RelativeState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Random predator policy making use of the improved state representation
 */
public class TemporalRandomPredatorPolicy extends RandomPredatorPolicy {
	
	public TemporalRandomPredatorPolicy()
	{
		super();
	}

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
}

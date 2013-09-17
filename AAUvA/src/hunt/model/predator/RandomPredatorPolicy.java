package hunt.model.predator;

import hunt.model.AbsoluteState;
import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Predator policy that makes random moves
 */
public class RandomPredatorPolicy extends PlannerPredatorPolicy {

	/**
	 * Random number generator
	 */
	private Random generator;
	
	/**
	 * Create and initialize policy
	 */
	public RandomPredatorPolicy()
	{
		generator = new Random();
		
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
	public List<HuntState> getAllStates() {
		List<HuntState> result = new ArrayList<HuntState>();
		
		// Predator position
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				// Prey position
				for (int k = 0; k < Position.BWIDTH; k++) {
					for (int l = 0; l < Position.BHEIGHT; l++) {
						result.add(new AbsoluteState(new Position(i,j), new Position(k,l)));
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Decides on an action for the agent. Ignores the probability distribution (for now)
	 */
	@Override
	public Position getAction(HuntState s) {
		// Alowed actions
		List<Position> actions = this.getActions(s);
		
		// Get a number between 0 and 1
		double randomNumber = generator.nextDouble();
		// Get a number between 0 and the amount of actions
		int index = (int) (randomNumber * actions.size());
		// Use the number to pick a random action
		return actions.get(index);
	}

}

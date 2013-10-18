package hunt.model;

import java.util.ArrayList;
import java.util.List;

import hunt.controller.Move;
import hunt.model.board.Position;

/**
 * Abstract prey agent
 */
public abstract class AbstractPrey {
	
	/**
	 * Get the available actions in the given state
	 * @param s - the current state
	 * @return the actions the prey may take
	 */
	public List<Position> getActions(HuntState s) {
		List<Position> moves = new ArrayList<Position>();
		moves.add(Move.SOUTH);
		moves.add(Move.NORTH);
		moves.add(Move.WEST);
		moves.add(Move.EAST);
		moves.add(Move.WAIT);
		
		// Take only moves that do not lead to predators
		List<Position> result = new ArrayList<Position>();
		for (Position move : moves) {
			HuntState newState = s.movePrey(move);
			if (!newState.predatorWins()) {
				result.add(move);
			}
		}
		
		//return result;
		return moves;
	}
	
	/**
	 * Returns an action based on the current state
	 * @param s - the current state
	 * @return the chosen action for the prey
	 */
	public abstract Position getAction(HuntState s);

	/**
	 * Return the probability of taking a certain action given a certain state
	 * @param state - the current state
	 * @param action - the action
	 * @return the probability of taking action in state
	 */
	public abstract double getProbabilityOfAction(HuntState state,
			Position action);

	public abstract void giveObservation(StateAndRewardObservation preyObservation);

}

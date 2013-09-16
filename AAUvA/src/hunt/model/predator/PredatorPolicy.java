package hunt.model.predator;

import java.util.List;

import hunt.model.HuntState;
import hunt.model.board.Board;
import hunt.model.board.Position;

public interface PredatorPolicy {
	
	public abstract void move(Board b);

	/**
	 * Return all moves a predator can make given a certain state
	 * @param oldState - the state where the predator needs to make a move
	 * @return all allowed actions in the given state
	 */
	public abstract List<Position> getActions(HuntState oldState);

	/**
	 * Return the probability of picking a certain action given a certain state
	 * @param oldState
	 * @param action
	 * @return
	 */
	public abstract double getActionProbability(HuntState oldState, Position action);

	/**
	 * Return a list of states that may be reached after performing a certain action in a certain state
	 * @param oldState
	 * @param action
	 * @return
	 */
	public abstract List<HuntState> getNextStates(HuntState oldState, Position action);

	/**
	 * Return the probability of reaching a certain state after having taken a certain action in a certain state
	 * @param oldState
	 * @param newState
	 * @param action
	 * @return
	 */
	public abstract double getTransitionProbability(HuntState oldState,
			HuntState newState, Position action);

	/**
	 * Return the expected reward obtained for transitioning from one state to another under a certain action
	 * @param oldState
	 * @param newState
	 * @param action
	 * @return
	 */
	public abstract double getReward(HuntState oldState, HuntState newState,
			Position action);

	/**
	 * Returns a list of all possible states
	 * @return
	 */
	public abstract List<HuntState> getAllStates(Board board);

	/**
	 * Decides on a move given a state
	 * @param s - the current state
	 * @return the move the predator will make
	 */
	public abstract Position getAction(HuntState s);


}

package hunt.model;

import hunt.model.board.Position;

/**
 * A state representation for the model
 */
public interface HuntState {

	/**
	 * Finds out whether this is a terminal state
	 * @return true if this is a terminal state, false otherwise
	 */
	public abstract boolean isTerminal();
	
	/**
	 * Creates a new HuntState wherein the predator has taken an action
	 * @param action - the predator's action
	 * @return the new HuntState
	 */
	public abstract HuntState movePredator(Position action);
	
	/**
	 * Creates a new HuntState wherein the prey has taken an action
	 * @param action - the prey action
	 * @return the new HuntState
	 */
	public abstract HuntState movePrey(Position action);
	
	/**
	 * Copies the information in this HuntState
	 * @return a new HuntState identical to this one
	 */
	public abstract HuntState copy();

	/**
	 * Whether the predators won
	 * @return true if the prey has been caught, false otherwise
	 */
	public abstract boolean predatorWins();

	/**
	 * Returns the action the prey has taken to reach this state given the previous state
	 * @param oldState - the previous state of the program
	 * @return
	 */
	public abstract Position getPreyAction(HuntState oldState);
}

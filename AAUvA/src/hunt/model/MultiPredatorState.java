package hunt.model;

import hunt.model.board.Position;

/**
 * State with multiple predators
 */
public abstract class MultiPredatorState implements HuntState {

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
	public abstract MultiPredatorState movePredator(String name, Position action);
	
	/**
	 * Creates a new HuntState wherein the prey has taken an action
	 * @param action - the prey action
	 * @return the new HuntState
	 */
	public abstract MultiPredatorState movePrey(Position action);
	
	/**
	 * Copies the information in this HuntState
	 * @return a new HuntState identical to this one
	 */
	public abstract MultiPredatorState copy();

	/**
	 * Whether the predators won
	 * @return true if the prey has been caught, false otherwise
	 */
	public abstract boolean predatorWins();

	/**
	 * @return
	 */
	public boolean predatorsCollide() {
		// TODO Auto-generated method stub
		return false;
	}

}

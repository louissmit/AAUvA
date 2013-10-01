package hunt.model;

import hunt.model.board.Position;

/**
 * State action pair
 */
public class StateActionPair {

	private HuntState state;
	private Position action;
	public StateActionPair(HuntState copy, Position copy2) {
		this.state = copy;
		this.action = copy2;
	}
	/**
	 * @return the state
	 */
	public HuntState getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(HuntState state) {
		this.state = state;
	}
	/**
	 * @return the action
	 */
	public Position getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(Position action) {
		this.action = action;
	}
	
}

package hunt.model;

import hunt.model.board.Position;

public class StateAndAction {
	
	public HuntState state;
	public Position action;
	
	public StateAndAction(HuntState _state,Position _action)
	{
		this.state=_state;
		this.action=_action;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + state.hashCode();
		result = prime * result + action.hashCode();
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StateAndAction))
			return false;
		StateAndAction other = (StateAndAction) obj;
		if (state != other.state)
			return false;
		if (action != other.action)
			return false;
		return true;
	}


}

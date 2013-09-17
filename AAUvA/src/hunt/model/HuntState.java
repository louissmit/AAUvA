package hunt.model;

import hunt.model.board.Position;

/**
 * Representation of the current layout of the grid world 
 */
public class HuntState {
	
	/**
	 * Position of the prey in the grid world
	 */
	private Position preyPosition;
	/**
	 * Position of the predator in the grid world
	 */
	private Position predatorPosition;
	
	/**
	 * Constructor with fields initialized
	 * @param preyPosition - the position of the prey
	 * @param predatorPosition - the position of the predator
	 */
	public HuntState(Position preyPosition, Position predatorPosition) {
		this.preyPosition = preyPosition;
		this.predatorPosition = predatorPosition;
	}
	
	@Override
	public String toString() {
		return "Predator(" + predatorPosition + "), Prey(" + preyPosition + ")";
	}
	
	/**
	 * Return the position of the prey
	 * @return the position of the prey
	 */
	public Position getPreyPosition() {
		return preyPosition.copy();
	}
	
	/**
	 * Return the position of the predator
	 * @return the position of the predator
	 */
	public Position getPredatorPosition() {
		return predatorPosition.copy();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((predatorPosition == null) ? 0 : predatorPosition.hashCode());
		result = prime * result
				+ ((preyPosition == null) ? 0 : preyPosition.hashCode());
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
		if (!(obj instanceof HuntState))
			return false;
		HuntState other = (HuntState) obj;
		if (predatorPosition == null) {
			if (other.predatorPosition != null)
				return false;
		} else if (!predatorPosition.equals(other.predatorPosition))
			return false;
		if (preyPosition == null) {
			if (other.preyPosition != null)
				return false;
		} else if (!preyPosition.equals(other.preyPosition))
			return false;
		return true;
	}

	/**
	 * Finds out whether this is a terminal state
	 * @return true if this is a terminal state, false otherwise
	 */
	public boolean isTerminal() {
		return this.predatorPosition.equals(this.preyPosition);
	}
}


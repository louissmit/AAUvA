package hunt.model;

import hunt.model.board.Position;

/**
 * Smart state representation
 */
public class RelativeState implements HuntState {
	
	/**
	 * Relative distance of the prey compared to the predator
	 */
	private Position distance;
	
	/**
	 * Constructor
	 * @param distance - the initial distance
	 */
	public RelativeState(Position distance) {
		this.distance = distance;
	}

	@Override
	public boolean isTerminal() {
		return distance.equals(new Position(0,0));
	}

	@Override
	public HuntState movePredator(Position action) {
		return new RelativeState(distance.copy().subtract(action));
	}

	@Override
	public HuntState movePrey(Position action) {
		return new RelativeState(distance.copy().add(action));
	}

	@Override
	public HuntState copy() {
		return new RelativeState(this.distance.copy());
	}

	@Override
	public boolean predatorWins() {
		return distance.equals(new Position(0,0));
	}

	@Override
	public Position getPreyAction(HuntState oldState) {
		if (!(oldState instanceof RelativeState)) {
			throw new IllegalArgumentException("oldState must be of type TemporalState");
		}
		RelativeState state = (RelativeState) oldState;
		
		return this.distance.copy().subtract(state.getDistance());
	}

	/**
	 * Returns the distance to the prey
	 * @return the distance to the prey
	 */
	private Position getDistance() {
		return distance.copy();
	}
	
	@Override
	public String toString() {
		return "Distance to prey: (" + distance + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
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
		if (!(obj instanceof RelativeState))
			return false;
		RelativeState other = (RelativeState) obj;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		return true;
	}

}

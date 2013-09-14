package hunt.model;

import hunt.model.board.Position;

public class HuntState {
	
	private Position preyPosition;
	private Position predatorPosition;
	
	public HuntState(Position preyPosition, Position predatorPosition) {
		this.preyPosition = preyPosition;
		this.predatorPosition = predatorPosition;
	}
	
	public Position getPreyPosition() {
		return new Position(this.preyPosition.getX(),this.preyPosition.getY());
	}
	
	public Position getPredatorPosition() {
		return new Position(this.predatorPosition.getX(),this.predatorPosition.getY());
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
}


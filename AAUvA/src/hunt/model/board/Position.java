package hunt.model.board;

import hunt.controller.Move;

public class Position {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		if (!(obj instanceof Position))
			return false;
		Position other = (Position) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
	

	
	public boolean isEqual(Position p) {
		return (p.x == this.x) && (p.y == this.y);
	}
	
	public Position isAdjacent(Position p) {
		Position move = new Position(p.x - this.x , p.y - this.y);
		if(move.isEqual(Move.EAST)) {
			return Move.EAST;
		} else if (move.isEqual(Move.WEST)) {
			return Move.WEST;
		} else if (move.isEqual(Move.NORTH)) {
			return Move.NORTH;
		} else if (move.isEqual(Move.SOUTH)) {
			return Move.SOUTH;		
		}
		return Move.WAIT;
	}
	
	public String toString(){
		return "("+this.x+", "+this.y+")";
	}

	/**
	 * @param newX
	 * @param newY
	 * @return
	 */
	public Position set(int newX, int newY) {
		// TODO Auto-generated method stub
		this.x = newX;
		this.y = newY;
		return this;
	}
	
}

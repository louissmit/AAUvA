package hunt.model.board;

/**
 * A combination of an x-coordinate and a y-coordinate in the grid-world
 */
public class Position {
	
	/**
	 * Height of the board
	 */
	public static final int BHEIGHT = 11;
	/**
	 * Width of the board
	 */
	public static final int BWIDTH = 11;
	
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

	/**
	 * X coordinate
	 */
	private int x;
	/**
	 * Y coordinate
	 */
	private int y;

	/**
	 * Create a new position
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * X coordinate
	 * @return x coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Y coordinate
	 * @return y coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Test for equality
	 * @param p another position
	 * @return true if the positions are equal, false otherwise
	 */
	public boolean isEqual(Position p) {
		return (p.x == this.x) && (p.y == this.y);
	}
	
	@Override
	public String toString(){
		return this.x + ", " + this.y;
	}
	
	/**
	 * Add the values in another Position to this Position 
	 * @param pos - the other position
	 * @return this
	 */
	public Position add(Position pos) {
		this.x = mod((this.x + pos.x), BWIDTH);
		this.y = mod((this.y + pos.y), BHEIGHT); 
		return this;
	}

	/**
	 * Produces a correct modulo, taking negative numbers into account
	 * @param x - the input
	 * @param n - the number to take the modulo over
	 * @return x modulo n
	 */
	private int mod(int x, int n) {
		int i = x % n;
		if (i < 0) {
			i += n;
		}
		return i;
	}

	/**
	 * Create an identical move object
	 * @return a copy of this move object
	 */
	public Position copy() {
		return new Position(this.x, this.y);
	}

	/**
	 * Subtract a position from this position
	 * @param pos - the other position
	 * @return this
	 */
	public Position subtract(Position pos) {
		Position posInverse = new Position(-pos.x, -pos.y);
		return this.add(posInverse);
	}
	
}

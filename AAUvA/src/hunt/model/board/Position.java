package hunt.model.board;

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
	
	public Position add(Position p) {
		this.x += p.x;
		this.y += p.y;
		return this;
	}
	
	public Position cap(Board board) {

		this.x = x % board.getWidth();
		this.y = y % board.getHeight();
		
		return this;
	}
	
	public boolean isEqual(Position p) {
		return (p.x == this.x) && (p.y == this.y);
	}
	
	public String toString(){
		return "("+this.x+", "+this.y+")";
	}
	
}

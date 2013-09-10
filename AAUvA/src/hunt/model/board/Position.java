package hunt.model.board;

public class Position {
	
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

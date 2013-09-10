package hunt.model.board;

import hunt.controller.Move;

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
	
	public Position update(Position move, Board board) {
		update(this, move, board);
		return this;
	}
	
	public Position update(Position p, Position move, Board board) {
		p.x = clamp(p.x + move.x, board.getWidth());
		p.y = clamp(p.y + move.y, board.getHeight());
		return p;
	}
	
	private int clamp(int x, int n) {
		int res;
		if(x < 0) {
			res = x + n;
		} else {
			res = x % n;
		}	
		return res;
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
	
}

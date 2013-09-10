package hunt.controller;

import hunt.model.board.Position;

public interface Move {

	public static final Position WAIT = new Position(0, 0);
	public static final Position NORTH = new Position(0, -1);
	public static final Position SOUTH = new Position(0, 1);
	public static final Position EAST = new Position(1, 0);
	public static final Position WEST = new Position(-1, 0);
	
}

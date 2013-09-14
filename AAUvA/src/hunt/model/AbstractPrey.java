package hunt.model;

import hunt.model.board.Board;
import hunt.model.board.Position;

public abstract class AbstractPrey {

	private Position position;
	
	public abstract void move(Board b);

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public AbstractPrey setPosition(int x, int y) {
		this.position = new Position(x, y);
		return this;
	}

}

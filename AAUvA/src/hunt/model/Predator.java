package hunt.model;

import hunt.model.board.Board;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

public class Predator {
	
	protected PredatorPolicy policy;

	private Position position;
	
	public Predator(PredatorPolicy policy) {
		this.policy = policy;
	}

	public void move(Board board) {
		this.policy.move(board);
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public Predator setPosition(int x, int y) {
		this.position = new Position(x, y);
		return this;
	}

}

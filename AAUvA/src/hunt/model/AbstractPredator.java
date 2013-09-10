package hunt.model;

import hunt.model.board.Board;
import hunt.model.board.Position;

public abstract class AbstractPredator {
	private int reward = 0;
	
	public abstract Position move(HuntState state, Board b);

	public void giveReward() {
		this.reward = 10;
	}

}

package hunt.model;

import java.util.List;

import hunt.model.board.Board;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

public class Predator {
	private int reward = 0;
	
	protected Board board;
	
	protected PredatorPolicy policy;
	
	public Predator(PredatorPolicy policy) {
		this.policy = policy;
	}

	public void giveReward() {
		this.reward = 10;
	}

	public Position move(HuntState s, Board board2) {
		return this.policy.move(s, board2);
	}

}

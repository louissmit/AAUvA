package hunt.model;

import hunt.model.board.Board;
import hunt.model.board.Position;

public abstract class AbstractPredator {
	
	public abstract Position move(HuntState state, Board b);

}

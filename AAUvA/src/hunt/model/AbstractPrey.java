package hunt.model;

import hunt.model.board.Board;
import hunt.model.board.Position;

public abstract class AbstractPrey {
	
	public abstract Position move(HuntState s, Board b);

}

package hunt.model;

import hunt.controller.Move;
import hunt.model.board.Board;
import hunt.model.board.Position;

public abstract class AbstractPrey {
	
	public abstract Position move(HuntState s, Board b);
	public abstract double GetProbabilityOfAction(HuntState state,Position move, Board b);

}

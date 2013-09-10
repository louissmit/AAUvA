package hunt.model;
import hunt.controller.Move;
import hunt.model.board.Board;
import hunt.model.board.Position;

import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class RandomPredator extends AbstractPredator {

	private Random generator;
	
	public RandomPredator()
	{
		generator = new Random();
	}
	@Override
	public Position move(HuntState state, Board b) {
		Position current = state.getPredatorPosition();
		double randomNumber = generator.nextDouble();
		
		if(randomNumber<=0.2)
		{
			return current.update(Move.WAIT, b);
		}
		else if(randomNumber<=0.4)
		{
			return current.update(Move.EAST, b);
		}
		else if(randomNumber<=0.6)
		{
			return current.update(Move.NORTH, b);
		}
		else if(randomNumber<=0.8)
		{
			return current.update(Move.SOUTH, b);
		}
		else
		{
			return current.update(Move.WEST, b);
		}
			
	}

}

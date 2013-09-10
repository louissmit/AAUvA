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
			return current.add(Move.WAIT).cap(b);
		}
		else if(randomNumber<=0.4)
		{
			return current.add(Move.EAST).cap(b);
		}
		else if(randomNumber<=0.6)
		{
			return current.add(Move.NORTH).cap(b);
		}
		else if(randomNumber<=0.8)
		{
			return current.add(Move.SOUTH).cap(b);
		}
		else
		{
			return current.add(Move.WEST).cap(b);
		}
			
	}

}

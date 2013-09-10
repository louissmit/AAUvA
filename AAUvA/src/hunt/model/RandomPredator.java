package hunt.model;
import hunt.controller.Move;
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
	public Position move(HuntState state) {
		Position current = state.getPredatorPosition();
		double randomNumber = generator.nextDouble();
		
		if(randomNumber<=0.2)
		{
			return current.add(Move.WAIT);
		}
		else if(randomNumber<=0.4)
		{
			return current.add(Move.EAST);
		}
		else if(randomNumber<=0.6)
		{
			return current.add(Move.NORTH);
		}
		else if(randomNumber<=0.8)
		{
			return current.add(Move.SOUTH);
		}
		else
		{
			return current.add(Move.WEST);
		}
			
	}

}

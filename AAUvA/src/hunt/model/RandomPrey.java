package hunt.model;

import hunt.controller.Move;
import hunt.model.board.Position;

import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class RandomPrey extends AbstractPrey {

	private Random generator;
	
	public RandomPrey()
	{
		generator=new Random();
	}
	@Override
	public Position move(HuntState state) {
		Position current = state.getPredatorPosition();
		double randomNumber = generator.nextDouble();
		
		if(randomNumber<=0.8)
		{
			return current.add(Move.WAIT);
		}
		else if(randomNumber<=0.85)
		{
			return current.add(Move.EAST);
		}
		else if(randomNumber<=0.9)
		{
			return current.add(Move.NORTH);
		}
		else if(randomNumber<=0.95)
		{
			return current.add(Move.SOUTH);
		}
		else
		{
			return current.add(Move.WEST);
		}
			
	}

}
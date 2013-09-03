package hunt.model;
import hunt.controller.Move;

import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class RandomPredator extends AbstractPredator {

	private Random generator;
	
	public RandomPredator()
	{
		generator=new Random();
	}
	@Override
	public int move() {
		// TODO Auto-generated method stub
		double randomNumber=generator.nextDouble();
		if(randomNumber<=0.2)
		{
			return Move.WAIT;
		}
		else if(randomNumber<=0.4)
		{
			return Move.EAST;
		}
		else if(randomNumber<=0.6)
		{
			return Move.NORTH;
		}
		else if(randomNumber<=0.8)
		{
			return Move.SOUTH;
		}
		else
		{
			return Move.WEST;
		}
			
	}

}

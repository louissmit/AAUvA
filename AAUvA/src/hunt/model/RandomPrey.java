package hunt.model;

import hunt.controller.Move;

import java.util.Random;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

public class RandomPrey extends AbstractPrey {

	private Random generator;
	
	public RandomPrey()
	{
		generator=new Random();
	}
	@Override
	public int move() {
		double randomNumber=generator.nextDouble();
		if(randomNumber<=0.8)
		{
			return Move.WAIT;
		}
		else if(randomNumber<=0.85)
		{
			return Move.EAST;
		}
		else if(randomNumber<=0.9)
		{
			return Move.NORTH;
		}
		else if(randomNumber<=0.95)
		{
			return Move.SOUTH;
		}
		else
		{
			return Move.WEST;
		}
			
	}

}
package hunt.model;

import hunt.controller.Move;
import java.util.ArrayList;
import hunt.model.board.Board;
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
	public Position move(HuntState state, Board b) {
		Position current = state.getPreyPosition();
		
		//there must be a better way + testing
		ArrayList<Position> moves = new ArrayList<Position>();
		moves.add(Move.SOUTH);
		moves.add(Move.NORTH);
		moves.add(Move.WEST);
		moves.add(Move.EAST);
		
		double randomNumber = generator.nextDouble();
		double range = 0.05;
		Position adj = current.isAdjacent(b.getPredatorPosition());

		
		if(moves.contains(adj)) {
			moves.remove(adj);
			range = 0.2 / 3;
		}
		if(randomNumber<=0.8)
		{
			return current.update(Move.WAIT, b);
		}
		else if(randomNumber <= 0.8 + range){
			return current.update(moves.get(0), b);
		}
		else if(randomNumber <= 0.8 + 2*range){
			return current.update(moves.get(1), b);
		}
		else if(randomNumber <= 0.8 + 3*range){
			return current.update(moves.get(2), b);
		}
		else {
			return current.update(moves.get(2), b);
		}		
	}

}
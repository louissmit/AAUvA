package hunt.model;

import hunt.controller.Move;
import java.util.ArrayList;
import java.util.List;

import hunt.model.board.Board;
import hunt.model.board.Position;

import java.util.Random;


public class RandomPrey extends AbstractPrey {

	private Random generator;
	
	public RandomPrey()
	{
		generator=new Random();
	}
	@Override
	public void move(Board board) {
		Position current = this.getPosition();
		
		//there must be a better way + testing
		ArrayList<Position> moves = new ArrayList<Position>();
		moves.add(Move.SOUTH);
		moves.add(Move.NORTH);
		moves.add(Move.WEST);
		moves.add(Move.EAST);
		
		double randomNumber = generator.nextDouble();
		double range = 0.05;
		Position adj = current.isAdjacent(board.getPredator().getPosition());

		
		if(moves.contains(adj)) {
			moves.remove(adj);
			range = 0.2 / 3;
		}
		
		if(randomNumber <= range){
			board.updatePrey(moves.get(0));
		}
		else if(randomNumber <= 2*range){
			board.updatePrey(moves.get(1));
		}
		else if(randomNumber <= 3*range){
			board.updatePrey(moves.get(2));
		}
		else if(randomNumber <= 4*range && moves.size() > 3){
			board.updatePrey(moves.get(3));
		} else {
			board.updatePrey(Move.WAIT);
		}
	}
	@Override
	public Position getAction(HuntState s) {
		Position action;
		
		// Decide on move or wait
		double randomNumber = generator.nextDouble();
		if (randomNumber < 0.8) {
			action = Move.WAIT;
		} else {
			List<Position> actions = this.getActions(s);
			actions.remove(Move.WAIT);
			
			// Pick an action
			randomNumber = generator.nextDouble();
			int index = (int) (randomNumber * actions.size());
			action = actions.get(index);
		}
		
		return action;
	}

}
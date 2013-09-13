package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.HuntState;
import hunt.model.board.Board;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPredatorPolicy implements PredatorPolicy {

	private Random generator;
	
	public RandomPredatorPolicy()
	{
		generator = new Random();
	}

	
	@Override
	public void move(Board board) {
		double randomNumber = generator.nextDouble();
		
		if(randomNumber<=0.2)
		{
			board.updatePredator(Move.WAIT);
		}
		else if(randomNumber<=0.4)
		{
			board.updatePredator(Move.EAST);
		}
		else if(randomNumber<=0.6)
		{
			board.updatePredator(Move.NORTH);
		}
		else if(randomNumber<=0.8)
		{
			board.updatePredator(Move.SOUTH);
		}
		else
		{
			board.updatePredator(Move.WEST);
		}
	}
	@Override
	public List<Position> getActions(HuntState oldState) {
		List<Position> result = new ArrayList<Position>();
		result.add(Move.EAST);
		result.add(Move.NORTH);
		result.add(Move.SOUTH);
		result.add(Move.WAIT);
		result.add(Move.WEST);
		return result;
	}
	@Override
	public double getActionProbability(HuntState oldState, Position action) {
		double result = 0;
		if (this.getActions(oldState).contains(action)) {
			result = 0.2;
		}
		return result;
	}
	@Override
	public List<HuntState> getNextStates(HuntState oldState, Position action) {
		Position predPosition = oldState.getPredatorPosition();
		Position preyPosition = oldState.getPreyPosition();
		// Apply possible prey moves to its position
		// Generate next states
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getTransitionProbability(HuntState oldState,
			HuntState newState, Position action) {
		Position predPositionOld = oldState.getPredatorPosition();
		Position predPositionNew = newState.getPredatorPosition();
		if (predPositionOld.equals(predPositionNew)) {
			// Calculate prey move between old and new state
			// Calucalte probability of moving
		}
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getReward(HuntState oldState, HuntState newState,
			Position action) {
		double result = 0;
		if (newState.getPredatorPosition().equals(newState.getPreyPosition())) {
			result = 10;
		}
		return result;
	}
	@Override
	public List<HuntState> getAllStates(Board board) {
		List<HuntState> result = new ArrayList<HuntState>();
		
		// Predator position
		for (int i = 0; i < board.getWidth(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				// Prey position
				for (int k = 0; k < board.getWidth(); k++) {
					for (int l = 0; l < board.getHeight(); l++) {
						result.add(new HuntState(new Position(i,j), new Position(k,l)));
					}
				}
			}
		}
		
		return result;
	}

}

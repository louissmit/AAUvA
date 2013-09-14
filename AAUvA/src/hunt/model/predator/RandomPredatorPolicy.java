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
	
	private Board board;
	
	public RandomPredatorPolicy()
	{
		generator = new Random();
	}
	
	public RandomPredatorPolicy(Board b) {
		this.board = b;
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
		predPosition.update(action, this.board);
		List<HuntState> states=new ArrayList<HuntState>();
		HuntState currentState=new HuntState(preyPosition, predPosition);
		if(board.getPrey().GetProbabilityOfAction(currentState, Move.EAST, this.board)>0)
			states.add(new HuntState(oldState.getPreyPosition().update(Move.EAST,board), predPosition));
		if(board.getPrey().GetProbabilityOfAction(currentState, Move.WEST, this.board)>0)
			states.add(new HuntState(oldState.getPreyPosition().update(Move.WEST,board), predPosition));
		if(board.getPrey().GetProbabilityOfAction(currentState, Move.NORTH, this.board)>0)
			states.add(new HuntState(oldState.getPreyPosition().update(Move.NORTH,board), predPosition));
		if(board.getPrey().GetProbabilityOfAction(currentState, Move.SOUTH, this.board)>0)
			states.add(new HuntState(oldState.getPreyPosition().update(Move.SOUTH,board), predPosition));
		states.add(new HuntState(oldState.getPreyPosition().update(Move.WAIT,board), predPosition));
		// Apply possible prey moves to its position
		// Generate next states
		// TODO Auto-generated method stub
		return states;
	}
	@Override
	public double getTransitionProbability(HuntState oldState,
			HuntState newState, Position action) {
		Position predPositionOld = oldState.getPredatorPosition();
		Position predPositionNew = newState.getPredatorPosition();
		predPositionOld.update(action, this.board);
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
	public List<HuntState> getAllStates() {
		List<HuntState> result = new ArrayList<HuntState>();
		
		// Predator position
		for (int i = 0; i < this.board.getWidth(); i++) {
			for (int j = 0; j < this.board.getHeight(); j++) {
				// Prey position
				for (int k = 0; k < this.board.getWidth(); k++) {
					for (int l = 0; l < this.board.getHeight(); l++) {
						result.add(new HuntState(new Position(i,j), new Position(k,l)));
					}
				}
			}
		}
		
		return result;
	}

}

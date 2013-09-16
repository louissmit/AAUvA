package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.board.Board;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPredatorPolicy implements PredatorPolicy {

	private Random generator;
	/**
	 * Prey agent
	 */
	private AbstractPrey prey;
	
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
		List<HuntState> states = new ArrayList<HuntState>();
		
		// In terminal states, loop to self
		if (!oldState.isTerminal()) {
			// Update predator position and state
			Position predatorPosition = oldState.getPredatorPosition().copy().add(action);
			HuntState midState = new HuntState(predatorPosition, oldState.getPreyPosition().copy());
			
			Position preyPositionOld = oldState.getPreyPosition();
			for (Position preyAction : this.prey.getActions(midState)) {
				// Update prey position and state
				Position preyPositionNew = preyPositionOld.copy().add(preyAction);
				states.add(new HuntState(predatorPosition.copy(),preyPositionNew));
			}
		} else {
			states.add(new HuntState(oldState.getPreyPosition().copy(), oldState.getPredatorPosition().copy()));
		}
		
		return states;
	}
	@Override
	public double getTransitionProbability(HuntState oldState,
			HuntState newState, Position action) {
		double result = 0;
		
		// Terminal states always have probability 1 of looping back
		if (!oldState.isTerminal()) {
			Position predPositionNew = newState.getPredatorPosition();
			
			Position preyPositionOld = oldState.getPreyPosition();
			Position preyPositionNew = newState.getPreyPosition();
	
			if (this.getActions(oldState).contains(action)) {
				// State after predator takes a turn
				HuntState midState = new HuntState(predPositionNew.copy(), preyPositionOld);
				
				// Inferred prey action
				Position preyAction = preyPositionNew.copy().substract(preyPositionOld);
				
				// Determine probability of prey taking this action given the intermediate state
				result = this.prey.getProbabilityOfAction(midState, preyAction);
			}
		} else {
			result = 1;
		}

		return result;
	}
	@Override
	public double getReward(HuntState oldState, HuntState newState,
			Position action) {
		double result = 0;
		
		// Terminal states can no longer get rewards
		if (!oldState.isTerminal()) {
			if (newState.getPredatorPosition().equals(newState.getPreyPosition())) {
				result = 10;
			}
		}
		
		return result;
	}
	@Override
	public List<HuntState> getAllStates() {
		List<HuntState> result = new ArrayList<HuntState>();
		
		// Predator position
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				// Prey position
				for (int k = 0; k < Position.BWIDTH; k++) {
					for (int l = 0; l < Position.BHEIGHT; l++) {
						result.add(new HuntState(new Position(i,j), new Position(k,l)));
					}
				}
			}
		}
		
		return result;
	}


	@Override
	public Position getAction(HuntState s) {
		// Alowed actions
		List<Position> actions = this.getActions(s);
		
		// Get a number between 0 and 1
		double randomNumber = generator.nextDouble();
		// Get a number between 0 and the amount of actions
		int index = (int) (randomNumber * actions.size());
		// Use the number to pick a random action
		return actions.get(index);
	}

	/**
	 * Sets the prey
	 * @param prey - the prey agent
	 * @return this
	 */
	public PredatorPolicy setPrey(AbstractPrey prey) {
		this.prey = prey;
		return this;
	}

}

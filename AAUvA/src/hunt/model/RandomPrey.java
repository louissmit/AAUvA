package hunt.model;

import hunt.controller.Move;
import java.util.List;

import hunt.model.board.Position;

import java.util.Random;

/**
 * Prey that randomly moves about the board
 */
public class RandomPrey extends AbstractPrey {

	/**
	 * Random number generator
	 */
	private Random generator;
	
	/**
	 * Initialize
	 */
	public RandomPrey()
	{
		generator=new Random();
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
	@Override
	public double getProbabilityOfAction(HuntState state, Position action) {
		double result = 0;
		if (action.equals(Move.WAIT)) {
			// Wait has given probability
			result = 0.8;
		} else {
			// Divide leftover probability over available actions (minus 1 to compensate for Move.WAIT)
			result = 0.2 / (this.getActions(state).size() - 1);
		}
		
		return result;
	}
	@Override
	public void giveObservation(StateAndRewardObservation preyObservation) {
		// TODO Auto-generated method stub
		
	}

}
package hunt.model.predator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hunt.controller.Move;
import hunt.model.HuntState;
import hunt.model.board.Position;

public abstract class PredatorPolicy {
	
	/**
	 * A mapping from states to actions to the probabilities of taking that action in that state
	 */
	protected Map<HuntState, HashMap<Position, Double>> probabilities;
	
	/**
	 * Return all moves a predator can make given a certain state
	 * @param oldState - the state where the predator needs to make a move
	 * @return all allowed actions in the given state
	 */
	public List<Position> getActions(HuntState oldState) {
		List<Position> result = new ArrayList<Position>();
		result.add(Move.EAST);
		result.add(Move.NORTH);
		result.add(Move.SOUTH);
		result.add(Move.WAIT);
		result.add(Move.WEST);
		return result;
	}

	/**
	 * Return the probability of picking a certain action given a certain state
	 * @param oldState - the old state
	 * @param action - the action taken
	 * @return the probability of this combination occurring (policy-dependent)
	 */
	public double getActionProbability(HuntState oldState, Position action) {
		return this.probabilities.get(oldState).get(action);
	}

	/**
	 * Returns a list of all possible states
	 */
	public abstract List<HuntState> getAllStates();

	/**
	 * Decides on a move given a state
	 * @param s - the current state
	 * @return the move the predator will make
	 */
	public Position getAction(HuntState s) {
		// Alowed actions
		
		List<Position> actions = this.getActions(s);
		HashMap<Position, Double> distribution=probabilities.get(s);
		Random generator = new Random();
		double randomNumber = generator.nextDouble();
		double prob=0;
		for (Position action : actions) {
			prob+=distribution.get(action);
			if(randomNumber<=prob)
				return action;
		}
		return Move.WAIT;
	}
	
	/**
	 * sets map of probabilities of taking actions in particular state
	 * @param _probabilities
	 */
	public void setProbabilities(Map<HuntState, HashMap<Position, Double>> _probabilities)
	{
		this.probabilities=_probabilities;
	}
	/**
	 * gets map of probabilities of taking actions in particular state
	 * @return
	 */
	public Map<HuntState, HashMap<Position, Double>> getProbabilities()
	{
		return this.probabilities;
	}

}

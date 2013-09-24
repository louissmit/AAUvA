package hunt.model.predator;

import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PlannerPredatorPolicy extends PredatorPolicy {
	/**
	 * Prey agent
	 */
	private AbstractPrey prey;

	public void setActionProbability(HuntState state, HashMap<Position, Double> distribution){
		this.probabilities.put(state, distribution);	}

	/**
	 * Make the agent behave deterministically in a certain state
	 * @param state - the state that the agent should make a certain action
	 * @param action - the action that the agents should take in state
	 */
	public void setAction(HuntState state, Position action){
		HashMap<Position, Double> dist = this.probabilities.get(state);
		for(Position key: dist.keySet()){
			dist.put(key, 0.0);
		}
		dist.put(action, 1.0);
	}

	/**
	 * Return a list of states that may be reached after performing a certain action in a certain state
	 * @param oldState - the current state
	 * @param action - the action taken in the current state
	 * @return - the states that may be reached from the state-action pair
	 */
	public List<HuntState> getNextStates(HuntState oldState, Position action) {
		List<HuntState> states = new ArrayList<HuntState>();

		// In terminal states, loop to self
		if (!oldState.isTerminal()) {
			// Update predator position and state
			HuntState midState = oldState.movePredator(action);

			if (!midState.isTerminal()) {
				for (Position preyAction : this.prey.getActions(midState)) {
					// Update prey position and state
					states.add(midState.movePrey(preyAction));
				}
			} else {
				states.add(midState);
			}
		} else {
			states.add(oldState.copy());
		}

		return states;
	}

	/**
	 * Return the probability of reaching a certain state after having taken a certain action in a certain state
	 * @param oldState - the old state
	 * @param newState - the new state
	 * @param action - the action moving the state from the old to the new
	 * @return - the probability of reaching newState after performing action in oldState
	 */
	public double getTransitionProbability(HuntState oldState,
			HuntState newState, Position action) {
		double result = 0;

		// Terminal states always have probability 1 of looping back
		if (!oldState.isTerminal()) {
			// Make sure action is allowed; should never fail, really
			if (this.getActions(oldState).contains(action)) {

				// Apply predator action
				HuntState midState = oldState.movePredator(action);

				if (!midState.isTerminal()) {
					// Inferred prey action
					Position preyAction = newState.getPreyAction(midState);

					// Determine probability of prey taking this action given the intermediate state
					result = this.prey.getProbabilityOfAction(midState, preyAction);
				} else {
					result = 1;
				}
			}
		} else {
			result = 1;
		}

		return result;
	}

	/**
	 * Return the expected reward obtained for transitioning from one state to another under a certain action
	 * @param oldState - the old state
	 * @param newState - the new state
	 * @param action - the action moving the state from the old to the new
	 * @return - the reward gotten for reaching newState after performing action in oldState
	 */
	public double getReward(HuntState oldState, HuntState newState,
			Position action) {
		double result = 0;

		// Terminal states can no longer get rewards
		if (!oldState.isTerminal()) {
			if (newState.isTerminal() && newState.predatorWins()) {
				result = 10;
			}
		}

		return result;
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
	
	public String toString() {
		return this.probabilities.toString();
	}

}



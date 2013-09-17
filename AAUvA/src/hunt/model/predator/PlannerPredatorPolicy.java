package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class PlannerPredatorPolicy extends PredatorPolicy {
	/**
	 * Prey agent
	 */
	private AbstractPrey prey;

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

	@Override
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

	@Override
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

	@Override
	public List<HuntState> getAllStates() {
		// TODO Auto-generated method stub
		return null;
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

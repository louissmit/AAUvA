package hunt.scripts;

import java.util.ArrayList;

import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

/**
 * Class in charge of running simulations in the grid world  
 */
public class Simulator {
	
	/**
	 * Is the simulator still running
	 */
	private boolean running = true;
	/**
	 * The current state of the grid world
	 */
	protected HuntState currentState, startState;
	
	/**
	 * The deciding policy for the predator
	 */
	protected PredatorPolicy predPolicy;
	/**
	 * The acting agent for the prey
	 */
	protected AbstractPrey preyPolicy;
	
	/**
	 * Run the simulator
	 */
	public void run(int runs) {
		
		double avg = 0.0;
		int i = 0;
		int x = 0;
		ArrayList<Integer> runlist = new ArrayList<Integer>();
		while(i < runs) {
			x = 0;
			this.reset();
			while(running) {
				currentState = transition(currentState);
				x++;
			}
			avg += x;
			runlist.add(x);
			running = true;
			i++;
		}
		avg = avg / runs;
		double temp = 0;
		for(double run: runlist){
			temp += (run - avg)*(run - avg);
		}
		System.out.println("Average: " + avg + ", standard deviation: " + Math.sqrt(temp / runs));
	}
	
	/**
	 * Advance the state
	 * @param state - the current state
	 * @return - the new state
	 */
	public HuntState transition(HuntState state) {
		// Update predator
		Position predatorAction = predPolicy.getAction(state);
		
		state = state.movePredator(predatorAction);
		
		// Check for end state
		if (state.isTerminal()) {
			running = false;
		} else {
			// Update prey
			Position preyAction = preyPolicy.getAction(state);
			state = state.movePrey(preyAction);
		}
		
		return state;
	}

	/**
	 * Update the state
	 * @param state - the new state 
	 */
	public void setStartState(HuntState state) {
		this.startState = state;
		this.reset();
	}
	
	public void reset(){
		this.currentState = this.startState;
	}

	/**
	 * Give an agent a reward for an action
	 * @param action - the action the agent took
	 * @return the reward and next state after the action
	 */
	public StateAndRewardObservation movePredator(Position action) {
		double reward = 0;
		
		HuntState nextState = currentState.copy().movePredator(action);
		if (nextState.isTerminal() && nextState.predatorWins()) {
			reward = 10;
		} else {
			Position preyAction = this.preyPolicy.getAction(nextState.copy());
			nextState = nextState.movePrey(preyAction);
		}
		
		this.currentState = nextState.copy();
		return new StateAndRewardObservation(nextState, reward);
	}
	
	/**
	 * Set the policy for the predator
	 * @param predatorPolicy - the policy
	 */
	public void setPredatorPolicy(PredatorPolicy predatorPolicy) {
		this.predPolicy = predatorPolicy;
	}

	/**
	 * Set the prey agent
	 * @param randomPrey - the agent
	 */
	public void setPrey(AbstractPrey prey) {
		this.preyPolicy = prey;
	}

}

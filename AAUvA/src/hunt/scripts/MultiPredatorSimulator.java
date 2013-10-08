package hunt.scripts;

import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.MultiPredatorState;
import hunt.model.Predator;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

import java.util.ArrayList;
import java.util.List;

public class MultiPredatorSimulator {
	
	/**
	 * Is the simulator still running
	 */
	private boolean running = true;
	/**
	 * The current state of the grid world
	 */
	protected MultiPredatorState currentState, startState;
	
	/**
	 * The deciding policy for the predator
	 */
	protected List<Predator> predators;
	/**
	 * The acting agent for the prey
	 */
	protected AbstractPrey preyPolicy;
	
	public MultiPredatorSimulator() {
		predators = new ArrayList<Predator>();
	}
	
	/**
	 * Run the simulator
	 */
	public void run(int runs) {
		assert(currentState != null);
		
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
	public MultiPredatorState transition(MultiPredatorState state) {
		// Get predator actions
		List<Position> predatorActions = new ArrayList<Position>();
		for (int i = 0; i < this.predators.size(); i++) {
			predatorActions.add(this.predators.get(i).getAction(state));
		}
		// Get prey action
		Position preyAction = preyPolicy.getAction(state);
		
		// Apply predator actions
		for (int i = 0; i < predatorActions.size(); i++) {
			Predator actingPredator = this.predators.get(i);
			state = state.movePredator(actingPredator.getName(), predatorActions.get(i));
		}
		// Apply prey action
		state = state.movePrey(preyAction);
		
		// Check for end state
		if (state.isTerminal()) {
			running = false;
		}
		
		// TODO: calculate rewards and new states
		StateAndRewardObservation predatorObservation = null;
		for (int i = 0; i < this.predators.size(); i++) {
			this.predators.get(i).giveObservation(predatorObservation);
		}
		StateAndRewardObservation preyObservation = null;
		this.preyPolicy.giveObservation(preyObservation);
		
		return state;
	}

	/**
	 * Update the state
	 * @param state - the new state 
	 */
	public void setStartState(MultiPredatorState state) {
		this.startState = state;
		this.reset();
	}
	
	public void reset(){
		this.currentState = this.startState;
	}

	/**
	 * Add a predator to the system
	 * @param predatorPolicy - the policy
	 */
	public void addPredator(Predator predator) {
		this.predators.add(predator);
	}

	/**
	 * Set the prey agent
	 * @param randomPrey - the agent
	 */
	public void setPrey(AbstractPrey prey) {
		this.preyPolicy = prey;
	}

}

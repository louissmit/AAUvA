package hunt.scripts;

import hunt.model.AbstractPrey;
import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.MultiPredatorState;
import hunt.model.Predator;
import hunt.model.StateActionPair;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiPredatorSimulator {
	
	/**
	 * Is the simulator still running
	 */
	private boolean running = true;
	/**
	 * The current state of the grid world
	 */
	protected BasicMPState currentState, startState;
	
	/**
	 * The deciding policy for the predator
	 */
	protected List<Predator> predators;
	/**
	 * The acting agent for the prey
	 */
	protected AbstractPrey prey;
	protected boolean randomInitializationEachEpisode;
	protected double lastPredatorReward;
	protected List<BasicMPState>  allstates;
	private Utility util;
	
	public MultiPredatorSimulator(boolean _randomInitForEachEpisode) {
		this.randomInitializationEachEpisode=_randomInitForEachEpisode;
		predators = new ArrayList<Predator>();
		this.util = new Utility();
	}
	
	/**
	 * Run the simulator
	 */
	public void run(int runs) {
		assert(currentState != null);
		
		double avg = 0.0;
		int i = 0;
		int x = 0;
		int lastOnes = 0;
		int rewards=0;
		double stepSize = 100;
		ArrayList<Integer> runlist = new ArrayList<Integer>();
		util.setupSerializer("test");
		while(i < runs) {
			x = 0;
			this.reset();
			while(running) {
				currentState = transition(currentState);
				x++;
			}
			for (Predator pred : this.predators) {
				pred.updatePolicy();
			}
			if(i%stepSize==0)
				lastOnes=0;

			lastOnes+=x;
			rewards+=this.lastPredatorReward;
			if(i%stepSize==(stepSize-1))
			{
				lastOnes/=stepSize;
				rewards/=stepSize;
				//util.serializeEpisode(i+1, lastOnes);
				util.serializeEpisode(i+1, lastOnes,rewards);
				// System.out.println("Episode: "+(i+1)+" number of steps needed to catch the prey: "+lastOnes);
			}
			avg += x;
			runlist.add(x);
			running = true;
			i++;
		}
		util.closeSerializer();
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
	public BasicMPState transition(BasicMPState state) {
		// Get predator actions
		BasicMPState oldState = (BasicMPState)state.copy();

		Position action;
		Position preyAction = prey.getAction(oldState);
		state = state.movePrey(preyAction);
		for (Predator pred : this.predators) {
			action = pred.getAction(oldState);
			pred.UpdateLastStateActionPair(new StateActionPair(oldState.copy(), action.copy()));
			state = state.movePredator(pred.getName(), action);
		}

		// Check for end state
		double predatorreward = 0;
		double preyreward = 0;
		if (state.isTerminal()) {
			running = false;
			if(state.predatorWins()) predatorreward = 10;
			else if(state.predatorsCollide()) {
				predatorreward = -10;
				preyreward = 10;
			}
		
			this.lastPredatorReward=predatorreward;
		}
		
		StateAndRewardObservation predatorObservation = new StateAndRewardObservation(state, predatorreward);
		for (Predator pred : this.predators) {
			pred.giveObservation(predatorObservation);
		}
		StateAndRewardObservation preyObservation = new StateAndRewardObservation(state, preyreward);
		this.prey.giveObservation(preyObservation);
		
		return state;
	}

	/**
	 * Update the state
	 * @param state - the new state 
	 */
	public void setStartState(BasicMPState state) {
		this.startState = state;
		this.reset();
	}
	
	public void reset(){
		if(this.randomInitializationEachEpisode)
		{
			Random random=new Random();
			if(allstates==null)
				allstates=BasicMPState.getAllStatesInThisType(this.predators.size());
			int stateNumber=random.nextInt(allstates.size());
			this.currentState=(BasicMPState)allstates.get(stateNumber).copy();
		}
		else
		{
			this.currentState = this.startState;
		}
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
		this.prey= prey;
	}

}

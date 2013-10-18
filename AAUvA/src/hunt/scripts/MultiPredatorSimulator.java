package hunt.scripts;

import hunt.controller.Move;
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
	private double wins = 0;
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
	private Random generator;
	
	public MultiPredatorSimulator(boolean _randomInitForEachEpisode) {
		this.randomInitializationEachEpisode=_randomInitForEachEpisode;
		predators = new ArrayList<Predator>();
		this.util = new Utility();
		this.generator = new Random();
	}
	
	/**
	 * Run the simulator
	 */
	public void run(int runs) {
		assert(currentState != null);
		
		double avg = 0.0;
		int i = 0;
		int x = 0;
		double lastOnes = 0;
		double rewards=0;
		double stepSize = 10;
		ArrayList<Integer> runlist = new ArrayList<Integer>();
		util.setupSerializer("test"+this.predators.size());
		while(i < runs) {
			x = 0;
			this.reset();
			while(running) {
				currentState = transition(currentState);
				x++;
			}
			
			//predators.get(0).updatePolicy();
			
			if(i%stepSize==0)
			{
				rewards=0;
				lastOnes=0;
			}

			lastOnes+=x;
			rewards+=this.lastPredatorReward;
			if(i%stepSize==(stepSize-1))
			{
				lastOnes/=(stepSize);
				rewards/=(stepSize);
				//util.serializeEpisode(i+1, lastOnes);
				double winrate = this.wins / (i + 1);
				util.serializeEpisode(i+1, winrate ,rewards);
				
//				System.out.println(this.wins);
//				System.out.println(i+1);
//				System.out.println("winrate = " + this.wins / (i + 1));
//				System.out.println("Episode: "+(i+1)+" number of steps needed to catch the prey: "+lastOnes);
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
		if(generator.nextDouble() < 0.2) {
			preyAction = Move.WAIT;
		}
		state = state.movePrey(preyAction);
		prey.UpdateLastStateActionPair(new StateActionPair(oldState.copy(), preyAction.copy()));
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
			if(state.predatorWins()){
				this.wins += 1;
				predatorreward = 10;
			}
			if(state.predatorsCollide()) {
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
			/*
			Random random=new Random();
			if(allstates==null)
				allstates=BasicMPState.getAllStatesInThisType(this.predators.size());
			int stateNumber=random.nextInt(allstates.size());
			this.currentState=(BasicMPState)allstates.get(stateNumber).copy();
			*/
			this.currentState=BasicMPState.generateRandomState(this.predators.size());
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

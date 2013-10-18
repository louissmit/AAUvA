package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.PredatorInternalState;
import hunt.model.RelativeState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MultiAgentsRandomPolicy extends PlannerPredatorPolicy {
	
	private int numberOfPredators;
	
	public MultiAgentsRandomPolicy()
	{}
	
	public MultiAgentsRandomPolicy(int _numberOfPredators)
	{
		this.numberOfPredators=_numberOfPredators;
		this.probabilities = new HashMap<HuntState, HashMap<Position, Double>>();
		/*
		for (HuntState state : this.getAllStates()) {
			HashMap<Position, Double> distribution = new HashMap<Position, Double>();
			
			List<Position> actions = this.getActions(state);
			for (Position action : actions) {
				distribution.put(action, new Double(((double) 1) / actions.size()));
			}
			
			this.probabilities.put(state, distribution);
		}
		*/
		//****
			
	}
	
	@Override
	public List<HuntState> getAllStates() {
		
		return null;
		/*
		ArrayList<Position> allPositions=new ArrayList<Position>();
		ArrayList<HuntState> allStates=new ArrayList<HuntState>();
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				allPositions.add(new Position(i,j));
				PredatorInternalState state=new PredatorInternalState(new Position(i,j));
				allStates.add(state);
			}
		}
		
		ArrayList<Position> predators=new ArrayList<Position>();
		for(int i=1;i<numberOfPredators;i++)
		{
			ArrayList<HuntState> currentListStates=new ArrayList<HuntState>();
			for(Position position:allPositions)
			{
				for(HuntState state:allStates)
				{
					//BasicMPState newState=(BasicMPState) state.copy();
					//newState.putPredator(Integer.toString(i+1), position);
					PredatorInternalState newState=(PredatorInternalState)state.copy();
					newState.AddPredator(position);
					currentListStates.add(newState);
				}
			}
			allStates=currentListStates;
		}
		
		return allStates;
		*/
	}
	
	private void CreateRandomDistributionForState(HuntState state)
	{
		HashMap<Position, Double> distribution = new HashMap<Position, Double>();
		
		List<Position> actions = this.getActions(state);
		for (Position action : actions) {
			distribution.put(action, new Double(((double) 1) / actions.size()));
		}
		
		this.probabilities.put(state, distribution);
	}
	
	
	/**
	 * Return the probability of picking a certain action given a certain state
	 * @param oldState - the old state
	 * @param action - the action taken
	 * @return the probability of this combination occurring (policy-dependent)
	 */
	@Override
	public double getActionProbability(HuntState oldState, Position action) {
		if(!this.probabilities.containsKey(oldState))
		{
			this.CreateRandomDistributionForState(oldState);
		}
		return this.probabilities.get(oldState).get(action);
	}

	/**
	 * Decides on a move given a state
	 * @param s - the current state
	 * @return the move the predator will make
	 */
	@Override
	public Position getAction(HuntState s) {
		// Alowed actions
		
		if(!this.probabilities.containsKey(s))
		{
			this.CreateRandomDistributionForState(s);
		}
		HashMap<Position, Double> distribution=probabilities.get(s);
		List<Position> actions = this.getActions(s);
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
}

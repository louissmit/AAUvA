package hunt.model.predator;

import hunt.controller.Move;
import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.PredatorInternalState;
import hunt.model.QTable;
import hunt.model.RelativeState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MultiAgentsLearningPolicy extends LearningPredatorPolicy {
	
	private int numberOfPredators;
	private double epsilon;
	private List<HuntState> allStates;
	
	public MultiAgentsLearningPolicy(int _numberOfPredators,double __epsilon,List<HuntState> _allStates)
	{
		this.numberOfPredators=_numberOfPredators;
		this.epsilon=__epsilon;
		this.allStates=_allStates;
		this.probabilities = new HashMap<HuntState, HashMap<Position, Double>>();
		for (HuntState state : allStates) {
			HashMap<Position, Double> distribution = new HashMap<Position, Double>();
			
			List<Position> actions = this.getActions(state);
			for (Position action : actions) {
				distribution.put(action, new Double(((double) 1) / actions.size()));
			}
			
			this.probabilities.put(state, distribution);
		}
		//****
			
	}
	/*
	@Override
	public List<HuntState> getAllStates() {
		
		ArrayList<Position> allPositions=new ArrayList<Position>();
		ArrayList<HuntState> allStates=new ArrayList<HuntState>();
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				allPositions.add(new Position(i,j));
				PredatorInternalState state=new PredatorInternalState(new Position(i,j));
				allStates.add(state);
			}
		}
		
		for(int i=1;i<numberOfPredators;i++)
		{
			ArrayList<HuntState> currentListStates=new ArrayList<HuntState>();
			for(Position position:allPositions)
			{
				for(HuntState state:allStates)
				{
					PredatorInternalState newState=(PredatorInternalState)state.copy();
					newState.AddPredator(position);
					currentListStates.add(newState);
				}
			}
			allStates=currentListStates;
		}
		
		return allStates;
	}
	*/
	@Override
	public void setProbabilitiesWithQ(HuntState state,
			Map<Position, Double> QValues) {
		
		Set<Position> actions=QValues.keySet();
		Position bestAction=Move.NORTH;
		double max=-10000;
		for(Position action: actions)
		{
			double Qval=QValues.get(action);
			if(Qval>max)
			{
				bestAction=action;
				max=Qval;
			}
		}
		this.setAction(state, bestAction);
		
	}
	

	@Override
	public void setProbabilitiesWithQ(QTable qtable) {
		List<HuntState> states = this.allStates;
		for (HuntState state : states) {
			HashMap<Position, Double> result=qtable.getQValues(state);
			if(result.size()>0) 
				this.setProbabilitiesWithQ(state,result );
		}
		
	}
	
	/**
	 * Sets the epsilon-greedy action in a certain state
	 */
	public void setAction(HuntState state, Position action){
		HashMap<Position, Double> dist = this.probabilities.get(state);
		
		int countActions = dist.size();
		for (Position key: dist.keySet()) {
			// Divide epsilon over all actions minus the greedy action
			dist.put(key, epsilon / (countActions - 1));
		}
		dist.put(action, 1.0 - epsilon);
		this.probabilities.put(state, dist);
	}
}

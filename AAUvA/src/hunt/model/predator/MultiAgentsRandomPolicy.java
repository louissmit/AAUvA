package hunt.model.predator;

import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.RelativeState;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MultiAgentsRandomPolicy extends RandomPredatorPolicy {
	
	public static int numberOfPredators=2;
	
	public MultiAgentsRandomPolicy()
	{
		//this.numberOfPredators=_numberOfPredators;
		//*** Copied from the super() because calling super() is not possible after setting this variable
		generator = new Random();
		this.probabilities = new HashMap<HuntState, HashMap<Position, Double>>();
		for (HuntState state : this.getAllStates()) {
			HashMap<Position, Double> distribution = new HashMap<Position, Double>();
			
			List<Position> actions = this.getActions(state);
			for (Position action : actions) {
				distribution.put(action, new Double(((double) 1) / actions.size()));
			}
			
			this.probabilities.put(state, distribution);
		}
		//****
			
	}

	@Override
	public List<HuntState> getAllStates() {
		
		ArrayList<Position> allPositions=new ArrayList<Position>();
		ArrayList<HuntState> allStates=new ArrayList<HuntState>();
		for (int i = 0; i < Position.BWIDTH; i++) {
			for (int j = 0; j < Position.BHEIGHT; j++) {
				allPositions.add(new Position(i,j));
				BasicMPState state=new BasicMPState();
				state.putPredator("1", new Position(i,j));
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
					BasicMPState newState=(BasicMPState) state.copy();
					newState.putPredator(Integer.toString(i+1), position);
					currentListStates.add(newState);
				}
			}
			allStates=currentListStates;
		}
		
		return allStates;
	}
}

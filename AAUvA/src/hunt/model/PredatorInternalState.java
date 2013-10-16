package hunt.model;
import hunt.model.board.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Internal state representation for the predator.
 * Only used as index, so lacks most function implementations. 
 */
public class PredatorInternalState extends MultiPredatorState {

	private Position prey;
	private List<Position> predators;

	/**
	 * Constructor. Actually copies the values given to avoid hashing problems.
	 * @param prey
	 * @param predators
	 */
	public PredatorInternalState(Position prey, List<Position> predators) {
		this.prey = prey.copy();
		this.predators = new ArrayList<Position>();
		for (Position pred : predators) {
			this.predators.add(pred.copy());
		}
	}

	public PredatorInternalState(Position prey) {
		this.prey = prey.copy();
		this.predators = new ArrayList<Position>();
	}

	public void AddPredator(Position pred)
	{
		this.predators.add(pred.copy());
	}

	@Override
	public HuntState movePredator(Position action) {
		return null;
	}

	@Override
	public Position getPreyAction(HuntState oldState) {
		return null;
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public MultiPredatorState movePredator(String name, Position action) {
		return null;
	}

	@Override
	public MultiPredatorState movePrey(Position action) {
		return null;
	}

	@Override
	public MultiPredatorState copy() {
		return new PredatorInternalState(this.prey, this.predators);
	}

	@Override
	public boolean predatorWins() {
		return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PredatorInternalState [prey=" + prey + ", predators="
				+ predators + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for(Position distance:this.predators)
		{
			result = prime * result
					+ ((distance == null) ? 0 : distance.hashCode());
		}
		result = prime * result+ ((prey == null) ? 0 : prey.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PredatorInternalState))
			return false;
		PredatorInternalState other = (PredatorInternalState) obj;
		if(this.predators.size() != other.predators.size()) return false;
		int counter=0;
		for(Position localPos:this.predators)
		{
			for(Position otherPos:other.getPositions())
			{
				if(localPos.equals(otherPos))
					counter++;
			}
		}

		if(counter>=this.predators.size() && this.prey.equals(other.prey))
			return true;
		else
			return false;
	}
	
	public static List<HuntState> getAllStates(int numberOfPredators) {
		
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

	@Override
	public List<Position> getPositions() {
		return this.predators;		}

}

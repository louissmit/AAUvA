package hunt.model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hunt.model.board.Position;

public class BasicMPState extends MultiPredatorState {
	
	private Map<String, Position> predatorPositions;
	private final double randomChanceForWait=0.2;

	
	public BasicMPState() {
		predatorPositions = new HashMap<String, Position>();
	}

	/**
	 * @return the predatorPositions
	 */
	public Map<String, Position> getPredatorPositions() {
		return predatorPositions;
	}
	
	public Position getPredatorPosition(String name){
		return this.predatorPositions.get(name);
	}

	@Override
	public List<Position> getPositions()
	{
		return new ArrayList<Position>(this.predatorPositions.values());
	}

	@Override
	public boolean isTerminal() {
		ArrayList<Position> allPosition=new ArrayList<Position>();
		for(Position position: predatorPositions.values())
		{
			if(position.getX()==0 && position.getY()==0)
				return true;
			if(allPosition.contains(position))
				return true;
			else
				allPosition.add(position);
		}
		return false;
	}

	@Override
	public BasicMPState movePrey(Position action) {
		Random random=new Random();
		double randomNumber=random.nextDouble();
		if(randomNumber>=randomChanceForWait)
		{
			BasicMPState newState=new BasicMPState();
			for(String name:this.predatorPositions.keySet())
			{
				newState.putPredator(name, this.predatorPositions.get(name).copy().subtract(action));
			}
			return newState;
		}	
		else
			return (BasicMPState) this.copy();
	}

	@Override
	public MultiPredatorState copy() {
		BasicMPState newState=new BasicMPState();
		for(String name:this.predatorPositions.keySet())
		{
			newState.putPredator(name, this.predatorPositions.get(name).copy());
		}
		return newState;

	}

	@Override
	public boolean predatorWins() {
		ArrayList<Position> allPosition=new ArrayList<Position>();
		boolean preyCatched=false;
		for(Position position: predatorPositions.values())
		{
			if(position.getX()==0 && position.getY()==0)
				preyCatched=true;
			if(allPosition.contains(position))
				return false;
			else
				allPosition.add(position);
		}
		return preyCatched;
	}
	@Override
	public boolean predatorsCollide() {
		for(Position position: predatorPositions.values())
		{
			for(Position position2: predatorPositions.values())
			{
				if(position.getX()==position2.getX() && position.getY()==position2.getY())
					return true;
			}
		}
		return false;
	}

	@Override
	public BasicMPState movePredator(String name, Position action) {
		
		BasicMPState newState=(BasicMPState)this.copy();
		newState.putPredator(name, this.predatorPositions.get(name).copy().add(action));	
		return newState;
	}
	
	/*
	 * the same behavior as addPredator, only for clear and intuitive name
	 */
	public void putPredator(String name, Position position) {
		this.predatorPositions.put(name, position);
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for(Position distance:this.predatorPositions.values())
		{
			result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
		}
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
		if (!(obj instanceof BasicMPState))
			return false;
		BasicMPState other = (BasicMPState) obj;
		int counter=0;
		for(Position localPos:this.predatorPositions.values())
			for(Position otherPos:other.getPositions())
			{
				if(localPos.equals(otherPos))
					counter++;
			}
		if(counter>=this.predatorPositions.size())
			return true;
		else
			return false;
	}
	
	public static List<BasicMPState> getAllStatesInThisType(int numberOfPredators) {
		
		 ArrayList<Position> allPositions=new ArrayList<Position>();
         ArrayList<BasicMPState> allStates=new ArrayList<BasicMPState>();
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
                 ArrayList<BasicMPState> currentListStates=new ArrayList<BasicMPState>();
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
	
	public static List<HuntState> getAllStates(int numberOfPredators) {
		
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
	
	/**
	 * @deprecated
	 */
	@Override
	public HuntState movePredator(Position action) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated
	 */
	@Override
	public Position getPreyAction(HuntState oldState) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BasicMPState [predatorPositions=" + predatorPositions + "]";
	}



}

package hunt.model;

import java.awt.List;
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
	
	public void addPredator(String name, Position position) {
		this.predatorPositions.put(name, position);
	}
	
	public Map<String, Position> getPositions()
	{
		return this.predatorPositions;
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
	public MultiPredatorState movePrey(Position action) {
		Random random=new Random();
		double randomNumber=random.nextDouble();
		if(randomNumber>=randomChanceForWait)
		{
			BasicMPState newState=new BasicMPState();
			for(String name:this.predatorPositions.keySet())
			{
				newState.addPredator(name, this.predatorPositions.get(name).copy().subtract(action));
			}
			return newState;
		}	
		else
			return this.copy();
	}

	@Override
	public MultiPredatorState copy() {
		BasicMPState newState=new BasicMPState();
		for(String name:this.predatorPositions.keySet())
		{
			newState.addPredator(name, this.predatorPositions.get(name).copy());
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
	public MultiPredatorState movePredator(String name, Position action) {
		
		BasicMPState newState=(BasicMPState)this.copy();
		newState.replacePredator(name, this.predatorPositions.get(name).copy().add(action));	
		return newState;
	}
	
	/*
	 * the same behavior as addPredator, only for clear and intuitive name
	 */
	public void replacePredator(String name, Position position) {
		this.predatorPositions.put(name, position);
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
		if (!(obj instanceof RelativeState))
			return false;
		BasicMPState other = (BasicMPState) obj;
		int counter=0;
		for(Position localPos:this.predatorPositions.values())
			for(Position otherPos:other.getPositions().values())
			{
				if(localPos.equals(otherPos))
					counter++;
			}
		if(counter==this.predatorPositions.size())
			return true;
		else
			return false;
	}


}

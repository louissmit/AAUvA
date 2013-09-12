package hunt.scripts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import hunt.controller.HuntController;
import hunt.controller.Move;
import hunt.model.*;
import hunt.model.board.Position;

///Value Iteration- 1.4
public class ValueIteration {

	private AbstractPredator predator;
	private final double minTheta=0.01;
	private double gamma;

	public Hashtable<HuntState, Double> stateValues;
	public Hashtable<HuntState,Position> policy;
	
	public ValueIteration(AbstractPredator _predator,double _gamma)
	{
		this.predator=_predator;
		this.gamma=_gamma;
	}

	/**
	 * Calculates values for each state and stores them in the stateValues field
	 */
	public void Iterate()
	{
		List<HuntState> states=predator.getAllStates();
		for(int i=0;i<states.size();i++)
		{
			stateValues.put(states.get(i), 0.0);
		}
		
		double delta=0;
		while(!(delta<minTheta))
		{
			for(int i=0;i<states.size();i++)
			{
				HuntState localState=states.get(i);
				double oldValue=stateValues.get(localState);
				List<Double> valuesForAction=new ArrayList<Double>();
				double calculatedValue=CalculateValueForAction(Move.EAST, localState);
				valuesForAction.add(calculatedValue);
				
				calculatedValue=CalculateValueForAction(Move.WEST, localState);
				valuesForAction.add(calculatedValue);
				
				calculatedValue=CalculateValueForAction(Move.NORTH, localState);
				valuesForAction.add(calculatedValue);
				
				calculatedValue=CalculateValueForAction(Move.SOUTH, localState);
				valuesForAction.add(calculatedValue);
				
				calculatedValue=CalculateValueForAction(Move.WAIT, localState);
				valuesForAction.add(calculatedValue);
				
				double maxValue=Collections.max(valuesForAction);
				stateValues.put(localState, maxValue);
				delta=Math.max(delta, Math.abs(oldValue-maxValue));
			}
			
		}
	}
	
	private double CalculateValueForAction(Position move,HuntState state)
	{
		double value=0;
		List<HuntState> nextStates=predator.getNextStates(state, move);
		for(HuntState nextState:nextStates)
		{
			double probability=predator.getTransitionProbability(state,nextState,move);
			double reward=predator.getReward(state,nextState,move);
			value+=probability*(reward+gamma*stateValues.get(nextState));
		}
		return value;
	}
	
	/*
	 * Calculate optimal policy for current stateValues and saves result in the policy field.
	 * policy SHOULD be encapsulated to a separate class "Policy". Instances of predators
	 * should have reference to the Policy class.
	 */
	public void CalculateOptimalPolicyForCurrentValues()
	{
		List<HuntState> states=predator.getAllStates();
		for(int i=0;i<states.size();i++)
		{
			HuntState localState=states.get(i);

			double lastCalculatedValue=-10000;
			Position lastMove=Move.WAIT;
			
			double calculatedValue=CalculateValueForAction(Move.EAST, localState);
			if(calculatedValue>lastCalculatedValue)
			{
				lastMove=Move.EAST;
				lastCalculatedValue=calculatedValue;
			}
			
			calculatedValue=CalculateValueForAction(Move.WEST, localState);
			if(calculatedValue>lastCalculatedValue)
			{
				lastMove=Move.WEST;
				lastCalculatedValue=calculatedValue;
			}
			
			calculatedValue=CalculateValueForAction(Move.NORTH, localState);
			if(calculatedValue>lastCalculatedValue)
			{
				lastMove=Move.NORTH;
				lastCalculatedValue=calculatedValue;
			}
			
			calculatedValue=CalculateValueForAction(Move.SOUTH, localState);
			if(calculatedValue>lastCalculatedValue)
			{
				lastMove=Move.SOUTH;
				lastCalculatedValue=calculatedValue;
			}			
			policy.put(localState, lastMove);
		}
	}

	
}

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

public class ValueIteration {

	private HuntController controller;
	private final double minTheta=0.1;
	private double gamma;

	public Hashtable<HuntState, Double> stateValues;
	
	public ValueIteration(HuntController _controller,double _gamma)
	{
		this.controller=_controller;
		this.gamma=_gamma;
	}
	
	public void Iterate()
	{
		List<HuntState> states=HuntController.getAllStates();
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
	
	private double CalculateValueForAction(int move,HuntState state)
	{
		double value=0;
		List<HuntState> nextStates=controller.getNextStates(move,state);
		for(HuntState nextState:nextStates)
		{
			double probability=controller.getTransitionProbability(state,nextState,move);
			double reward=controller.getReward(state,nextState,move);
			value+=probability*(reward+gamma*stateValues.get(nextState));
		}
		return value;
	}

	
}

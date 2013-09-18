package hunt.scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import hunt.controller.Move;
import hunt.model.*;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

/**
 * Value iteration algorithm
 *
 */
public class ValueIteration {

	/**
	 * Policy used for probability and reward distributions
	 */
	private PredatorPolicy policy;
	/**
	 * Total amount of iterations ran
	 */
	private int numberOfIterations;
	/**
	 * Error threshold
	 */
	private final double minTheta=0.0001;
	/**
	 * Discount factor
	 */
	private double gamma;

	/**
	 * The values for states, calculated by the algorithm
	 */
	public Hashtable<HuntState, Double> stateValues;
	/**
	 * The optimal policy as determined by the algorithm
	 */
	public Hashtable<HuntState,Position> optimalPolicy;
	
	/**
	 * Initialize
	 * @param _policy - the policy to use for transition and reward functions
	 * @param _gamma - discount factor
	 */
	public ValueIteration(PredatorPolicy _policy,double _gamma)
	{
		this.policy=_policy;
		this.gamma=_gamma;
		stateValues=new Hashtable<HuntState,Double>();
		optimalPolicy=new Hashtable<HuntState,Position>();
	}

	/**
	 * Calculates values for each state and stores them in the stateValues field
	 */
	public void Iterate()
	{
		List<HuntState> states=policy.getAllStates();
		for(int i=0;i<states.size();i++)
		{
			stateValues.put(states.get(i), 0.0);
		}
		
		double delta=minTheta;
		int iterations=0;
		while(!(delta<minTheta))
		{
			delta=0;
			for(int i=0;i<states.size();i++)
			{
				HuntState localState=states.get(i);
				if(!localState.getPredatorPosition().isEqual(localState.getPreyPosition()))
				{
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
			iterations++;
		}
		this.numberOfIterations=iterations;
	}
	
	/**
	 * Calculate the value for a state-action pair
	 * @param move - the action
	 * @param state - the state
	 * @return the value for that state-action pair
	 */
	private double CalculateValueForAction(Position move,HuntState state)
	{
		double value=0;
		List<HuntState> nextStates=policy.getNextStates(state, move);
		for(HuntState nextState:nextStates)
		{
			double probability=policy.getTransitionProbability(state,nextState,move);
			double reward=policy.getReward(state,nextState,move);
			value+=probability*(reward+gamma*stateValues.get(nextState));
		}
		return value;
	}
	
	/**
	 * Calculate optimal policy for current stateValues and saves result in the policy field.
	 * policy SHOULD be encapsulated to a separate class "Policy". Instances of predators
	 * should have reference to the Policy class.
	 */
	public void CalculateOptimalPolicyForCurrentValues()
	{
		List<HuntState> states=policy.getAllStates();
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
			optimalPolicy.put(localState, lastMove);
		}
	}
	/**
	 * Get the amount of iterations required
	 * @return the amount of iterations
	 */
	public int getIterations()
	{
		return this.numberOfIterations;
	}

	
}
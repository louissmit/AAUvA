package hunt.scripts;
import hunt.controller.Move;
import hunt.model.HuntState;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.PlannerPredatorPolicy;
import hunt.model.predator.PredatorPolicy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QLearn {

	/**
	 * Policy used for probability and reward distributions
	 */
	private LearningPredatorPolicy policy;
	
	/**
	 * simulator of environment
	 */
	private Simulator simulator;
	/**
	 * Discount factor
	 */
	private double gamma;
	
	/**
	 * learning rate
	 */
	private double alpha;

	/**
	 * The values for states, calculated by the algorithm
	 */
	public Hashtable<HuntState, Hashtable<Position,Double>> stateActionValues;

	
	/**
	 * Initialize
	 * @param _policy - the policy to use for transition and reward functions
	 * @param _simulator - simulator 
	 * @param _gamma - discount factor
	 * @param _alpha - learning rate
	 * @param _initialization - start values for Q
	 */
	public QLearn(LearningPredatorPolicy _policy,Simulator _simulator,double _gamma,double _alpha,double initialization)
	{
		this.policy=_policy;
		this.simulator=_simulator;
		this.gamma=_gamma;
		this.alpha=_alpha;
		stateActionValues=new Hashtable<HuntState,Hashtable<Position,Double>>();
		List<HuntState> allStates=policy.getAllStates();
		for(HuntState state:allStates)
		{
			List<Position> actions=policy.getActions(state);
			Hashtable<Position,Double> actionsMap=new Hashtable<Position,Double>();
			for(Position action:actions)
			{
				actionsMap.put(action,initialization);
			}
			this.stateActionValues.put(state,actionsMap);
		}
	}

	/**
	 * Run simulation for one episode and returns number of steps required to catch the pray. It also updates policy in every step
	 */
	public int Iterate()
	{
		List<HuntState> states=policy.getAllStates();
		Random random=new Random();
		int randomStateNumber=random.nextInt(states.size());
		//int randomStateNumber=random.nextInt(4);
		HuntState currentState=states.get(randomStateNumber);
		simulator.setStartState(currentState);
		double reward=0;
		int numberOfIterations=0;
		while(!currentState.isTerminal())
		{
			Position action=policy.getAction(currentState);
			StateAndRewardObservation observation=simulator.movePredator(action);
			reward=observation.getReward();
			HuntState nextState=observation.getState();
			List<Position> actionsInNextStep=policy.getActions(nextState);
			
			Position maxAction=Move.WAIT;
			double maxNextValue=-100000;
			
			for(Position nextAction:actionsInNextStep)
			{
				double nextValue=this.stateActionValues.get(nextState).get(nextAction);
				if (nextValue>maxNextValue)
				{
					maxAction=nextAction;
					maxNextValue=nextValue;
				}
			}
			double currentValue=this.stateActionValues.get(currentState).get(action);
			double newValue=currentValue+this.alpha*(reward+this.gamma*maxNextValue-currentValue);
			this.stateActionValues.get(currentState).put(action,newValue );
			UpdatePolicy(currentState);
			currentState=nextState;
			numberOfIterations++;
		}	
		return numberOfIterations;
	}
	
	/**
	 * Updates 
	 */
	private void UpdatePolicy(HuntState state) {
		List<Position> possibleActions=policy.getActions(state);
		Map<Position,Double> QValues=new HashMap<Position, Double>();
		for(Position action:possibleActions)
		{
			double value=this.stateActionValues.get(state).get(action);
			QValues.put(action, value);
		}
		policy.setProbabilitiesWithQ(state,QValues);
		
	}

	/**
	 * returns policy of the predator
	 * @return
	 */
	public PredatorPolicy GetPolicy()
	{
		return this.policy;
	}
}

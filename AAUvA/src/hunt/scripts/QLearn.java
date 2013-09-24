package hunt.scripts;
import hunt.controller.Move;
import hunt.model.HuntState;
import hunt.model.StateAndAction;
import hunt.model.board.Position;
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
	private PlannerPredatorPolicy policy;
	/**
	 * Total amount of iterations ran
	 */
	private int numberOfIterations;
	
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
	public Hashtable<StateAndAction, Double> stateActionValues;

	
	/**
	 * Initialize
	 * @param _policy - the policy to use for transition and reward functions
	 * @param _gamma - discount factor
	 */
	public QLearn(PlannerPredatorPolicy _policy,Simulator _simulator,double _gamma,double _alpha)
	{
		this.policy=_policy;
		this.simulator=_simulator;
		this.gamma=_gamma;
		this.alpha=_alpha;
		stateActionValues=new Hashtable<StateAndAction,Double>();
	}

	/**
	 * Run simulation for one episode and returns number of steps required to catch the pray. It also updates policy in every step
	 */
	public int Iterate()
	{
		List<HuntState> states=policy.getAllStates();
		Random random=new Random();
		int randomStateNumber=random.nextInt()%states.size();
		HuntState currentState=states.get(randomStateNumber);
		simulator.setStartState(currentState);
		double reward=0;
		int numberOfIterations=0;
		while(!currentState.isTerminal())
		{
			Position action=policy.getAction(currentState);
			StateAndRewardObservation observation=simulator.Move(currentState,action);
			reward=observation.reward;
			HuntState nextState=observation.state;
			List<Position> actionsInNextStep=policy.getAction(nextState);
			
			Position maxAction=Move.WAIT;
			double maxNextValue=-100000;
			
			for(Position nextAction:actionsInNextStep)
			{
				StateAndAction currentStateAndAction=new StateAndAction(nextState, nextAction);
				double nextValue=this.stateActionValues.get(currentStateAndAction);
				if (nextValue>maxNextValue)
				{
					maxAction=nextAction;
					maxNextValue=nextValue;
				}
			}
			StateAndRewardObservation currentStateAction=new StateAndRewardObservation(currentState,action);
			double currentValue=this.stateActionValues.get(currentStateAction);
			double newValue=currentValue+this.alpha*(reward+this.gamma*maxNextValue-currentValue);
			this.stateActionValues.put(currentStateAction,newValue );
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
			StateAndAction stateAndAction=new StateAndAction(state, action);
			double value=this.stateActionValues.get(stateAndAction);
			QValues.put(action, value);
		}
		policy.setActionQ(state,QValues);
		
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

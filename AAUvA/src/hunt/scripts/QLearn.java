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

public class QLearn extends QGeneral{


	/**
	 * @param _policy
	 * @param _simulator
	 * @param _gamma
	 * @param _alpha
	 * @param initialization
	 */
	public QLearn(LearningPredatorPolicy _policy, Simulator _simulator,
			double _gamma, double _alpha, double initialization) {
		super(_policy, _simulator, _gamma, _alpha, initialization);
		// TODO Auto-generated constructor stub
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
}

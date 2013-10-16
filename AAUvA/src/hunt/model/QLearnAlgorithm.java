package hunt.model;

import hunt.controller.Move;
import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.MultiAgentsLearningPolicy;
import hunt.model.predator.MultiAgentsRandomPolicy;
import hunt.scripts.Simulator;

import java.util.List;

/**
 * Q-learning
 */
public class QLearnAlgorithm extends LearningAlgorithm {

	/** Table to store Q-values in */
	protected QTable qtable;
	
	
	/**
	 * Policy used for probability and reward distributions
	 */
	protected MultiAgentsLearningPolicy policy;
	
	/**
	 * simulator of environment
	 */
	protected Simulator simulator;
	/**
	 * Discount factor
	 */
	protected double gamma;
	
	/**
	 * learning rate
	 */
	protected double alpha;

	

	
	public QLearnAlgorithm(QTable qTable, MultiAgentsLearningPolicy asPolicy,
			double _gamma, double _alpha) 
	{
		this.policy= asPolicy;
		this.gamma=_gamma;
		this.alpha=_alpha;
		this.qtable = qTable;
	}

		
	/**
	 * Updates the Q-table
	 * @param oldStateAndAction - previous state and the action taken therein
	 * @param observation - observed new state and reward (must be instance of StateAndRewardObservation)
	 * @return the q-table
	 */
	public QTable update(StateActionPair oldStateAndAction, Object observation) {
		
		StateAndRewardObservation stateAndReward=(StateAndRewardObservation)observation;
		double reward=0;
		HuntState currectState=oldStateAndAction.getState();
		Position action=oldStateAndAction.getAction();
		reward=stateAndReward.getReward();
		HuntState nextState=stateAndReward.getState();
		List<Position> actionsInNextStep=policy.getActions(nextState);
		
		Position maxAction=Move.WAIT;
		double maxNextValue=-100000;
		
		for(Position nextAction:actionsInNextStep)
		{
			double nextValue=this.qtable.getQValue(nextState, nextAction);
			if (nextValue>maxNextValue)
			{
				maxAction=nextAction;
				maxNextValue=nextValue;
			}
		}
		double currentValue=this.qtable.getQValue(currectState, action);
		double newValue=currentValue+this.alpha*(reward+this.gamma*maxNextValue-currentValue);
		this.qtable.update(currectState, action, newValue); 
		
		return qtable;
	}
	
}

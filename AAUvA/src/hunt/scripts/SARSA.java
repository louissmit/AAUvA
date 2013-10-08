/**
 * 
 */
package hunt.scripts;

import java.util.List;
import java.util.Random;

import hunt.model.HuntState;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;

/**
 * @author louissmit
 *
 */
public class SARSA extends QGeneral{

	/**
	 * @param _policy
	 * @param _simulator
	 * @param _gamma
	 * @param _alpha
	 * @param initialization
	 */
	public SARSA(LearningPredatorPolicy _policy, Simulator _simulator,
			double _gamma, double _alpha, double initialization) {
		super(_policy, _simulator, _gamma, _alpha, initialization);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Run simulation for one episode and returns number of steps required to catch the pray. It also updates policy in every step
	 */
	public int Iterate()
	{
		int numberOfIterations=0;
		double reward=0;
		Random random=new Random();
		List<HuntState> states=policy.getAllStates();
		int randomStateNumber=random.nextInt(states.size());

		HuntState currentState = states.get(randomStateNumber);
		simulator.setStartState(currentState);
		Position action = policy.getAction(currentState);

		while(!currentState.isTerminal())
		{
			StateAndRewardObservation observation = simulator.movePredator(action);
			reward = observation.getReward();
			HuntState nextState = observation.getState();

			Position nextAction = policy.getAction(nextState);

			double nextValue = this.stateActionValues.get(nextState).get(nextAction);
			double currentValue=this.stateActionValues.get(currentState).get(action);

			double newValue=currentValue+this.alpha*(reward+this.gamma*nextValue-currentValue);
			this.stateActionValues.get(currentState).put(action,newValue );
			UpdatePolicy(currentState);
			currentState=nextState;
			action = nextAction;
			numberOfIterations++;
		}	
		return numberOfIterations;
	}

}

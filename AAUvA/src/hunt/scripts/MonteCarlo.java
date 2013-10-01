package hunt.scripts;

import hunt.model.HuntState;
import hunt.model.RelativeState;
import hunt.model.StateActionPair;
import hunt.model.StateAndRewardObservation;
import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.PredatorPolicy;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Monte Carlo script
 */
public class MonteCarlo extends QGeneral {
	
//	/**
//	 * Policy used for probability and reward distributions
//	 */
//	protected LearningPredatorPolicy policy;
//	
//	/**
//	 * simulator of environment
//	 */
//	protected Simulator simulator;
//	/**
//	 * Discount factor
//	 */
//	protected double gamma;

//	/**
//	 * The values for states, calculated by the algorithm
//	 */
//	public Hashtable<HuntState, Hashtable<Position,Double>> stateActionValues;

	/**
	 * Initialize
	 * @param _policy - the policy to use for transition and reward functions
	 * @param _simulator - simulator 
	 * @param _gamma - discount factor
	 * @param _alpha - unused
	 * @param _initialization - start values for Q
	 */
	public MonteCarlo(LearningPredatorPolicy _policy,Simulator _simulator,double _gamma, double _alpha, double initialization)
	{
		super(_policy, _simulator, _gamma, _alpha, initialization);
		this.policy=_policy;
		this.simulator=_simulator;
		this.gamma=_gamma;
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
	
	public int Iterate() {
		// Hashtable from states to actions to a list of returns
		this.simulator.setStartState(new RelativeState(new Position(5,5)));
		Hashtable<HuntState, Hashtable<Position,List<Double>>> returns = new Hashtable<HuntState, Hashtable<Position,List<Double>>>();
		List<StateActionPair> visitedStatesAndActions = new ArrayList<StateActionPair>();
		double totalReward = 0;
		
		while (!this.simulator.currentState.isTerminal()) {
			HuntState currentState = this.simulator.currentState;
			Position predatorMove = this.policy.getAction(currentState);
			StateAndRewardObservation observation = this.simulator.movePredator(predatorMove);
			totalReward += observation.getReward();
			visitedStatesAndActions.add(new StateActionPair(currentState.copy(), predatorMove.copy()));
//			System.out.println(this.simulator.currentState);
		}
		
		int episodeLength = visitedStatesAndActions.size();
		for (int i = 0; i < episodeLength; i++) {
			int exponent = episodeLength - i - 1;
			StateActionPair entry = visitedStatesAndActions.get(i);
			double reward = totalReward * (Math.pow(this.gamma, exponent));
			
			HuntState state = entry.getState();
			Position action = entry.getAction();
			
			if (!returns.containsKey(state)) {
				returns.put(state, new Hashtable<Position,List<Double>>());
			}
			if (!returns.get(state).containsKey(action)) {
				returns.get(state).put(action, new ArrayList<Double>());
			}
			returns.get(entry.getState()).get(entry.getAction()).add(new Double(reward));
		}
		
		for (HuntState state : returns.keySet()) {
			for (Position action : returns.get(state).keySet()) {
				double sum = 0;
				for (int i = 0; i < returns.get(state).get(action).size(); i++) {
					sum += returns.get(state).get(action).get(i);
				}
				
				double average = sum / returns.get(state).get(action).size();
				stateActionValues.get(state).put(action, new Double(average));
			}
			
			// Improvement
//			System.out.println(state);
//			System.out.println(stateActionValues.get(state));
			this.policy.setProbabilitiesWithQ(state, stateActionValues.get(state));
//			System.out.println(this.policy.getProbabilities().get(state));
		}
		
		return episodeLength;
	}

	/**
	 * returns policy of the predator
	 * @return
	 */
	public PredatorPolicy getPolicy()
	{
		return this.policy;
	}

}

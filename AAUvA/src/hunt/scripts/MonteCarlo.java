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
	
	protected Hashtable<HuntState, Hashtable<Position,Double>> returns;
	protected Hashtable<HuntState, Hashtable<Position, Integer>> visitedCount;
	
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
		returns = new Hashtable<HuntState, Hashtable<Position,Double>>();
		visitedCount = new Hashtable<HuntState, Hashtable<Position, Integer>>();
	}
	
	public int Iterate() {
		
		// Hashtable from states to actions to a list of returns
//		System.out.println("--- NEW EPISODE -----------------------------------------------------------------------------------------------------");
		this.simulator.setStartState(new RelativeState(new Position(5,5)));
		Hashtable<HuntState, Hashtable<Position,List<Double>>> newReturns = new Hashtable<HuntState, Hashtable<Position,List<Double>>>();
		List<StateActionPair> visitedStatesAndActions = new ArrayList<StateActionPair>();
		double totalReward = 0;
		
		while (!this.simulator.currentState.isTerminal()) {
			HuntState currentState = this.simulator.currentState;
//			System.out.println(this.simulator.currentState);
//			System.out.println(this.stateActionValues.get(this.simulator.currentState));
			Position predatorMove = this.policy.getAction(currentState.copy());
			StateAndRewardObservation observation = this.simulator.movePredator(predatorMove.copy());
			totalReward += observation.getReward();
			visitedStatesAndActions.add(new StateActionPair(currentState.copy(), predatorMove.copy()));
//			System.out.println(predatorMove);
		}
		
		int episodeLength = visitedStatesAndActions.size();
		for (int i = 0; i < episodeLength; i++) {
			int exponent = episodeLength - i - 1;
			StateActionPair entry = visitedStatesAndActions.get(i);
		
			double reward = totalReward * (Math.pow(this.gamma, exponent));
			
			HuntState state = entry.getState();
			Position action = entry.getAction();
			
			if (!newReturns.containsKey(state)) {
				newReturns.put(state, new Hashtable<Position,List<Double>>());
			}
			if (!newReturns.get(state).containsKey(action)) {
				newReturns.get(state).put(action, new ArrayList<Double>());
			}
			newReturns.get(entry.getState()).get(entry.getAction()).add(new Double(reward));
			
		}
		
		for (HuntState state : newReturns.keySet()) {
			for (Position action : newReturns.get(state).keySet()) {
				if (!returns.containsKey(state)) {
					returns.put(state, new Hashtable<Position,Double>());
					visitedCount.put(state, new Hashtable<Position,Integer>());
				}
				if (!returns.get(state).containsKey(action)) {
					returns.get(state).put(action, 0.0);
					visitedCount.get(state).put(action, 0);
				}
				
				double sum = this.returns.get(state).get(action) * this.visitedCount.get(state).get(action);
				for (int i = 0; i < newReturns.get(state).get(action).size(); i++) {
					sum += newReturns.get(state).get(action).get(i);
				}
				
				int newVisitedCount = this.visitedCount.get(state).get(action) + newReturns.get(state).get(action).size();
				double average = sum / newVisitedCount;
				stateActionValues.get(state).put(action, new Double(average));

				returns.get(state).put(action,new Double(average));
				visitedCount.get(state).put(action,new Integer(newVisitedCount));
			}
			
			// Improvement
//			System.out.println(state);
//			System.out.println(stateActionValues.get(state));
			this.policy.setProbabilitiesWithQ(state, stateActionValues.get(state));
//			System.out.println(this.policy.getProbabilities().get(state));
		}
		
//		System.out.println(episodeLength);
		
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

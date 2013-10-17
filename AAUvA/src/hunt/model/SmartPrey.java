/**
 * 
 */
package hunt.model;

import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.PredatorPolicy;

/**
 * @author louissmit
 *
 */
public class SmartPrey extends AbstractPrey {

	/** Action selection policy */
	protected PredatorPolicy policy;
	/** Learning algorithm */
	protected LearningAlgorithm learningAlg;
	/**
	 * @param policy
	 * @param learningAlg
	 * @param lastStateActionPair
	 */
	public SmartPrey(PredatorPolicy policy, LearningAlgorithm learningAlg) {
		this.policy = policy;
		this.learningAlg = learningAlg;
	}

	/** Most recently seen state and the action taken in that state */
	private StateActionPair lastStateActionPair;

	/* (non-Javadoc)
	 * @see hunt.model.AbstractPrey#getAction(hunt.model.HuntState)
	 */
	@Override
	public Position getAction(HuntState state) {
		// TODO Auto-generated method stub
		return policy.getAction(state);
	}

	/* (non-Javadoc)
	 * @see hunt.model.AbstractPrey#getProbabilityOfAction(hunt.model.HuntState, hunt.model.board.Position)
	 */
	@Override
	public double getProbabilityOfAction(HuntState state, Position action) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see hunt.model.AbstractPrey#giveObservation(hunt.model.StateAndRewardObservation)
	 */
	@Override
	public void giveObservation(StateAndRewardObservation preyObservation) {
		if (this.learningAlg != null && lastStateActionPair != null) {
			// TODO: convert returned state to local state ??
			QTable qtable = this.learningAlg.update(lastStateActionPair, preyObservation);
			if (this.policy instanceof LearningPredatorPolicy) {
				((LearningPredatorPolicy) this.policy).setProbabilitiesWithQ(qtable);
			}
		}
	}

}

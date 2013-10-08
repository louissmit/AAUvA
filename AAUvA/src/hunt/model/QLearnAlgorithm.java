package hunt.model;

/**
 * Q-learning
 */
public class QLearnAlgorithm extends LearningAlgorithm {

	/** Table to store Q-values in */
	protected QTable qtable;
	
	/**
	 * Instantiation
	 * @param qtable - Q-table
	 */
	public QLearnAlgorithm(QTable qtable) {
		this.qtable = qtable;
	}
	
	/**
	 * Updates the Q-table
	 * @param oldStateAndAction - previous state and the action taken therein
	 * @param observation - observed new state and reward (must be instance of StateAndRewardObservation)
	 * @return the q-table
	 */
	public QTable update(StateActionPair oldStateAndAction, Object observation) {
		// TODO: update Q-table
		return qtable;
	}
	
}

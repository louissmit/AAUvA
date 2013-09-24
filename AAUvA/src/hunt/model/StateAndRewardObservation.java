package hunt.model;

/**
 * Feedback from the environment for policies 
 */
public class StateAndRewardObservation {
	
	/** The state that has been observed */
	private HuntState state;
	/** The reward that was gotten */
	private double reward;

	/**
	 * Initialize
	 * @param state - the observed state
	 * @param reward - the obtained reward
	 */
	public StateAndRewardObservation(HuntState state, double reward) {
		this.state = state;
		this.reward = reward;
	}

	/**
	 * @return the state
	 */
	public HuntState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(HuntState state) {
		this.state = state;
	}

	/**
	 * @return the reward
	 */
	public double getReward() {
		return reward;
	}

	/**
	 * @param reward the reward to set
	 */
	public void setReward(double reward) {
		this.reward = reward;
	}

}

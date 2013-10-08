package hunt.model;

/**
 * Superclass for learning algorithms
 */
public abstract class LearningAlgorithm {

	public abstract QTable update(StateActionPair lastStateActionPair,
			Object observation);

}

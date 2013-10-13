package hunt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.PredatorPolicy;
import hunt.model.predator.SoftmaxPredatorPolicy;

/**
 * Predator object
 */
public class Predator {

	/** Action selection policy */
	protected PredatorPolicy policy;
	/** Learning algorithm */
	protected LearningAlgorithm learningAlg;
	/** Predator identifier */
	protected String name;

	/** Most recently seen state and the action taken in that state */
	private StateActionPair lastStateActionPair;
	
	/**
	 * Create a predator
	 * @param name - predator identifier
	 * @param policy - action selection policy
	 * @param alg - learning algorithm
	 */
	public Predator(String name, PredatorPolicy policy, LearningAlgorithm alg) {
		this.name = name;
		this.policy = policy;
		this.learningAlg = alg;
	}
	
	public Predator(String name, PredatorPolicy policy) {
		this.name = name;
		this.policy = policy;
		this.learningAlg = null;
		
	}

	/**
	 * Get an action for the given state 
	 * @param state
	 * @return
	 */
	public Position getAction(BasicMPState state) {
		PredatorInternalState localState = this.convertState(state);
		return policy.getAction(localState);
	}
	
	/**
	 * Give this predator an observation and update the Q-table and policy.
	 * @param observation
	 */
	public void giveObservation(StateAndRewardObservation observation) {
		if (this.learningAlg != null && lastStateActionPair != null) {
			// TODO: convert returned state to local state ??
			QTable qtable = this.learningAlg.update(lastStateActionPair, observation);
			if (this.policy instanceof LearningPredatorPolicy) {
				((LearningPredatorPolicy) this.policy).setProbabilitiesWithQ(qtable);
			}
		}
	}
	
	/**
	 * Convert a global state to a state from this predator's point of view
	 * @param relativeState "Positions of predators relative to prey"
	 * @return
	 */
	public PredatorInternalState convertState(BasicMPState relativeState) {
		Map<String, Position> predators = relativeState.getPredatorPositions();
		Position myPos = predators.get(this.name);
		predators.remove(this.name);
		Position prey = myPos.copy().negate();  

		for(Map.Entry<String, Position> pred : predators.entrySet()) {
			Position newPos = myPos.subtract(pred.getValue());
			newPos.negate();
			pred.setValue(newPos);
		}
		PredatorInternalState state = new PredatorInternalState(prey, new ArrayList<Position>(predators.values()));
		return state;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * Internal state representation for the predator.
	 * Only used as index, so lacks most function implementations. 
	 */
	public class PredatorInternalState extends MultiPredatorState {
		
		private Position prey;
		private List<Position> predators;
		
		/**
		 * Constructor. Actually copies the values given to avoid hashing problems.
		 * @param prey
		 * @param predators
		 */
		public PredatorInternalState(Position prey, List<Position> predators) {
			this.prey = prey.copy();
			this.predators = new ArrayList<Position>();
			for (Position pred : predators) {
				this.predators.add(pred.copy());
			}
		}

		@Override
		public HuntState movePredator(Position action) {
			return null;
		}

		@Override
		public Position getPreyAction(HuntState oldState) {
			return null;
		}

		@Override
		public boolean isTerminal() {
			return false;
		}

		@Override
		public MultiPredatorState movePredator(String name, Position action) {
			return null;
		}

		@Override
		public MultiPredatorState movePrey(Position action) {
			return null;
		}

		@Override
		public MultiPredatorState copy() {
			return new PredatorInternalState(this.prey, this.predators);
		}

		@Override
		public boolean predatorWins() {
			return false;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "PredatorInternalState [prey=" + prey + ", predators="
					+ predators + "]";
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#hashCode()
		 */
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((predators == null) ? 0 : predators.hashCode());
			result = prime * result + ((prey == null) ? 0 : prey.hashCode());
			return result;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#equals(java.lang.Object)
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof PredatorInternalState))
				return false;
			PredatorInternalState other = (PredatorInternalState) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (predators == null) {
				if (other.predators != null)
					return false;
			} else if (!predators.equals(other.predators))
				return false;
			if (prey == null) {
				if (other.prey != null)
					return false;
			} else if (!prey.equals(other.prey))
				return false;
			return true;
		}

		private Predator getOuterType() {
			return Predator.this;
		}
		
	}
	
}

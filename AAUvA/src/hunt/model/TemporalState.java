package hunt.model;

import hunt.model.board.Position;

/**
 * Smart state representation
 */
public class TemporalState implements HuntState {
	
	/**
	 * Relative distance of the prey compared to the predator
	 */
	private Position distance;
	
	/**
	 * Constructor
	 * @param distance - the initial distance
	 */
	public TemporalState(Position distance) {
		this.distance = distance;
	}

	@Override
	public boolean isTerminal() {
		return distance.equals(new Position(0,0));
	}

	@Override
	public HuntState movePredator(Position action) {
		return new TemporalState(distance.copy().subtract(action));
	}

	@Override
	public HuntState movePrey(Position action) {
		return new TemporalState(distance.copy().add(action));
	}

	@Override
	public HuntState copy() {
		return new TemporalState(this.distance.copy());
	}

	@Override
	public boolean predatorWins() {
		return distance.equals(new Position(0,0));
	}

	@Override
	public Position getPreyAction(HuntState oldState) {
		if (!(oldState instanceof TemporalState)) {
			throw new IllegalArgumentException("oldState must be of type TemporalState");
		}
		TemporalState state = (TemporalState) oldState;
		
		return this.distance.copy().subtract(state.getDistance());
	}

	/**
	 * Returns the distance to the prey
	 * @return the distance to the prey
	 */
	private Position getDistance() {
		return distance.copy();
	}

}

package hunt.model;

import java.util.ArrayList;
import java.util.List;

import hunt.controller.Move;
import hunt.model.board.Position;

public abstract class AbstractPrey {

	private Position position;

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public AbstractPrey setPosition(int x, int y) {
		this.position = new Position(x, y);
		return this;
	}
	
	/**
	 * Get the available actions in the given state
	 * @param s - the current state
	 * @return the actions the prey may take
	 */
	public List<Position> getActions(HuntState s) {
		List<Position> moves = new ArrayList<Position>();
		moves.add(Move.SOUTH);
		moves.add(Move.NORTH);
		moves.add(Move.WEST);
		moves.add(Move.EAST);
		moves.add(Move.WAIT);
		
		// Take only moves that do not lead to predators
		List<Position> result = new ArrayList<Position>();
		Position preyPosition = s.getPreyPosition();
		for (Position move : moves) {
			if (!preyPosition.copy().add(move).equals(s.getPredatorPosition())) {
				result.add(move);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns an action based on the current state
	 * @param s - the current state
	 * @return the chosen action for the prey
	 */
	public abstract Position getAction(HuntState s);

	/**
	 * Return the probability of taking a certain action given a certain state
	 * @param state - the current state
	 * @param action - the action
	 * @return the probability of taking action in state
	 */
	public abstract double getProbabilityOfAction(HuntState state,
			Position action);

}

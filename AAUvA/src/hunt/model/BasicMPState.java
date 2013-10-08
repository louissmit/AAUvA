package hunt.model;

import java.util.HashMap;
import java.util.Map;

import hunt.model.board.Position;

public class BasicMPState extends MultiPredatorState {
	
	private Map<String, Position> predatorPositions;
	
	public BasicMPState() {
		predatorPositions = new HashMap<String, Position>();
	}
	
	public void addPredator(String name, Position action) {
		this.predatorPositions.put(name, action);
	}

	@Override
	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultiPredatorState movePrey(Position action) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiPredatorState copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean predatorWins() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MultiPredatorState movePredator(String name, Position action) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated
	 */
	@Override
	public HuntState movePredator(Position action) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @deprecated
	 */
	@Override
	public Position getPreyAction(HuntState oldState) {
		// TODO Auto-generated method stub
		return null;
	}

}

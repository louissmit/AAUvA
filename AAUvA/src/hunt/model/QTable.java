package hunt.model;

import hunt.model.board.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for Q-values
 */
public class QTable {
	
	/** Q-table object */
	private Map<HuntState, HashMap<Position, Double>> table;

	/** Update the Q-table for a certain state */
	public void update(HuntState state, HashMap<Position, Double> values) {
		this.table.put(state, values);
	}
	public void update(HuntState state, Position action, Double value) {
		this.assertStatePresent(state);
		this.table.get(state).put(action, value);
	}
	public void update(StateActionPair stateActionPair, Double value) {
		this.update(stateActionPair.getState(), stateActionPair.getAction(), value);
	}
	
	/** Get Q-values for a certain state */
	public HashMap<Position, Double> getQValues(HuntState state) {
		this.assertStatePresent(state);
		return this.table.get(state);
	}
	public Double getQValue(HuntState state, Position action) {
		this.assertStateActionPresent(state, action);
		return this.table.get(state).get(action);
	}
	public Double getQValue(StateActionPair stateActionPair) {
		return this.getQValue(stateActionPair.getState(), stateActionPair.getAction());
	}
	
	/** Make sure the requested value is present */
	protected void assertStatePresent(HuntState state) {
		if (!this.table.containsKey(state)) {
			this.table.put(state, new HashMap<Position, Double>());
		}
	}
	protected void assertStateActionPresent(HuntState state, Position action) {
		this.assertStatePresent(state);
		if (!this.table.get(state).containsKey(action)) {
			this.table.get(state).put(action, 0.0);
		}
	}
	
}

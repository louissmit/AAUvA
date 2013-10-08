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
	
	/** Get Q-values for a certain state 
	 * @return */
	public HashMap<Position, Double> getQValues(HuntState state) {
		return this.table.get(state);
	}
	
	public Double getQValue(HuntState state, Position action) {
		return this.table.get(state).get(action);
	}
	
	public void update(HuntState state, Position action, Double value) {
		if (!this.table.containsKey(state)) {
			this.table.put(state, new HashMap<Position, Double>());
		}
		this.table.get(state).put(action, value);
	}
	
	public Double getQValue(StateActionPair stateActionPair) {
		// TODO implement
		return 0.0;
	}
	
	public void update(StateActionPair stateActionPair, Double value) {
		// TODO implement
	}
	
}

package hunt.model.predator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hunt.model.EvolutionaryPolicy;
import hunt.model.HuntState;
import hunt.model.board.Position;

public class EvolutionaryPredatorPolicy extends PredatorPolicy implements EvolutionaryPolicy {
	
	private Map<HuntState, Position> policy;
	private int predatorCount;
	
	public EvolutionaryPredatorPolicy(int predatorCount) {
		this.policy = new HashMap<HuntState, Position>();
	}

	@Override
	public EvolutionaryPolicy getInstance() {
		return new EvolutionaryPredatorPolicy(predatorCount);
	}

	@Override
	public void randomizeActions() {
		policy.clear();
		List<HuntState> allStates = this.getAllStates();
		for (int i = 0; i < allStates.size(); i++) {
			List<Position> allActions = this.getActions(allStates.get(i));
			Position action = allActions.get((int) (Math.random() * allActions.size()));
			policy.put(allStates.get(i).copy(), action.copy());
		}
	}

	@Override
	public List<HuntState> getAllStates() {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EvolutionaryPolicy breed(EvolutionaryPolicy partner,
			double mutationRate) {
		// TODO Auto-generated method stub
		return null;
	}

}

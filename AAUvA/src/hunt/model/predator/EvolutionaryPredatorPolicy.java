package hunt.model.predator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hunt.model.BasicMPState;
import hunt.model.EvolutionaryPolicy;
import hunt.model.HuntState;
import hunt.model.PredatorInternalState;
import hunt.model.board.Position;

public class EvolutionaryPredatorPolicy extends PredatorPolicy implements EvolutionaryPolicy {
	
	private Map<HuntState, Position> policy;
	private int predatorCount;
	private double probabilityOfMutation=0.01;
	
	public EvolutionaryPredatorPolicy(int predatorCount) {
		this.policy = new HashMap<HuntState, Position>();
		randomizeActions();
	}
	
	@Override
	public Position getAction(HuntState s) {
		return this.policy.get(s);
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
		
		return  PredatorInternalState.getAllStates(this.predatorCount);
	}

	@Override
	public EvolutionaryPolicy breed(EvolutionaryPolicy partner,
			double mutationRate) {
		
		Map<HuntState, Position> partnerPolicy=partner.getPolicy();
		Random random=new Random();
		for(HuntState hunt:this.policy.keySet())
		{
			double rand=random.nextDouble();
			if(rand<=this.probabilityOfMutation)
			{
				List<Position> actions=this.getActions(hunt);
				int index=random.nextInt(actions.size());
				this.policy.put(hunt,actions.get(index));
			}
			else
			{
				boolean fromMother=random.nextBoolean();
				if(fromMother)
					this.policy.put(hunt,partnerPolicy.get(hunt));
			}
		}
		return this;
	}

	@Override
	public Map<HuntState, Position> getPolicy() {
		return this.policy;
	}

}

package hunt.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import hunt.model.board.Position;
import hunt.model.predator.EvolutionaryPredatorPolicy;

public class EvolutionaryPreyPolicy extends AbstractPrey implements EvolutionaryPolicy {

	private Map<HuntState, Position> policy;
	private int predatorCount;
	private double probabilityOfMutation=0.05;
	
	
	public EvolutionaryPreyPolicy(int _predatorCount) {
		this.predatorCount=_predatorCount;
		this.policy=new HashMap<HuntState, Position>();
		randomizeActions();
	}
	

	@Override
	public Position getAction(HuntState s) {
		
		if(this.policy.containsKey(s))
			return policy.get(s);
		else 
			return null;
	}

	@Override
	public double getProbabilityOfAction(HuntState state, Position action) {
		
		if(this.policy.containsKey(state))
		{
			Position policyAction=policy.get(state);
			if(action==policyAction)
				return 1;
			else
				return 0;
		}
		return 0;
	}

	@Override
	public void giveObservation(StateAndRewardObservation preyObservation) {
		// TODO Auto-generated method stub
		
	}


	
	
	public EvolutionaryPreyPolicy() {
		this.policy = new HashMap<HuntState, Position>();
	}

	@Override
	public EvolutionaryPolicy getInstance() {
		return new EvolutionaryPreyPolicy(predatorCount);
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

	private List<HuntState> getAllStates() {
		
		return BasicMPState.getAllStates(this.predatorCount);
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

package hunt.model;

import hunt.model.board.Position;

public class EvolutionaryPreyPolicy extends AbstractPrey implements EvolutionaryPolicy {

	public EvolutionaryPreyPolicy(int predatorCount) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public EvolutionaryPolicy getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void randomizeActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Position getAction(HuntState s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getProbabilityOfAction(HuntState state, Position action) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void giveObservation(StateAndRewardObservation preyObservation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public EvolutionaryPolicy breed(EvolutionaryPolicy mother,
			double mutationRate) {
		// TODO Auto-generated method stub
		return null;
	}

}

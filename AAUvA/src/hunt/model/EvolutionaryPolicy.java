package hunt.model;
import hunt.model.board.Position;

import java.util.Map;

public interface EvolutionaryPolicy {

	EvolutionaryPolicy getInstance();

	void randomizeActions();

	EvolutionaryPolicy breed(EvolutionaryPolicy mother, double mutationRate);
	
	Map<HuntState, Position> getPolicy();

}

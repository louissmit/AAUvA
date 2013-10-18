package hunt.model;

public interface EvolutionaryPolicy {

	EvolutionaryPolicy getInstance();

	void randomizeActions();

	EvolutionaryPolicy breed(EvolutionaryPolicy mother, double mutationRate);

}

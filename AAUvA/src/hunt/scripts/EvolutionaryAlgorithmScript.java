package hunt.scripts;

import hunt.model.BasicMPState;
import hunt.model.EvolutionaryPolicy;
import hunt.model.EvolutionaryPreyPolicy;
import hunt.model.Predator;
import hunt.model.board.Position;
import hunt.model.predator.EvolutionaryPredatorPolicy;

import java.util.ArrayList;
import java.util.List;

public class EvolutionaryAlgorithmScript {
	private List<EvolutionaryPolicy> predatorPopulation;
	private List<EvolutionaryPolicy> preyPopulation;
	private int amountOfGenerations;
	private int maxEpisodeLength;
	private int populationSize;
	private int selectionSize;
	private int predatorCount;
	private List<Position> predatorPositions;
	
	// Intermediate storage for improved efficiency
	private List<Double> predatorEvaluations;
	private List<Double> preyEvaluations;
	private double averagePredatorEvaluation;
	private double averagePreyEvaluation;
	private List<EvolutionaryPolicy> intermediatePredatorPopulation;
	private List<EvolutionaryPolicy> intermediatePreyPopulation;
	private double mutationRate;
	
	public EvolutionaryAlgorithmScript(int amountOfGenerations, int episodeLength, int populationSize, int selectionSize, int predatorCount) {
		// Order in which predators are added
		this.predatorPositions = new ArrayList<Position>();
		predatorPositions.add(new Position(-5,-5));
		predatorPositions.add(new Position(5,5));
		predatorPositions.add(new Position(-5,5));
		predatorPositions.add(new Position(5,-5));
		
		this.amountOfGenerations = amountOfGenerations;
		this.maxEpisodeLength = episodeLength;
		this.populationSize = populationSize;
		this.selectionSize = selectionSize;
		this.predatorCount = predatorCount;
	}

	public void run() {
		this.initialize(predatorCount);
		
		for (int i = 0; i < amountOfGenerations; i++) {
			this.runEvaluation();
			this.runSelection(predatorPopulation, predatorEvaluations, averagePredatorEvaluation, intermediatePredatorPopulation);
			this.runSelection(preyPopulation, preyEvaluations, averagePreyEvaluation, intermediatePreyPopulation);
			this.runCrossoverAndMutation(intermediatePredatorPopulation, populationSize, predatorPopulation);
			this.runCrossoverAndMutation(intermediatePreyPopulation, populationSize, preyPopulation);
		}
	}

	private void initialize(int predatorCount) {
		this.initializePopulation(this.predatorPopulation, new EvolutionaryPredatorPolicy(predatorCount));
		this.initializePopulation(this.preyPopulation, new EvolutionaryPreyPolicy(predatorCount));
	}

	private void initializePopulation(List<EvolutionaryPolicy> population, EvolutionaryPolicy template) {
		population.clear();
		
		for (int i = 0; i < this.populationSize; i++) {
			EvolutionaryPolicy individual = template.getInstance();
			individual.randomizeActions();
			population.add(individual);
		}
	}

	private void runEvaluation() {
		// Initialization, empty previous values
		predatorEvaluations.clear();
		preyEvaluations.clear();
		
		for (int i = 0; i < populationSize; i++) {
			EvolutionaryPredatorPolicy predatorPolicy = (EvolutionaryPredatorPolicy) predatorPopulation.get(i);
			EvolutionaryPreyPolicy preyPolicy = (EvolutionaryPreyPolicy) preyPopulation.get(i);
			
			// Set up simulator
			MultiPredatorSimulator simulator = new MultiPredatorSimulator();
			BasicMPState startState = new BasicMPState();
			for (int j = 0; j < this.predatorCount; j++) {
				String predatorName = "Predator " + j;
				simulator.addPredator(new Predator(predatorName, predatorPolicy));
				startState.putPredator(predatorName, predatorPositions.get(j));
			}
			simulator.setPrey(preyPolicy);
			
			// Run simulator
			int episodeLength = 0;
			while (episodeLength < maxEpisodeLength && !simulator.currentState.isTerminal()) {
				episodeLength++;
				simulator.transition(simulator.currentState);
			}
			
			// Returns for the episode
			double predatorEpisodeReturn = 0;
			double preyEpisodeReturn = 0;
			if (simulator.currentState.isTerminal()) {
				if (simulator.currentState.predatorsCollide()) {
					predatorEpisodeReturn = -10;
					preyEpisodeReturn = 10; 
				} else {
					predatorEpisodeReturn = 10;
					preyEpisodeReturn = -10; 
				}
			}
			
			// Predator evaluation
			double predatorEvaluation = predatorEpisodeReturn / episodeLength;
			predatorEvaluations.add(predatorEvaluation);
			averagePredatorEvaluation += predatorEvaluation / populationSize;
			// Prey evaluation
			double preyEvaluation = preyEpisodeReturn / episodeLength;
			preyEvaluations.add(preyEvaluation);
			averagePreyEvaluation += preyEvaluation / populationSize;
		}
		
	}

	private void runSelection(List<EvolutionaryPolicy> population,
			List<Double> evaluations,
			double averageEvaluation,
			List<EvolutionaryPolicy> intermediatePopulation) {
		intermediatePopulation.clear();
		
		double selectionInterval = ((double) population.size()) / selectionSize;
		double stochasticSelection = Math.random() * selectionInterval;
		double selectionRemainder = stochasticSelection;
		
		for (int i = 0; i < population.size(); i++) {
			// Fitness value
			double fitness = evaluations.get(i) / averageEvaluation;
			
			// Add individuals while there are selectionIntervals inside the fitness region
			while (fitness > selectionInterval) {
				intermediatePopulation.add(population.get(i));
				fitness -= selectionInterval;
			}
			
			// If the fitness region encompasses the selectionRemainder, add one extra individual
			if (fitness > selectionRemainder) {
				intermediatePopulation.add(population.get(i));
			}
			
			// Decrease the selectionRemainder and make sure it remains positive
			selectionRemainder -= fitness;
			if (selectionRemainder < 0) {
				selectionRemainder += selectionInterval;
			}
		}
		
	}
	
	private void runCrossoverAndMutation(List<EvolutionaryPolicy> intermediatePopulation, int populationSize, List<EvolutionaryPolicy> targetPopulation) {
		targetPopulation.clear();
		
		for (int i = 0; i < populationSize; i++) {
			int fatherIndex = (int) (Math.random() * intermediatePopulation.size());
			int motherIndex = (int) (Math.random() * intermediatePopulation.size());
			EvolutionaryPolicy father = intermediatePopulation.get(fatherIndex);
			EvolutionaryPolicy mother = intermediatePopulation.get(motherIndex);
			
			EvolutionaryPolicy child = father.breed(mother, mutationRate);
			targetPopulation.add(child);
		}
	}

}

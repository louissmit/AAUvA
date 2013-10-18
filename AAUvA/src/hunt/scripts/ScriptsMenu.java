package hunt.scripts;

import hunt.model.AbsoluteState;
import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.MultiPredatorState;
import hunt.model.Predator;
import hunt.model.PredatorInternalState;
import hunt.model.QTable;
import hunt.model.RandomPrey;
import hunt.model.RelativeState;
import hunt.model.SmartPrey;
import hunt.model.board.Position;
import hunt.model.predator.*;
import hunt.model.QLearnAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Collection and basic user interface for different script created during the 
 * Autonomous Agents course 
 */
public class ScriptsMenu {

	/**
	 * Available commands
	 */
	protected List<Command> commands;

	/**
	 * Determines whether to continue running or not.
	 */
	protected boolean exit;

	private Utility util;
	/**
	 * Constructor, populates the list of commands
	 */
	public ScriptsMenu() {
		commands = new ArrayList<Command>();
		commands.add(new ExitCommand());
		commands.add(new SimulatorCommand());
		commands.add(new PolicyEvaluationCommand());
		commands.add(new PolicyIterationCommand());
		commands.add(new ValueIterationCommand());
		commands.add(new QLearnCommand());
		commands.add(new QLearnEpsilonCommand());
		commands.add(new SARSACommand());
//		commands.add(new MonteCarloCommand());
		util = new Utility();
		commands.add(new MonteCarloCommand());
		commands.add(new MultiPredatorSimulatorCommand());
		commands.add(new MultiPredatorQLearnSimulatorCommand());
		
	}

	/**
	 * Perform input-script execution cycles
	 */
	public void run() {
		exit = false;
		Scanner s = new Scanner(System.in);
		while (!exit) { 
			System.out.print("Select an action: ");
			String command = s.next();
			String argString = s.nextLine();
			String[] args = argString.split(" ");

			boolean found = false;
			for (int i = 0; i < commands.size(); i++) {
				if (command.toLowerCase().equals(commands.get(i).getCommand())) {
					commands.get(i).execute(args);
					found = true;
				}
			}

			if (!found) {
				System.out.println("Command not recognized");
			}
		}
		s.close();
	}

	/**
	 * Interface for available commands 
	 */
	private interface Command {
		/**
		 * The command
		 * @return the command that invokes this script
		 */
		public String getCommand(); 

		/**
		 * Execute this command
		 * @param args user-inputted arguments for the command. args[0] is equal to getCommand
		 */
		public void execute(String[] args);
	}
	
	/**
	 * Simulator with multiple predators
	 */
	private class MultiPredatorSimulatorCommand implements Command {

		@Override
		public String getCommand() {
			return "multipred";
		}



		@Override
		public void execute(String[] args) {
			List<String> argList = Arrays.asList(args);
			int numberOfAgents=1;
			if(argList.size()>1)
			{
				int index=1;
				//int index=argList.indexOf("-numberofagents") + 1;
				//if(index>0)
				numberOfAgents= Integer.parseInt(argList.get(index));
			}
			if(numberOfAgents>0&&numberOfAgents<=4)
			{
				MultiAgentsRandomPolicy asPolicy = new MultiAgentsRandomPolicy(numberOfAgents);
				MultiPredatorSimulator sim = new MultiPredatorSimulator(false);
				BasicMPState startState=new BasicMPState();
				for(int i=0;i<numberOfAgents;i++)
				{
					Predator pred = new Predator(Integer.toString(i+1), asPolicy);
					sim.addPredator(pred);
				}
		
				startState.putPredator(Integer.toString(1), new Position(5, 5));
				if(numberOfAgents>1)
					startState.putPredator(Integer.toString(2), new Position(6, 6));
				if(numberOfAgents>2)
					startState.putPredator(Integer.toString(3), new Position(5, 6));
				if(numberOfAgents>3)
					startState.putPredator(Integer.toString(4), new Position(6, 5));
				


				sim.setPrey(new RandomPrey());
				sim.setStartState(startState);
				sim.run(100);
			}
		}
		
	}
	
	/**
	 * Simulator with multiple predators
	 */
	private class MultiPredatorQLearnSimulatorCommand implements Command {

		@Override
		public String getCommand() {
			return "multipredqlearn";
		}
		
		private double epsilon=0.1;
		private double gamma=0.9;
		private double alpha=0.5;

		@Override
		public void execute(String[] args) {
			List<String> argList = Arrays.asList(args);
			int numberOfAgents=1;
			if(argList.size()>1)
			{
				int index=1;
				//int index=argList.indexOf("-numberofagents") + 1;
				//if(index>0)
				numberOfAgents= Integer.parseInt(argList.get(index));
			}
			if(numberOfAgents>0&&numberOfAgents<=4)
			{
				MultiAgentsLearningPolicy asPolicy = new MultiAgentsLearningPolicy(numberOfAgents,epsilon);
				MultiPredatorSimulator sim = new MultiPredatorSimulator(true);
				BasicMPState startState=new BasicMPState();
				QTable qtable=new QTable();
				QLearnAlgorithm qlearn=new QLearnAlgorithm(qtable, asPolicy, gamma, alpha);
				for(int i=0;i<numberOfAgents;i++)
				{
					Predator pred = new Predator(Integer.toString(i+1), asPolicy,qlearn);
					sim.addPredator(pred);
				}
		
				startState.putPredator(Integer.toString(1), new Position(5, 5));
				if(numberOfAgents>1)
					startState.putPredator(Integer.toString(2), new Position(6, 6));
				if(numberOfAgents>2)
					startState.putPredator(Integer.toString(3), new Position(5, 6));
				if(numberOfAgents>3)
					startState.putPredator(Integer.toString(4), new Position(6, 5));
				
				MultiAgentsLearningPolicy policy = new MultiAgentsLearningPolicy(numberOfAgents,epsilon);
				QTable qTable=new QTable();
				QLearnAlgorithm q = new QLearnAlgorithm(qTable, policy, gamma, alpha);
				//TODO: need get rid of dependency of policy on internal predator state
				sim.setPrey(new RandomPrey());
				//sim.setPrey(new SmartPrey(policy, q));
				sim.setStartState(startState);
				sim.run(5000);
			}
		}
		
	}

	/**
	 * Stop running
	 */
	private class ExitCommand implements Command {

		public String getCommand() {
			return "exit";
		}

		public void execute(String[] args) {
			exit = true;
		}
	}

	/**
	 * Perform game simulations using random policies 
	 */
	private class SimulatorCommand implements Command {

		public String getCommand() {
			return "simulator";
		}

		public void execute(String[] args) {
			Simulator sim = new Simulator();
			HuntState startState = new AbsoluteState(new Position(5,5), new Position(0,0));
			sim.setStartState(startState);
			sim.setPredatorPolicy(new RandomPredatorPolicy());
			sim.setPrey(new RandomPrey());
			sim.run(100); 
		}
	}

	/**
	 * Perform policy evaluation of the random predator policy 
	 */
	private class PolicyEvaluationCommand implements Command {

		public String getCommand() {
			return "evaluate";
		}

		public void execute(String[] args) {
			boolean smartMode = Arrays.asList(args).contains("smart");
			// Timer
			long startTime = System.nanoTime();
			PolicyEvaluator eval = runEval(smartMode);
			long endTime = System.nanoTime();
			printResults(eval, startTime, endTime, smartMode);
		}
		public PolicyEvaluator runEval(boolean smartMode){
			PlannerPredatorPolicy policy;
			if (smartMode) {
				policy = new TemporalRandomPredatorPolicy();
			} else {
				policy = new RandomPredatorPolicy();
			}
			policy.setPrey(new RandomPrey());
			PolicyEvaluator eval = new PolicyEvaluator(policy);
			return eval.run();
		}
		public void printResults(PolicyEvaluator eval, long startTime, long endTime, boolean smartMode) {
			Map<HuntState, Double> result = eval.getValues();
			Position pos0_0 = new Position(0,0);
			Position pos2_3 = new Position(2,3);
			Position pos2_10 = new Position(2,10);
			Position pos5_4 = new Position(5,4);
			Position pos5_5 = new Position(5,5);
			Position pos10_0 = new Position(10,0);
			Position pos10_10 = new Position(10,10);

			List<AbsoluteState> states = new ArrayList<AbsoluteState>();
			states.add(new AbsoluteState(pos0_0, pos5_5));
			states.add(new AbsoluteState(pos2_3, pos5_4));
			states.add(new AbsoluteState(pos2_10, pos10_0));
			states.add(new AbsoluteState(pos10_10, pos0_0));
			states.add(new AbsoluteState(pos10_10, pos10_0));

			for (AbsoluteState state : states) {
				HuntState finalState = state;
				if (smartMode) {
					finalState = new RelativeState(state.getPreyPosition().copy().subtract(state.getPredatorPosition()));
				}
				System.out.println("Value for " + finalState.toString() + ": " + result.get(finalState));
			}

			System.out.println("Amount of iterations required: " + eval.getIterations());

			System.out.println("Time taken (nanoseconds): " + (endTime - startTime));
		}
	}

	/**
	 * Perform policy iteration of the random predator policy 
	 */
	private class PolicyIterationCommand extends PolicyEvaluationCommand {

		public String getCommand() {
			return "piterate";
		}
		public void execute(String[] args) {
			boolean smartMode = Arrays.asList(args).contains("smart");
			long startTime = System.nanoTime();

			PolicyEvaluator eval = super.runEval(smartMode);
			new PolicyIterator(eval).run();

			long endTime = System.nanoTime();
			System.out.println(eval.getIterations());
			util.printStates(eval.getValues(), smartMode);
			//			printResults(eval, startTime, endTime, smartMode);
		}
	}

	/**
	 * Perform value iteration for the random policy
	 */
	private class ValueIterationCommand implements Command {

		private boolean smartMode = false;
		private boolean simulatorTest=false;

		public String getCommand() {
			return "valueiteration";
		}

		public void execute(String[] args) {
			this.smartMode = Arrays.asList(args).contains("smart");
			this.simulatorTest = Arrays.asList(args).contains("test");

			double gamma=0.1;
			runValueIteration(gamma);
			gamma=0.5;
			runValueIteration(gamma);
			gamma=0.7;
			runValueIteration(gamma);
			gamma=0.9;
			runValueIteration(gamma);
		}

		/**
		 * Perform the value iteration algorithm
		 * @param gamma - the discount factor for this value iteration
		 */
		private void runValueIteration(double gamma) {
			PlannerPredatorPolicy policy;
			if (smartMode) {
				policy = new TemporalRandomPredatorPolicy();
			} else {
				policy = new RandomPredatorPolicy();
			}
			policy.setPrey(new RandomPrey());
			ValueIteration valIter = new ValueIteration(policy, gamma);

			// Timer
			long startTime = System.nanoTime();
			valIter.Iterate();
			long endTime = System.nanoTime();
			Map<HuntState, Double> result = valIter.stateValues;
			valIter.CalculateOptimalPolicyForCurrentValues();
			util.printStates(result, smartMode);

			System.out.println("Amount of iterations required for gamma"+gamma+": " + valIter.getIterations());

			System.out.println("Time taken (nanoseconds): " + (endTime - startTime));

			Position preyPos=new Position(5,5);
			if(this.simulatorTest)
			{
				System.out.println("Simulation using new policy: ");

				Simulator sim = new Simulator();
				HuntState state;
				Position predPos = new Position(0,0);
				if (smartMode) {
					state = new RelativeState(preyPos.copy().subtract(predPos)); 
					System.out.println(state);
				} else {
					state=new AbsoluteState(preyPos, predPos);
					System.out.println("Predator(" + predPos.toString() + "), Prey(" + preyPos.toString() + "):");
				}
				sim.setStartState(state);
				sim.setPredatorPolicy(valIter.GetPolicy());
				sim.setPrey(new RandomPrey());
				sim.run(100);
			}
		}
	}
	private abstract class QGeneralCommand implements Command {

		protected List<Double> alphaRates=new ArrayList<Double>(Arrays.asList(0.1,0.2,0.3,0.4,0.5));
		protected List<Double> discountFactors=new ArrayList<Double>(Arrays.asList(0.1,0.5,0.7,0.9));


		public void executeQ(QGeneral script, int numberOfIterations,String filename) {
			List<Integer> episodes=runQ(script, numberOfIterations,filename);
			double lastOnes=0;
			double avg=10;
			util.setupSerializer(filename);
			for(int i=0;i<episodes.size();i++)
			{
				if(i%avg==0)
					lastOnes=0;

				lastOnes+=episodes.get(i);

				if(i%avg==(avg-1))
				{
					lastOnes/=avg;
					util.serializeEpisode(i+1, lastOnes);
					// System.out.println("Episode: "+(i+1)+" number of steps needed to catch the prey: "+lastOnes);
				}
			}
			util.closeSerializer();
		}

		/**
		 * Perform the Q-Learn
		 * @param gamma - the discount factor for this value iteration
		 */
		private List<Integer> runQ(QGeneral script, int numberOfIteration,String name) {

			List<Integer> results=new ArrayList<Integer>();
			// Timer
			long startTime = System.currentTimeMillis();
			for(int i=0;i<numberOfIteration;i++)
			{
				int result = script.Iterate();
				results.add(result);
			}
			long endTime = System.currentTimeMillis();

			
			System.out.println("Time taken (milliseconds) for "+name+": " + (endTime - startTime));
			return results;
		}

	}
	/**
	 * Perform value iteration for the random policy
	 */
	private class QLearnCommand extends QGeneralCommand {

		private String policyId = "";

		public String getCommand() {
			return "qlearn";
		}

		public void execute(String[] args) {
			List<String> argList = Arrays.asList(args);
			int policyIndex = argList.indexOf("-policy") + 1;
			if (policyIndex > 0 && policyIndex < argList.size()) {
				policyId = argList.get(policyIndex);
			}
			
			double epsilon=0.1;
			for(double alpha:this.alphaRates)
			{
				for(double discountFactor:this.discountFactors)
				{
					int numberOfIterations=2000;
					LearningPredatorPolicy policy;
					if (policyId.equals("softmax")) {
						policy = new SoftmaxPredatorPolicy(0.1);
					} else {
						policy = new EpsilonGreedyPredatorPolicy(epsilon);
					}
					Simulator sim = new Simulator();
					sim.setPredatorPolicy(policy);
					sim.setPrey(new RandomPrey());
					QLearn qlearn = new QLearn(policy,sim,discountFactor,alpha,15);
					super.executeQ(qlearn, numberOfIterations,"qlearn"+Double.toString(alpha)+" "+Double.toString(discountFactor)+policyId);
				}
			}
		}

	}
	/**
	 * Perform value iteration for the random policy
	 */
	private class QLearnEpsilonCommand extends QGeneralCommand {



		public String getCommand() {
			return "qlearnepsilon";
		}

		public void execute(String[] args) {
			double[] epsilons = {0.1, 0.2};
			int[] qinits = {0, 5, 10, 10000};
			double alpha = 0.1;
			double gamma = 0.7;
			int numberOfIterations=10000;
			for(double epsilon: epsilons){
				for(int qinit: qinits){

					LearningPredatorPolicy policy = new EpsilonGreedyPredatorPolicy(epsilon);
					Simulator sim = new Simulator();
					sim.setPredatorPolicy(policy);
					sim.setPrey(new RandomPrey());
					QLearn qlearn = new QLearn(policy,sim,gamma,alpha, qinit);
					String name = "qlearnepsilon" + epsilon +" "+qinit;
					super.executeQ(qlearn, numberOfIterations, name);
				}

			}

		}

	}

	private class SARSACommand extends QGeneralCommand{
		public String getCommand() {
			return "sarsa";
		}

		public void execute(String[] args) {
			double epsilon=0.1;
			for(double alpha:this.alphaRates)
			{
				for(double discountFactor:this.discountFactors)
				{
					int numberOfIterations=2000;
					LearningPredatorPolicy policy;
					policy = new EpsilonGreedyPredatorPolicy(epsilon);
					Simulator sim = new Simulator();
					sim.setPredatorPolicy(policy);
					sim.setPrey(new RandomPrey());
					SARSA sarsa = new SARSA(policy,sim,discountFactor,alpha,15);
					super.executeQ(sarsa, numberOfIterations,"sarsa"+Double.toString(alpha)+" "+Double.toString(discountFactor));
				}
			}
		}
	}
	private class MonteCarloCommand extends QGeneralCommand{
		public String getCommand() {
			return "mc";
		}
		
		private String policyId = "";
		
		public void execute(String[] args) {
			List<String> argList = Arrays.asList(args);
			int policyIndex = argList.indexOf("-policy") + 1;
			if (policyIndex > 0 && policyIndex < argList.size()) {
				policyId = argList.get(policyIndex);
			}
			LearningPredatorPolicy policy;
			if (policyId.equals("softmax")) {
				policy = new SoftmaxPredatorPolicy(0.1);
			} else {
				policy = new EpsilonGreedyPredatorPolicy(0.1);
			}
			 
//			LearningPredatorPolicy policy = new SoftmaxPredatorPolicy(0.1);
//			policy.setProbabilities(new TemporalRandomPredatorPolicy().getProbabilities());
			
			Simulator sim = new Simulator();
			sim.setPredatorPolicy(policy);
			sim.setPrey(new RandomPrey());
//			sim.setStartState(new RelativeState(new Position(5,5)));
			
			double gamma=0.1;
			MonteCarlo mc = new MonteCarlo(policy,sim,gamma,0,0);
			super.executeQ(mc, 10000,"montecarlo"+policyId);
//			mc.runEpisode();
		}

	}
}


package hunt.scripts;

import hunt.model.AbsoluteState;
import hunt.model.HuntState;
import hunt.model.RandomPrey;
import hunt.model.TemporalState;
import hunt.model.board.Position;
import hunt.model.predator.*;

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
			sim.setState(startState);
			sim.setPredatorPolicy(new RandomPredatorPolicy());
			sim.setPrey(new RandomPrey());
			sim.run();
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
					finalState = new TemporalState(state.getPreyPosition().copy().subtract(state.getPredatorPosition()));
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
			new PolicyImprover(eval).run();

			long endTime = System.nanoTime();
			printResults(eval, startTime, endTime, smartMode);
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

			Position preyPos=new Position(5,5);
			for(int i=0;i<Position.BWIDTH;i++)
			{
				for(int j=0;j<Position.BHEIGHT;j++)
				{
					Position predPos = new Position(i,j);
					HuntState state;
					if (smartMode) {
						state = new TemporalState(preyPos.copy().subtract(predPos)); 
						System.out.println(state + ": " + result.get(state));
					} else {
						state=new AbsoluteState(preyPos, predPos);
						System.out.println("Predator(" + predPos.toString() + "), Prey(" + preyPos.toString() + "):" + result.get(state));
					}
				}
			}
			System.out.println("Amount of iterations required for gamma"+gamma+": " + valIter.getIterations());
			
			System.out.println("Time taken (nanoseconds): " + (endTime - startTime));
			
			if(this.simulatorTest)
			{
				System.out.println("Simulation using new policy: ");
				
				Simulator sim = new Simulator();
				HuntState state;
				Position predPos = new Position(0,0);
				if (smartMode) {
					state = new TemporalState(preyPos.copy().subtract(predPos)); 
					System.out.println(state);
				} else {
					state=new AbsoluteState(preyPos, predPos);
					System.out.println("Predator(" + predPos.toString() + "), Prey(" + preyPos.toString() + "):");
				}
				sim.setState(state);
				sim.setPredatorPolicy(valIter.GetPolicy());
				sim.setPrey(new RandomPrey());
				sim.run();
			}
		}
	}
}


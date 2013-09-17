package hunt.scripts;

import hunt.model.HuntState;
import hunt.model.RandomPrey;
import hunt.model.board.Position;
import hunt.model.predator.RandomPredatorPolicy;

import java.util.ArrayList;
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
			HuntState startState = new HuntState(new Position(5,5), new Position(0,0));
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
			PolicyEvaluator eval = new PolicyEvaluator(new RandomPredatorPolicy().setPrey(new RandomPrey()));
			eval.run();
			Map<HuntState, Double> result = eval.getValues();
			
			Position pos0_0 = new Position(0,0);
			Position pos2_3 = new Position(2,3);
			Position pos2_10 = new Position(2,10);
			Position pos5_4 = new Position(5,4);
			Position pos5_5 = new Position(5,5);
			Position pos10_0 = new Position(10,0);
			Position pos10_10 = new Position(10,10);
			
			List<HuntState> states = new ArrayList<HuntState>();
			states.add(new HuntState(pos0_0, pos5_5));
			states.add(new HuntState(pos2_3, pos5_4));
			states.add(new HuntState(pos2_10, pos10_0));
			states.add(new HuntState(pos10_10, pos0_0));
			states.add(new HuntState(pos10_10, pos10_0));
			
			for (HuntState state : states) {
				System.out.println("Value for " + state.toString() + ": " + result.get(state));
			}
			
			System.out.println("Amount of iterations required: " + eval.getIterations());
		}
		
	}
	
	/**
	 * Perform value iteration for the random policy
	 */
	private class ValueIterationCommand implements Command {

		public String getCommand() {
			return "valueiteration";
		}

		public void execute(String[] args) {
//			Board board = new Board(11, 11);
//			board.addPredator(new Predator(new RandomPredatorPolicy(board)),0,0);
//			board.setPrey(new RandomPrey(),5,5);
			
			double gamma=0.1;
			runValueIteration(gamma);
			gamma=0.5;
			runValueIteration(gamma);
			gamma=0.7;
			runValueIteration(gamma);
			gamma=0.9;
			runValueIteration(gamma);
		}

		private void runValueIteration(double gamma) {
			ValueIteration valIter = new ValueIteration(new RandomPredatorPolicy().setPrey(new RandomPrey()), gamma);
			valIter.Iterate();
			Map<HuntState, Double> result = valIter.stateValues;

			List<HuntState> states = new ArrayList<HuntState>();
//			Position preyPos=new Position(5,5);
			Position preyPos=new Position(2,2);
			for(int i=0;i<Position.BWIDTH;i++)
			{
				for(int j=0;j<Position.BHEIGHT;j++)
				{
					/*
					for(int k=0;k<board.getWidth();k++)
					{
						for(int l=0;l<board.getHeight();l++)
						{
							states.add(new HuntState(new Position(l,k), new Position(i,j)));
						}
					}
					*/
					states.add(new HuntState(preyPos, new Position(i,j)));
				}
			}
			for (HuntState state : states) {
				System.out.println("Value for " + state.toString() + ": " + result.get(state));
			}
			System.out.println("Amount of iterations required for gamma"+gamma+": " + valIter.getIterations());
		}

	}

}

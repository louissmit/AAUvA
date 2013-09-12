package hunt.scripts;

import hunt.model.HuntState;
import hunt.model.Predator;
import hunt.model.board.Board;
import hunt.model.board.Position;
import hunt.model.predator.RandomPredatorPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ScriptsMenu {

	protected List<Command> commands;
	
	protected boolean exit;
	
	public ScriptsMenu() {
		commands = new ArrayList<Command>();
		commands.add(new ExitCommand());
		commands.add(new PolicyEvaluationCommand());
	}

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
	
	private interface Command {
		public String getCommand(); 
		
		public void execute(String[] args);
	}
	
	private class ExitCommand implements Command {
		
		public String getCommand() {
			return "exit";
		}
		
		public void execute(String[] args) {
			exit = true;
		}
	}
	
	private class PolicyEvaluationCommand implements Command {

		public String getCommand() {
			return "evaluate";
		}

		public void execute(String[] args) {
			PolicyEvaluator eval = new PolicyEvaluator(new RandomPredatorPolicy(new Board(11, 11)));
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
			
			for (HuntState state : states) {
				System.out.println("Value for " + state.toString() + ": " + result.get(state));
			}
			
			System.out.println("Amount of iterations required: " + eval.getIterations());
		}
		
	}

}

package hunt.scripts;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScriptsMenu {

	protected List<Command> commands;
	
	protected boolean exit;
	
	public ScriptsMenu() {
		commands = new ArrayList<Command>();
		commands.add(new ExitCommand());
		commands.add(new IterativePolicyEvaluationCommand());
	}

	public void run() {
		exit = false;
		while (!exit) { 
			System.out.print("Select an action: ");
			Scanner s = new Scanner(System.in);
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
	
	private class IterativePolicyEvaluationCommand implements Command {

		public String getCommand() {
			return "evaluate";
		}

		public void execute(String[] args) {
		}
		
	}

}

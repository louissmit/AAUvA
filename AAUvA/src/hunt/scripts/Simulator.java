package hunt.scripts;

import hunt.model.AbstractPrey;
import hunt.model.HuntState;
import hunt.model.board.Position;
import hunt.model.predator.PredatorPolicy;

/**
 * Class in charge of running simulations in the grid world  
 */
public class Simulator {
	
	/**
	 * Is the simulator still running?
	 */
	private boolean running = true;
	/**
	 * The current state of the grid world
	 */
	protected HuntState currentState;
	
	/**
	 * The deciding policy for the predator
	 */
	protected PredatorPolicy predPolicy;
	/**
	 * The acting agent for the prey
	 */
	protected AbstractPrey preyPolicy;
	
	/**
	 * Run the simulator
	 */
	public void run() {
		int i = 0;
		
		while(running) {
			currentState = transition(currentState);
			System.out.println(currentState);
			i++;
		}
		System.out.println("Runs: " + i);
	
	/**
	 * Advance the state
	 * @param s - the current state
	 * @return - the new state
	 */
	public HuntState transition(HuntState s) {
		// Update predator
		Position predatorAction = predPolicy.getAction(s);
		
		s = new HuntState(s.getPreyPosition(), s.getPredatorPosition().add(predatorAction));
		
		// Check for end state
		if (s.isTerminal()) {
			running = false;
		} else {
			// Update prey
			Position preyAction = preyPolicy.getAction(s);
			s = new HuntState(s.getPreyPosition().add(preyAction), s.getPredatorPosition());
		}
		
		return s;
	}

	/**
	 * Update the state
	 * @param state - the new state 
	 */
	public void setState(HuntState state) {
		this.currentState = state;
	}

	/**
	 * Set the policy for the predator
	 * @param predatorPolicy - the policy
	 */
	public void setPredatorPolicy(PredatorPolicy predatorPolicy) {
		this.predPolicy = predatorPolicy;
	}

	/**
	 * Set the prey agent
	 * @param randomPrey - the agent
	 */
	public void setPrey(AbstractPrey prey) {
		this.preyPolicy = prey;
	}

}
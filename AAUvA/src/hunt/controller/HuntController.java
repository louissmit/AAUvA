package hunt.controller;

import hunt.model.HuntState;
import hunt.model.board.Board;
import hunt.model.board.Position;

public class HuntController {
	
	private Board board;
	private int runs = 400;
	boolean running = true;
	
	public HuntController(Board board) {
		this.board = board;
	}

	public void run() {
		int i = 0;
		
		HuntState currentState = board.getState();
		
		while(i < runs && running) {
			currentState = transition(currentState);
			System.out.println("Preditor("+board.getPredator().getPosition().toString()
					+", Prey("+board.getPrey().getPosition().toString()+")");
			i++;
		}
		System.out.println("runs: " + i);	}
	
	public HuntState transition(HuntState s) {
		
		board.movePredator();
		Position predatorPosition = board.getPredator().getPosition();
		Position preyPosition = board.getPrey().getPosition();
		
		if(predatorPosition.isEqual(preyPosition)){
			running = false;
		} else {
			board.movePrey();
		}
		
		HuntState sPrime = board.getState();
		
		return sPrime;
	}

}

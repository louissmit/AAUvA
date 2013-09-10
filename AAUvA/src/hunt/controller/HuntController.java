package hunt.controller;

import java.util.ArrayList;

import hunt.model.*;
import hunt.model.board.*;

public class HuntController {
	
	private Board board;
	private int runs = 100;
	boolean running = true;
	
	public HuntController(Board board) {
		this.board = board;
	}

	public void run() {
		int i = 0;
		HuntState currentState = new HuntState(
			new Position(0, 0),
			new Position(5, 5)
		);
		
		while(i < 0 && running) {
			currentState = transition(currentState);
			System.out.println("Preditor("+board.getPredatorPosition()+", Prey("+board.getPreyPosition()+")");
			System.out.println("runs: " + i);			i++;

		}
	}
	
	public HuntState transition(HuntState s) {
		
		Position newPredPos = board.getPredator().move(s);
		if(newPredPos.isEqual(board.getPreyPosition())) running = false;
		Position newPreyPos = board.getPrey().move(s);
		
		HuntState sPrime = new HuntState(newPreyPos, newPredPos);
		
		return sPrime;
	}

}

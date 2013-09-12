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
			System.out.println("Preditor("+board.getPredatorPosition().toString()+", Prey("+board.getPreyPosition().toString()+")");
			System.out.println("runs: " + i);			i++;
		}
	}
	
	public HuntState transition(HuntState s) {
		
		Position newPredPos = board.getPredator().move(s, board);
		Position newPreyPos = board.getPreyPosition();
		
		if(newPredPos.isEqual(board.getPreyPosition())){
			board.getPredator().giveReward();
			running = false;
		} else {
			newPreyPos = board.getPrey().move(s, board);
		}
		
		HuntState sPrime = new HuntState(newPreyPos, newPredPos);
		
		return sPrime;
	}

}

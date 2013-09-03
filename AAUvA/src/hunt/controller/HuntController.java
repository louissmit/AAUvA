package hunt.controller;

import hunt.model.*;

public class HuntController {
	
	private Board board;
	private int runs = 100;
	
	public HuntController(Board board) {
		this.board = board;
	}

	public void run() {
		int i = 0;
		boolean running = true;
		while(i < 0 && running) {
			List<Position> newpos = new List<Position>;
			
			for(Predator pred : board.getPredators()) {
				if(board.getPrey().getNextPosition() == pred.getNextPosition()) {
					running = false;
				}
			}
			i++;
		}
	}

}

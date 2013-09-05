package hunt.controller;

import hunt.model.*;

public class HuntController {
	
	private Board board;
	private int runs = 100;
	
	public HuntController(Board board) {
		this.board = board;
	}

	public void run() {
		for(int i = 0; i < runs; i++) {
			for(Predator pred : board.getPredators()) {
				if(board.getPrey().getNextPosition() != pred.getNextPosition()) {
					
				}
			}
		}
	}

}
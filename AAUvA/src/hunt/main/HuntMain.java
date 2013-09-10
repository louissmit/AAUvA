package hunt.main;

import hunt.controller.HuntController;
import hunt.model.*;
import hunt.model.board.Board;

public class HuntMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board(11, 11);
		board.addPredator(new RandomPredator(),0,0);
		board.setPrey(new RandomPrey(),5,5);
		HuntController cont = new HuntController(board);

		cont.run();

	}

}

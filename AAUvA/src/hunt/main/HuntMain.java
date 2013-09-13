package hunt.main;

import hunt.controller.HuntController;
import hunt.model.*;
import hunt.model.board.Board;
import hunt.model.predator.RandomPredatorPolicy;

public class HuntMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board = new Board(11, 11);
		board.addPredator(new Predator(new RandomPredatorPolicy()).setPosition(0, 0));
		board.setPrey(new RandomPrey().setPosition(5, 5));
		HuntController cont = new HuntController(board);

		cont.run();

	}

}

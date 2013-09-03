package hunt.main;

import hunt.controller.HuntController;
import hunt.model.*;

public class HuntMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HuntController cont = new HuntController();
		cont.addPredator(new RandomPredator(),0,0);
		cont.addPrey(new RandomPrey(),5,5);
		cont.run();

	}

}

package hunt.model.board;

import hunt.model.Predator;
import hunt.model.AbstractPrey;
import hunt.model.HuntState;

import java.util.List;

public class Board {
	
	private int width;
	private int height;
	
	private Predator predator;
	private AbstractPrey prey;
	private Position preyPosition;
	private Position predatorPosition;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void addPredator(Predator predator, int x, int y) {
		this.predatorPosition = new Position(x, y);
		this.predator = predator;
	}
	
	public void setPredators(Predator pred) {
		this.predator = pred; 
	}
	
	public void setPrey(AbstractPrey prey, int x, int y) {
		this.preyPosition = new Position(x, y);
		this.prey = prey;
	}
	
	public Predator getPredator() {
		return predator;
	}
	
	public AbstractPrey getPrey() {
		return prey;
	}	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Position getPreyPosition() {
		return preyPosition;
	}
	
	public Position getPredatorPosition() {
		return predatorPosition;
	}

	public HuntState getState() {
		// TODO Auto-generated method stub
		return new HuntState(preyPosition, predatorPosition);
	}
	
}

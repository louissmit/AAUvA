package hunt.model.board;

import hunt.model.AbstractPredator;
import hunt.model.AbstractPrey;

import java.util.List;

public class Board {
	
	private int width;
	private int height;
	
	private List<Position> predators;
	private Position prey;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void addPredator(AbstractPredator predator, int x, int y) {
		predators.add(new Position(x, y));
	}
	
	public void setPredators(List<Position> predators) {
		this.predators = predators; 
	}
	
	public void setPrey(AbstractPrey prey, int x, int y) {
		this.prey = new Position(x, y);
	}
	
	public List<Position> getPredators() {
		return predators;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Position getPreyPosition() {
		return prey;
	}

}

package hunt.model.board;

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
	
	public void addPredator(int x, int y) {
		predators.add(new Position(x, y));
	}
	
	public void setPredators(List<Position> predators) {
		this.predators = predators; 
	}
	
	public void setPrey(int x, int y) {
		prey = new Position(x, y);
	}
	
	public List<Position> getPredators() {
		return predators;
	}
	
	public Position getPrey() {
		return prey;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

}

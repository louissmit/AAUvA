package hunt.model.board;

import hunt.model.Predator;
import hunt.model.AbstractPrey;
import hunt.model.HuntState;

public class Board {
	
	private int width;
	private int height;
	
	private Predator predator;
	private AbstractPrey prey;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void addPredator(Predator predator) {
		this.predator = predator;
	}
	
	public void setPrey(AbstractPrey prey) {
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

	public HuntState getState() {
		// TODO Auto-generated method stub
		return new HuntState(predator.getPosition(), prey.getPosition());
	}
	
	public Position update(Position p, Position move) {
		int newX = clamp(p.getX() + move.getX(), this.getWidth());
		int newY = clamp(p.getY() + move.getY(), this.getHeight());
		return p.set(newX, newY);
	}
	
	private int clamp(int x, int n) {
		int res;
		if(x < 0) {
			res = x + n;
		} else {
			res = x % n;
		}	
		return res;
	}

	/**
	 * @return
	 */
	public void movePredator() {
		this.predator.move(this);
	}

	/**
	 * @return
	 */
	public void updatePredator(Position move) {
		this.update(this.predator.getPosition(), move);
	}
	/**
	 * 
	 */
	public void movePrey() {
		this.prey.move(this);
	}

	/**
	 * @param position
	 * @return
	 */
	public void updatePrey(Position move) {
		// TODO Auto-generated method stub
		this.update(this.prey.getPosition(), move);
	}
	
}

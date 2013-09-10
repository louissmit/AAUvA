package hunt.model;

import hunt.model.board.Position;

public class HuntState {
	
	private Position preyPosition;
	private Position predatorPosition;
	
	public HuntState(Position preyPosition, Position predatorPosition) {
		this.preyPosition = preyPosition;
		this.predatorPosition = predatorPosition;
	}
	
	public Position getPreyPosition() {
		return preyPosition;
	}
	
	public Position getPredatorPosition() {
		return predatorPosition;
	}
}


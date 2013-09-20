package hunt.scripts;

import java.util.Map;

import hunt.model.board.Position;
import hunt.model.AbsoluteState;
import hunt.model.HuntState;
import hunt.model.RelativeState;

/**
 * provides some useful functions
 *
 */
public class Utility {

	public static void printStates(Map<HuntState, Double> result, boolean smartMode){
		Position preyPos=new Position(5,5);
		for(int i=0;i<Position.BWIDTH;i++)
		{
			for(int j=0;j<Position.BHEIGHT;j++)
			{
				Position predPos = new Position(i,j);
				HuntState state;
				if (smartMode) {
					state = new RelativeState(preyPos.copy().subtract(predPos)); 
					System.out.println(state + ": " + result.get(state));
				} else {
					state=new AbsoluteState(preyPos, predPos);
					System.out.println("Predator(" + predPos.toString() + "), Prey(" + preyPos.toString() + "):" + result.get(state));
				}
			}
		}

	}
}

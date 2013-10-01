package hunt.scripts;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
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

	private BufferedWriter writer;


	/**
	 * 
	 */
	public Utility() {
		// TODO Auto-generated method stub

	}

	public void printStates(Map<HuntState, Double> result, boolean smartMode){
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

	public void setupSerializer(String fileName) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName + ".csv"), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	public void serializeEpisode(int episodeNr, double iterations){
		try {
			writer.append(episodeNr +", " + iterations);
			writer.newLine();
		} catch (IOException ex){
			// report
		} 
	}
	
	
	
	public void closeSerializer() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}

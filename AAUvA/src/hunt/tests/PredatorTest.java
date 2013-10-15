/**
 * 
 */
package hunt.tests;
import hunt.model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hunt.model.BasicMPState;
import hunt.model.HuntState;
import hunt.model.Predator;
import hunt.model.PredatorInternalState;
import hunt.model.QTable;
import hunt.model.board.Position;
import hunt.model.predator.LearningPredatorPolicy;
import hunt.model.predator.MultiAgentsRandomPolicy;
import hunt.model.predator.RandomPredatorPolicy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author louissmit
 *
 */
public class PredatorTest {

	/**
	 * @throws java.lang.Exception
	 * 
	 */
	private Predator p1;
	private BasicMPState startState;
	@Before
	public void setUp() throws Exception {
		RandomPredatorPolicy policy = new MultiAgentsRandomPolicy();
		this.p1 = new Predator("1", policy);
		Predator p2 = new Predator("2", policy);

		this.startState = new BasicMPState();
		startState.putPredator("1", new Position(2, 0));
		startState.putPredator("2", new Position(10, 5));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link hunt.model.Predator#convertState(hunt.model.BasicMPState)}.
	 */
	@Test
	public void testConvertState() {
		Position prey = new Position(9, 0);
		List<Position> predators = new ArrayList<Position>();
		predators.add(new Position(10, 5));

		PredatorInternalState desired = new PredatorInternalState(prey, predators);
		PredatorInternalState internalState = p1.convertState(startState);

		assertEquals(desired, internalState);
	}

}

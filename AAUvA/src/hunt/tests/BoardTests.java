package hunt.tests;

import static org.junit.Assert.*;
import hunt.controller.Move;
import hunt.model.board.Board;
import hunt.model.board.Position;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BoardTests {
	Position p1, p2, p3, p4, c1, c2, c3, c4, c5, c6;
	Board b;

	@Before
	public void setUp() throws Exception {
		//clamp data
		this.p1 = new Position(5, 10);
		this.p2 = new Position(5, 0);
		this.p3 = new Position(0, 10);
		this.p4 = new Position(10, 10);
		//adjacent data
		this.c1 = new Position(5, 5);
		this.c2 = new Position(5, 6);	
		this.c3 = new Position(5, 4);	
		this.c4 = new Position(4, 5);	
		this.b = new Board(11, 11);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClamp() {
//		b.update(Move.SOUTH, b);
//		assertTrue(p1.isEqual(new Position(5, 0)));
//		p2.update(Move.NORTH, b);
//		assertTrue(p2.isEqual(new Position(5, 10)));
//		p3.update(Move.WEST, b);
//		assertTrue(p3.isEqual(new Position(10, 10)));
//		p4.update(Move.EAST, b);
//		assertTrue(p4.isEqual(new Position(0, 10)));
	}
	
	@Test
	public void testAdjacent() {
		assertTrue(c1.isAdjacent(c2).isEqual(Move.SOUTH));	
		assertTrue(c1.isAdjacent(c3).isEqual(Move.NORTH));	
		assertTrue(c1.isAdjacent(c4).isEqual(Move.WEST));	
	}

}

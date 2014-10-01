package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PlaygroundTest {

	private Playground plygrnd;
	private Figure testFigure;
	private Figure[] testFigureArray;
	
	@Before
	public void setUp() {
		plygrnd = new Playground();
		for(int i = 0; i<4; i++) {
			plygrnd.addPlayer(new Player(i));
		}
//		plygrnd.addPlayer(new Player(0));
		
		testFigure = new Figure(0,plygrnd.getPlayer(0));

		testFigureArray = new Figure[40];

	}
	@Test
	public void test() {
		assertEquals(4, plygrnd.getAnzMit());
		plygrnd.setAnzMit(2);
		assertEquals(2, plygrnd.getAnzMit());
		plygrnd.setFigureOnPosition(null, 15);
		plygrnd.setFigureOnPosition(testFigure, 10);
		
		assertEquals(testFigure, plygrnd.getFigureOnPosition(10));
		assertEquals(null, plygrnd.getFigureOnPosition(5));
		assertFalse(plygrnd.isOccupied(0));
		assertTrue(plygrnd.isOccupied(10));
		testFigureArray[10] = testFigure;
		assertArrayEquals(testFigureArray, plygrnd.getFieldArray());
		for(int i = 0; i< 4; i++) {
			Player pl = plygrnd.getPlayer(i);
			for( int j=0;j<4;j++){
			plygrnd.storeFigure(pl.popFigure(), j);
			}
			plygrnd.getTargetArray(plygrnd.getPlayer(i).getPlayerID());
		}
		plygrnd.getTargetArray(5);
		
		
		
	}

}

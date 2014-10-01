package model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class PlayerTest {

	Player plyr0;
	Player plyr1;
	Player plyr2;
	Player plyr3;

	@Before
	public void setUp() {
		plyr0 = new Player(0);
		plyr1 = new Player(1);
		plyr2 = new Player(2);
		plyr3 = new Player(3);
	}
	

	@Test
	public void test() {
		assertEquals(0, plyr0.getPlayerID());
		assertEquals(0, plyr0.getStartField());
		
		plyr0.pushFigure(plyr0.getFigure(0));
		int x = plyr0.rolling();					
		assertTrue(x < 7 && x > 0);
		assertEquals(4, plyr0.getStackSize());
		assertFalse(plyr0.figureStackEmpty());
		for(int i=0; i< 5; i++){
			plyr0.popFigure();
		}
		assertTrue(plyr0.figureStackEmpty());
		plyr0.pushFigure(plyr0.getFigure(0));
		
		for(int i=0; i< 4; i++){
			plyr0.storeFigure(plyr0.getFigure(i));
		}
		plyr0.getPgFigureArray();
		assertFalse(plyr0.figureArrayEmpty());
		plyr0.removeFigureFromActiveSoldiers(plyr0.getFigure(0));
		assertTrue(plyr0.isFigureAvailable(0));
		assertFalse(plyr0.isFigureAvailable(4));
	}

}

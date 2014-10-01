package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class FigureTest {

	private Figure fig;
	private Player plyr;
	
	@Before
	public void setUp() {
		fig = new Figure(1, plyr = new Player(0));

	}
	
	
	@Test
	public void test() {
		assertEquals(0, fig.getWeglaenge());
		assertEquals(1, fig.getFigureID());
		assertEquals(0, fig.getFigurePos());
		fig.setFigurePos(10);
		assertEquals(10, fig.getFigurePos());
		fig.setWeglaenge(12);
		assertEquals(12, fig.getWeglaenge());
		fig.resetWegLaenge();
		assertEquals(0, fig.getWeglaenge());
		assertEquals(0, fig.getPlayerID());
		assertEquals(plyr, fig.hasPlayer());
		assertEquals(0, fig.getPlayerID());
		
		
	}

}

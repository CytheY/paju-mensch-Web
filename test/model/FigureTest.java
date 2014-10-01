package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class FigureTest {

	private Figure fig;
	private Player plyr;
	
	@Before
	public void setUp() {
		fig = new Figure(10, plyr);

	}
	
	
	@Test
	public void test() {
		assertEquals(0, fig.getWegLaenge());
		assertEquals(10, fig.getFigureID());
		
	}

}

package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {

	Player plyr;
	
	@Before
	public void setUp() {
		plyr = new Player("Bob", 2);

	}
	
	
	@Test
	public void test() {
		assertEquals("Bob" , plyr.getName());
		assertEquals(2, plyr.getPlayerID());
		
	}

}

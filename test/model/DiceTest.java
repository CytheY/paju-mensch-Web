package model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class DiceTest extends TestCase {

	
	private Dice dice;
	
	@Before
	public void setUp() {
		dice = new Dice();

	}
	
	@Test
	public void testRoll() {
		for (int i=0; i < 19900; i++) {
			int x = dice.roll();					
			assertTrue(x < 7 && x > 0);
		}
		
	}

}

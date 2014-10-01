package model;

public class Dice {
	
	private static final int WUERFELAUGEN = 6;
	private static final int MULTIPLIKATOR10 = 10;
	/*
	 * Würfle eine Zahl, die zwischen 1 und 6 ist
	 */
	public int roll() {
		return (int)(((Math.random()*MULTIPLIKATOR10) % WUERFELAUGEN)+1);
	}
}

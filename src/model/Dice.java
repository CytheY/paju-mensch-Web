package model;

public class Dice {
	
	private static final int WUERFELAUGEN = 6;
	private static final int MULTIPLIKATOR10 = 10;
	public int roll() {
		return (int)(((Math.random()*MULTIPLIKATOR10) % WUERFELAUGEN)+1);
	}
}

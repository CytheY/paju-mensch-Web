package de.paju.mensch.model;

public class Dice {
	
	private static final int WUERFEL_AUGEN = 6;
	private static final int MULTIPLIKATOR10 = 10;
	/*
	 * W�rfle eine Zahl, die zwischen 1 und 6 ist
	 */
	public int roll() {
		return (int)(((Math.random()*MULTIPLIKATOR10) % WUERFEL_AUGEN)+1);
	}
}

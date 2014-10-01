package model;

public class Dice {

	public int roll() {
		int x =  (int)(((Math.random()*10) % 6)+1);
		return x;
	}
}

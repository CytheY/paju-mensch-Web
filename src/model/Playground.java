package model;

import java.util.*;

import model.Figure;
import model.Player;

public class Playground {

	private int anzMit;
	private Figure fieldArray[];
	
	
	private Figure target1[];
	private Figure target2[];
	private Figure target3[];
	private Figure target4[];
	
	private LinkedList<Player> players = new LinkedList<Player>();
		
	public Playground() {
		this.fieldArray = new Figure[40];
		this.target1 = new Figure[4];
		this.target2 = new Figure[4];
		this.target3 = new Figure[4];
		this.target4 = new Figure[4];
		this.anzMit = 0;
	}

	public void addPlayer(Player newPlayer){		
		players.add(newPlayer);
		anzMit++;
	}
	public int getAnzMit() {
		return anzMit;
	}

	public void setAnzMit(int anzMit) {
		this.anzMit = anzMit;
	}
	
	public Figure getFigureOnPosition(int position){
		if(this.fieldArray[position] == null){
			return null;
		}
	    return this.fieldArray[position];
	}
	public void setFigureOnPosition(Figure fig, int position){
		if (fig != null)
			fig.setWegLaenge(position);
	    this.fieldArray[position] = fig;
	}
	
	public boolean isOccupied(int position) {
	    if(this.fieldArray[position] == null) {
		return false;
	    }
	    return true;
	}	
	
	
	public Player getPlayer(int playerID){
	    return (players.get(playerID)) ;
	}
	public Figure[] getFieldArray(){
		return fieldArray;
	}
	
}

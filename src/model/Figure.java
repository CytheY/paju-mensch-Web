package model;

public class Figure {
	private int wegLaenge;
	private int figureID;
	private Player player;
	
	public Figure(int figID,Player player) {
	    this.wegLaenge = 0;
	    this.figureID = figID;
	    this.player = player;
	};
	
	public int getFigurePos() {
		return this.wegLaenge;
	}
	public void setFigurePos(int position) {
		wegLaenge = position;
	}
	
	public void resetWegLaenge(){
	    this.wegLaenge = 0;
	}

	public int getFigureID() {
	    return figureID;
	}
	
	public Player hasPlayer(){
	    return this.player;
	}
	public void setWegLaenge(int w){
	    this.wegLaenge =  this.wegLaenge;
	}
	public int getWeglaenge(){
	    return this.wegLaenge;
	}

	
	
}

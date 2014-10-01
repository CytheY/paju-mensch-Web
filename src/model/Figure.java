package model;

public class Figure {
	private int wegLaenge;
	private int position;
	private int figureID;
	private Player player;
	
	public Figure(int figID,Player player) {
	    this.wegLaenge = 0;
	    this.figureID = figID;
	    this.player = player;
	};
	
	public int getFigurePos() {
		return this.position;
	}
	public void setFigurePos(int position) {
		this.position = position;
	}
	
	public void resetWegLaenge(){
	    this.wegLaenge = 0;
	}
	public int getWeglaenge(){
	    return this.wegLaenge;
	}
	public void setWeglaenge(int w){
	    this.wegLaenge += w;
	}

	public int getFigureID() {
	    return figureID;
	}
	
	public Player hasPlayer(){
	    return this.player;
	}
	
	public int getPlayerID() {
		return player.getPlayerID();
	}
	

	
	
}

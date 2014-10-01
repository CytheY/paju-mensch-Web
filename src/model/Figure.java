package model;

public class Figure {
	private int wegLaenge;
	private int position;
	private int figureID;
	private Player player;
	
	/*
	 * initialisiere Figur mit Standardwerte
	 */
	public Figure(int figID,Player player) {
	    this.wegLaenge = 0;
	    this.figureID = figID;
	    this.player = player;
	}
	
	/*
	 * Gebe aktuelle Postion der Figur zurück
	 */
	public int getFigurePos() {
		return this.position;
	}
	
	/*
	 * setze Figur auf Stelle "position" 
	 */
	public void setFigurePos(int position) {
		this.position = position;
	}
	
	/*
	 * setze WegLaenge einer Figur wieder auf 0
	 */
	public void resetWegLaenge(){
	    this.wegLaenge = 0;
	}
	
	/*
	 * Hole die WegLaenge einer Figur
	 */
	public int getWeglaenge(){
	    return this.wegLaenge;
	}
	
	/*
	 * setze Weglaenge auf Wert w
	 */
	public void setWeglaenge(int w){
	    this.wegLaenge += w;
	}

	/*
	 * gebe SpielFigurID zurück
	 */
	public int getFigureID() {
	    return figureID;
	}
	
	/*
	 * hat Figur einen Spieler
	 */
	public Player hasPlayer(){
	    return this.player;
	}
	
	/*
	 * gebe SpielerID zurück
	 */
	public int getPlayerID() {
		return player.getPlayerID();
	}
	

	
	
}

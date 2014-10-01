package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import model.Figure;
import model.Player;

public class Playground {

	private int anzMit;
	private Figure fieldArray[];
	private static final int FELDLAENGE = 40;
	private static final int ZIELFELDLAENGE = 4;

	private List<String> fieldCoordinatelist = new ArrayList<String>();
	
	private Figure target1[];
	private List<String> target0Coordinatelist = new ArrayList<String>();

	private Figure target2[];
	private List<String> target1Coordinatelist = new ArrayList<String>();

	private Figure target3[];
	private List<String> target2Coordinatelist = new ArrayList<String>();

	private Figure target4[];
	private List<String> target3Coordinatelist = new ArrayList<String>();

	private List<String> stackCoords = new ArrayList<String>();
	private List<Player> players = new LinkedList<Player>();
	
	/*
	 * initialisiere Spielfeld
	 */
	public Playground() {
		this.fieldArray = new Figure[FELDLAENGE];
		this.target1 = new Figure[ZIELFELDLAENGE];
		this.target2 = new Figure[ZIELFELDLAENGE];
		this.target3 = new Figure[ZIELFELDLAENGE];
		this.target4 = new Figure[ZIELFELDLAENGE];
		this.anzMit = 0;
	}
	
	/*
	 * lade alle Koordinaten des Spielfeldes ein (fuer GUI)
	 */
	public void addCoordinates() throws FileNotFoundException{
		// Feldkoordinaten

		FileReader fr = new FileReader("fieldCoords.txt");
		FileReader fr2 = new FileReader("stackCoords.txt");
		FileReader fr3 = new FileReader("targetCoords.txt");

		BufferedReader br = new BufferedReader(fr);
		BufferedReader br2 = new BufferedReader(fr2);
		BufferedReader br3 = new BufferedReader(fr3);

		
			
		
		
		String zeile = null;
		try {
			/*
			 * lese alle Spielfeld Koordinaten ein
			 */
			while((zeile = br.readLine()) != null){
				fieldCoordinatelist.add(zeile);
			}

			/*
			 * lese Stack koordianten der Spieler ein
			 */
			while((zeile = br2.readLine()) != null){
				if(zeile.contains("Spieler1")){
					stackCoords.add(zeile);
				}
				if(zeile.contains("Spieler2")){
					stackCoords.add(zeile);
					
				}
				if(zeile.contains("Spieler3")){
					stackCoords.add(zeile);
					
				}
				if(zeile.contains("Spieler4")){
					stackCoords.add(zeile);
				}
			}
			
			/*
			 * lese alle target Koordinaten der Spieler ein
			 */
			while((zeile = br3.readLine()) != null){
				if(zeile.contains("Spieler1")){
					target0Coordinatelist.add(zeile);
				}
				if(zeile.contains("Spieler2")){
					target1Coordinatelist.add(zeile);
					
				}
				if(zeile.contains("Spieler3")){
					target2Coordinatelist.add(zeile);
					
				}
				if(zeile.contains("Spieler4")){
					target3Coordinatelist.add(zeile);
				}
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	
		
		
	}
	
	/*
	 * liefere alle x,y Koordinaten der Spielfeldes zurück (als Liste)
	 */
	public List<String> getFieldCoordnates(){
		return fieldCoordinatelist;
	}
	/*
	 * liefere alle x,y Koordinaten des targetfeldes zurück (als Liste)
	 */
	public List<String> getTargetCoordnates(int playerID){
		switch(playerID){
		case Player.SPIELER1:
			return target0Coordinatelist;
		case Player.SPIELER2:
			return target1Coordinatelist;

		case Player.SPIELER3:
			return target2Coordinatelist;

		case Player.SPIELER4:
			return target3Coordinatelist;
		}
		return null;
	}
	
	/*
	 * liefere alle x,y Koordinaten des Stackfeldes zurück (als Liste)
	 */
	public List<String> getStackCoords(){
		return stackCoords;
	}
	
	/*
	 * Fuege Spieler hinzu und erhöhe die Anzahl der Mitspieler
	 */
	public void addPlayer(Player newPlayer){		
		players.add(newPlayer);
		anzMit++;
	}
	
	/*
	 * liefere die Anzahl der Mitspieler zurück
	 */
	public int getAnzMit() {
		return anzMit;
	}

	/*
	 * setze die Anzahl der Mitspieler auf anzMit
	 */
	public void setAnzMit(int anzMit) {
		this.anzMit = anzMit;
	}
	
	/*
	 * liefere Figur an Stelle position zurück
	 */
	public Figure getFigureOnPosition(int position){
		if(this.fieldArray[position] == null){
			return null;
		}
	    return this.fieldArray[position];
	}
	
	/*
	 * setze Figur auf Stelle position
	 */
	public void setFigureOnPosition(Figure fig, int position){
		if (fig != null) {
			fig.setFigurePos(position);
		}
	    this.fieldArray[position] = fig;
	}
	
	/*
	 * pruefe ob Feld an Stelle position belegt ist
	 * falls nein => false
	 */
	public boolean isOccupied(int position) {
	    if(this.fieldArray[position] == null) {
		return false;
	    }
	    return true;
	}	
	
	/*
	 * gebe Spieler mit SpielerID zurück
	 */
	public Player getPlayer(int playerID){
	    return players.get(playerID) ;
	}
	
	/*
	 * gebe Feld von Figuren des Spielfeldes zurück
	 */
	public Figure[] getFieldArray(){
		return fieldArray;
	}
	
	/*
	 * gebe Feld von Figuren des targetarray zurück (Spieler speziefisch)
	 */
	public Figure[] getTargetArray(int playerID){
		switch(playerID){
		case Player.SPIELER1:
			return target1;
		case Player.SPIELER2:
			return target2;
		case Player.SPIELER3:
			return target3;
		case Player.SPIELER4:
			return target4;
		default:
			return null;
		}
	}
	
	/*
	 * setz Spielfigur eines Spielers ins targetArray
	 */
	public void storeFigure(Figure fig, int storagePoint){
		int c = fig.getPlayerID();
		switch(c)
		{
		case Player.SPIELER1:
			target1[storagePoint] = fig;
			break;
		case Player.SPIELER2:
			target2[storagePoint] = fig;
			break;
		case Player.SPIELER3:
			target3[storagePoint] = fig;
			break;
		case Player.SPIELER4:
			target4[storagePoint] = fig;
			break;
		}
	}
}

package model;

import java.awt.Color;
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
	
	
	public Playground() {
		this.fieldArray = new Figure[FELDLAENGE];
		this.target1 = new Figure[ZIELFELDLAENGE];
		this.target2 = new Figure[ZIELFELDLAENGE];
		this.target3 = new Figure[ZIELFELDLAENGE];
		this.target4 = new Figure[ZIELFELDLAENGE];
		this.anzMit = 0;
	}
	
	public void addCoordinates() throws FileNotFoundException{
		// Feldkoordinaten
		FileReader fr = new FileReader("C:\\Temp\\EclipseWorkSpace\\mensch\\src\\model\\fieldCoords.txt");
		FileReader fr2 = new FileReader("C:\\Temp\\EclipseWorkSpace\\mensch\\src\\model\\stackCoords.txt");
		FileReader fr3 = new FileReader("C:\\Temp\\EclipseWorkSpace\\mensch\\src\\model\\targetCoords.txt");

		BufferedReader br = new BufferedReader(fr);
		BufferedReader br2 = new BufferedReader(fr2);
		BufferedReader br3 = new BufferedReader(fr3);

			
		
		
		String zeile = null;
		try {
			while((zeile = br.readLine()) != null){
				fieldCoordinatelist.add(zeile);
			}

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
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	
	public List<String> getFieldCoordnates(){
		return fieldCoordinatelist;
	}
	
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
	
	public List<String> getStackCoords(){
		return stackCoords;
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
		if (fig != null) {
			fig.setFigurePos(position);
		}
	    this.fieldArray[position] = fig;
	}
	
	public boolean isOccupied(int position) {
	    if(this.fieldArray[position] == null) {
		return false;
	    }
	    return true;
	}	
	
	public Player getPlayer(int playerID){
	    return players.get(playerID) ;
	}
	public Figure[] getFieldArray(){
		return fieldArray;
	}
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

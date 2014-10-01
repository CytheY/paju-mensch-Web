package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import model.Dice;
import model.Figure;

public class Player {

	private final int playerID;
	private Dice dice;
	private int startField;
	private static final int SPIELFIGUREN = 4;
	private static final int MAXSPIELFIGUREN = 4;
	private static final int STARTFELDSPIELER1 = 0;
	private static final int STARTFELDSPIELER2 = 10;
	private static final int STARTFELDSPIELER3 = 20;
	private static final int STARTFELDSPIELER4 = 30;
	public static final int SPIELER1 = 0;
	public static final int SPIELER2 = 1;
	public static final int SPIELER3 = 2;
	public static final int SPIELER4 = 3;

	private Deque<Figure> startStack = new ArrayDeque<Figure>();
	private Figure[] pgFigureArray = new Figure[SPIELFIGUREN];
	private List<String> stackCoords = new ArrayList<String>();

	public Player(int playerID) {
		this.playerID = playerID;
		/*
		 * gebe der playerID "4" Spielfiguren und pushe sie auf Stack
		 */
		for (int i = SPIELFIGUREN - 1; i >= 0; i--) {
			startStack.push(new Figure(i, this));
		}
		this.dice = new Dice();

		/*
		 * Startfeld der einzelnen Spieler wird initialisiert
		 */
		
		switch (playerID) {
		case SPIELER1:
			this.startField = STARTFELDSPIELER1;
			break;
		case SPIELER2:
			this.startField = STARTFELDSPIELER2;
			break;
		case SPIELER3:
			this.startField = STARTFELDSPIELER3;
			break;
		case SPIELER4:
			this.startField = STARTFELDSPIELER4;
			break;
		default:
		}

	}

	/*
	 * Hole aktuelle SpielerZahl
	 */
	
	public int getPlayerID() {
		return this.playerID;
	}


	/*
	 * gebe List von Strings mit allen Stack koordinaten zurück
	 */
	
	public List<String> getStackCoords(){
		return stackCoords;
	}
	
	/*
	 * Hole Figur vom eigenen Stack herunter
	 */
	
	public Figure popFigure() {
		Figure tmp;
		// Keine Figuren mehr im Startfeld!
		if (startStack.isEmpty()) {
			return null;
		}
		tmp = startStack.pop();
		pgFigureArray[tmp.getFigureID()] = tmp;
		return tmp;
	}

	/*
	 * lege Figur zurück auf Stack, und setze Weglaenge wieder auf 0
	 */
	
	public void pushFigure(Figure figure) {
		if (startStack.size() == MAXSPIELFIGUREN) {
			return;
		}
		figure.resetWegLaenge();
		pgFigureArray[figure.getFigureID()] = null;
		startStack.push(figure);
		return;
	}

	/*
	 * prüfe ob Stack leer ist, falls ja => true
	 */
	public boolean figureStackEmpty() {
		if (startStack.isEmpty()) {
			return true;
		}
		return false;
	}

	
	public boolean figureArrayEmpty() {
		for (int i = 0; i < pgFigureArray.length; i++) {
			if (pgFigureArray[i] == null) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	/*
	 * Hole die Groeße des SpielerStacks
	 */
	public int getStackSize() {
		return startStack.size();
	}
	
	/*
	 * Würfle
	 */
	public int rolling() {
		return dice.roll();
	}

	/*
	 * Hole Startposition des Spielers
	 */
	public int getStartField() {
		return this.startField;
	}

	/*
	 * Hole Figur an Stelle figureID
	 */
	public Figure getFigure(int figureID) {
		return pgFigureArray[figureID];
	}

	/*
	 * setze Figur in targetfeld
	 */
	public void storeFigure(Figure fig) {
		for (int i = 0; i < pgFigureArray.length; i++) {
			if (pgFigureArray[i] == null) {
				pgFigureArray[i] = fig;
				return;
			}
		}
	}

	/*
	 * Figur vom Spielfeld löschen
	 */
	public void removeFigureFromActiveSoldiers(Figure fig) {
		pgFigureArray[fig.getFigureID()] = null;
		return;
	}

	/*
	 * gebe feld mit alle Figuren zurück
	 */
	public Figure[] getPgFigureArray() {
		return pgFigureArray;
	}

	/*
	 * gülte Spielfigur nummer überprüfen
	 */
	public boolean isFigureAvailable(int figID) {
		if (figID > SPIELFIGUREN - 1 || pgFigureArray[figID] == null){
			return false;
		}
		return true;
	}

}

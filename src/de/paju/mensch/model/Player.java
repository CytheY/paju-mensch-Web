package de.paju.mensch.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import de.paju.mensch.model.Dice;
import de.paju.mensch.model.Figure;

public class Player {

	private final int playerID;
	private Dice dice;
	private int startField;
	private static final int SPIEL_FIGUREN = 4;
	private static final int MAX_SPIEL_FIGUREN = 4;
	private static final int START_FELD_SPIELER1 = 0;
	private static final int START_FELD_SPIELER2 = 10;
	private static final int START_FELD_SPIELER3 = 20;
	private static final int START_FELD_SPIELER4 = 30;
	public static final int SPIELER1 = 0;
	public static final int SPIELER2 = 1;
	public static final int SPIELER3 = 2;
	public static final int SPIELER4 = 3;

	private Deque<Figure> startStack = new ArrayDeque<Figure>();
	private Figure[] pgFigureArray = new Figure[SPIEL_FIGUREN];
	private List<String> stackCoords = new ArrayList<String>();

	public Player(int playerID) {
		this.playerID = playerID;
		/*
		 * gebe der playerID "4" Spielfiguren und pushe sie auf Stack
		 */
		for (int i = SPIEL_FIGUREN - 1; i >= 0; i--) {
			startStack.push(new Figure(i, this));
		}
		this.dice = new Dice();

		/*
		 * Startfeld der einzelnen Spieler wird initialisiert
		 */
		
		switch (playerID) {
		case SPIELER1:
			this.startField = START_FELD_SPIELER1;
			break;
		case SPIELER2:
			this.startField = START_FELD_SPIELER2;
			break;
		case SPIELER3:
			this.startField = START_FELD_SPIELER3;
			break;
		case SPIELER4:
			this.startField = START_FELD_SPIELER4;
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
	 * gebe List von Strings mit allen Stack koordinaten zur�ck
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
	 * lege Figur zur�ck auf Stack, und setze Weglaenge wieder auf 0
	 */
	
	public void pushFigure(Figure figure) {
		if (startStack.size() == MAX_SPIEL_FIGUREN) {
			return;
		}
		figure.resetWegLaenge();
		pgFigureArray[figure.getFigureID()] = null;
		startStack.push(figure);
		return;
	}

	/*
	 * pr�fe ob Stack leer ist, falls ja => true
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
	 * Hole die Groe�e des SpielerStacks
	 */
	public int getStackSize() {
		return startStack.size();
	}
	
	/*
	 * W�rfle
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
	 * Figur vom Spielfeld l�schen
	 */
	public void removeFigureFromActiveSoldiers(Figure fig) {
		pgFigureArray[fig.getFigureID()] = null;
		return;
	}

	/*
	 * gebe feld mit alle Figuren zur�ck
	 */
	public Figure[] getPgFigureArray() {
		return pgFigureArray;
	}

	/*
	 * g�lte Spielfigur nummer �berpr�fen
	 */
	public boolean isFigureAvailable(int figID) {
		if (figID > SPIEL_FIGUREN - 1 || pgFigureArray[figID] == null){
			return false;
		}
		return true;
	}

}

package model;

import java.util.*;

import model.Dice;
import model.Figure;

public class Player {

	private final int playerID;
	private Dice dice;
	private int startField;
	private int endField;

	private Stack<Figure> startStack = new Stack<Figure>();
	private Figure[] pgFigureArray = new Figure[4];

	public Player(int playerID) {
		this.playerID = playerID;
		for (int i = 4; i > 0; i--) {
			startStack.push(new Figure(i, this));
		}
		this.dice = new Dice();

		switch (playerID) {
		case 1:
			this.startField = 0;
		case 2:
			this.startField = 10;
		case 3:
			this.startField = 20;
		case 4:
			this.startField = 30;
		default:
		}
		this.endField = startField - 1;
	}

	public int getPlayerID() {
		return this.playerID;
	}

	public Figure popFigure() {
		Figure tmp;
		if (startStack.isEmpty()) {
			System.out.println("Keine Figuren mehr im Startfeld!");
			return null;
		}
		tmp = startStack.pop();
		pgFigureArray[tmp.getFigureID()] = tmp;
		return tmp;
	}

	public void pushFigure(Figure figure) {
		if (startStack.size() == 4) {
			System.out
					.println("Alle Figuren des Spielers sind bereits im Startfeld");
			return;
		}
		figure.resetWegLaenge();
		pgFigureArray[figure.getFigureID()] = null;
		startStack.push(figure);
		return;
	}

	public boolean figureStackEmpty() {
		if (startStack.isEmpty()) {
			return true;
		}
		return false;
	}

	public int getStackSize() {
		return startStack.size();
	}

	public int rolling() {
		return dice.roll();
	}

	public int getStartField() {
		return this.startField;
	}

	public Figure getFigure(int figureID) {
		return pgFigureArray[figureID];
	}

	public void storeFigure(Figure fig) {
		for (int i = 0; i < pgFigureArray.length; i++) {
			if (pgFigureArray[i] == null) {
				pgFigureArray[i] = fig;
				return;
			}
		}
	}

	public void printSoldiers() {
		for (int i = 0; i < 4; i++) {
			if (pgFigureArray[i] == null) {
				System.out.println(" null ");
			} else {
				System.out.println(" " + pgFigureArray[i].getFigureID());
			}
		}
	}
	
	public void hasFigure(Figure fig){
		
	}
}

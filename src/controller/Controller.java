package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.Figure;
import model.Player;
import model.Playground;
import observer.Observable;
import view.TextGUI;

public class Controller extends Observable {

	private Playground pg;
	private int activePlayerID;
	private int roll;
	private static final int MAXSPIELER = 4;
	private static final int MAXGEFAHRENEWEGLAENGE = 39;
	private static final int GEWUERFELTESECHS = 6;
	private static final int VORHANDENEFIGUREN = 4;
	private static final int ERSTESZIELFELD = 40;
	private static final int ZWEITESZIELFELD = 41;
	private static final int DRITTESZIELFELD = 42;
	private static final int VIERTESZIELFELD = 43;
	private static final int NULL = 0;
	private static final int EINS = 1;
	private static final int ZWEI = 2;
	private static final int DREI = 3;

	public Controller() {
		this.pg = new Playground();
	}

	public void init() throws NumberFormatException, IOException {

		Scanner in = new Scanner(System.in);
		System.out.println("Anzahl der Spieler eingeben");
		int pl = in.nextInt();
		System.out.println("pl ist " + pl);
		
		while (pl > MAXSPIELER ) {
			System.out.println("Maximale Spieleranzahl: 4, bitte eingeben.");
			pl = in.nextInt();
		}
		for (int i = 0; i < pl; i++) {
			pg.addPlayer(new Player(i));
		}
		try {
			pg.addCoordinates();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		runningGame(pl);
	}

	public void comingOut(int playerID) {
		int startField = pg.getPlayer(playerID).getStartField();
		Figure newFig = pg.getPlayer(playerID).popFigure();
		/**
		 * if startField is Occupied => occupied by Enemy
		 **/
		if (pg.isOccupied(startField)) {
			kickEnemyFigure(startField);
		}
		pg.setFigureOnPosition(newFig, startField);
	}

	public void kickEnemyFigure(int position) {
		Figure enemy = pg.getFigureOnPosition(position);
		enemy.resetWegLaenge();
		enemy.hasPlayer().pushFigure(enemy);
	}

	public void moveForward(Figure fig, int positions) {

		int oldPos = fig.getFigurePos();
		int newPos = oldPos + positions;
		fig.setWeglaenge(positions);
				
		/**
		 * wenn Figur mit aktuellen Wurf ueber eine Runde gelaufen ist -> store
		 * into Array
		 **/
		
		if (fig.getWeglaenge() > MAXGEFAHRENEWEGLAENGE) {
			int c = fig.getWeglaenge();
			switch (c) {
			case ERSTESZIELFELD:
				pg.storeFigure(fig, NULL);
				pg.getPlayer(fig.getPlayerID()).removeFigureFromActiveSoldiers(fig);
				pg.setFigureOnPosition(null, oldPos);
				return;
			case ZWEITESZIELFELD:
				pg.storeFigure(fig, EINS);
				pg.getPlayer(fig.getPlayerID()).removeFigureFromActiveSoldiers(fig);
				pg.setFigureOnPosition(null, oldPos);
				return;
			case DRITTESZIELFELD:
				pg.storeFigure(fig, ZWEI);
				pg.getPlayer(fig.getPlayerID()).removeFigureFromActiveSoldiers(fig);
				pg.setFigureOnPosition(null, oldPos);
				return;
			case VIERTESZIELFELD:
				pg.storeFigure(fig, DREI);
				pg.getPlayer(fig.getPlayerID()).removeFigureFromActiveSoldiers(fig);
				pg.setFigureOnPosition(null, oldPos);
				return;
			default:
				fig.setFigurePos(oldPos);
				return;
			}

		}
		
		if( newPos > MAXGEFAHRENEWEGLAENGE){
			newPos = newPos % MAXGEFAHRENEWEGLAENGE;
			fig.setFigurePos(newPos);
		} else {
			fig.setFigurePos(newPos);
		}

		/** wenn neues Feld besetzt und Figur auf Feld ist NICHT die eigene **/
		if (pg.isOccupied(newPos)
				&& pg.getFigureOnPosition(newPos).hasPlayer() != fig
						.hasPlayer()) {
			kickEnemyFigure(newPos);
			pg.setFigureOnPosition(null, oldPos);
			pg.setFigureOnPosition(fig, newPos);
			/** wenn neues Feld besetzt und Figur auf Feld ist die eigene **/
		} else if (pg.isOccupied(newPos)
				&& pg.getFigureOnPosition(newPos).hasPlayer() == fig
						.hasPlayer()) {
			/** waehle andere Figur zum Laufen **/
			fig = pickFigure(fig.hasPlayer().getPlayerID());
			oldPos = fig.getFigurePos();
			newPos = fig.getFigurePos() + positions;
			pg.setFigureOnPosition(null, oldPos);
			pg.setFigureOnPosition(fig, newPos);
		}
		pg.setFigureOnPosition(null, oldPos);
		pg.setFigureOnPosition(fig, newPos);
		return;
		// if feldende erreicht
	}

	public Figure pickFigure(int playerID) {
		int tmp;
		notifyObserversPrintActiveFigures();
		Scanner in = new Scanner(System.in);
		tmp = in.nextInt();
		while(!pg.getPlayer(playerID).isFigureAvailable(tmp)) {
			tmp = in.nextInt();
		}
		return pg.getPlayer(playerID).getFigure(tmp);	
		
	}

	public void runningGame(int pl) {
		int i = 0;
		while (i < pl) {
			activePlayerID = i;
			roll = 0;			
			roll = pg.getPlayer(i).rolling();
			notifyObserversPrintDice();
		
			/**
			 * wenn erster Wurf keine 'GEWUERFELTESECHS' und Spieler hat noch alle Figuren auf
			 * Stack, wuerfle 2 weitere Male
			 **/
			if ((roll != GEWUERFELTESECHS && pg.getPlayer(i).getStackSize() == VORHANDENEFIGUREN) 
					|| (roll != GEWUERFELTESECHS && pg.getPlayer(i).getStackSize() != VORHANDENEFIGUREN 
					&& pg.getPlayer(i).figureArrayEmpty() )) {
				for (int k = 0; k < 2 && roll != GEWUERFELTESECHS; k++) {
					roll = pg.getPlayer(i).rolling();
					
					notifyObserversPrintDice();
					/** wenn Wurf immernoch keine 'GEWUERFELTESECHS' , naechster Spieler **/

				}
				if (roll != GEWUERFELTESECHS) {
					i++;
					if (i == pl) {
						i = 0;
					}
					continue;
				}
			}
			

			/**
			 * wenn wuerfel 'GEWUERFELTESECHS' zeigt UND spieler noch figuren auf Stack hat UND
			 * sein Startfeld NICHT von seiner eigenen Figur besetzt ist
			 * 
			 */
			
			if (roll == GEWUERFELTESECHS && pg.getPlayer(i).figureStackEmpty() == false) {
				System.out.println("Spieler " + i + " kommt raus!");
				comingOut(i);
			} else if (roll == GEWUERFELTESECHS && pg.getPlayer(i).figureStackEmpty() == true) {
				moveForward(pickFigure(i), roll);
			} else if (roll != GEWUERFELTESECHS) {
				moveForward(pickFigure(i), roll);
			}
			i++;
			if (i == pl) {
				i = 0;
			}
			notifyObserversArray();
			
		}
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	public Player getActivePlayer() {
		return pg.getPlayer(activePlayerID);
	}
	
	public int getRoll() {
		return roll;
	}
	public Figure[] getPlayerFigures(){
		return pg.getPlayer(activePlayerID).getPgFigureArray();
	}
	public Figure[] getPgArray(){
		return pg.getFieldArray();
	}
	
	public List<String> getFieldCoords(){
		return pg.getFieldCoordnates();
	}
	
	public List<String> getTargetCoords(int player){
		return pg.getTargetCoordnates(player);
	}
	public List<String> getStackCoords() throws FileNotFoundException{
		 System.out.println("tmp Stack list" + pg.getStackCoords());
		return pg.getStackCoords();
	}
	public Figure getFigureOnPos(int k){
		return pg.getFigureOnPosition(k);
	}
	
	public int getStackSize(int playerID){
		System.out.println("testdafadgdfg");
		if(pg.getPlayer(playerID) == null)
			return -1;
		return pg.getPlayer(playerID).getStackSize();
	}
	
	public int getAnzahlMitspieler(){
		return pg.getAnzMit();
	}
	
	public Figure[] getTargetFigureArray(int playerID){
		return pg.getTargetArray(playerID);
	}
}

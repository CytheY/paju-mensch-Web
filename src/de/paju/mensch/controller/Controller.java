package de.paju.mensch.controller;

import java.io.FileNotFoundException;
import java.util.List;

import de.paju.mensch.model.Figure;
import de.paju.mensch.model.Player;
import de.paju.mensch.model.Playground;
import de.paju.mensch.observer.Observable;

public class Controller extends Observable {

	/*
	 * @author juschnei, marisser
	 */

	// zeigt an in Welchem Status das Spiel ist, um die gewuenschte Aktionen
	// auszufuehren
	public enum GAME_STATE {
		CHOOSE_FIG, ROLL, CHOOSE_PLAYER_COUNT, GAME_STOP
	}

	private Playground pg;
	private int activePlayerID;
	private int activeFigureID;
	private GAME_STATE status;
	private int roll;
	private int pl = 0;
	public static final int MAX_SPIELER = 4;
	public static final int MAX_GEFAHRENE_WEG_LAENGE = 39;
	public static final int GEWUERFELTE_SECHS = 6;
	public static final int VORHANDENE_FIGUREN = 4;
	public static final int ERSTES_ZIELFELD = 40;
	public static final int ZWEITES_ZIELFELD = 41;
	public static final int DRITTES_ZIELFELD = 42;
	public static final int VIERTES_ZIELFELD = 43;
	public static final int NULL = 0;
	public static final int EINS = 1;
	public static final int ZWEI = 2;
	public static final int DREI = 3;

	public Controller() {
		this.pg = new Playground();
	}

	// Versuche die Coordinaten .txt Dateien einzulesen und lasse die Anzahl der
	// Spieler auswaehlen
	public void start() {
		try {
			pg.addCoordinates();
		} catch (FileNotFoundException e) {

		}
		status = GAME_STATE.CHOOSE_PLAYER_COUNT;
		notifyChoosePlayerCount();
	}

	/*
	 * Fuege Anzahl der Spieler zum Spielfel hinzu. setze aktueller Spieler auf
	 * 0. zeichne das Spielfeld zeichne alle Spieler + Targetfelder +
	 * Stackfelder setze "status" auf ROLL und lasse ersten spieler wuerfeln
	 */

	public void inputPlayerCount(int playerCount) {
		pl = playerCount;
		for (int i = 0; i < playerCount; i++) {
			pg.addPlayer(new Player(i));
		}
		activePlayerID = 0;
		notifyShowGameFrame();
		notifyObserversPlayerStatus();

		status = GAME_STATE.ROLL;
		notifyObserversRoll();
	}

	/*
	 * erhoehe Spieleranzahl um 1, wenn AnzahlSpieler erreicht, fange wieder bei
	 * Spieler 0 an
	 */

	public void incrementPlayerID() {
		if (roll == GEWUERFELTE_SECHS) {
			return;
		}

		activePlayerID++;
		if (activePlayerID == pl) {
			activePlayerID = 0;
		}
	}
	
	/*
	 * setze aktuelle SpielerID auf activeSpielerID
	 */
	public void setActivePlayerID(int activePlayerID) {
		this.activePlayerID = activePlayerID;
	}

	/*
	 * setze Wuerfel auf beliebige Zahl
	 */
	public void setRoll(int roll) {
		this.roll = roll;
	}
	
	/*
	 * lasse erste Zahl wuerfeln zeichne Spielfeld neu zeichne Wuerfel
	 */

	public void doDice() {
		roll = pg.getPlayer(activePlayerID).rolling();
		notifyShowGameFrame();
		notifyObserversPrintDice();

		if ((roll != GEWUERFELTE_SECHS && pg.getPlayer(activePlayerID)
				.getStackSize() == VORHANDENE_FIGUREN)
				|| (roll != GEWUERFELTE_SECHS
						&& pg.getPlayer(activePlayerID).getStackSize() != VORHANDENE_FIGUREN && pg
						.getPlayer(activePlayerID).figureArrayEmpty())) {
			for (int k = 0; k < 2 && roll != GEWUERFELTE_SECHS; k++) {
				roll = pg.getPlayer(activePlayerID).rolling();

				notifyObserversPrintDice();
				/**
				 * wenn Wurf immernoch keine 'GEWUERFELTESECHS' , naechster
				 * Spieler
				 **/

			}
			if (roll != GEWUERFELTE_SECHS) {
				incrementPlayerID();
				notifyObserversPlayerStatus();
				status = GAME_STATE.ROLL;
				notifyObserversRoll();
				return;
			}
		}

		/**
		 * wenn wuerfel 'GEWUERFELTESECHS' zeigt UND spieler noch figuren auf
		 * Stack hat UND sein Startfeld NICHT von seiner eigenen Figur besetzt
		 * ist
		 * 
		 */
		if (roll == GEWUERFELTE_SECHS) {
			if (pg.getFigureOnPosition(pg.getPlayer(activePlayerID)
					.getStartField()) != null) {

				if (pg.getPlayer(activePlayerID).figureStackEmpty() == false
						&& pg.getFigureOnPosition(
								pg.getPlayer(activePlayerID).getStartField())
								.getPlayerID() == activePlayerID) {
					moveForward(pg.getFigureOnPosition(pg.getPlayer(
							activePlayerID).getStartField()), roll);
					notifyObserversPlayerStatus();
					status = GAME_STATE.ROLL;
					notifyObserversRoll();
					notifyShowGameFrame();
					return;
				}
			} else if (pg.getPlayer(activePlayerID).figureStackEmpty() == false) {
				comingOut(activePlayerID);
				notifyObserversPlayerStatus();
				status = GAME_STATE.ROLL;
				notifyObserversRoll();
				notifyShowGameFrame();
				return;
			} else {
				status = GAME_STATE.CHOOSE_FIG;
				notifyChooseFigure();
				notifyObserversPrintActiveFigures();
				return;

			}
		} else if (roll != GEWUERFELTE_SECHS) {
			status = GAME_STATE.CHOOSE_FIG;
			notifyChooseFigure();
			notifyObserversPrintActiveFigures();
			return;
		}
		incrementPlayerID();
		notifyObserversPlayerStatus();
		notifyShowGameFrame();
		status = GAME_STATE.ROLL;
		notifyObserversRoll();
	}

	/*
	 * hole aktuelle Spieleranzahl
	 */

	public int getPl() {
		return pl;
	}

	/*
	 * hole maximale Spieleranzahl
	 */

	public static int getMaxspieler() {
		return MAX_SPIELER;
	}

	/*
	 * setze aktuelle Spieleranzahl
	 */

	public void setPl(int pl) {
		this.pl = pl;
	}

	/*
	 * Spieler hat "6". Eigenes erstes Startfeld wird abgerufen Figur wird von
	 * eigenen Stack geholt Falls Feld von andere Figur beleget, so schmeise
	 * diese, falls nicht, setze Figur auf Feld
	 */

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

	/*
	 * wenn 2 Figuren auf eine postition sind/wollen, dann setze Weglaenge der
	 * Figur auf 0 und lege geschmissene Figur zurueck auf Stack
	 */

	public void kickEnemyFigure(int position) {
		Figure enemy = pg.getFigureOnPosition(position);
		enemy.resetWegLaenge();
		enemy.hasPlayer().pushFigure(enemy);

	}

	/*
	 * setze Figur um die laenge "positions" nach vorne
	 */
	public void moveForward(Figure fig, int positions) {
		int oldPos = fig.getFigurePos();
		int newPos = oldPos + positions;
		fig.setWeglaenge(positions);

		/**
		 * wenn Figur mit aktuellen Wurf ueber eine Runde gelaufen ist, store
		 * into Array Falls Zielfeld ueberschritten, dann lass Spielfigur an
		 * ihrer Stelle
		 **/
		
		putZielfeld(fig, oldPos);

		/*
		 * Wenn Spielerfigur am Ende des Feldes und WegLaenge kleiner als laenge
		 * des Feldes, dann setze figur an Stelle 0 des Spielfeldes
		 */

		newPos = setFigureToFirstArray(fig, newPos);

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
			notifyChooseFigure();
			notifyObserversPrintActiveFigures();
			return;

		}
		pg.setFigureOnPosition(null, oldPos);
		pg.setFigureOnPosition(fig, newPos);
		return;
	}

	private int setFigureToFirstArray(Figure fig, int newPos) {
		if (newPos > MAX_GEFAHRENE_WEG_LAENGE) {
			newPos = newPos % MAX_GEFAHRENE_WEG_LAENGE;
			fig.setFigurePos(newPos);
		} else {
			fig.setFigurePos(newPos);
		}
		return newPos;
	}

	private void putZielfeld(Figure fig, int oldPos) {
		int c = fig.getWeglaenge();
		if (c > MAX_GEFAHRENE_WEG_LAENGE) {

			switch (c) {
			case ERSTES_ZIELFELD:
					pg.storeFigure(fig, NULL);
					pg.getPlayer(fig.getPlayerID())
							.removeFigureFromActiveSoldiers(fig);
					pg.setFigureOnPosition(null, oldPos);
					return;
				
			case ZWEITES_ZIELFELD:
					pg.storeFigure(fig, EINS);
					pg.getPlayer(fig.getPlayerID())
							.removeFigureFromActiveSoldiers(fig);
					pg.setFigureOnPosition(null, oldPos);
					return;
				
			case DRITTES_ZIELFELD:

					pg.storeFigure(fig, ZWEI);
					pg.getPlayer(fig.getPlayerID())
							.removeFigureFromActiveSoldiers(fig);
					pg.setFigureOnPosition(null, oldPos);
					return;
				
			case VIERTES_ZIELFELD:

					pg.storeFigure(fig, DREI);
					pg.getPlayer(fig.getPlayerID())
							.removeFigureFromActiveSoldiers(fig);
					pg.setFigureOnPosition(null, oldPos);
					return;
				
			default:
				fig.setFigurePos(oldPos);
				return;
			}
		} else {
			notifyChooseFigure();
			notifyObserversPrintActiveFigures();

		}
	}

	public void setPickFigure(int figureID) {
		if (pg.getPlayer(activePlayerID).isFigureAvailable(figureID)) {
			activeFigureID = figureID;
			moveForward(pg.getPlayer(activePlayerID).getFigure(activeFigureID),
					roll);
		}
		incrementPlayerID();
		notifyObserversPlayerStatus();
		notifyShowGameFrame();
		status = GAME_STATE.ROLL;
		notifyObserversRoll();
		return;
	}

	public void update() {

	}

	/*
	 * hole den aktuell spielenden Spieler
	 */

	public Player getActivePlayer() {
		return pg.getPlayer(activePlayerID);
	}

	/*
	 * hole den aktuell gewuerfelten Wert
	 */

	public int getRoll() {
		return roll;
	}

	/*
	 * Gebe Feld zurueck, mit allen Spielfiguren eines Spielers (auf Stack)
	 */

	public Figure[] getPlayerFigures() {
		return pg.getPlayer(activePlayerID).getPgFigureArray();
	}

	/*
	 * Gebe Spielfeld zurueck, mit allen Figuren
	 */

	public Figure[] getPgArray() {
		return pg.getFieldArray();
	}

	/*
	 * Liste mit Strings, wo alle x, y Koordinaten des Spielfelds hinterlegt
	 * sind
	 */

	public List<String> getFieldCoords() {
		return pg.getFieldCoordnates();
	}

	/*
	 * Liste mit Strings, wo alle x, y Koordinaten des Targetfelds hinterlegt
	 * sind
	 */

	public List<String> getTargetCoords(int player) {
		return pg.getTargetCoordnates(player);
	}

	/*
	 * Liste mit Strings, wo alle x, y Koordinaten des Stackfeldes hinterlegt
	 * sind
	 */

	public List<String> getStackCoords() throws FileNotFoundException {
		return pg.getStackCoords();
	}

	/*
	 * Hole Figur an stelle k
	 */

	public Figure getFigureOnPos(int k) {
		return pg.getFigureOnPosition(k);
	}

	/*
	 * gebe Anzahl vorhandenen Figuren auf Stack von aktuellen Spieler zurueck
	 */

	public int getStackSize(int playerID) {
		return pg.getPlayer(playerID).getStackSize();
	}

	/*
	 * gebe Anzahl Mitspieler zurueck
	 */

	public int getAnzahlMitspieler() {
		return pl;
	}

	/*
	 * gebe von Spieler das Zielfeld-Array zurueck
	 */

	public Figure[] getTargetFigureArray(int playerID) {
		return pg.getTargetArray(playerID);
	}

	/*
	 * Hole den aktuellen Spielstatus
	 */

	public GAME_STATE getStatus() {
		return status;
	}

	/*
	 * setze den aktuellen Spielstatus
	 */

	public void setStatus(GAME_STATE status) {
		this.status = status;
	}

}

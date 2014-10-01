package view;

import model.Figure;

import observer.IObserver;

import org.apache.log4j.*;

import controller.Controller;

public class TextGUI implements IObserver {
	private final int wuerfelEINS = 1;
	private final int wuerfelZWEI = 2;
	private final int wuerfelDREI = 3;
	private final int wuerfelVIER = 4;
	private final int wuerfelFUENF = 5;
	private final int wuerfelSECHS = 6;
	private final String KANTE = " ------- ";
	
	private Logger logger = Logger.getLogger("view.TextGUI");

	private Controller controller;
	
	public TextGUI(Controller controller) {
		super();
		this.controller = controller;
	}
	
	
	public void printArrayInformation(Figure array[], int i) {
		switch(i){
		case 1:
			println("Spielfeld Array: ");
			printArrays(array);
			break;
		case 2:
			println("Ziel-Box Arrays: ");

		}
	}
	
	public void printArrays(Figure array[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				sb.append("[");
				sb.append(array[i].hasPlayer().getPlayerID());
				sb.append(":");
				sb.append(array[i].getFigureID());
				sb.append("]");
			}else{
				sb.append("[ ]");
			}
		}
		sb.append("\n");
		print(sb.toString());
	}
	
	public void printActiveFigures(Figure array[]){
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {			
				printFigureStatus(array[i]);
			}
		}
		println("");
	}
	
//	public static void printStatusLine(int i){
//		switch (i)
//		{
//		case 1:
//			System.out.println("Spieler");
//		
//		}
//				
//	}
	
	public void printFigureStatus(Figure fig){
		println("-----------------------------------------------");
		println("PlayerID: " + fig.getPlayerID());
		println("FigurID: " + fig.getFigureID());
		println("    Figurposition im Spielfeld: " + fig.getFigurePos());
		println("    FigurWegLaenge: " + fig.getWeglaenge());
		println("-----------------------------------------------");
	}
	
	public void printMoveForward(Figure fig, int roll ){
		println("Spieler " + fig.getPlayerID() + ":Figur " + fig.getFigureID() + "wurde ausgewaehlt");
		println("    Bewegt sich um " + roll + " Schritte nach vorne");
	}
	
	public void printDice(int playerID, int value){
		println("Player " + playerID + " Wuerfel: ");
		switch (value){
		case wuerfelEINS:
			println(KANTE);
			println("|       |");
			println("|   O   |");
			println("|       |"); 
			println(KANTE);
			break;

		case wuerfelZWEI:
			println(KANTE);
			println("|     O |");
			println("|       |");
			println("| O     |");
			println(KANTE);
			break;
		case wuerfelDREI:
			println(KANTE);
			println("|     O |");
			println("|   O   |");
			println("| O     |");
			println(KANTE);
			break;
		case wuerfelVIER:
			println(KANTE);
			println("| O   O |");
			println("|       |");
			println("| O   O |");
			println(KANTE);
			break;
		case wuerfelFUENF:
			println(KANTE);
			println("| O   O |");
			println("|   O   |");
			println("| O   O |");
			println(" ------- ");
			break;
		case wuerfelSECHS:
			println(KANTE);
			println("| O   O |");
			println("| O   O |");
			println("| O   O |");
			println(KANTE);
			break;
		}
		
	}
	public void println(String sg){
		logger.info(sg );
	}
	public void print(String sg){
		logger.info(sg);
	}
	
	public void updatePrintDice() {		
		printDice(controller.getActivePlayer().getPlayerID(), controller.getRoll());
	}


	public void updatePrintArray() {
		printArrays(controller.getPgArray());
	}
	
	public void updatePrintFigures(){
		printActiveFigures(controller.getPlayerFigures());
	}
}

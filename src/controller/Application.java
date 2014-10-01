package controller;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import view.GUI;
import view.TextGUI;

public class Application {

    /**
     * @param args
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static void main(String[] args) throws NumberFormatException, IOException {
    	PropertyConfigurator.configure("log4j.properties");
    	
    	   	
    	Controller game = new Controller();

    	
    	TextGUI tui = new TextGUI(game);
    	GUI gui = new GUI(game);
    	game.addObserver(tui);
    	game.addObserver(gui);
    	game.init();
	
    }

}

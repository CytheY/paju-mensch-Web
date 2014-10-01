package view;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;

import controller.Controller;


public final class Application {
	
	private Application(){};
    /**
     * @param args
     * @throws IOException 
     * @throws NumberFormatException 
     */
    public static void main(String[] args) throws IOException {
    	PropertyConfigurator.configure("log4j.properties");
    	

    	   	
    	Controller game = new Controller();

    	
    	TextGUI tui = new TextGUI(game);
    	GUI gui = new GUI(game);
    	

    	game.addObserver(tui);
    	game.addObserver(gui);
    	
    	game.start();
    }

}

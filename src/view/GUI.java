package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
//import java.awt.geom.GeneralPath;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Figure;

import controller.Controller;

import observer.IObserver;


// XXXXXXXXXXXXX GUI XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public class GUI extends JFrame implements IObserver {

	private static final long serialVersionUID = 1L;

	GamePaint gamePaint;
	JTextField jtsetStone;
	JTextField jtstoneRun;

	private Controller controller;

	public GUI(Controller controller) {
		super("Mensch ärger dich nicht");
		gamePaint = new GamePaint(controller);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel(new FlowLayout());
		JButton wuerfel = new JButton("Würfel");

		panel.add(wuerfel);

		getContentPane().add(gamePaint, BorderLayout.CENTER);
		getContentPane().add(panel, BorderLayout.SOUTH);

		setSize(550, 650);
		setResizable(false);
		setVisible(true);
		this.controller = controller;
	}

	public void updatePrintDice() {
		// TODO Auto-generated method stub

	}

	public void updatePrintArray() {
		gamePaint.repaint();
	}

	public void updatePrintFigures() {
		// TODO Auto-generated method stub

	}

}

// XXXXXXXXXXXXXX Zeichnen des Spiels XXXXXXXXXXXXXXXXXXXXXXX
class GamePaint extends JComponent {

	private static final long serialVersionUID = 1L;

	public int stoneField = 1;
	private Controller controller;

	public GamePaint(Controller controller) {
		super();
		this.controller = controller;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// Hintergrund zeichenen
		g2.setColor(Color.BLACK);
		drawBackground(g2);

		// Spielfeld zeichnen
		g2.setColor(Color.WHITE);

		drawField(g2);
		drawTargets(g2);
	    //drawStacks(g2);


		// Raster zeichnen
		g2.setColor(Color.WHITE);
		drawGrid(g2);

		 //Koordinaten Zeichnen
		 g2.setColor(Color.GREEN);
		 drawCoord(g2);

	}

	// Methode zum Zeichnen der Speilsteine
	private void drawBackground(Graphics2D g2) {
		g2.fillRect(0, 0, 550, 550);
	}

	// Methode zum Zeichnen des Spielfeldes

	public void drawField(Graphics2D g2) {
		ArrayList<String> fieldCoords = (ArrayList<String>) controller
				.getFieldCoords();
		Figure[] fieldFigures = controller.getPgArray();

		String pattern = " ";
		int k = 0;
		for (String src : fieldCoords) {
			String[] parts = src.split(pattern);

			String firstValue = parts[0];
			int i = Integer.parseInt(firstValue);
			String secondValue = parts[1];
			int j = Integer.parseInt(secondValue);

			if (fieldFigures[k] != null) {
				int c = fieldFigures[k].getPlayerID();
				switch (c) {
				case 0:
					drawFigure(g2, i, j, Color.GREEN);
					drawFigureID(g2, i, j, k);

					break;
				case 1:
					drawFigure(g2, i, j, Color.YELLOW);
					drawFigureID(g2, i, j, k);
					break;
				case 2:
					drawFigure(g2, i, j, Color.BLUE);
					drawFigureID(g2, i, j, k);
					break;
				case 3:
					drawFigure(g2, i, j, Color.RED);
					drawFigureID(g2, i, j, k);
					break;
				default:
					g2.setColor(Color.WHITE);
				}
			} else {
				g2.setColor(Color.WHITE);
				g2.fillRect(i, j, 50, 50);
			}
			k++;
		}

	}

	public void drawTargets(Graphics2D g2){
		String pattern = " ";

		for(int k = 0; k < 4; k++){
			ArrayList<String> targetCoords = (ArrayList<String>) controller.getTargetCoords(k);
			Figure[] targetFigures = controller.getTargetFigureArray(k);
			int p = 0;
			for(String src : targetCoords){
				String[] parts = src.split(pattern);

				String firstValue = parts[0];
				int i = Integer.parseInt(firstValue);
				String secondValue = parts[1];
				int j = Integer.parseInt(secondValue);
				
				if (targetFigures[p] != null) {
					int c = targetFigures[k].getPlayerID();
					switch (c) {
					case 0:
						drawFigure(g2, i, j, Color.GREEN);
						drawFigureID(g2, i, j, k);

						break;
					case 1:
						drawFigure(g2, i, j, Color.YELLOW);
						drawFigureID(g2, i, j, k);
						break;
					case 2:
						drawFigure(g2, i, j, Color.BLUE);
						drawFigureID(g2, i, j, k);
						break;
					case 3:
						drawFigure(g2, i, j, Color.RED);
						drawFigureID(g2, i, j, k);
						break;
					default:
						g2.setColor(Color.WHITE);
					}
				}else {
					g2.setColor(Color.GRAY);
					g2.fillRect(i, j, 50, 50);
				}
			}

		}
	   

	}
	public void drawStacks(Graphics2D g2) throws FileNotFoundException{
		ArrayList<String> tmpList = (ArrayList<String>) controller.getStackCoords();
		
			 String pattern = " ";
			 int ss = 0;

				for (String src : tmpList) {
					
				    String[] parts = src.split(pattern);
				    
				    String firstValue = parts[0]; 
				    int i = Integer.parseInt(firstValue);
			        String secondValue = parts[1];
					int j = Integer.parseInt(secondValue);
					if(src.contains("Spieler1")){
						drawFigure(g2,i,j,Color.GREEN);
					}
					if(src.contains("Spieler2")){
						drawFigure(g2,i,j,Color.YELLOW);
					}
					if(src.contains("Spieler3")){
						drawFigure(g2,i,j,Color.BLUE);
					}
					if(src.contains("Spieler4")){
						drawFigure(g2,i,j,Color.RED);
					}
				
		 }
	}

	private void drawGrid(Graphics2D g2) {
		for (int i = 0; i <= 550; i = i + 50) {
			for (int j = 0; j <= 550; j = j + 50) {
				g2.drawLine(i, j, 0, j);
			}
		}
		for (int i = 0; i <= 550; i = i + 50) {
			for (int j = 0; j <= 550; j = j + 50) {
				g2.drawLine(j, i, j, 0);
			}
		}
	}

	private void drawCoord(Graphics2D g2) {
		for (int i = 0; i < 550; i = i + 50) {
			for (int j = 0; j < 550; j = j + 50) {
				g2.drawString(i + ":" + j, i, j + 50);
			}
		}
	}

	private void drawFigureID(Graphics2D g2, int x, int y, int k) {
		int id = controller.getFigureOnPos(k).getFigureID();
		g2.setColor(Color.BLACK);

		g2.drawString("Figur " + id, x + 5, y + 50 - 25);

	}

	private void drawFigure(Graphics2D g2, int x, int y, Color color) {

		g2.setColor(color);
		g2.fillRect(x, y, 50, 50);

	}
}

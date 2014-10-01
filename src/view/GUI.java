package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Figure;

import controller.Controller;
import controller.Controller.GAME_STATE;

import observer.IObserver;

// XXXXXXXXXXXXX GUI XXXXXXXXXXXXXXXXXXXXXXXXXXXXX
public class GUI extends JFrame implements IObserver {

	private static final long serialVersionUID = 1L;

	private GamePaint gamePaint;
	

	private Controller controller;
	
	
	private JTextField output;
	private JTextField input;
	private JTextField status;
	
	private static final int WIDTH = 630;
	private static final int HEIGHT = 650;
	
    private static final int WIDTHBG = 550;
	private static final int HEIGHTBG = 550;
	
	private static final int WIDTHREC = 50;
	private static final int HEIGHTREC = 50;
	
	private static final int NULL = 0;
	private static final int EINS = 1;
	private static final int ZWEI = 2;
	private static final int DREI = 3;
	
	
	
	public GUI(Controller controller) {
		super("Mensch �rger dich nicht");
		
		this.controller = controller;
		
		gamePaint = new GamePaint(controller);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ActionListener buttons = new ButtonListener(this.controller);
		
		getContentPane().setLayout(new BorderLayout());

		JPanel panelSouth = new JPanel();
		panelSouth.setLayout(new GridLayout(2, 2));
		
		JButton wuerfel = new JButton("W�rfel");
		wuerfel.addActionListener(buttons);
		
		JButton enter = new JButton("Enter");
		enter.addActionListener(buttons);
		
		JPanel panelWest = new JPanel();
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.PAGE_AXIS));
		
		JButton figur0 = new JButton("Figur0");
		JButton figur1 = new JButton("Figur1");
		JButton figur2 = new JButton("Figur2");
		JButton figur3 = new JButton("Figur3");
		figur0.addActionListener(buttons);
		figur1.addActionListener(buttons);
		figur2.addActionListener(buttons);
		figur3.addActionListener(buttons);
		
		panelWest.add(figur0);
		panelWest.add(figur1);
		panelWest.add(figur2);
		panelWest.add(figur3);

			
		
		output = new JTextField();
		input = new JTextField();
		status = new JTextField();
		
		status.setEditable(false);
		output.setEditable(false);
		
		panelSouth.add(wuerfel);
		panelSouth.add(status);
		panelSouth.add(output);
		panelSouth.add(input);
		panelSouth.add(enter);
		
		getContentPane().add(gamePaint, BorderLayout.CENTER);
		getContentPane().add(panelSouth, BorderLayout.PAGE_END);
		getContentPane().add(panelWest,BorderLayout.LINE_END);

		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);		
	}

	public void updatePrintDice() {
		
	}
	public void updatePlayerStatus(){
		int c = controller.getActivePlayer().getPlayerID();
		switch(c){
		case NULL:
			output.setText("Gr�ner Spieler ist dran!");
			break;
		case EINS:
			output.setText("Gelber Spieler ist dran!");
			break;
		case ZWEI:
			output.setText("Blauer Spieler ist dran!");
			break;
		case DREI:
		    output.setText("Roter Spieler ist dran!");
			break;
		default:
			output.setText("Gr�ner Spieler ist dran!");
			break;
		}
	}

	public void updateShowGameFrame() {
		gamePaint.repaint();
		status.setText("Es wurde gewuerfelt:" + controller.getRoll());
	}

	public void updatePrintFigures() {
		// TODO Auto-generated method stub

	}
	public void updateInput(){
		///
	}
	
	public void updateChooseFigure() {
		
		
	}
	
	public void inputChoosePlayerCount(){
		
	}

	public void updateObserversRoll() {
		
	}

	
	private class ButtonListener implements ActionListener {
		private Controller controller;
		
		public ButtonListener(Controller controller) {
			super();
			this.controller = controller;
		}

		public void actionPerformed(ActionEvent e) {
			if (controller.getStatus() == GAME_STATE.ROLL){
				if (e.getActionCommand().equals("W�rfel")) {
					controller.doDice();
					status.setText("Es wurde gewuerfelt:" + controller.getRoll());
				}
			}
			
			if (controller.getStatus() == GAME_STATE.CHOOSE_FIG) {
				if(e.getActionCommand().equals("Figur0")) {
					controller.setPickFigure(NULL);
				}
				if (e.getActionCommand().equals("Figur1")) {
					controller.setPickFigure(EINS);
				}
				if (e.getActionCommand().equals("Figur2")) {
					controller.setPickFigure(ZWEI);
				}
				if (e.getActionCommand().equals("Figur3")) {
					controller.setPickFigure(DREI);
				}
			}
			if(controller.getStatus()== GAME_STATE.CHOOSE_PLAYER_COUNT){
				if(e.getActionCommand().equals("Enter")) {
					controller.inputPlayerCount(Integer.parseInt(input.getText()));
				}
			}
			
		}
	}

	// XXXXXXXXXXXXXX Zeichnen des Spiels XXXXXXXXXXXXXXXXXXXXXXX
	class GamePaint extends JComponent {

		private static final long serialVersionUID = 1L;

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
			for (int i = 0; i < controller.getAnzahlMitspieler(); i++) {
				try {

					drawStacks(g2, i);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
				}
			}

			 // Raster zeichnen
			 g2.setColor(Color.BLACK);
			 drawGrid(g2);


		}

		// Methode zum Zeichnen der Speilsteine
		private void drawBackground(Graphics2D g2) {
			g2.fillRect(0, 0, WIDTHBG, HEIGHTBG);
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
					case NULL:
						drawFigure(g2, i, j, Color.GREEN);
						drawFigureID(g2, i, j, k);
						break;
					case EINS:
						drawFigure(g2, i, j, Color.YELLOW);
						drawFigureID(g2, i, j, k);
						break;
					case ZWEI:
						drawFigure(g2, i, j, Color.BLUE);
						drawFigureID(g2, i, j, k);
						break;
					case DREI:
						drawFigure(g2, i, j, Color.RED);
						drawFigureID(g2, i, j, k);
						break;
					default:
						g2.setColor(Color.WHITE);
					}
				} else {
					g2.setColor(Color.WHITE);
					g2.fillRect(i, j, WIDTHREC, HEIGHTREC);
				}
				k++;
			}

		}

		public void drawTargets(Graphics2D g2) {
			String pattern = " ";
			for (int k = 0; k < controller.getAnzahlMitspieler(); k++) {
				ArrayList<String> targetCoords = (ArrayList<String>) controller
						.getTargetCoords(k);
				Figure[] targetFigures = controller.getTargetFigureArray(k);
				int p = 0;
				for (String src : targetCoords) {
					String[] parts = src.split(pattern);

					String firstValue = parts[0];
					int i = Integer.parseInt(firstValue);
					String secondValue = parts[1];
					int j = Integer.parseInt(secondValue);

					if (targetFigures[p] != null) {
						int c = targetFigures[p].getPlayerID();
						switch (c) {
						case NULL:
							drawFigure(g2, i, j, Color.GREEN);
							// drawFigureID(g2, i, j, k);
							break;
						case EINS:
							drawFigure(g2, i, j, Color.YELLOW);
							// drawFigureID(g2, i, j, k);
							break;
						case ZWEI:
							drawFigure(g2, i, j, Color.BLUE);
							// drawFigureID(g2, i, j, k);
							break;
						case DREI:
							drawFigure(g2, i, j, Color.RED);
							// drawFigureID(g2, i, j, k);
							break;
						default:
							g2.setColor(Color.WHITE);
						}
					} else {
						g2.setColor(Color.GRAY);
						g2.fillRect(i, j, WIDTHREC, HEIGHTREC);
					}
					p++;
				}
			}

		}

		public void drawStacks(Graphics2D g2, int playerID)
				throws FileNotFoundException {
			ArrayList<String> stackCoord = (ArrayList<String>) controller
					.getStackCoords();
			int ss = 0;
			int drawn = 0;
			int zeilevon = 0;
			int zeilebis = 0;
			String pattern = " ";
			if (playerID == NULL) {
				ss = controller.getStackSize(0);
				zeilevon = 0;
				zeilebis = 4;
			} else if (playerID == EINS) {
				ss = controller.getStackSize(1);
				zeilevon = 4;
				zeilebis = 8;
			} else if (playerID == ZWEI) {
				ss = controller.getStackSize(2);
				zeilevon = 8;
				zeilebis = 12;
			} else if (playerID == DREI) {
				ss = controller.getStackSize(3);
				zeilevon = 12;
				zeilebis = 16;
			}

			for (; zeilevon < zeilebis; zeilevon++) {
				String[] parts = stackCoord.get(zeilevon).split(pattern);

				String firstValue = parts[0];
				int i = Integer.parseInt(firstValue);
				String secondValue = parts[1];
				int j = Integer.parseInt(secondValue);

				if (stackCoord.get(zeilevon).contains("Spieler1") && drawn < ss) {
					drawFigure(g2, i, j, Color.GREEN);
					drawn++;
				}
				if (stackCoord.get(zeilevon).contains("Spieler2") && drawn < ss) {
					drawFigure(g2, i, j, Color.YELLOW);
					drawn++;
				}
				if (stackCoord.get(zeilevon).contains("Spieler3") && drawn < ss) {
					drawFigure(g2, i, j, Color.BLUE);
					drawn++;
				}
				if (stackCoord.get(zeilevon).contains("Spieler4") && drawn < ss) {
					drawFigure(g2, i, j, Color.RED);
					drawn++;
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

		private void drawFigureID(Graphics2D g2, int x, int y, int k) {
			int id = controller.getFigureOnPos(k).getFigureID();
			g2.setColor(Color.BLACK);

			g2.drawString("Figur " + id, x + 5, y + 50 - 25);

		}

		private void drawFigure(Graphics2D g2, int x, int y, Color color) {

			g2.setColor(color);
			g2.fillRect(x, y, WIDTHREC, HEIGHTREC);

		}
	}

	
	
}



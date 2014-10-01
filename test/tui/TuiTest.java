package tui;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;

import view.TextGUI;

public class TuiTest {

	TextGUI tui;
	@Before
	public void testSetup() {
		Controller ctrl = new Controller();
		tui = new TextGUI(ctrl);
		
		
	}
	@Test
	public void test() {
		tui.inputChoosePlayerCount();
		tui.updateChooseFigure();
		tui.updateInput();
		tui.updateObserversRoll();
		tui.updateShowGameFrame();
		
		tui.printDice(0, 1);
		tui.printDice(0, 2);
		tui.printDice(0, 3);
		tui.printDice(0, 4);
		tui.printDice(0, 5);
		tui.printDice(0, 6);
	}

}

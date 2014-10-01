package controller;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import controller.Controller.GAME_STATE;

public class TestController extends TestCase {

	Controller ctrl;
	
	@Before
	public void setUp() {
		ctrl = new Controller();
		ctrl.setPl(4);
		ctrl.start();
		

	}
	@Test
	public void test() throws NumberFormatException, IOException {
		ctrl.inputPlayerCount(ctrl.getPl());
		ctrl.comingOut(0);
		ctrl.comingOut(1);
		ctrl.moveForward(ctrl.getFigureOnPos(10), 0);
		ctrl.moveForward(ctrl.getFigureOnPos(0), 41);
		ctrl.comingOut(0);
		ctrl.moveForward(ctrl.getFigureOnPos(0), 40);
		ctrl.comingOut(0);
		ctrl.moveForward(ctrl.getFigureOnPos(0), 42);
		ctrl.comingOut(0);
		ctrl.moveForward(ctrl.getFigureOnPos(0), 43);

		for ( int i = 0; i < 100; i++) {
			ctrl.doDice();
		}
		ctrl.setStatus(GAME_STATE.CHOOSE_FIG);
		for ( int i = 0; i < 100; i++) {
			ctrl.doDice();
		}
		ctrl.setRoll(6);
		ctrl.incrementPlayerID();
		ctrl.setRoll(5);
		ctrl.setActivePlayerID(3);
		ctrl.incrementPlayerID();
		ctrl.setPickFigure(10);
		
		
		assertEquals(4, ctrl.getPl()); 
		assertEquals(4, ctrl.getAnzahlMitspieler());
		assertEquals(4, Controller.getMaxspieler());

		ctrl.kickEnemyFigure(10);
		ctrl.setStatus(GAME_STATE.ROLL);
		assertEquals(GAME_STATE.ROLL, ctrl.getStatus());
		
		
		
		
		ctrl.getPlayerFigures();
		ctrl.getActivePlayer();
		ctrl.getPgArray();
		ctrl.getRoll();
		ctrl.getFieldCoords();
		ctrl.getTargetCoords(0);
		ctrl.getStackSize(0);
		ctrl.getStackCoords();
		ctrl.getTargetFigureArray(0);
		ctrl.setPickFigure(0);
		ctrl.update();
		
		
	}

}

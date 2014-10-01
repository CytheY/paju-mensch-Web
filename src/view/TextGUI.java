package view;

import java.util.Scanner;

import model.Figure;

public class TextGUI {

	public static void printGame(Figure array[]) {

		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				System.out.print("[" + i + "]" + "[PlID: " + array[i].hasPlayer().getPlayerID()
						+ ", FigID: " + array[i].getFigureID() + " ]");
			}else
				System.out.print("[null]");
		}
		System.out.println();

	}
}

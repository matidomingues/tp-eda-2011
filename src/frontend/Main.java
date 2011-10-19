package frontend;

import base.Board;
import base.Game;
import base.Player;
import base.Point;

public class Main {

	public static void main(String[] args) {	
		long starttime = System.currentTimeMillis();
		BoardDrawer test = new BoardDrawer();
		long endtime = System.currentTimeMillis();
		test.newGame();
		System.out.println("tiempo en cargar: " + (endtime - starttime));

	}
}

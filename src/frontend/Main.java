package frontend;

import base.Board;
import base.Player;
import base.Point;

public class Main {

	public static void main(String[] args) {	
		Board test = new Board();
		Player human = new Player();
		Player enemy = new Player();
		test.noCheckAdd(human, enemy, new Point(3,3));
		test.printMap(human, enemy);
		
	}

}

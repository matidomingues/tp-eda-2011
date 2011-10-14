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
		test.noCheckAdd(enemy, human, new Point(4,3));
		test.noCheckAdd(enemy, human, new Point(3,4));
		test.noCheckAdd(human, enemy, new Point(4,4));
		//test.add(enemy, human, new Point(5,5));
		test.printMap(human, enemy);
		
	}

}

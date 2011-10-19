package frontend;

import base.Board;
import base.Cell;
import base.Player;

public class Main {

	public static void main(String[] args) {	
		Board test = new Board(new int[8][8]);
		Player human = new Player(Cell.White);
		Player enemy = new Player(Cell.Black);
		long starttime = System.currentTimeMillis();
		test.add(3,3, enemy.getColor());
		test.add(4,4, enemy.getColor());
		test.add(3,4, human.getColor());
		test.add(4,3, human.getColor());
		test.printMap(human, enemy);
		human.setNewMoves(test.moves(human.getColor()));
		enemy.setNewMoves(test.moves(enemy.getColor()));
		System.out.println("El enemigo tiene " + enemy.getMovesSize() + " jugadas posibles");
		System.out.println("El humano tiene " + human.getMovesSize() + " jugadas posibles");

		long endtime = System.currentTimeMillis();
		System.out.println("tiempo en cargar: " + (endtime - starttime));
	}
}

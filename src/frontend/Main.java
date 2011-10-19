package frontend;

import base.Board;
import base.Cell;
import base.Player;
import base.Point;

public class Main {

	public static void main(String[] args) {	
		Board test = new Board(new int[8][8]);
		Player human = new Player(Cell.White);
		Player enemy = new Player(Cell.Black);
		long starttime = System.currentTimeMillis();
		test.add(3,3, enemy.getColor());
		/*test.add(4,4, enemy.getColor());
		test.add(3,4, human.getColor());
		test.add(4,3, human.getColor());
    */
		for(int i = 0; i<8 ; i++){
			for(int w = 0; w<8; w++){
				if(!((w==0 && i == 0 )|| (w == 7 && i == 0) || (w == 0 && i == 7) || (w == 7 && i ==7) || (i == 3 && w == 3))){
					test.noCheckAdd(human, enemy, new Point(i,w));
				}
			}
		}
		human.setNewMoves(test.moves(human.getColor()));
		enemy.setNewMoves(test.moves(enemy.getColor()));
		System.out.println("El enemigo tiene " + enemy.getMovesSize() + "jugadas posibles");
		System.out.println("El humano tiene " + human.getMovesSize() + "jugadas posibles");
		//test.add(enemy, human, new Point(0,0));
		//test.add(enemy,human,new Point(7,0));
		//test.add(enemy,human, new Point(7,7));
		//test.add(enemy,human, new Point(0,7));
		long endtime = System.currentTimeMillis();
		test.printMap(human, enemy);
		System.out.println("tiempo en cargar: " + (endtime - starttime));
		//BoardDrawer test2 = new BoardDrawer();
		//test2.newGame(test, human, enemy);
	}
}

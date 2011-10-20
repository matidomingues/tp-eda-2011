package frontend;

import base.Board;
import base.Player;
import base.Point;

public class Main {

	public static void main(String[] args) {	
		Board test = new Board();
		Player human = new Player();
		Player enemy = new Player();
		long starttime = System.currentTimeMillis();
		test.noCheckAdd(enemy, human, new Point(3,3));
		for(int i = 0; i<8 ; i++){
			for(int w = 0; w<8; w++){
				if(!((w==0 && i == 0 )|| (w == 7 && i == 0) || (w == 0 && i == 7) || (w == 7 && i ==7) || (i == 3 && w == 3))){
					test.noCheckAdd(human, enemy, new Point(i,w));
				}
			}
		}
		//test.add(enemy, human, new Point(0,0));
		//test.add(enemy,human,new Point(7,0));
		//test.add(enemy,human, new Point(7,7));
		//test.add(enemy,human, new Point(0,7));
		long endtime = System.currentTimeMillis();
		test.printMap(human, enemy);
		System.out.println("tiempo en cargar: " + (endtime - starttime));
	}
}

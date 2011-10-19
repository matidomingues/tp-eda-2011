package frontend;

import base.Board;
import base.Game;
import base.Player;
import base.Point;

public class Main {

	public static void main(String[] args) {	
		Game test = new Game();
		long starttime = System.currentTimeMillis();
		test.noCheckAdd(3,3,2);
		for(int i = 0; i<8 ; i++){
			for(int w = 0; w<8; w++){
				if(!((w==0 && i == 0 )|| (w == 7 && i == 0) || (w == 0 && i == 7) || (w == 7 && i ==7) || (i == 3 && w == 3))){
					test.noCheckAdd(i,w,1);
				}
			}
		}
		test.add(0,0,2);
		test.add(7,0,2);
		test.add(7,7,2);
		//test.add(0,7,2);
		long endtime = System.currentTimeMillis();
		test.print();
		System.out.println("tiempo en cargar: " + (endtime - starttime));

	}
}

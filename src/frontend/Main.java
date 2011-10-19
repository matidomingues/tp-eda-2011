package frontend;

import base.Game;
import base.GameDepth;
import base.GameTime;

public class Main {

	public static void main(String[] args) {

		Game game;

		if (args.length < 3) {
			System.out.println("Invalid arguments");
			return;
		}
		try{
		if (args[0].equals("-visual")) {
			if (args[1].equals("-maxtime")) {
				game = new GameTime(null, Integer.valueOf(args[2]));
			} else if (args[1].equals("-depth")) {
				game = new GameDepth(null, Integer.valueOf(args[2]));
			} else {
				System.out.println("Invalid arguments");
				return;
			}
			if (args.length >= 4) {
				if (args[3].equals("-prune")) {
					game.setPrune(true);
					if (args.length == 5) {
						if (args[4].equals("-tree")) {
							game.setTreeMode(true);
						}
					} else if (args[3].equals("-tree")) {
						game.setTreeMode(true);
					}
				}

			} else if (args[0].equals("-file")) {
				if (args[4].equals("-maxtime")) {
					game = new GameTime(args[1], Integer.valueOf(args[5]));
				} else if (args[4].equals("-depth")) {
					game = new GameDepth(args[1], Integer.valueOf(args[5]));
				}
				if (args.length >= 7) {
					if (args[6].equals("-prune")) {
						game.setPrune(true);
						if (args.length == 8) {
							if (args[7].equals("-tree")) {
								game.setTreeMode(true);
							}
						} else if (args[6].equals("-tree")) {
							game.setTreeMode(true);
						}
					}
					// game.setPlayer(Integer.valueOf(args[3]);
				} else {
					System.out.println("Invalid arguments");
					return;
				}
			}
		}}catch(Exception e){
		 System.out.println("Invalid File");
		 return;
		}
	}
}

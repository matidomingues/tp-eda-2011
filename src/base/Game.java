package base;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Game {
	private Board board;
	private Player currentPlayer;

	public Board load (String filePath) throws Exception
	{
		File file;
		BufferedReader game;
		file = new File(filePath);
		game = new BufferedReader(new FileReader(file));
		String line;
		line = game.readLine();
		Board gameBoard = new Board();
		for(int i = 0; i<8; i++){
			for(int j = 0; j<8;j++){
				if(line.charAt(j) == 1){
					gameBoard.add(i, j, Cell.White);
				}
				else if(line.charAt(j) == 2){
					gameBoard.add(i, j,Cell.Black);
				}
				else if(line.charAt(j) == ' '){
					gameBoard.add(i, j,Cell.Empty);
				}
				else{
					throw new Exception();
				}
			}
			line = game.readLine();
		}

		return gameBoard;
	}

	
	
}

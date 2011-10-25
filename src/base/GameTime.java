package base;

public class GameTime extends Game {
	
	public GameTime(String filePath,int time ) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.n = time;
	}



	
	//MINIMAX BASADO EN TIME
	
	private class Node{
		private Board board;
		private int value = 0;
	}



	@Override
	public Point miniMax (Board board, int n, Cell currentPlayer)
		{
			// TODO Auto-generated method stub
			return null;
		}

}

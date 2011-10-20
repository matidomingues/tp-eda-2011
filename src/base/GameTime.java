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

	@Override
	public Point miniMax() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//MINIMAX BASADO EN TIME
}

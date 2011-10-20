package base;

public class GameDepth extends Game{

	public GameDepth(String filePath,int depth) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.n = depth;
	}

	@Override
	public Point miniMax() {
		// TODO Auto-generated method stub
		return null;
	}
	
	//MINIMAX BASADO EN DEPTH
	
	
}

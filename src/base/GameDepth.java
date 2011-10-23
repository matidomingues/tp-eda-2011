package base;


import MinMax.PointCalc;


public class GameDepth extends Game {

	public GameDepth(String filePath, int depth) throws Exception {
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

		PointCalc test = new PointCalc();
		return test.getPointByDepth(board, n, Cell.White, Cell.Black);

	}

	// MINIMAX BASADO EN DEPTH

}

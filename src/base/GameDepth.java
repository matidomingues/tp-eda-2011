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
		Point p;
		PointCalc test = new PointCalc();
		p = test.getPointByDepth(board, n, Cell.White, Cell.Black);
		for(Point a: currentPlayerValidMoves.get(p)){
			System.out.println( "dir " + a);
		}
		System.out.println(currentPlayer);
		board.addAndTurn(p, currentPlayer, currentPlayerValidMoves.get(p));
		return p;
	
	}

	// MINIMAX BASADO EN DEPTH

}

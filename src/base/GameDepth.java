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
	
	public Point miniMax(Board board, int depth, Cell player){
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		for(Point p: board.moves(player).keySet()){
			Board child = board.clone();
			child.add(p.getX(), p.getY(), player);
			int euristicPoint = minimax(board,depth-1,Integer.MIN_VALUE,Integer.MIN_VALUE, player.oposite());
			if(euristic <= euristicPoint){
				point = p;
			}			
		}
		return point;		
		
	}
	
	private int minimax(Board board,int depth,int alpha,int beta, Cell player){
		if(depth == 0 || gameEnded(board)){
			board.evaluateBoard(player);
		}
		if(player == currentPlayer){
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				child.add(p.getX(), p.getY(), player);
				alpha = Math.max(alpha, minimax(child,depth-1,alpha,beta,player.oposite()));
				if(prune && (beta <= alpha)){
					break;
				}
			}
			return alpha;
		}
		else{
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				child.add(p.getX(), p.getY(), player);
				beta = Math.min(beta, minimax(child,depth-1,alpha,beta,player.oposite()));
				if(prune && (beta <= alpha)){
					break;
				}
			}
			return beta;
		}
	}
}

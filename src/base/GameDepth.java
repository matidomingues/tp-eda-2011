package base;

import testbiz.GraphViz;

public class GameDepth extends Game {

	GraphViz vz = new GraphViz();
	
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
		vz.addln(vz.start_graph());
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		for(Point p: board.moves(player).keySet()){
			Board child = board.clone();
			vz.addln( board.hashCode() + " -> "+child.hashCode());
			child.add(p.getX(), p.getY(), player);
			int euristicPoint = minimax(child,depth-1,Integer.MIN_VALUE,Integer.MIN_VALUE, player.oposite());
			vz.addln(child.hashCode() + " [label = \"" + p + euristicPoint+ "\"];");
			if(euristic <= euristicPoint){
				point = p;
			}			
		}
		vz.addln(board.hashCode() + " [label = \"null " + euristic + "\"];");
		vz.addln(vz.end_graph());
		System.out.println(vz.getDotSource());
		return point;		
		
	}
	
	private int minimax(Board board,int depth,int alpha,int beta, Cell player){
		if(depth == 0 || gameEnded(board)){
			return board.evaluateBoard(player);
		}
		if(player == currentPlayer){
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				vz.addln(board.hashCode() + " -> "+ child.hashCode());
				child.add(p.getX(), p.getY(), player);
				alpha = Math.max(alpha, minimax(child,depth-1,alpha,beta,player.oposite()));
				vz.addln(child.hashCode() + " [label = \"" + p + alpha+ "\"];");
				if(prune && (beta <= alpha)){
					break;
				}
			}
			return alpha;
		}
		else{
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				vz.addln(board.hashCode() + " -> "+ child.hashCode());
				child.add(p.getX(), p.getY(), player);
				beta = Math.min(beta, minimax(child,depth-1,alpha,beta,player.oposite()));
				vz.addln(child.hashCode() + " [label = \"" + p + beta + "\"];");
				if(prune && (beta <= alpha)){
					break;
				}
			}
			return beta;
		}
	}
}

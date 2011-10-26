package base;

import java.util.ArrayList;
import java.util.List;

import base.Game.Node;

public class GameTime extends Game {
	
	private final long time;
	private long maxTime;
	
	public GameTime(String filePath,int time ) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.time = 1000*time;
	}

	
	public Point miniMax(Board board, int time, Cell player){
		maxTime =this.time + System.currentTimeMillis();
		Point answer=null;
		Point aux=null;
		int step = 1;
		while(System.currentTimeMillis() < maxTime){
			aux = miniMaxMax(board, step, player);
			if(aux != null){
				answer = aux;
			}
			step++;
			if(board.emptySlots() - (step-1) <= 0){
				return answer;
			}
		}
		return answer;
	}
	
	public Point miniMaxMax(Board board, int depth, Cell player){
		
		if(System.currentTimeMillis()  > maxTime){
			return null;
		}
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		for(Point p: board.moves(player).keySet()){
			Board child = board.clone();
			child.add(p.getX(), p.getY(), player);
			int euristicPoint = minimax(board,depth-1,Integer.MIN_VALUE,Integer.MIN_VALUE, player.oposite());
			if(euristic <= euristicPoint){
				point = p;
			}
			if(System.currentTimeMillis() > maxTime){
				return null;
			}
		}
		
		return point;		
		
	}
	
	public int minimax(Board board,int depth,int alpha,int beta, Cell player){
		if( System.currentTimeMillis()  > maxTime){
			return 0;
		}
		if(depth <= 0 || gameEnded(board)){
			return board.evaluateBoard(currentPlayer);
		}
		if (player == currentPlayer) {
			return goDown(board, player, alpha, beta, depth, false);
		} else {
			return goDown(board, player, alpha, beta, depth, true);
		}
	}
	private int goDown(Board board, Cell player, int alpha, int beta, int depth, boolean ismin){
		Integer local = null;
		List<Node> data = new ArrayList<Node>();
		Point finalp = null;
		boolean isgrey = false;
		for (Point p : board.moves(player).keySet()) {
			System.out.println(currentPlayer + " " +currentPlayerValidMoves.size());
			Board child = board.clone();
			if(prune){
				addLine(board.hashCode() + " -> " + child.hashCode());
				child.add(p.getX(), p.getY(), player);
			}
			if (treeMode && isgrey) {
				data.add(new Node(child.hashCode(), p, null, false));
			} else {
				int aux = minimax(child, depth - 1, alpha, beta, player
						.oposite());
				if(local == null){
					local = aux;
					finalp = p;
				}else if(ismin && local > aux){
					local = aux;
					finalp = p;
				}else if(!ismin && local < aux){
					local = aux;
					finalp = p;
				}	
				if (ismin && beta > aux) {
					beta = aux;
				}else if (!ismin && alpha < aux) {
					alpha = aux;
				}
				if(treeMode){
					data.add(new Node(child.hashCode(), p, aux, false));
				}

			}
			if (prune && (beta <= alpha)) {
				isgrey = true;
			}
		}
		if(local == null){
			local = minimax(board,depth-1,alpha,beta,player.oposite());
		}else if(treeMode){
			addToDot(finalp, data);
		}
		return local;
	}
	

}

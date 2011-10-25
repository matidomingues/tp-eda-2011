package base;

public class GameTime extends Game {
	
	private long maxTime;
	private long startTime;
	
	public GameTime(String filePath,int time ) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		maxTime = 1000*time;
	}

	
	public Point miniMax(Board board, int time, Cell player){
		startTime = System.currentTimeMillis();
		Point answer=null;
		Point aux=null;
		int step = 1;
		while(startTime - System.currentTimeMillis() > maxTime){
			aux = miniMaxMax(board, step, player);
			if(aux != null){
				answer = aux;
			}
			step++;
		}
		/*if(answer == null){
			throw new Exception();
		}*/
		return answer;
	}
	
	public Point miniMaxMax(Board board, int depth, Cell player){
		
		if(startTime - System.currentTimeMillis() > maxTime){
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
			if(startTime - System.currentTimeMillis() > maxTime){
				return null;
			}
		}
		
		return point;		
		
	}
	
	public int minimax(Board board,int depth,int alpha,int beta, Cell player){
		if(startTime - System.currentTimeMillis() > maxTime){
			return 0;
		}
		if(depth == 0 || gameEnded(board)){
			board.evaluateBoard(currentPlayer);
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

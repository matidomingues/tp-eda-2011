package base;

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
		}
		/*if(answer == null){
			throw new Exception();
		}*/
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

package base;

public class Game {

	private Board board;
	private Player currentPlayer;
	private int[][] heuristic;
	
	public Game(){
		this.heuristic = this.createHeuristic();
		this.board = new Board(heuristic);	
	}
	
	public int[][] createHeuristic(){
		int [][] board = new int[8][8];
		for( int i = 2; i <= 5; i++){
			for(int j = 2; j <=5; j++){
				board[i][j] = 3;
			}
		}
		board[2][2] = board[2][5] = board[5][2] = board[5][5] = 15;
		board[1][1] = board[1][6] = board[6][1] = board[6][6] = -40;
		for(int j = 2; j <= 5; j++){
			board[1][j] = -5;
			board[6][j] = -5;
		}
		for(int i = 2; i <= 5; i++){
			board[i][1] = -5;
			board[i][6] = -5;
		}
		board[0][0] = board[0][7] = board[7][0] = board[7][7] = 120;
		board[0][1] = board[0][6] = board[1][0] = board[1][7] = -20;
		board[7][1] = board[7][6] = board[6][0] = board[6][7] = -20;
		board[0][2] = board[0][5] = board[2][0] = board[2][7] = 20;
		board[5][0] = board[5][7] = board[7][1] = board[7][6] = 20;
		for(int j = 3; j <= 4; j++){
			board[0][j] = 5;
			board[7][j] = 5;
		}
		for(int i = 3; i <= 4; i++){
			board[i][0] = 5;
			board[i][7] = 5;
		}		
		return board;
	}
	
}

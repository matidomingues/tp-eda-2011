package base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Game {
	protected Board board;
	protected Cell currentPlayer;
	protected int[][] heuristic;
	protected int n;
	protected boolean prune = false;
	protected boolean treeMode = false;
	protected MapObserver observer;
	protected HashMap<Point, ArrayList<Point>> currentPlayerValidMoves = new HashMap<Point, ArrayList<Point>>();

	public abstract Point miniMax(Board board, int depth, Cell player);

	public void setPrune(boolean value) {
		this.prune = value;
	}

	public void setTreeMode(boolean value) {
		this.treeMode = value;
	}

	public void addAndTurn(int x, int y) {
		board.addAndTurn(new Point(x, y), Cell.Black,
				currentPlayerValidMoves.get(new Point(x, y)));
	}

	public void subscribe(MapObserver observer) {
		board.setObserver(observer);
	}

	public void notifyChange(Point p, Cell color) {
		observer.updatePoint(p, color);
	}

	public Board load(String filePath) throws Exception {
		File file;
		BufferedReader game;
		file = new File(filePath);
		game = new BufferedReader(new FileReader(file));
		String line;
		line = game.readLine();
		Board gameBoard = new Board(heuristic);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (line.charAt(j) == '1') {
					gameBoard.add(i, j, Cell.Black);
				} else if (line.charAt(j) == '2') {
					gameBoard.add(i, j, Cell.White);
				} else if (line.charAt(j) == ' ') {
				} else {
					throw new Exception();
				}
			}
			line = game.readLine();
		}

		return gameBoard;
	}

	public int[][] createHeuristic() {
		int[][] board = new int[8][8];
		for (int i = 2; i <= 5; i++) {
			for (int j = 2; j <= 5; j++) {
				board[i][j] = 4;
			}
		}
		board[2][2] = board[2][5] = board[5][2] = board[5][5] = 15;
		board[1][1] = board[1][6] = board[6][1] = board[6][6] = -50;
		for (int j = 2; j <= 5; j++) {
			board[1][j] = -5;
			board[6][j] = -5;
		}
		for (int i = 2; i <= 5; i++) {
			board[i][1] = -5;
			board[i][6] = -5;
		}
		board[0][0] = board[0][7] = board[7][0] = board[7][7] = 135;
		board[0][1] = board[0][6] = board[1][0] = board[1][7] = -40;
		board[7][1] = board[7][6] = board[6][0] = board[6][7] = -40;
		board[0][2] = board[0][5] = board[2][0] = board[2][7] = 20;
		board[5][0] = board[5][7] = board[7][2] = board[7][5] = 20;
		for (int j = 3; j <= 4; j++) {
			board[0][j] = 5;
			board[7][j] = 5;
		}
		for (int i = 3; i <= 4; i++) {
			board[i][0] = 7;
			board[i][7] = 7;
		}

		System.out.println("HEURISTICA");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + "  ");

			}
			System.out.println("");
		}
		return board;
	}

	public void play() {
		Point pointToPlay = miniMax(board,n,currentPlayer);
		System.out.println("POINT TO PLAY:  " + pointToPlay);
		if (pointToPlay != null) {
			board.addAndTurn(pointToPlay, Cell.White,
					currentPlayerValidMoves.get(pointToPlay));
		}
	}

	public void initNewBoard() {
		this.board.initNewBoard();
	}

	public void moves(int player) {
		if (player == 1) {
			currentPlayer = Cell.Black;
			currentPlayerValidMoves = board.moves(Cell.Black);
		} else {
			currentPlayer = Cell.White;
			currentPlayerValidMoves = board.moves(Cell.White);
		}
	}

	public boolean isValidMove(int x, int y) {
		if (currentPlayerValidMoves.containsKey(new Point(x, y))) {
			return true;
		}
		return false;
	}

	public void print() {
		board.printMap(Cell.White, Cell.Black);
	}

	public boolean gotMoves() {
		if (currentPlayerValidMoves.keySet().size() != 0) {
			return true;
		}
		return false;
	}

	public void setPlayer(int player) throws Exception {
		if (player == 1) {
			currentPlayer = Cell.Black;
		} else if (player == 2) {
			currentPlayer = Cell.White;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Board getBoard() {
		return board;
	}

	public Cell getCurrentPlayer() {
		return currentPlayer;
	}

	public int getN() {
		return n;
	}

	public boolean gameEnded(Board board) {
		return board.gameEnded();
	}

	public Cell winner() {
		return board.winner();		
	}
}

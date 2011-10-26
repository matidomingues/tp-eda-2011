package base;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {

	private Cell[][] board;
	private int[][] heuristic;
	private MapObserver observer;
	private int cantBlack = 0;
	private int cantWhite = 0;

	public Board(int[][] heuristic) {
		this.board = new Cell[8][8];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				this.board[i][j] = Cell.Empty;
			}
		}
		this.heuristic = heuristic;
	}

	public void initNewBoard() {
		board[3][3] = board[4][4] = Cell.White;
		cantWhite += 2;
		board[3][4] = board[4][3] = Cell.Black;
		cantBlack += 2;
		notifyChange(new Point(3, 3), Cell.White);
		notifyChange(new Point(4, 4), Cell.White);
		notifyChange(new Point(3, 4), Cell.Black);
		notifyChange(new Point(4, 3), Cell.Black);
	}

	private Board(Cell[][] board, int[][] heuristic) {
		this.heuristic = heuristic;
		this.board = new Cell[8][8];
		for (int i = 0; i < 8; i++) {
			for (int w = 0; w < 8; w++) {
				this.board[i][w] = board[i][w];
			}
		}
	}

	public Cell getCell(Point loc) {
		return board[loc.x][loc.y];
	}

	public boolean inBorder(Point loc) {
		if (loc.x >= 0 && loc.x < board.length && loc.y >= 0
				&& loc.y < board.length) {
			return true;
		}
		return false;

	}

	/**
	 * test de impresion.
	 */
	public void printMap(Cell human, Cell enemy) {
		for (Cell[] a : board) {
			for (Cell b : a) {
				if (b == human) {
					System.out.print("1");
				} else if (b == enemy) {
					System.out.print("2");
				} else {
					System.out.print("0");
				}
			}
			System.out.println("");
		}
	}

	// TODO: agregar cantidad que come para ponderar, por ahora
	// pondera nada mas por cantidad de movimientos y fichas
	// se basa en mis fichas menos las del otro
	public int evaluateBoard(Cell player) {
		int ret = 0;
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Cell color = board[i][j];
				if (color == player) {
					ret += heuristic[i][j];
				} else if (color != Cell.Empty) {
					ret -= heuristic[i][j];
				}
			}
		}
		return ret;
	}

	public void add(int x, int y, Cell val) {
		board[x][y] = val;
	}

	private void setCell(Point loc, Cell color) {
		if(board[loc.x][loc.y] != color){
			if(board[loc.x][loc.y] == Cell.Empty){
				switch(color){
				case White: cantWhite++;
				case Black: cantBlack++;
				}
			}else{
				switch(color){
				case White: cantWhite++;
							cantBlack--;
				case Black: cantBlack++;
							cantWhite--;
				}	
			}
			board[loc.x][loc.y] = color;
			notifyChange(loc, color);
		}
	}

	private void setLine(Point loc, Point dir, Cell color) {
		if (getCell(loc) == color) {
			return;
		}
		setCell(loc, color);
		setLine(loc.sumPoint(dir), dir, color);

	}

	public void addAndTurn(Point p, Cell color,
			ArrayList<Point> directionsToTurn) {
		setCell(p, color);
		for (Point dir : directionsToTurn) {
			setLine(p.sumPoint(dir), dir, color);
		}
		return;
	}

	public HashMap<Point, ArrayList<Point>> moves(Cell turn) {
		HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point, ArrayList<Point>>();
		for (int boardX = 0; boardX < board.length; boardX++) {
			for (int boardY = 0; boardY < board.length; boardY++) {
				if (board[boardX][boardY] == turn) {
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							Point pos = new Point(boardX + i, boardY + j);
							if (pos.x < board.length && pos.x >= 0
									&& pos.y < board.length && pos.y >= 0) {
								checkNeighbors(pos, validMoves, turn,
										new Point(i, j));
							}
						}
					}
				}
			}
		}
		return validMoves;
	}

	public void checkNeighbors(Point pos,
			HashMap<Point, ArrayList<Point>> validMoves, Cell turn, Point dir) {

		Cell neighbor = board[pos.x][pos.y];
		if (neighbor != Cell.Empty && neighbor != turn) {
			boolean inBounds = true;
			while (inBounds) {
				pos.x += dir.x;
				pos.y += dir.y;
				if (pos.x >= board.length || pos.x < 0 || pos.y >= board.length
						|| pos.y < 0) {
					inBounds = false;
				} else {
					if (board[pos.x][pos.y] == Cell.Empty) {
						if (validMoves.containsKey(pos)) {
							validMoves.get(pos).add(Point.antiDirection(dir));
						} else {
							validMoves.put(pos, new ArrayList<Point>());
							validMoves.get(pos).add(Point.antiDirection(dir));
						}
						inBounds = false;
					}
					if (board[pos.x][pos.y] == turn) {
						inBounds = false;
					}

				}
			}
		}
	}

	public Board clone() {
		return new Board(board, heuristic);
	}

	public void notifyChange(Point p, Cell color) {
		observer.updatePoint(p, color);
	}

	public void setObserver(MapObserver observer) {
		this.observer = observer;
	}

	public boolean compBoard(Cell[][] board) {

		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if (this.board[i][j] != board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public Cell[][] rotateBoard(Cell[][] board) {
		Cell[][] ret = new Cell[this.board.length][this.board[0].length];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				ret[j][board.length - j] = board[i][j];
			}
		}
		return ret;
	}

	public Cell[][] transpBoard(Cell[][] board) {
		Cell[][] ret = new Cell[this.board.length][this.board[0].length];

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				ret[j][i] = board[i][j];
			
			}
		}
		return ret;
	}
	
	public boolean isSimetric(Cell[][] board){
		for(int i = 0; i <= 3; i++){
			if(this.compBoard(board)){
				return true;
			}
			board = rotateBoard(board);
			i++;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell[][] other = ((Board) obj).board;
		
		if(this.isSimetric(other)){
			return true;
		}else{
			other = transpBoard(other);
			if(this.isSimetric(other)){
				return true;
			}			
		}
	
		return false;
	}
	
	public Cell[][] getBoard(){
		return board;
	}

}

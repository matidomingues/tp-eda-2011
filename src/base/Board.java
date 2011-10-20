package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Board {

	private Cell[][] board;
	private int[][] heuristic;
	private MapObserver observer;

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
		board[3][4] = board[4][3] = Cell.Black;
		notifyChange(new Point(3, 3), Cell.White);
		notifyChange(new Point(4, 4), Cell.White);
		notifyChange(new Point(3, 4), Cell.Black);
		notifyChange(new Point(4, 3), Cell.Black);
	}

	private Board(Cell[][] board, int[][] heuristic) {
		this.heuristic = heuristic;
		for (int i = 0; i < board.length; i++) {
			for (int w = 0; w < board[0].length; w++) {
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
					ret = +heuristic[i][j];
				} else if (color != Cell.Empty) {
					ret = -heuristic[i][j];
				}
			}
		}
		return ret;
	}

	public void add(int x, int y, Cell val) {
		board[x][y] = val;
		notifyChange(new Point(x, y), val);
	}

	private void setCell(Point loc, Cell color) {
		board[loc.x][loc.y] = color;
		notifyChange(loc, color);
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
		List<Point> moves = new ArrayList<Point>();
		for (int boardX = 0; boardX < board.length; boardX++) {
			for (int boardY = 0; boardY < board.length; boardY++) {
				if (board[boardX][boardY] == turn) {
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							Point pos = new Point(boardX + i, boardY + j);
							if (pos.x < board.length && pos.x >= 0
									&& pos.y < board.length && pos.y >= 0) {

								Cell neighbor = board[pos.x][pos.y];
								if (neighbor != Cell.Empty && neighbor != turn) {
									boolean inBounds = true;
									while (inBounds) {
										pos.x += i;
										pos.y += j;
										if (pos.x >= board.length || pos.x < 0
												|| pos.y >= board.length
												|| pos.y < 0) {
											inBounds = false;
										} else {
											if (board[pos.x][pos.y] == Cell.Empty) {
												moves.add(new Point(pos.x,
														pos.y));
												inBounds = false;
											}
											if (board[pos.x][pos.y] == turn) {
												inBounds = false;
											}
										}

									}

								}
							}
						}
					}
				}
			}

		}
		HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point, ArrayList<Point>>();
		for (Point p : moves) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					Point pos = new Point(p.x + i, p.y + j);
					if (pos.x < board.length && pos.x >= 0
							&& pos.y < board.length && pos.y >= 0) {

						Cell neighbor = board[pos.x][pos.y];
						if (neighbor != Cell.Empty && neighbor != turn) {
							boolean inBounds = true;
							while (inBounds) {
								ArrayList<Point> ar = new ArrayList<Point>();
								pos.x += i;
								pos.y += j;
								if (pos.x >= board.length || pos.x < 0
										|| pos.y >= board.length || pos.y < 0) {
									inBounds = false;
								} else {
									if (board[pos.x][pos.y] == turn) {
										ar.add(new Point(i, j));
										validMoves.put(new Point(p.x, p.y), ar);
										inBounds = false;
									} else if (board[pos.x][pos.y] == Cell.Empty) {
										inBounds = false;
									}
								}

							}

						}
					}
				}
			}
		}
		return validMoves;
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
}

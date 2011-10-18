package base;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Cell[][] board;
	private List<Point> changed;
	private int[][] heuristic;

	public Board(int[][] heuristic){
		this.board = new Cell[8][8];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[0].length; j++){
				this.board[i][j] = Cell.Empty;
			}
		}
		this.heuristic = heuristic;
	}
	
	public boolean add(Player actual, Player enemy, Point loc) {
		setNewMoves(actual, enemy);
		List<Point> directions = actual.getDirs(loc);
		if (directions == null) {
			return false;
		}
		setCell(loc, actual, enemy);
		for (Point dir : directions) {
			setChips(loc.sumPoint(dir), dir, actual, enemy);
		}
		lastMoves(actual, enemy);
		return true;
	}

	/**
	 * Para agregar un solo nodo sin validar nada, solo usar para armar un mapa
	 * nuevo. Arma los posibles.
	 * 
	 * @param actual
	 * @param enemy
	 * @param loc
	 */
	public void noCheckAdd(Player actual, Player enemy, Point loc) {
		setNewMoves(actual, enemy);
		setCell(loc, actual, enemy);
		lastMoves(actual, enemy);
	}

	private void lastMoves(Player actual, Player enemy) {
		for (Point a : changed) {
			checkValid(a, actual);
			checkValid(a, enemy);
		}
		for (Point a : actual.getFinalPoints()) {
			checkValid(a, actual);
		}
		for (Point a : enemy.getFinalPoints()) {
			checkValid(a, enemy);
		}
		actual.setFinalMoves();
		enemy.setFinalMoves();
	}

	private void setNewMoves(Player actual, Player enemy) {
		actual.setNewMoves();
		enemy.setNewMoves();
		changed = new ArrayList<Point>();
	}

	public Cell getCell(Point loc) {
		return board[loc.x][loc.y];
	}

	private void setCell(Point loc, Player actual, Player enemy) {
		Cell aux = board[loc.x][loc.y];
		if (aux == enemy.getColor()) {
			enemy.decChips();
		}
		board[loc.x][loc.y] = actual.getColor();
		actual.incChips();
		changed.add(loc);

	}

	private void setChips(Point loc, Point dir, Player actual, Player enemy) {
		if (getCell(loc) == actual.getColor()) {
			return;
		}
		setCell(loc, actual, enemy);
		setChips(loc.sumPoint(dir), dir, actual, enemy);

	}

	public boolean inBorder(Point loc) {
		if (loc.x >= 0 && loc.x < board.length && loc.y >= 0 && loc.y < board.length) {
			return true;
		}
		return false;

	}

	public void checkValid(Point loc, Player actual) {
		Cell auxpos;
		Point auxp;
		for (int i = -1; i <= 1; i++) {
			for (int w = -1; w <= 1; w++) {
				if (inBorder(loc.sumPoint(new Point(i, w)))
						&& !(i == 0 && w == 0)) {

					auxpos = board[loc.x + i][loc.y + w];

					if (auxpos == null && getCell(loc) != null
							&& getCell(loc) != actual.getColor()) {
						auxp = checkLast(loc,
								Point.antiDirection(new Point(i, w)), actual, 0);
						if (auxp != null && getCell(auxp) == actual.getColor()) {
							actual.addMove(new Point(loc.x + i, loc.y + w),
									Point.antiDirection(new Point(i, w)));
						}
					} else if (auxpos != null && auxpos != actual.getColor()
							&& getCell(loc) == actual.getColor()) {
						auxp = checkLast(loc, new Point(i, w), actual, 0);
						if (auxp != null && getCell(auxp) == null) {
							actual.addMove(auxp,
									Point.antiDirection(new Point(i, w)));
						}
					} else if (auxpos == actual.getColor()
							&& getCell(loc) != actual.getColor()
							&& getCell(loc) != null) {
						auxp = checkLast(loc,
								Point.antiDirection(new Point(i, w)), actual, 0);
						if (auxp != null && getCell(auxp) == null) {
							actual.addMove(auxp, new Point(i, w));
						}
					} else if (getCell(loc) == null && auxpos != null
							&& auxpos != actual.getColor()) {
						auxp = checkLast(loc, new Point(i, w), actual, 0);
						if (auxp != null && getCell(auxp) == actual.getColor()) {
							actual.addMove(loc, new Point(i, w));
						}
					}
				}

			}
		}
	}

	private Point checkLast(Point loc, Point dir, Player actual, int num) {
		if (!inBorder(loc)) {
			return null;
		} else if (num != 0
				&& (getCell(loc) == null || getCell(loc) == actual.getColor())) {
			return loc;
		}
		return checkLast(loc.sumPoint(dir), dir, actual, num + 1);
	}

	/**
	 * test de impresion.
	 */
	public void printMap(Player actual, Player enemy) {
		for (Cell[] a : board) {
			for (Cell b : a) {
				if (b == actual.getColor()) {
					System.out.print("1");
				} else if (b == enemy.getColor()) {
					System.out.print("2");
				} else {
					System.out.print("0");
				}
			}
			System.out.println("");
		}
		System.out.println("Player Points");
		for (Point a : actual.getFinalPoints()) {
			System.out.println(a.x + " " + a.y);
			/*
			 * for (Point b : actual.getDirs(a)) { System.out.println(b.x + " "
			 * + b.y); }
			 */}
		System.out.println("Enemy Points");
		for (Point a : enemy.getFinalPoints()) {
			System.out.println(a.x + " " + a.y);
			/*
			 * for (Point b : enemy.getDirs(a)) { System.out.println(b.x + " " +
			 * b.y); }
			 */}

		System.out.println("Player points: " + actual.getChips());
		System.out.println("Enemy points: " + enemy.getChips());
	}

	// TODO: agregar cantidad que come para ponderar, por ahora
	// pondera nada mas por cantidad de movimientos y fichas
	// se basa en mis fichas menos las del otro
	public int evaluateBoard(Player player) {
		int ret = 0;
		for (int i = 0; i < board[0].length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Cell color = board[i][j];
				if (color == player.getColor()) {
					ret = +heuristic[i][j];
				} else if (color != Cell.Empty) {
					ret = -heuristic[i][j];
				}
			}
		}
		return ret;
	}

}

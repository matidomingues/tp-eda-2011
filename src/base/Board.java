package base;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private Player[][] board = new Player[8][8];
	List<Point> changed;

	public boolean add(Player actual, Player enemy, Point loc) {
		setNewMoves(actual, enemy);
		List<Point> directions = actual.getDirs(loc);
		if (directions == null) {
			return false;
		}
		setPosition(loc, actual, enemy);
		for (Point dir : directions) {
			setLocation(loc.sumPoint(dir), dir, actual, enemy);
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
		setPosition(loc, actual, enemy);
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

	public Player getPos(Point loc) {
		return board[loc.x][loc.y];
	}

	private void setPosition(Point loc, Player actual, Player enemy) {
		Player aux = board[loc.x][loc.y];
		if (aux != null && aux != actual) {
			aux.deChips();
		}
		board[loc.x][loc.y] = actual;
		actual.inChips();
		changed.add(loc);

	}

	private void setLocation(Point loc, Point dir, Player actual, Player enemy) {
		if (getPos(loc) == actual) {
			return;
		}
		setPosition(loc, actual, enemy);
		setLocation(loc.sumPoint(dir), dir, actual, enemy);

	}

	public boolean inBorder(Point loc) {
		if (loc.x >= 0 && loc.x < 8 && loc.y >= 0 && loc.y < 8) {
			return true;
		}
		return false;

	}

	public void checkValid(Point loc, Player actual) {
		Player auxpos;
		Point auxp;
		for (int i = -1; i <= 1; i++) {
			for (int w = -1; w <= 1; w++) {
				if (inBorder(loc.sumPoint(new Point(i, w)))
						&& !(i == 0 && w == 0)) {

					auxpos = board[loc.x + i][loc.y + w];

					if (auxpos == null && getPos(loc) != null
							&& getPos(loc) != actual) {
						auxp = checkLast(loc, Point.antiDirection(new Point(i,
								w)), actual, 0);
						if (auxp != null && getPos(auxp) == actual) {
							actual.addMove(new Point(loc.x + i, loc.y + w),
									Point.antiDirection(new Point(i, w)));
						}
					} else if (auxpos != null && auxpos != actual
							&& getPos(loc) == actual) {
						auxp = checkLast(loc, new Point(i, w), actual, 0);
						if (auxp != null && getPos(auxp) == null) {
							actual.addMove(auxp, Point.antiDirection(new Point(
									i, w)));
						}
					} else if (auxpos == actual && getPos(loc) != actual
							&& getPos(loc) != null) {
						auxp = checkLast(loc, Point.antiDirection(new Point(i,
								w)), actual, 0);
						if (auxp != null && getPos(auxp) == null) {
							actual.addMove(auxp, new Point(i, w));
						}
					} else if (getPos(loc) == null && auxpos != null
							&& auxpos != actual) {
						auxp = checkLast(loc, new Point(i, w), actual, 0);
						if (auxp != null && getPos(auxp) == actual) {
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
		} else if (num != 0 && (getPos(loc) == null || getPos(loc) == actual)) {
			return loc;
		}
		return checkLast(loc.sumPoint(dir), dir, actual, num + 1);
	}

	/**
	 * test de impresion.
	 */
	public void printMap(Player actual, Player enemy) {
		for (Player[] a : board) {
			for (Player b : a) {
				if (b == actual) {
					System.out.print("1");
				} else if (b == enemy) {
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
	public int evaluateBoard(Player actual, Player enem) {
		return (actual.getMovesSize() - enem.getMovesSize())+(actual.getChips() - enem.getChips());
	}

}

package base;

import java.util.List;

public class Board {

	private Player[][] board = new Player[8][8];

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
		checkValid(loc, actual);
		checkValid(loc, enemy);

	}

	private void setLocation(Point loc, Point dir, Player actual, Player enemy) {
		if (getPos(loc) == actual) {
			return;
		}
		setPosition(loc, actual, enemy);
		setLocation(loc.sumPoint(dir), dir, actual, enemy);

	}

	public boolean inBorder(Point loc) {
		if (loc.x >= 0 && loc.x < 9 && loc.y >= 0 && loc.y < 9) {
			return true;
		}
		return false;

	}

	public void checkValid(Point loc, Player actual) {
		Player auxpos;
		Point auxp;
		for (int i = -1; i <= 1; i++) {
			for (int w = -1; w <= 1; i++) {
				// TODO: esta bien a lo cabeza, despues lo arreglo.
				if (!inBorder(loc.sumPoint(new Point(i, w))) || i == 0
						&& w == 0) {
					break;
				}
				System.out.println("x: "+ loc.x + i+" y: "+ loc.y + w);
				auxpos = board[loc.x + i][loc.y + w];

				if (auxpos == null && getPos(loc) != null
						&& getPos(loc) != actual) {
					auxp = checkLast(loc, Point.antiDirection(new Point(i, w)),
							actual);
					if (getPos(auxp) == actual) {
						actual.addMove(new Point(loc.x + i, loc.y + w),
								new Point(i, w));
					}
				} else if (auxpos != null && auxpos != actual
						&& getPos(loc) == actual) {
					auxp = checkLast(loc, new Point(i, w), actual);
					if (getPos(auxp) == null) {
						actual.addMove(auxp, new Point(i, w));
					}
				} else if (auxpos != null && auxpos == actual
						&& getPos(loc) != actual) {
					auxp = checkLast(loc, Point.antiDirection(new Point(i, w)),
							actual);
					if (getPos(auxp) == null) {
						actual.addMove(auxp, new Point(i, w));
					}
				}

			}
		}
	}

	private Point checkLast(Point loc, Point dir, Player actual) {
		if (!inBorder(loc)) {
			return null;
		} else if (getPos(loc) == null || getPos(loc) == actual) {
			return new Point(loc.x, loc.y);
		}
		return checkLast(loc, dir, actual);
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
				} else{
					System.out.print("0");
				}
				System.out.println("");
			}
		}
	}

}
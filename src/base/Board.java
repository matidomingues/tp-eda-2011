package base;

import java.util.List;

public class Board {

	private Player[][] board = new Player[8][8];

	public boolean add(Player actual, Player enemy, Point loc) {
		List<Point> directions = actual.getDirs(loc);
		if (directions == null) {
			return false;
		}
		setPosition(loc, actual, enemy);
		for (Point dir : directions) {
			setLocation(loc.sumPoint(dir), dir, actual, enemy);
		}
		return true;
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

	public List<Point> possibleMoves() {
		return null;
	}

	public void checkValid(Point loc, Player actual) {
		Player auxpos;
		Point auxp;
		for (int i = -1; i <= 1; i++) {
			for (int w = -1; w <= 1; i++) {
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
					auxp = checkLast(loc, Point.antiDirection(new Point(i, w)),
							actual);
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

}

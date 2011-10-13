package base;

import java.util.List;

public class Board {

	private Player[][] board = new Player[8][8];

	public boolean add(Player actual, Player enem, Point loc) {
		int posmove = 0;
		Player aboard;
		Point aux = new Point();
		for (int i = -1; i <= 1; i++) {
			for (int w = -1; w <= 1; i++) {
				aux.setPoint(loc.x + i, loc.y + w);
				if ((i != 0 && w != 0) || inBorder(aux)) {
					aboard = getPos(aux);
					if (aboard != null && aboard != actual) {
						if (setLoc(aux, new Point(i, w), actual)) {
							setPos(loc, actual);
							posmove = 1;
						}
					}
				}
			}
		}
		if (posmove == 1) {
			return true;
		}
		return false;
	}

	public Player getPos(Point loc) {
		return board[loc.x][loc.y];
	}

	private void setPos(Point loc, Player actual) {
		Player aux = board[loc.x][loc.y];
		if (aux != null && aux != actual) {
			aux.deChips();
		}
		board[loc.x][loc.y] = actual;
		actual.inChips();

	}

	private boolean setLoc(Point loc, Point dir, Player actual) {
		if (!inBorder(loc) || board[loc.x][loc.y] == null) {
			return false;
		}
		if (board[loc.x][loc.y] == actual) {
			return true;
		}
		if (setLoc(new Point(loc.x + dir.x, loc.y + dir.y), dir, actual)) {
			setPos(loc, actual);
			return true;
		}
		return false;

	}

	public boolean checkLoc(Point loc, Point dir, Player actual, Player looking) {
		if (!inBorder(loc) || board[loc.x][loc.y] == null) {
			return false;
		}
		if (board[loc.x][loc.y] == actual) {
			return true;
		}
		return setLoc(new Point(loc.x + dir.x, loc.y + dir.y), dir, actual);
	}

	public boolean inBorder(Point loc) {
		if (loc.x >= 0 && loc.x < 9 && loc.y >= 0 && loc.y < 9) {
			return true;
		}
		return false;
	
	}
	
	public List<Point> possibleMoves(){
		return null;
	}

	
	
}

package base;

import java.util.HashSet;
import java.util.Set;

public class Player {

	private Set<Point> validMoves = new HashSet<Point>();
	private int chips = 0;

	public boolean checkValid(Point loc) {
		if (validMoves.contains(loc)) {
			return true;
		}
		return false;
	}

	public void deChips() {
		this.chips -= 1;
	}

	public void inChips() {
		this.chips += 1;
	}
}

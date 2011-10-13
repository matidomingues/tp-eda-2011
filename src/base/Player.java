package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {

	private	HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point,ArrayList<Point>>();
	private List<Point> possibleMoves;
	private int chips = 0;

	public boolean checkValid(Point loc) {
		if (validMoves.containsKey(loc)) {
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
	
	public void addMove(Point p, Point dir){
		if(!validMoves.containsKey(p)){
			validMoves.put(p, new ArrayList<Point>());
		}
		validMoves.get(p).add(dir);
	}
	
	public List<Point> getDirs(Point p){
		return validMoves.get(p);
	}
}

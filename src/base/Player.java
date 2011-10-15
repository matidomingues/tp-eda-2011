package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Player {

	private HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point, ArrayList<Point>>();
	private HashMap<Point, ArrayList<Point>> possibleMoves;
	private int chips = 0;

	public boolean checkValid(Point loc) {
		if (validMoves.containsKey(loc)) {
			return true;
		}
		return false;
	}

	public void setNewMoves() {
		this.possibleMoves = new HashMap<Point, ArrayList<Point>>();
	}

	public void deChips() {
		this.chips -= 1;
	}

	public void inChips() {
		this.chips += 1;
	}

	public void addMove(Point p, Point dir) {
		if (!possibleMoves.containsKey(p)) {
			possibleMoves.put(p, new ArrayList<Point>());
		}
		possibleMoves.get(p).add(dir);
	}

	public void setFinalMoves() {
		validMoves = possibleMoves;
	}

	public List<Point> getDirs(Point p) {
		return validMoves.get(p);
	}

	// TODO: ver de pasar todo a set o hacer un hashmap que devuelva una lista
	// de keys

	public Set<Point> getFinalPoints() {
		return validMoves.keySet();
	}
	
	public int getChips(){
		return chips;
	}
	
	public int getMovesSize(){
		return validMoves.size();
	}
}

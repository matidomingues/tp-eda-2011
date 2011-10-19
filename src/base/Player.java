package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Player {

	// TODO
	private HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point, ArrayList<Point>>();
	private HashMap<Point, ArrayList<Point>> possibleMoves;
	private int chips = 0;
	private Cell color;

	private Player(Player data) {
		for (Point a : data.validMoves.keySet()) {
			for (Point b : data.validMoves.get(a)) {
				if (!validMoves.containsKey(a)) {

					validMoves.put(a, new ArrayList<Point>());
				}
				validMoves.get(a).add(b);
			}
		}
		this.color = data.color;
		this.chips = data.chips;
	}

	public Player(Cell color) {
		this.color = color;
	}

	public boolean checkValid(Point loc) {
		if (validMoves.containsKey(loc)) {
			return true;
		}
		return false;
	}

	public void setNewMoves() {
		this.possibleMoves = new HashMap<Point, ArrayList<Point>>();
	}
	
	public void setNewMoves(HashMap<Point, ArrayList<Point>> validMoves) {
		this.validMoves = validMoves;
	}

	public void decChips() {
		this.chips -= 1;
	}

	public void incChips() {
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

	public int getChips() {
		return chips;
	}

	public int getMovesSize() {
		return validMoves.size();
	}

	public Cell getColor() {
		return color;
	}

	public Player clone() {
		return new Player(this);

	}
	
	public Point getRandPoint(){
		for(Point a: possibleMoves.keySet()){
			return a;
		}
		return null;
	}
}

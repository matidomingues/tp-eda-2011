package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Player {

	
	private int chips = 0;
	private Cell color;
	private HashMap<Point, ArrayList<Point>> validMoves = new HashMap<Point, ArrayList<Point>>();

	private Player(Player data) {
		this.color = data.color;
		this.chips = data.chips;
	}
	
	public void setValidMoves(HashMap<Point, ArrayList<Point>> validMoves){
		this.validMoves = validMoves;
	}
	
	public int getValidMovesSize(){
		return validMoves.keySet().size();
	}

	public List<Point> getValidDirections(Point p){
		return validMoves.get(p);
	}
	public Player(Cell color) {
		this.color = color;
	}

	
	public void decChips() {
		this.chips -= 1;
	}

	public void incChips() {
		this.chips += 1;
	}

	
	public int getChips() {
		return chips;
	}


	public Cell getColor() {
		return color;
	}

	public Player clone() {
		return new Player(this);

	}

}

package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Player {

	private int chips = 0;
	private Cell color;

	private Player(Player data) {
		this.color = data.color;
		this.chips = data.chips;
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

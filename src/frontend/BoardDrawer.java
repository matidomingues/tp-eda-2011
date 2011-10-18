package frontend;

import java.awt.Color;

import javax.swing.JFrame;


import base.Board;
import base.Player;
import base.Point;
import gui.GamePanel;
import gui.GamePanelListener;

public class BoardDrawer extends JFrame {

	private GamePanel drawer;
	private Board table;
	private Player human;
	private Player artificial;
	
	public void newGame(Board table, Player human, Player enemy){
		this.drawer = new GamePanel(8,8,30,new onClickListener(),Color.YELLOW);
		this.table = table;
		this.human = human;
		this.artificial = enemy;
		setTitle("");
		setSize(500, 500);
		setResizable(false);
		add(drawer);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class onClickListener implements GamePanelListener{

		@Override
		public void onMousePress(int row, int column) {
			table.add(human, artificial, new Point(row,column));
		}
		
		
	}
	
}

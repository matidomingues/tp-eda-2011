package frontend;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;


import base.Board;
import base.Cell;
import base.Game;
import base.Player;
import base.Point;
import gui.GamePanel;
import gui.GamePanelListener;
import gui.ImageUtils;

public class BoardDrawer extends JFrame implements GUI{

	private GamePanel drawer;
	private Image[] images = new Image[3];
	private Game main;
	
	
	public void newGame(Board table, Player human, Player enemy){
		this.drawer = new GamePanel(8,8,50,new onClickListener(),Color.BLACK);
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
			
		}
	}

	@Override
	public void updatePoint(Point p, Cell color) {
		if(color == Cell.Black){
			drawer.put(images[2], p.getX(), p.getY());
		}else{
			drawer.put(images[1], p.getX(), p.getY());
		}
	
	}
	
	public void loadImage(){
		try{
			images[0] = null;
			images[1] = ImageUtils.loadImage("resources/red.jpg");
			images[2] = ImageUtils.loadImage("resources/blue.jpg");
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
}

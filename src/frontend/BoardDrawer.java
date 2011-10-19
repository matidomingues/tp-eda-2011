package frontend;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;

import base.Board;
import base.Cell;
import base.Game;
import base.MapObserver;
import base.Player;
import base.Point;
import gui.GamePanel;
import gui.GamePanelListener;
import gui.ImageUtils;

public class BoardDrawer extends JFrame implements MapObserver {

	private GamePanel drawer;
	private Image[] images = new Image[3];
	private Game main;
	private boolean pturn;

	public void newGame() {
		main = new Game();
		main.subscribe(this);

		this.drawer = new GamePanel(8, 8, 50, new onClickListener(),
				Color.WHITE);
		loadImage();
		setTitle("");
		setSize(400, 500);
		setResizable(false);
		add(drawer);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start();
	}

	private class onClickListener implements GamePanelListener {

		@Override
		public void onMousePress(int row, int column) {
			if (pturn) {
				main.add(row, column, 1);
				main.print();
				pturn = false;
			}
		}
	}

	@Override
	public void updatePoint(Point p, Cell color) {
		if (color == Cell.Black) {
			drawer.put(images[2], p.getY(), p.getX());
		} else {
			drawer.put(images[1], p.getY(), p.getX());
		}
		repaint();

	}

	public void loadImage() {
		try {
			images[0] = ImageUtils.loadImage("resources/empty.png");
			images[1] = ImageUtils.loadImage("resources/white.png");
			images[2] = ImageUtils.loadImage("resources/black.png");
		} catch (Exception e) {
			System.out.println("lala");
			e.printStackTrace();
		}

		for (int i = 0; i < 8; i++) {
			for (int w = 0; w < 8; w++) {
				drawer.put(images[0], w, i);
			}
		}

	}

	public void test() {

		main.noCheckAdd(3, 3, 2);
		for (int i = 0; i < 8; i++) {
			for (int w = 0; w < 8; w++) {
				if (!((w == 0 && i == 0) || (w == 7 && i == 0)
						|| (w == 0 && i == 7) || (w == 7 && i == 7) || (i == 3 && w == 3))) {
					main.noCheckAdd(i, w, 1);
				}
			}
		}
		// main.add(0,0,2);
		// main.add(7,0,2);
		// test.add(7,7,2);
		// test.add(0,7,2);
	}

	public void start() {
		main.noCheckAdd(3, 3, 1);
		main.noCheckAdd(3, 4, 2);
		main.noCheckAdd(4, 4, 1);
		main.noCheckAdd(4, 3, 2);
		while(!main.finished()){
			if(pturn){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				main.playAny();
				pturn = true;
			}
		}
		System.out.println("TERMINO PAPAPAPAPAPAP");
		
	}

}

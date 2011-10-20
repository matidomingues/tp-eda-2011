package frontend;

import java.awt.Color;
import java.awt.Image;

import javax.swing.JFrame;

import base.Cell;
import base.Game;
import base.MapObserver;
import base.Point;
import gui.GamePanel;
import gui.GamePanelListener;
import gui.ImageUtils;

@SuppressWarnings("serial")
public class BoardDrawer extends JFrame implements MapObserver {

	private GamePanel drawer;
	private Image[] images = new Image[3];
	private Game main;
	private boolean pturn = false;

	public BoardDrawer(Game game) {
		this.main = game;
	}

	public void newGame() {
		main.subscribe(this);

		this.drawer = new GamePanel(8, 8, 50, new onClickListener(),
				Color.WHITE);

		loadImage();
		main.initNewBoard();
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
				if (main.isValidMove(row, column)) {
					pturn = false;
					main.addAndTurn(row, column);
					System.out.println("");
					main.print();
				}
			}else{
				System.out.println("invalid click");
			}
		}
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

	@Override
	public void updatePoint(Point p, Cell color) {
		if (color == Cell.Black) {
			drawer.put(images[2], p.getY(), p.getX());
		} else {
			drawer.put(images[1], p.getY(), p.getX());
		}
		repaint();
	}

	public void start() {
		Point test = null;
		boolean pmoves= false , emoves= false;
		main.moves(1);
		pmoves = main.gotMoves();
		pturn = true;
		while (pmoves || emoves) {
			if (pturn) {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(!pmoves){
					pturn = false;
				}
			} else {
				main.moves(2);
				emoves = main.gotMoves();
				System.out.println(test);
				main.playAny();
				main.moves(1);
				pmoves = main.gotMoves();
				pturn = true;
				System.out.println("");
				main.print();
			}
		}

	}

}

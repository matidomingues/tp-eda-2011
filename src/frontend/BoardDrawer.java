

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

public class BoardDrawer extends JFrame implements MapObserver
	{
		
		private GamePanel	drawer;
		private Image[]		images	= new Image[3];
		private Game		main;
		private boolean		pturn;
		
		public void newGame ()
			{
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
		
		private class onClickListener implements GamePanelListener
			{
				
				@Override
				public void onMousePress (int row, int column)
					{
						if (pturn)
							{
								main.add(row, column, 1);
								main.print();
								pturn = false;
							}
					}
			}
		
		@Override
		public void updatePoint (Point p, Cell color)
			{
				if (color == Cell.Black)
					{
						drawer.put(images[2], p.getY(), p.getX());
					}
				else
					{
						drawer.put(images[1], p.getY(), p.getX());
					}
				repaint();
				
			}
		
		public void loadImage ()
			{
				try
					{
						images[0] = ImageUtils.loadImage("resources/empty.png");
						images[1] = ImageUtils.loadImage("resources/white.png");
						images[2] = ImageUtils.loadImage("resources/black.png");
					} catch (Exception e)
					{
						System.out.println("lala");
						e.printStackTrace();
					}
				
				for ( int i = 0; i < 8; i++)
					{
						for ( int w = 0; w < 8; w++)
							{
								drawer.put(images[0], w, i);
							}
					}
				
			}
		
		public void test ()
			{
				
				main.noCheckAdd(3, 3, 2);
				for ( int i = 0; i < 8; i++)
					{
						for ( int w = 0; w < 8; w++)
							{
								if (!((w == 0 && i == 0) || (w == 7 && i == 0)
										|| (w == 0 && i == 7)
										|| (w == 7 && i == 7) || (i == 3 && w == 3)))
									{
										main.noCheckAdd(i, w, 1);
									}
							}
					}
				// main.add(0,0,2);
				// main.add(7,0,2);
				// test.add(7,7,2);
				// test.add(0,7,2);
			}
		
		public void start ()
			{
				main.noCheckAdd(3, 3, 1);
				main.noCheckAdd(3, 4, 2);
				main.noCheckAdd(4, 4, 1);
				main.noCheckAdd(4, 3, 2);
				while (!main.finished())
					{
						if (pturn)
							{
								try
									{
										Thread.sleep(10);
									} catch (InterruptedException e)
									{
										e.printStackTrace();
									}
							}
						else
							{
								main.playAny(0);
								pturn = true;
							}
					}
				System.out.println("TERMINO PAPAPAPAPAPAP");
				
			}
		
		public void matiTest ()
			{
				/*for ( int i = 0; i < 10000; i++)
					{
						main = new Game();
						start2();
						
					}*/
				for ( int i = 0; i < 10000; i++)
					{
						main = new Game();
						start3();
						
					}
			}
		
		public void start3(){
			main.add(3, 3, Cell.Black);
			main.add(3, 4, Cell.White);
			main.add(4, 4, Cell.Black);
			main.add(4, 3, Cell.White);
			
			int i = 0;
			main.playAny(Cell.Black);
			main.playAny(Cell.White);
			while (!main.finished())
				{
					if (i == 0)
						{
							main.playAny(Cell.Black);
							i++;
						}
					else
						{
							main.playAny(Cell.White);
							i--;
						}

				}
			//main.print();
		}
		
		public void start2 ()
			{
				main.noCheckAdd(3, 3, 1);
				main.noCheckAdd(3, 4, 2);
				main.noCheckAdd(4, 4, 1);
				main.noCheckAdd(4, 3, 2);
				int i = 0;
				while (!main.finished())
					{
						if (i == 0)
							{
								main.playAny(i);
								i++;
							}
						else
							{
								main.playAny(i);
								i--;
							}

					}
				//main.print();
			}
	}

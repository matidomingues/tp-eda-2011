package jdungeon.frontend.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

/**
 * Panel que representa una grilla de imágenes, siendo posible agregarle y
 * quitarle imágenes. Asimismo, cuenta con una interfaz que permite a quien la
 * utilice ser notificada cuando el usuario posiciona el mouse sobre una celda
 * de la grilla.
 */
public class GamePanel extends JPanel {

	private int rows, columns;
	private int cellSize;
	private Color color;
	private Image[][] images;

	/**
	 * Crea un nuevo panel con las dimensiones indicadas.
	 * 
	 * @param rows
	 *            Cantidad de filas.
	 * @param columns
	 *            Cantidad de columnas.
	 * @param cellSize
	 *            Ancho y alto de cada imagen en píxeles.
	 * @param listener
	 *            Listener que será notificado cuando el usuario se posicione
	 *            sobre una celda de la grilla.
	 * @param color
	 *            Color de fondo del panel.
	 */
	public GamePanel(final int rows, final int columns, final int cellSize,
			final GamePanelListener listener, Color color) {
		setSize(columns * cellSize, rows * cellSize);
		images = new Image[rows][columns];
		this.rows = rows;
		this.columns = columns;
		this.cellSize = cellSize;
		this.color = color;

		addMouseMotionListener(new MouseMotionAdapter() {

			private Integer currentRow;
			private Integer currentColumn;

			@Override
			public void mouseMoved(MouseEvent e) {
				int row = e.getY() / cellSize;
				int column = e.getX() / cellSize;
				if (row >= rows || column >= columns || row < 0 || column < 0) {
					return;
				}

				if (!nullSafeEquals(currentRow, row)
						|| !nullSafeEquals(currentColumn, column)) {
					currentRow = row;
					currentColumn = column;
					listener.onMouseMoved(row, column);
				}
			}

			private boolean nullSafeEquals(Object o1, Object o2) {
				return o1 == null ? o2 == null : o1.equals(o2);
			}
		});
	}

	/**
	 * Ubica una imagen en la fila y columna indicadas.
	 */
	public void put(Image image, int row, int column) {
		images[row][column] = image;
	}

	/**
	 * Elimina la imagen ubicada en la fila y columna indicadas.
	 */
	public void clear(int row, int column) {
		images[row][column] = null;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
		g.fillRect(0, 0, columns * cellSize, rows * cellSize);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (images[i][j] != null) {
					g.drawImage(images[i][j], i * cellSize, j * cellSize, null);
				}
			}
		}
	}
}
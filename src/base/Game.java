package base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Clase Game que modela el comportamiento de un juego
 * Es una clase abstracta a la cual la extiende GameDepth y GameTime
 * ellas implementan el metodo abstracto miniMax y son las que instancian un Game
 */
public abstract class Game {
	protected Board board;
	protected Cell currentPlayer;
	protected int[][] heuristic;
	protected int n;
	protected boolean prune = false;
	protected boolean treeMode = false;
	protected MapObserver observer;
	protected HashMap<Point, ArrayList<Point>> currentPlayerValidMoves = new HashMap<Point, ArrayList<Point>>();
	protected BufferedWriter gv;

	/**
	 * Metodo miniMax que retorna el punto que considera la mejor jugada
	 * @param board tablero actual
	 * @param depth profundidad del arbol miniMax, o tiempo maximo
	 * @param player Jugador que va a mover
	 * @return Point retorna el punto que indica la mejor jugada
	 */
	public abstract Point miniMax(Board board, int depth, Cell player);

	/**
	 * Metodo que setea a modo prune
	 * Para que el algoritmo miniMax genere arbol con poda
	 * @param value valor booleano a setear
	 */
	public void setPrune(boolean value) {
		this.prune = value;
	}

	/**
	 * Metodo que setea a modo tree
	 * Para que el algorimo miniMax genere un dot con el dibujo del mismo 
	 * @param value valor booleano a setear
	 */
	public void setTreeMode(boolean value) {
		this.treeMode = value;
	}

	
	/**
	 * Metodo que llama a addAndTurn de la clase Board
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	public void addAndTurn(int x, int y) {
		board.addAndTurn(new Point(x, y), Cell.Black, currentPlayerValidMoves
				.get(new Point(x, y)));
	}

	/**
	 * Metodo que setea el Observer del Board de Game
	 * @param observer valor a setear
	 */
	public void subscribe(MapObserver observer) {
		board.setObserver(observer);
	}

	/** Metodo que notifica los cambios de color a main
	 * para dibujarlos en el tablero
	 * @param p punto el cual cambio
	 * @param color nuevo color
	 */
	public void notifyChange(Point p, Cell color) {
		observer.updatePoint(p, color);
	}

	/**
	 * Metodo que se encarga de instanciar un tablero cuando se juega en modo -file
	 * parsea el archivo y lanza excepcion en caso de que sea invalido
	 * o en caso de que haya algun problema en la lectura del mismo
	 * @param filePath path del archivo
	 * @return Board tablero cargado a partir del archivo
	 * @throws Exception En caso de que sea archivo invalido
	 */
	public Board load(String filePath) throws Exception {
		File file;
		BufferedReader game;
		file = new File(filePath);
		game = new BufferedReader(new FileReader(file));
		String line;
		line = game.readLine();
		Board gameBoard = new Board(heuristic);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (line.charAt(j) == '1') {
					gameBoard.add(i, j, Cell.Black);
				} else if (line.charAt(j) == '2') {
					gameBoard.add(i, j, Cell.White);
				} else if (line.charAt(j) == ' ') {
				} else {
					throw new Exception();
				}
			}
			line = game.readLine();
		}

		return gameBoard;
	}

	/**
	 * Metodo que crea e inicializa la matriz heuristica
	 * A partir de la cual se va a evualuar a los tableros
	 * @return int[][] matrix heuristica
	 */
	public int[][] createHeuristic() {
		int[][] board = new int[8][8];
		for (int i = 2; i <= 5; i++) {
			for (int j = 2; j <= 5; j++) {
				board[i][j] = 10;
			}
		}
		board[2][2] = board[2][5] = board[5][2] = board[5][5] = 20;
		
		for (int j = 2; j <= 5; j++) {
			board[1][j] = -5;
			board[6][j] = -5;
		}
		for (int i = 2; i <= 5; i++) {
			board[i][1] = -5;
			board[i][6] = -5;
		}
		
		
		board[0][0] = board[0][7] = board[7][0] = board[7][7] = 1300;
		board[1][1] = board[1][6] = board[6][1] = board[6][6] = -800;
		board[0][1] = board[0][6] = board[1][0] = board[1][7] = -700;
		board[7][1] = board[7][6] = board[6][0] = board[6][7] = -700;
		board[0][2] = board[0][5] = board[2][0] = board[2][7] = 200;
		board[5][0] = board[5][7] = board[7][2] = board[7][5] = 200;
		for (int j = 3; j <= 4; j++) {
			board[0][j] = 150;
			board[7][j] = 150;
		}
		for (int i = 3; i <= 4; i++) {
			board[i][0] = 150;
			board[i][7] = 150;
		}

		System.out.println("HEURISTICA");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + "  ");

			}
			System.out.println("");
		}
		return board;
	}

	/**
	 * Metodo que se encarga de llamar a los metodos necesarios
	 * para que juegue la computadora
	 */
	public void play() {
		Point pointToPlay = miniMax(board, n, currentPlayer);
		System.out.println("POINT TO PLAY:  " + pointToPlay);
		if (pointToPlay != null) {
			board.addAndTurn(pointToPlay, Cell.White, currentPlayerValidMoves
					.get(pointToPlay));
		}
	}

	/** Metodo que llama a initNewBoard de Board
	 * que se encarga de inicializar un tablero de una nueva partida
	 */
	public void initNewBoard() {
		this.board.initNewBoard();
	}

	/**
	 * metodo que setea el currentPlayer
	 * carga en currentPlayerValidMoves los movimientos del jugador actual
	 * @param player
	 */
	public void moves(int player) {
		if (player == 1) {
			currentPlayer = Cell.Black;
			currentPlayerValidMoves = board.moves(Cell.Black);
		} else {
			currentPlayer = Cell.White;
			currentPlayerValidMoves = board.moves(Cell.White);
		}
	}

	/** Metodo que retorna true en caso de que el punto
	 * pasado como parametro sea valido para un movimiento del jugador actual
	 * @param x fila
	 * @param y columna
	 * @return boolean true en caso de que sea valido, false en caso contrario
	 */
	public boolean isValidMove(int x, int y) {
		if (currentPlayerValidMoves.containsKey(new Point(x, y))) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo que llama a printMap de la clase Board
	 */
	public void print() {
		board.printMap(Cell.White, Cell.Black);
	}

	/**
	 * Metodo que se fija si el jugador actual tiene movimientos
	 * @return boolean true en caso de que tenga posibles movimientos
	 */
	public boolean gotMoves() {
		if (currentPlayerValidMoves.keySet().size() != 0) {
			return true;
		}
		return false;
	}

	/** Metodo que setea el jugador actual
	 * @param player jugador a setear
	 * @throws Exception en caso de que sea un jugador invalido
	 */
	public void setPlayer(int player) throws Exception {
		if (player == 1) {
			currentPlayer = Cell.Black;
		} else if (player == 2) {
			currentPlayer = Cell.White;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/** Getter del Board
	 * @return Board board
	 */
	public Board getBoard() {
		return board;
	}

	/** Getter del jugador actual
	 * @return Cell currentPlayer
	 */
	public Cell getCurrentPlayer() {
		return currentPlayer;
	}

	/** Getter de N, que es la profundidad del arbol minimax
	 * o es el tiempo maximo
	 * @return int n
	 */
	public int getN() {
		return n;
	}

	/** Metodo que retorna true en caso de que el juego haya terminado
	 * false en caso contrario
	 * @param board Board
	 * @return true en caso de que no haya mas movimientos por hacer
	 */
	public boolean gameEnded(Board board) {
		return board.gameEnded();
	}

	/** Metodo que retorna el jugador ganador
	 * Y Empty en caso de empate
	 * @return Cell ganador
	 */
	public Cell winner() {
		return board.winner();
	}

	/** Metodo que abre el archivo dot y escribe la primera linea
	 * @param num Integer
	 */
	protected void startWritter(Integer num) {
		FileWriter fstream;
		try {
			if (num == null) {
				fstream = new FileWriter("tree.dot");

			} else {
				fstream = new FileWriter("tree" + num + ".dot");
			}
			BufferedWriter out = new BufferedWriter(fstream);
			this.gv = out;
			gv.write("digraph G {");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** Metodo que agrega una linea al dot
	 * @param line linea a agregar
	 */
	protected void addLine(String line) {
		try {
			gv.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Metodo que cierra el archivo dot */
	protected void end() {
		try {
			gv.write("}");
			gv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Clase que modela el comportamiento de un nodo para el minimax
	 * el punto a jugar el valor heuristico del mismo
	 * tiene un booleano para saber si es un nodo podado
	 */
	protected static class Node {
		private Integer id;
		private Point p;
		private Integer heuristic;
		private boolean isGrey = false;
		private boolean ismin;

		/** Constructor de la clase Node
		 * @param id Integer id del node
		 * @param p punto a jugar
		 * @param heuristic valor heuristico del nodo
		 * @param min booleano si es nodo minimo o maximo
		 */
		public Node(Integer id, Point p, Integer heuristic, boolean min) {
			this.p = p;
			this.id = id;
			this.heuristic = heuristic;
			if (heuristic == null) {
				isGrey = true;
			}
			this.ismin = min;
		}
	}

	/** Metodo que agrega al Dot un punto y la lista de nodos
	 * @param p point
	 * @param data lista de nodos
	 */
	protected void addToDot(Point p, List<Node> data) {
		for (Node a : data) {
			if (a.isGrey) {
				if (a.ismin) {
					addLine(a.id
							+ " [label = \"("
							+ a.p
							+ "\",shape = ellipse, fillcolor=grey, style=filled];");
				} else {
					addLine(a.id + " [label = \"(" + a.p
							+ "\",shape = box, fillcolor=grey, style=filled];");
				}
			} else if (p == null) {
				if (a.ismin) {
					addLine(a.id + " [label = \"(" + a.p + ") " + a.heuristic
							+ "\",shape = ellipse];");
				} else {
					addLine(a.id + " [label = \"(" + a.p + ") " + a.heuristic
							+ "\",shape = box];");

				}

			} else if (p.equals(a.p)) {
				if (a.ismin) {
					addLine(a.id
							+ " [label = \"("
							+ a.p
							+ ") "
							+ a.heuristic
							+ "\",shape = ellipse, fillcolor = red, style = filled];");
				} else {
					addLine(a.id
							+ " [label = \"("
							+ a.p
							+ ") "
							+ a.heuristic
							+ "\",shape = box, fillcolor = red, style = filled];");
				}
			} else {
				if (a.ismin) {
					addLine(a.id + " [label = \"(" + a.p + ") " + a.heuristic
							+ "\",shape = ellipse];");
				} else {
					addLine(a.id + " [label = \"(" + a.p + ") " + a.heuristic
							+ "\",shape = box];");

				}
			}
		}
	}

	/** Metodo que cambia el nombre del archivo
	 * @param lastlevel int ultimo nivel
	 */
	protected void changeName(int lastlevel) {
		File data = new File("tree" + lastlevel + ".dot");
		File newfile = new File("tree.dot");
		data.renameTo(newfile);

	}

	/** Metodo que elimina archivos
	 * @param num int level del archivo a eliminar
	 */
	protected void deleteFiles(int num) {
		for (int i = 0; i <= num; i++) {
			File f = new File("tree" + i + ".dot");
			if (f.exists()) {
				f.delete();
			}
		}
	}
}

package base;

import java.util.ArrayList;
import java.util.List;

/** Clase que modela un juego por tiempo
 *  Extiende a la clase Game e implementa el metodo miniMax 
 */
public class GameTime extends Game {

	private long maxTime;

	/** Constructor de la clase GameTime
	 * @param filePath path del archivo en caso de que sea modo -file
	 * @param time int tiempo maximo a tardar para retornar la mejor jugada
	 * @throws Exception En caso de problemas con el archivo
	 */
	public GameTime(String filePath, int time) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.n = 1000 * time;
	}
	
	/** Metodo miniMax que retorna un punto
	 * donde considera la mejor jugada a realizar
	 * @param board Board tablero actual
	 * @param time int tiempo maximo para retornar la mejor jugada
	 * @param player Cell jugador max
	 * @return Point punto donde considera la mejor jugada 
	 */
	public Point miniMax(Board board, int time, Cell player) {
		maxTime = time + System.currentTimeMillis();
		Point answer = null;
		Point aux = null;
		Integer lastlevel = null;
		int step = 1;
		while (System.currentTimeMillis() < maxTime) {
			if (treeMode) {
				startWritter(step);
			}
			Board child = board.clone();
			aux = miniMaxMax(child, step, player);
			if (aux != null) {
				lastlevel = step;
				answer = aux;
			}
			step++;
			if (board.emptySlots() - (step - 1) <= 0) {
				if (treeMode) {
					System.out.println("printing level " + lastlevel);
					deleteFiles(step);
					changeName(lastlevel);
				}
				return answer;
			}
		}
		if (treeMode) {
			System.out.println("printing level " + lastlevel);
			deleteFiles(step);
			changeName(lastlevel);
		}
		return answer;
	}

	private Point miniMaxMax(Board board, int depth, Cell player) {

		if (System.currentTimeMillis() > maxTime) {
			return null;
		}
		List<Node> data = null;
		if (treeMode) {
			data = new ArrayList<Node>();
		}
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		for (Point p : board.moves(player).keySet()) {
			Board child = board.clone();
			if (treeMode) {
				addLine(board.hashCode() + " -> " + child.hashCode());
				child.add(p.getX(), p.getY(), player);
			}
			int euristicPoint = minimax(child, depth - 1, euristic,
					Integer.MAX_VALUE, player.oposite());

			if (treeMode) {
				data.add(new Node(child.hashCode(), p, euristicPoint, true));
			}

			if (euristic <= euristicPoint) {
				point = p;
				euristic = euristicPoint;
			}
		}
		if (treeMode) {
			addToDot(point, data);
			addLine(board.hashCode() + " [label = \"null " + euristic
					+ "\", shape = box, style = filled, fillcolor = red];");
			end();
		}
		return point;

	}

	private int minimax(Board board, int depth, int alpha, int beta, Cell player) {
		if (System.currentTimeMillis() > maxTime) {
			return 0;
		}
		if (depth == 0 || gameEnded(board)) {
			return board.evaluateBoard(currentPlayer);
		}
		if (player == currentPlayer) {
			return goDown(board, player, alpha, beta, depth, false);
		} else {
			return goDown(board, player, alpha, beta, depth, true);
		}
	}

	private int goDown(Board board, Cell player, int alpha, int beta,
			int depth, boolean ismin) {
		Integer local = null;
		List<Node> data = new ArrayList<Node>();
		Point finalp = null;
		boolean isgrey = false;
		for (Point p : board.moves(player).keySet()) {
			Board child = board.clone();
			if (treeMode) {
				addLine(board.hashCode() + " -> " + child.hashCode());
				child.add(p.getX(), p.getY(), player);
			}
			if (treeMode && isgrey) {
				if (ismin) {
					data.add(new Node(child.hashCode(), p, null, false));

				} else {
					data.add(new Node(child.hashCode(), p, null, true));
				}
			} else {
				int aux = minimax(child, depth - 1, alpha, beta, player
						.oposite());
				if (local == null) {
					local = aux;
					finalp = p;
				} else if (ismin && local > aux) {
					local = aux;
					finalp = p;
				} else if (!ismin && local < aux) {
					local = aux;
					finalp = p;
				}
				if (ismin && beta > aux) {
					beta = aux;
				} else if (!ismin && alpha < aux) {
					alpha = aux;
				}
				if (treeMode) {
					if (ismin) {
						data.add(new Node(child.hashCode(), p, aux, false));
					} else {
						data.add(new Node(child.hashCode(), p, aux, true));

					}
				}

			}
			if (prune && (beta <= alpha)) {
				isgrey = true;
			}
		}
		if (local == null) {
			local = minimax(board, depth - 1, alpha, beta, player.oposite());
		} else if (treeMode) {
			addToDot(finalp, data);
		}
		return local;
	}
}

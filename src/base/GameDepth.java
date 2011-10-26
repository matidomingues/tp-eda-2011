package base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameDepth extends Game {

	BufferedWriter gv;

	public GameDepth(String filePath, int depth) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.n = depth;
	}

	public Point miniMax(Board board, int depth, Cell player) {
		startWritter();
		List<Node> data = new ArrayList<Node>();
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		for (Point p : board.moves(player).keySet()) {
			Board child = board.clone();
			addLine(board.hashCode() + " -> " + child.hashCode());
			child.add(p.getX(), p.getY(), player);
			
			int euristicPoint = minimax(child, depth - 1, euristic,
					Integer.MAX_VALUE, player.oposite());
			
			data.add(new Node(child.hashCode(), p, euristicPoint, true));

			if (euristic < euristicPoint) {
				point = p;
				euristic = euristicPoint;
			}
		}
		addToDot(point, data);

		addLine(board.hashCode() + " [label = \"null " + euristic
				+ "\", shape = box, style = filled, fillcolor = red];");
		end();
		return point;

	}

	private int minimax(Board board, int depth, int alpha, int beta, Cell player) {
		Point finalp = null;
		List<Node> data = new ArrayList<Node>();
		boolean isgrey = false;
		if (depth == 0 || gameEnded(board)) {
			return board.evaluateBoard(currentPlayer);
		}
		if (player == currentPlayer) {
			for (Point p : board.moves(player).keySet()) {
				Board child = board.clone();
				addLine(board.hashCode() + " -> " + child.hashCode());
				child.add(p.getX(), p.getY(), player);
				if (isgrey) {
					data.add(new Node(child.hashCode(), p, null, true));
				} else {
					int aux = minimax(child, depth - 1, alpha, beta, player
							.oposite());

					if (alpha < aux) {
						alpha = aux;
						finalp = p;
					}
					data.add(new Node(child.hashCode(), p, aux, true));
				}
				if (prune && (beta <= alpha)) {
					isgrey = true;

				}
			}
			addToDot(finalp, data);
			return alpha;
		} else {
			for (Point p : board.moves(player).keySet()) {
				System.out.println("lastbeta"+alpha + " " + beta );
				Board child = board.clone();
				addLine(board.hashCode() + " -> " + child.hashCode());
				child.add(p.getX(), p.getY(), player);

				if (isgrey) {
					data.add(new Node(child.hashCode(), p, null, false));

				} else {
					int aux = minimax(child, depth - 1, alpha, beta, player
							.oposite());

					if (beta > aux) {
						beta = aux;
						finalp = p;
					}
					data.add(new Node(child.hashCode(), p, aux, false));

				}
				if (prune && (beta <= alpha)) {
					isgrey = true;
				}
			}
			addToDot(finalp, data);
			return beta;
		}
		
	}

	public void startWritter() {
		FileWriter fstream;
		try {
			fstream = new FileWriter("resources/dot.dot");
			BufferedWriter out = new BufferedWriter(fstream);
			this.gv = out;
			gv.write("digraph G {");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void addLine(String line) {
		try {
			gv.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void end() {
		try {
			gv.write("}");
			gv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class Node {
		private Integer id;
		private Point p;
		private Integer heuristic;
		private boolean isGrey = false;
		private boolean ismin;

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

	public void addToDot(Point p, List<Node> data) {
		System.out.println(p);
		for (Node a : data) {
			if (a.isGrey) {
				if(a.ismin){
					addLine(a.id + " [label = \"(" + a.p+ "\",shape = ellipse, fillcolor=grey, style=filled];");	
				}else{
					addLine(a.id + " [label = \"(" + a.p+ "\",shape = box, fillcolor=grey, style=filled];");
				}
			}else if(p == null){
				if(a.ismin){
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = ellipse];");
				}else{
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = box];");

				}
						
			} else if (p.equals(a.p)) {
				if(a.ismin){
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = ellipse, fillcolor = red, style = filled];");
				}else{
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = box, fillcolor = red, style = filled];");
				}
			}else{
				if(a.ismin){
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = ellipse];");
				}else{
					addLine(a.id+" [label = \"("+a.p+ ") "+ a.heuristic+ "\",shape = box];");

				}
			}
		}
	}
}

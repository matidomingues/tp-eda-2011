package base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import testbiz.GraphViz;

public class GameDepth extends Game {

	BufferedWriter gv;
	GraphViz vz = new GraphViz();
	
	public GameDepth(String filePath, int depth) throws Exception {
		this.heuristic = this.createHeuristic();
		if (filePath == null) {
			this.board = new Board(heuristic);
		} else {
			this.board = this.load(filePath);
		}
		this.n = depth;
	}
	
	public Point miniMax(Board board, int depth, Cell player){
		startWritter();
		Point point = null;
		int euristic = Integer.MIN_VALUE;
		int i = 0;
		System.out.println("MIN_VALUE: " + Integer.MIN_VALUE);
		for(Point p: board.moves(player).keySet()){
			System.out.println("nodo: " + i++);
			System.out.println("Point: " + p);
			Board child = board.clone();
			addLine(board.hashCode() + " -> "+child.hashCode());
			child.add(p.getX(), p.getY(), player);
			int euristicPoint = minimax(child,depth-1,Integer.MIN_VALUE,Integer.MAX_VALUE, player.oposite());
			addLine(child.hashCode() + " [label = \"(" + p + ") " + euristicPoint+ "\", shape = ellipse];");
			if(euristic <= euristicPoint){
				point = p;
				euristic = euristicPoint;
			}			
		}
		addLine(board.hashCode() + " [label = \"null " + euristic + "\", shape = box];");
		end();
		return point;		
		
	}
	
	private int minimax(Board board,int depth,int alpha,int beta, Cell player){
		if(depth == 0 || gameEnded(board)){
			return board.evaluateBoard(player);
		}
		if(player == currentPlayer){
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				addLine(board.hashCode() + " -> "+ child.hashCode());
				child.add(p.getX(), p.getY(), player);
				alpha = Math.max(alpha, minimax(child,depth-1,alpha,beta,player.oposite()));
				if(prune && (beta < alpha)){
					addLine(child.hashCode() + " [label = \"(" + p + ")\",shape = ellipse, fillcolor=grey, style=filled];");
					break;
				}else{
					addLine(child.hashCode() + " [label = \"(" + p + ") " + alpha+ "\",shape = ellipse];");
				}
			}
			return alpha;
		}
		else{
			for(Point p: board.moves(player).keySet()){
				Board child = board.clone();
				addLine(board.hashCode() + " -> "+ child.hashCode());
				child.add(p.getX(), p.getY(), player);
				beta = Math.min(beta, minimax(child,depth-1,alpha,beta,player.oposite()));

				if(prune && (beta < alpha)){
					addLine(child.hashCode() + " [label = \"(" + p + ")\", shape = box, fillcolor=grey, style=filled];");
					break;
				}else{
					addLine(child.hashCode() + " [label = \"(" + p + ") " + beta + "\", shape = box];");
				}
			}
			return beta;
		}
	}
	
	public void startWritter(){
		  FileWriter fstream;
		try {
			fstream = new FileWriter("resources/dot.dot");
			  BufferedWriter out = new BufferedWriter(fstream);
			  this.gv = out;
			  gv.write("digraph G {\n");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void addLine(String line){
		try {
			gv.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void end(){
		try {
			gv.write("}");
			gv.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	
	
	
	
	
	
}

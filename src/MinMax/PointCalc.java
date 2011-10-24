package MinMax;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import testbiz.GraphViz;

import base.Board;
import base.Cell;

import base.Point;

public class PointCalc {

	protected Cell currentPlayer;
	
	public Point getPointByDepth(Board board, int depth, Cell actual, Cell enemy) {
		this.currentPlayer = actual;
		Integer aux = null, value = null;
		Point actualMax = null;
		for(Point p: board.moves(actual).keySet()){
			System.out.println(p);
		}
		Node main = new Node(board,null);
		
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			Node nodeaux = new Node(newboard,p);
			aux = minDepth(newboard, depth - 1, null, enemy, actual, nodeaux);
			nodeaux.value = aux;
			main.sig.add(nodeaux);
			
			System.out.println(" aux papa: " + aux);
			if (value == null) {
				value = aux;
				actualMax = p;
			}else if (value < aux) {
				value = aux;
				actualMax = p;
			}
		}
		printminmax(main);
		return actualMax;
	}

	public Integer minDepth(Board board, int depth, Integer value, Cell actual,
			Cell enemy, Node head) {
		Integer aux = null, localmin = null;
		if (depth == 0) {
			return board.evaluateBoard(actual);
		}
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			Node nodeaux = new Node(newboard,p);
			aux = maxDepth(newboard, depth - 1, value, enemy, actual, nodeaux);
			nodeaux.value = aux;
			head.sig.add(nodeaux);
			if(localmin == null || localmin > aux ){
				localmin = aux;
			}
			
			if(depth != 1 && (value == null || localmin < value) ){
				value = localmin;
			} else if(value != null && localmin < value){
				return localmin;
			}
			
			
			
		}
		if(localmin == null){
			if(board.moves(enemy).keySet().size() != 0){
				localmin = maxDepth(board,depth-1,value,enemy,actual, head);
			}else{
				return Integer.MAX_VALUE;
			}
		}
		return localmin;
	}

	public Integer maxDepth(Board board, int depth, Integer value, Cell actual,
			Cell enemy, Node head) {
		Integer aux = null, localmax = null;
		if (depth == 0) {
			return board.evaluateBoard(actual);
		}
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			Node nodeaux = new Node(newboard, p);
			aux = minDepth(newboard, depth - 1, value, enemy, actual, nodeaux);
			nodeaux.value = aux;
			head.sig.add(nodeaux);
			if (value == null) {
				value = aux;
			} else if (aux > value) {
				value = aux;
			}
		
			

			if(localmax == null || localmax < aux ){
				localmax = aux;
			}
			
			if(depth != 1 && (value == null || localmax > value) ){
				value = localmax;
			} else if(value != null && localmax > value){
				return localmax;
			}
			
			
			
			
		}
		if(localmax  == null){
			if(board.moves(enemy).keySet().size() != 0){
				localmax = minDepth(board,depth-1,value,enemy,actual, head);
			}else{
				return 2000;
			}
		}
		return value;
	}

	
	private static class Node{
		Board board;
		Point p;
		Integer value;
		List<Node> sig;

		public Node(Board board, Point p){
			this.board = board;
			this.p = p;

			this.sig = new ArrayList<Node>();
		}
	}
	
	 public void printminmax(Node head){
		 GraphViz gv = new GraphViz();
		 
	      gv.addln(gv.start_graph());
	      printminmax(head,gv);
	      gv.addln(gv.end_graph());
	      System.out.println(gv.getDotSource());
	   
	      File out = new File("c:/dot.dot");    // Windows
	      gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), ".dot" ), out );
	 
	 }
	
	private void printminmax(Node head, GraphViz gv){
		if(head == null){
			return;
		}
		for(Node a: head.sig){
			if(head.p == null){
				gv.addln("null" + "b" + a.value +  " -> " + a.p.getX()+"a"+ a.p.getY() + "b" + a.value+ ";" );	
			}else{
				gv.addln(head.p.getX()+"a"+head.p.getY() + "b" + a.value +  " -> " + a.p.getX()+"a"+ a.p.getY() + "b" + a.value+ ";" );
			}
		
			printminmax(a, gv);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Point getPointByTime(Board board, int time, Cell actual, Cell enemy) {
		Point actualBest = null, actualaux = null;
		int i = 1;
		boolean endcicle = false;
		long starttime = System.currentTimeMillis(), lastduration;
		while (!endcicle) {
			actualaux = getPointByDepth(board, i, actual, enemy);
			lastduration = starttime - System.currentTimeMillis();
			if (lastduration < 0) {
				endcicle = true;
			} else if ((time - lastduration) < 0) {
				endcicle = true;
			} else {
				actualBest = actualaux;
			}
		}
		return actualBest;
	}

}

package MinMax;

import java.util.ArrayList;
import java.util.List;



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
		return actualMax;
	}

	
	
	
	
	
	public Integer minDepth(Board board, int depth, Integer value, Cell actual,
			Cell enemy, Node head) {
		Integer aux = null, localmin = null;
		if (depth == 0) {
			return board.evaluateBoard(currentPlayer);
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
			return board.evaluateBoard(currentPlayer);
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

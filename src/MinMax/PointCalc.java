package MinMax;

import base.Board;
import base.Cell;

import base.Point;

public class PointCalc {

	public Point getPointByDepth(Board board, int depth, Cell actual, Cell enemy) {
		Integer aux = null, value = null;
		Point actualMax = null;
		for(Point p: board.moves(actual).keySet()){
			System.out.println(p);
		}
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			aux = minDepth(newboard, depth - 1, null, enemy, actual);
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
			Cell enemy) {
		int aux;
		if (depth == 0) {
			return board.evaluateBoard(actual);
		}
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			aux = maxDepth(newboard, depth - 1, value, enemy, actual);
			if (value == null) {
				value = aux;
			} else if (aux < value) {
				value = aux;
			}

		}
		if(value == null){
			if(board.moves(enemy).keySet().size() != 0){
				value = maxDepth(board,depth-1,value,enemy,actual);
			}else{
				return 2000;
			}
		}
		return value;
	}

	public Integer maxDepth(Board board, int depth, Integer value, Cell actual,
			Cell enemy) {
		Integer aux = null;
		if (depth == 0) {
			return board.evaluateBoard(actual);
		}
		for (Point p : board.moves(actual).keySet()) {
			Board newboard = board.clone();
			newboard.add(p.getX(), p.getY(), actual);
			aux = minDepth(newboard, depth - 1, value, enemy, actual);
			if (value == null) {
				value = aux;
			} else if (aux > value) {
				value = aux;
			}

		}
		if(value == null){
			if(board.moves(enemy).keySet().size() != 0){
				value = minDepth(board,depth-1,value,enemy,actual);
			}else{
				return 2000;
			}
		}
		return value;
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

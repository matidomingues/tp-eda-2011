package base;

public class Game {

	private Board board;
	private Player currentPlayer;
	private MapObserver observer;
	
	public void subscribe(MapObserver observer){
		this.observer = observer;
	}
	
	public void notifyChange(Point p, Cell color){
		observer.updatePoint(p, color);
	}
	
}

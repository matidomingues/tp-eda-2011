package frontend;

public class Main {

	public static void main(String[] args) {	
		BoardDrawer test = new BoardDrawer();
		long starttime = System.currentTimeMillis();
		test.newGame();
		System.out.println(System.currentTimeMillis() - starttime);
	}
}

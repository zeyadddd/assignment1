package adversarialsearch;

public class Main {
	public static void main(String[] args) {
		
		State gameState = new State();
		
		gameState.read("data/board.txt");
		
		State newState = gameState.copy();
		
		System.out.println(gameState);
		
		System.out.println(newState);
		
		Game g=new Game();
		
		
		g.test();
	}
}

package adversarialsearch;
import java.io.*;
import java.util.Vector;


public class State {
	
	private char [][] board;
	
	private int[] agentX;
	
	private int [] agentY;
	
	private int [] score;
	
	private int turn;
	
	private int food;
	
	private Vector<String> moves;
	
	//  default constructor
	public State() {
		
		moves = new Vector <>();
		
	}
	
	public void read (String filename) {
		
		try {
			
			BufferedReader file = new BufferedReader(new FileReader(filename));
			
			// read the board dimensions from the first line
			String[] dimensions = file.readLine().split(" ");
			
			// first index as width and second as height
			int board_width = Integer.parseInt(dimensions[0]);
			
			int board_height = Integer.parseInt(dimensions[1]);
			
			// initialize the board 2D array 
			board = new char[board_height][board_width];
			
			// arrays to store the agents coordinates
			agentX = new int[2];
			
			agentY = new int[2];
			
			score = new int[2];
			
			food = 0;
			
			// loop to iterate over each row
			for (int i = 0; i < board_height; i++) {
				
				String line = file.readLine();
				
				// loop to iterate over each column
				for (int j = 0; j < board_width; j++) {
					
					board[i][j] = line.charAt(j);
					
					// if it is the starting position of agent A 
					if (board[i][j] == 'A') {
						
						agentX[0] = j;
						
						agentY[0] = i;
					}
					
					// if it is the starting position of agent B
					else if (board[i][j] == 'B') {
						
						agentX[1] = j;
						
						agentY[1] = i;
					}
					
					else if (board[i][j] == '*') {
						food++;
					}
				}
				
				
			}
			
			score[0] = score[1] = 0;
			
			turn = 0;
			
			file.close();
			
		}
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	

	public String toString() {
		
		StringBuilder p = new StringBuilder();
		
		for (char[] row : board) {
			for (char c : row) {
				
				p.append(c);
				
			}
			
			p.append('\n');
		}
	

		
		return p.toString();
	}
	
	public State copy() {
		
		State newBoard = new State();
		
		// the numbers of rows
		int height = this.board.length;
		
		// the number of columns
		int width = this.board[0].length;
		
		// a new board with the same original dimensions
		newBoard.board = new char[height][width];
		
		// copy each char to new board 
		for(int i = 0; i < height; i++) {
			
			for (int j = 0; j < width; j++ ) {
				
				newBoard.board[i][j] = this.board[i][j];
			}
		}
		
		newBoard.agentX = this.agentX.clone();
		
		newBoard.agentY = this.agentY.clone();
		
		newBoard.score = this.score.clone();
		
		newBoard.turn = this.turn;
		
		newBoard.food = this.food;
		
		return newBoard;
	}
	
	public Vector <String> legalMoves(int agent){
		
		Vector <String> actions = new Vector<>();
		
		int x = agentX[agent];
		
		int y = agentY[agent];
		
		// move up
		if (y > 0 && board[y - 1][x] != '#') {
			
			actions.add("up");
			
		}
		
		// move right
		if (x < board[0].length - 1 && board[y][x + 1] != '#') {
			
			actions.add("right");
			
		}
				
		// move down
		if (y < board.length - 1 && board[y + 1][x] != '#') {
			
			actions.add("down");
			
		}
	
		// move left
		if (x > 0 && board[y][x - 1] != '#') {
			
			actions.add("left");
			
		}
		
		// eat action
		if (board[y][x] == '*') {
			
			actions.add("eat");
			
		}
		
		// block action
        if (board[y][x] == ' ') {
        	
        	actions.add("block");
        	
        }
		
        return actions;
			
	}
	
	//helper function to determine the legal movements for the player agent 
		public Vector<String> legalMoves(){
			
			return legalMoves(turn);
			
		}
	

	
	public void execute(String action) {

		
		int x = agentX[turn];
		
		int y = agentY[turn];
		
		switch (action) {
		
		case "up":
			
			agentY[turn] = y - 1;
			
			break;
			
		case "right":
			
			agentX[turn] = x + 1;
			
			break;
			
		case "down":
			
			agentY[turn] = y + 1;
			
			break;
			
		case "left":
			
			agentX[turn] = x - 1;
			
			break;
			
		case "eat":
			
			score[turn]++;
			
			food--;
			
			board[y][x] = ' ';
			
			break;
			
		case "block":
			
			board[y][x] = '#';
			
			break;
		
		default:
			System.out.println(" unknown action: " + action);
	
		
		
		}
		
		// add the current action to the moves list
		moves.add(action);
		
		// switch turn to the next player agent
		if (turn == 0) {
			
			turn = 1;
		}
		else 
			turn = 0;
		
	}

	public boolean isLeaf() {
		
		// if all the food is collected
		if (food == 0) {
			
			return true;
		}
		
		// if an agent has no legal moves left
		if (legalMoves().isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	public double value(int agent) {
		
		int otherAgent;
		
		if (agent == 0) {
			otherAgent = 1;
		}
		else 
			otherAgent = 0;
		
		// if the game didn't end. " Tie "
		if (!isLeaf()) {
			
			return 0;
			
		}
		
		// the the current agent win
		if (score[agent] > score [otherAgent] || legalMoves(otherAgent).isEmpty()) {
			
			return 1;
		}
		
		// if the other agent win
		if (score[agent] < score [otherAgent] || legalMoves(agent).isEmpty()) {
			
			return -1;
		}
		
		// else it is a tie
		return 0;
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

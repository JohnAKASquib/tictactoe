package tictactoe;

import java.util.*; 


public class TTTGame {
	
	
	///////////////////////////////////////////////
	//  START INNER CLASS 
	private class BoardMove {
		  
		private int r;
		private int c;
		private char symbol; 
		
		
		BoardMove(int r, int c, char symbol ) {
			this.r = r;
			this.c = c;
			this.symbol = symbol; 
		}
		
		public int getR() {
			return r;
		}
		public int getC() {
			return c;
		}

		public char getSymbol() {
			return symbol;
		}


		@Override
		public String toString() {
			return "\nBoardMove [r=" + r + ", c=" + c + ", symbol=" + symbol+"]";
		}
	}
	//  END INNER CLASS 
	///////////////////////////////////////////////////////////////////
	

	private static final char X = 'X'; 
	private static final char O = 'O'; 
	private boolean twoPlayers = true;
	private static final char DEFAULT_BOARD_VALUE = '-'; 
	private static final char WHITE_SPACE = ' '; 
	private static final int BOARD_DIM = 3; 
	
	// private data members 
	private char [][] board; 
	private int[][] values;
	private boolean won; 
	private short turnCount; 
	private short winCombinationCode; 
	private char wonSymbol; 
	private BoardMove previousBoardMove; 
	private Stack<BoardMove> history; 
	
	// Constructor(s) 
	public TTTGame() {
		// create a board instance ... 
		board = new char[BOARD_DIM][BOARD_DIM]; 
		values = new int[3][3];
		reset(); 
	}
	
	public void resetGame() {
		reset(); 
	}
	
	// non-public methods 
	private void init() {
		// assign default value to each cell 
		for(int i = 0 ; i < board.length ; ++i) {
			for(int j = 0 ; j < board.length ; ++j) {
				// assign a DEFAULT_BOARD_VALUE each matrix element 
				board[i][j] = DEFAULT_BOARD_VALUE; 
			}
		}
	}
	
	// non-public methods 
	private void reset() {
		init(); 
		resetValues();
		won = false; 
		wonSymbol = WHITE_SPACE;
		turnCount = 0; 
		previousBoardMove = null;

		if(history == null) 
			history = new Stack<BoardMove>();
		else
			history.clear();
		
	}
	
	private boolean gameWon() {
		// if turnCount < 5, return false 
		if(turnCount < 5 ) 
			return false; 
		
		// perform further checks  
		return parseBoard(); 
	}
	
	/** 
	 * @return
	 */
	private boolean parseBoard() {  
		// check top  left position 
		if(isPositionAvailable(1,1) == false)  {
			// down diag 
			if( board[0][0] == board[1][1]  &&  board[1][1] == board[2][2]  ) {
				winCombinationCode = 7;
				return true; 
			}
				
			
			// top horiz 
			if( board[0][0] == board[0][1]  &&  board[0][1] == board[0][2]  ) {
				winCombinationCode = 1;
				return true;
			}
				
			
			// left vert  
			if( board[0][0] == board[1][0]  &&  board[1][0] == board[2][0]  ) {
				winCombinationCode = 4;
				return true;
			}
				
		}
		
		// check bottom left 
		if(isPositionAvailable(3,1) == false)  {
			// up diag 
			if( board[2][0] == board[1][1]  &&  board[1][1] == board[0][2]  ) {
				winCombinationCode = 8;
				return true; 
			}
				
			
			// bottom horiz 
			if( board[2][0] == board[2][1]  &&  board[2][1] == board[2][2]  ) {
				winCombinationCode = 3;
				return true;
			}
			
		}
		
		// middle row 
		if(isPositionAvailable(2,1) == false)  {
			// middle horoz 
			if( board[1][0] == board[1][1]  &&  board[1][1] == board[1][2]  ) {
				winCombinationCode = 2;
				return true; 
			}
				
		}
		
		// middle col 
		if(isPositionAvailable(1,2) == false)  {
			if( board[0][1] == board[1][1]  &&  board[1][1] == board[2][1]  ) {
				winCombinationCode = 5;
				return true; 
			}
		}
		
		// right col 
		if(isPositionAvailable(1,3) == false)  {
			if( board[0][2] == board[1][2]  &&  board[1][2] == board[2][2]  ) {
				winCombinationCode = 6;
				return true; 
			}
		}
		
		return false; 
		
	}
	private void checkAllRows() {
		int xcount=0, ocount=0,emptycount=0,i,j,k;
		for(i=0;i<3;i++) {//for each row
			//check the amount of x's o's and empty spaces
			for(j=0;j<3;j++)
			{
				if(board[i][j]=='X')
					xcount++;
				else if(board[i][j]=='O')
					ocount++;
				else
					emptycount++;
			}
			if(emptycount==3)
				for(k=0;k<3;k++)
					values[i][k]++;
			else if(emptycount==2&&xcount==1) {
				for(k=0;k<3;k++) {
					if(board[i][k]==DEFAULT_BOARD_VALUE)
						values[i][k]+=2;
				}	
			}
			else if (emptycount==2&&ocount==1) {
				for(k=0;k<3;k++) {
					if(board[i][k]==DEFAULT_BOARD_VALUE)
						values[i][k]+=3;
				}
			}
			else if(emptycount==1&&xcount==2) {
				for(k=0;k<3;k++) {
					if(board[i][k]==DEFAULT_BOARD_VALUE)
						values[i][k]+=50;
				}
			}
			else if(emptycount==1&&ocount==2) {
				for(k=0;k<3;k++) {
					if(board[i][k]==DEFAULT_BOARD_VALUE)
						values[i][k]+=100;
				}
			}
			xcount=0;
			ocount=0;
			emptycount=0;
		}
	}
	private void checkAllColumns() {
		int xcount=0, ocount=0,emptycount=0,i,j,k;
		for(i=0;i<3;i++) {//for each column
			//check the amount of x's o's and empty spaces
			for(j=0;j<3;j++)
			{
				if(board[j][i]=='X')
					xcount++;
				else if(board[j][i]=='O')
					ocount++;
				else
					emptycount++;
			}
			if(emptycount==3)
				for(k=0;k<3;k++)
					values[k][i]++;
			else if(emptycount==2&&xcount==1) {
				for(k=0;k<3;k++) {
					if(board[k][i]==DEFAULT_BOARD_VALUE)
						values[k][i]+=2;
				}	
			}
			else if (emptycount==2&&ocount==1) {
				for(k=0;k<3;k++) {
					if(board[k][i]==DEFAULT_BOARD_VALUE)
						values[k][i]+=3;
				}
			}
			else if(emptycount==1&&xcount==2) {
				for(k=0;k<3;k++) {
					if(board[k][i]==DEFAULT_BOARD_VALUE)
						values[k][i]+=50;
				}
			}
			else if(emptycount==1&&ocount==2) {
				for(k=0;k<3;k++) {
					if(board[k][i]==DEFAULT_BOARD_VALUE)
						values[k][i]+=100;
				}
			}
			xcount=0;
			ocount=0;
			emptycount=0;
		}
	}
	private void checkDiagonals() {
		int xcount=0, ocount=0,emptycount=0,i,j;
		for(i=0;i<3;i++) {
			if(board[i][i]=='X')
				xcount++;
			else if(board[i][i]=='O')
				ocount++;
			else
				emptycount++;
		}
		if(emptycount==3)
			for(i=0;i<3;i++)
				values[i][i]++;
		else if(emptycount==2&&xcount==1)
			for(i=0;i<3;i++) {
				if(board[i][i]==DEFAULT_BOARD_VALUE)
				values[i][i]+=2;
			}
		else if(emptycount==2&&ocount==1)
			for(i=0;i<3;i++) {
				if(board[i][i]==DEFAULT_BOARD_VALUE)
				values[i][i]+=3;
				}
		else if(emptycount==1&&xcount==2)
			for(i=0;i<3;i++) {
				if(board[i][i]==DEFAULT_BOARD_VALUE)
				values[i][i]+=50;
				}
		else if(emptycount==1&&ocount==2)
			for(i=0;i<3;i++) {
				if(board[i][i]==DEFAULT_BOARD_VALUE)
				values[i][i]+=100;
				}
		xcount=0;
		ocount=0;
		emptycount=0;
		j=2;	
		for(i=0;i<3;i++) {
			if(board[i][j]=='X')
				xcount++;
			else if(board[i][j]=='O')
				ocount++;
			else
				emptycount++;
			j--;
		}
		if(emptycount==3) {
			j=2;
			for(i=0;i<3;i++) {
				if(board[i][j]==DEFAULT_BOARD_VALUE)
					values[i][j]++;
				j--;
			}
		}
		else if(emptycount==2&&xcount==1) {
			j=2;
			for(i=0;i<3;i++) {
				if(board[i][j]==DEFAULT_BOARD_VALUE)
					values[i][j]+=2;
				j--;
			}
		}
		else if(emptycount==2&&ocount==1) {
			j=2;
			for(i=0;i<3;i++) {
				if(board[i][j]==DEFAULT_BOARD_VALUE)
					values[i][j]+=3;
				j--;
			}
		}
		else if(emptycount==1&&xcount==2) {
			j=2;
			for(i=0;i<3;i++) {
				if(board[i][j]==DEFAULT_BOARD_VALUE)
					values[i][j]+=50;
				j--;
			}
		}
		else if (emptycount==1&&ocount==2) {
			j=2;
			for(i=0;i<3;i++) {
				if(board[i][j]==DEFAULT_BOARD_VALUE)
					values[i][j]+=100;
				j--;
			}
		}
			
	}
	private int[] findBestSpot() {
		int[] bestSpot=new int[2];
		int high=0;
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(values[i][j]>high) {
					high=values[i][j];
					bestSpot[0]=i+1;
					bestSpot[1]=j+1;
				}
		resetValues();
		return bestSpot;
	}
	private void resetValues() {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				values[i][j]=0;
	}

	// public methods ... 
	

	public int[] pickBestMove(char player) {
		int[] bestSpot=new int[2];
		checkAllRows();
		checkAllColumns();
		checkDiagonals();
		bestSpot=findBestSpot();
		if(player=='O')
			setPositionO(bestSpot[0],bestSpot[1]);
		else
			setPositionX(bestSpot[0],bestSpot[1]);
		return bestSpot;
	}
	public char[][] getBoard() {
		return board;
	}
	
	public boolean getWon() {
		return won;
	}
	
	public short getWinCombinationCode() {
		return winCombinationCode;
	}
	
	public char getWonSymbol() {
		return wonSymbol;
	}

	public void setWonSymbol(char wonSymbol) {
		this.wonSymbol = wonSymbol;
	}

	public boolean isTwoPlayer() {
		return twoPlayers;
	}
	public void switchMode() {
		if(twoPlayers==true)
			twoPlayers=false;
		else
			twoPlayers=true;
	}
	
	public Stack<BoardMove> getHistory() {
		return history;
	}

	public boolean isPositionAvailable( int row, int col)  {
		if(board[row-1][col-1] == DEFAULT_BOARD_VALUE ) 
			return true; 
		
		return false; 
	}
	
	private boolean  setPosition(char value, int row, int col) {
		if(previousBoardMove != null && value == previousBoardMove.getSymbol()) 
			return false;
		
		// if target position is un assigned, then go forward
		if( !won  && isPositionAvailable(  row,  col) )  {
				board[row-1][col-1] = value; 
				this.previousBoardMove = new BoardMove(row, col, value); 
				history.push(this.previousBoardMove);
				
				++turnCount; 
				won = gameWon();  // needs to be executed after the count increment 
				
				//  check for "winner condition" 
				if(won) 
					wonSymbol = value; 
				
				return true; 
		}
		
		// otherwise, return negative indicator
		return false;
	} 
	
	public boolean  setPositionX(int row, int col) {
		return this.setPosition(X, row, col);
	}
	
	public boolean  setPositionO(int row, int col) {
		return this.setPosition(O, row, col);
	}
	
	
	/**
	 * THIS OP IS NA IF:
	 * - game instance in winning state 
	 * - if no positions have yet been set 
	 * - if undo() has already be performed 
	 * 
	 * OTHERWISE: 
	 * - decrement the turn count 
	 * - null out last position set history 
	 * - reset the lastPosition 
	 * @return
	 */
	public boolean  undo() {
		////////////////////  
		// check for NA condition 
		if(this.previousBoardMove == null || this.won   ) {
			return false; 
		}
		
		//////////////////
		//  perform UNDO steps  
		--this.turnCount; 
		
		////////////////////////////////////////////////////////////////////
		// if O was last position set, then to allow O to set again, we assign prevSymbol to X 
		//   The opposite for X being the last position 
		/**
		if(this.previousSetSymbol == X) 
			this.previousSetSymbol = O;
		else 
			this.previousSetSymbol = X; 
		*/
		
		this.board[this.previousBoardMove.getR() - 1][this.previousBoardMove.getC() - 1] = DEFAULT_BOARD_VALUE;
		
		this.previousBoardMove = history.pop(); 

		return true; 
	}



	public String toString() {
		StringBuffer buffer = new StringBuffer(512);
		
		buffer.append(String.format("%3c%3c%3c\n", board[0][0],board[0][1], board[0][2]  ));
		buffer.append(String.format("%3c%3c%3c\n", board[1][0],board[1][1], board[1][2]  ));
		buffer.append(String.format("%3c%3c%3c\n", board[2][0],board[2][1], board[2][2]  ));
		
		buffer.append("Winner? " + this.getWon() + "\n");
		buffer.append("Winning Symbol: " + this.getWonSymbol() + "\n");
		
		return buffer.toString(); 
	}

	public static void main(String[] args) {
		/* 
		 * Create an instance of TTTHame and invoke sequence of method calls that simulate a game 
		 * or, set of games .... 
		 */
		TTTGame game = new TTTGame(); 
		boolean check; 
		
		game.setPositionO(1,1);
		check = game.setPositionO(2,2);
		System.out.println("Set Succeeded (2 consec O moves?: " + check);
		
		game.setPositionX(2,2);
		game.setPositionO(3,3);
		
		System.out.println(game);
		
		check = game.setPositionX(1,1);
		System.out.println("Set Succeeded?: " + check);
		game.setPositionO(1,2);
		game.setPositionX(1,3);
		
		System.out.println(game);
		
		game.setPositionO(3,1);
		System.out.println("Before Undo?:\n" + game);
		game.undo(); 
		System.out.println("After Undo?:\n" + game);
		game.setPositionO(3,2);
		game.setPositionX(3,1);
		System.out.println(game);  // We have winner up-slope diag X 
		game.setPositionO(2,1);
		System.out.println(game);
		System.out.println(game.getHistory() + "\n\n");
		
		game.resetGame();
		System.out.println(game);

	}

}

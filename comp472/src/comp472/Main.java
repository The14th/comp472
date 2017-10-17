package comp472;

// Y AND X ARE MIXED UP
// Y IS HORIZONTAL AND X IS VERTICAL


import java.io.*;
import java.util.*;

public class Main {
	
	private int row = 5;
	private int col = 9;
	private char[][] board;
	private int redPiece;
	private int greenPiece;
	private char move;
	
	// ALWAYS TURN
	public Main(){
		
	board = new char [row][col];
	redPiece = 22;
	greenPiece = 22;
	move = 'R';
	
	
	for(int i = 0; i< row; i++){
		for(int j = 0; j<col; j++){
			board[i][j]='_';
		}
	}
	
		for (int j = 0; j < col; j++) {
			/*
			board[0][j] = 'R';
			board[1][j] = 'R';
			board[2][5] = 'R';
			board[2][6] = 'R';
			board[2][7] = 'R';
			board[2][8] = 'R';

			board[3][j] = 'G';
			board[4][j] = 'G';
			board[2][0] = 'G';
			board[2][1] = 'G';
			board[2][2] = 'G';
			board[2][3] = 'G';
			
			*/
			//Testing
			board[2][5] = 'R';
			board[2][1] = 'G';
			board[2][2] = 'G';
			board[2][3] = 'G';

		}
	}
	
	public void printBoard(){
		System.out.println("-------------------");
		System.out.println("  0 1 2 3 4 5 6 7 8");
		for(int i = 0; i< row; i++){
			  System.out.print((i) + " ");
			for(int j = 0; j<col; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		
		
	}
	
	public void makeMove(int from, int to){
		//Take first digit and then last digit
		int xOri = from/10;
		int yOri = from%10;
		
		int xTo = to/10;
		int yTo = to%10;
		
		board[xOri][yOri] = '_';
		board[xTo][yTo] = move;
		/*
		if(Math.abs(xTo)-xOri == 2){
			board[(xOri+xTo)/2][(yOri+yTo)/2]='_';
			if(move == 'R'){
				redPiece--;
			}else
					greenPiece--;
		*/
				
			
		}
	
	public void moving() throws IOException{
		
		Scanner k = new Scanner(System.in);
		 if( move == 'R')
			 System.out.println("\nRed's turn");
		 else
			 System.out.println("\nGreen's turn");
		 
		 
		 boolean moved = false;
		 
		 while(!moved){
			System.out.println("Enter Piece that you are at now(ie y = 2, x =5. so enter 25)");
			 int moveFrom = k.nextInt();
				System.out.println("Enter Piece you want to move to(ie y = 2, x =5. so enter 25)");
			 int moveTo = k.nextInt();
			 
			 if(validate(moveFrom,moveTo)){
			eating(moveFrom,moveTo); 
			makeMove(moveFrom,moveTo);
			
			 moved = true;
			 }/*
			 else{
				 System.out.println("No go");
			 }
			 */
			 
		 }
		 
		 
		 if (move == 'R')
			    move = 'G';
			else
			    move = 'R';
		    
		 
	}
	
	 public boolean gameOver() {
			return (redPiece == 0 || greenPiece == 0);
		    }
	
	public void eating(int from, int to){
		int xOri = from/10;
		int yOri = from%10;

		int xTo = to/10;
		int yTo = to%10;
		System.out.println("Hello");
		System.out.println("Xto "+xTo+ "and "+yTo);
		// check next peice
		//while loop to eat pecies in a row or column or diagonal until cant
		if(move == 'R' && board[xTo-1][yTo] =='G' || board[xTo][yTo-1] =='G' ||board[xTo-1][yTo-1] =='G'){

				board[xTo][yTo-1] = '_';
				System.out.println("Lost green");
				greenPiece--;
			
		}
	
		
		
	}
	
	public boolean validate(int from, int to){
		int xOri = from/10;
		int yOri = from%10;
		//System.out.println( "Xori "+xOri+ " "+ "Yori "+yOri);
		
		int xTo = to/10;
		int yTo = to%10;
		//System.out.println("xTo "+ xTo+ " yTo "+ yTo);
		
	
		
		//out of bounds things
		if(xOri < 0 || xOri >4 || yOri < 0 || yOri >8 ||
		xTo < 0 || xTo > 4	|| yTo < 0 || yTo > 8	){
			System.out.println("Over");
			return false;
		}
		//move only one
		else if( board[xTo][yTo] != '_' ){
			System.out.println("Already filled fam");
			return false;
		}
		
		
		return true;
	}

	public static void main(String[] args) throws IOException {
		
		Main n = new Main();
		n.printBoard();
		
		while(!n.gameOver()){
			n.moving();
			n.printBoard();
			System.out.println(n.greenPiece);
			System.out.println(n.redPiece);
		}
		
	}

}

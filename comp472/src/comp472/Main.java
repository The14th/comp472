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
			board[0][3] = 'G';
			board[0][7] = 'G';
			board[1][5] = 'R';
			board[2][5] = 'R';
			board[0][5] = 'G';
			board[2][2] = 'G';
			board[2][3] = 'G';
			board[2][7] = 'G';
			board[4][3] = 'G';
			board[4][5] = 'G';
			board[4][7] = 'G';

		}
	}
	
	public void printBoard(){
		System.out.println("-------------------------------");
		System.out.println("  0 1 2 3 4 5 6 7 8");
		for(int i = 0; i< row; i++){
			  System.out.print((i) + " ");
			for(int j = 0; j<col; j++){
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("");
		
		
	}
	
	public void makeMove(int from, int to){
		//Take first digit and then last digit
		int yOri = from/10;
		int xOri = from%10;
		
		int yTo = to/10;
		int xTo = to%10;
		
		board[yOri][xOri] = '_';
		board[yTo][xTo] = move;

				
			
		}
	
	public void moving() throws IOException{
		
		Scanner k = new Scanner(System.in);
		 if( move == 'R')
			 System.out.println("Red's turn:\n");
		 else
			 System.out.println("Green's turn:\n");
		 
		 
		 boolean moved = false;
		 
		 while(!moved){
			System.out.println("(FROM)Enter Piece that you are at now(ie y = 2, x =5. so enter 25)");
			 int moveFrom = k.nextInt();
				System.out.println("(TO)Enter Piece you want to move to(ie y = 2, x =5. so enter 25)");
			 int moveTo = k.nextInt();
			 
			 if(validate(moveFrom,moveTo, move)){
				eating(moveFrom,moveTo, move); 
				makeMove(moveFrom,moveTo);
				
				 moved = true;
			 }
			 
		 }
		 
		 
		 if (move == 'R')
			    move = 'G';
			else
			    move = 'R';
		    
		 
	}
	
	 public boolean gameOver() {
			return (redPiece == 0 || greenPiece == 0);
		    }
	
	public void eating(int from, int to, char color){
		char enemy = 'a';
		if (color == 'G'){
			enemy = 'R';
		}
		else
		{
			enemy = 'G';
		}
		boolean stop = false;
		int directionY = 0; //-1: up/1: down 
		int directionX = 0; //-1: left/1: right;
		int yOri = from/10;
		int xOri = from%10;
		int xAttack = 0;
		int yAttack = 0;

		int yTo = to/10;
		int xTo = to%10;
		System.out.println("Hello");
		System.out.println("Yto "+yTo+ " and "+xTo);
		
		if (yTo > yOri){
			directionY = 1;
		}
		else if (yTo < yOri){
			directionY = -1;
		}
		if (xTo > xOri){
			directionX = 1;
		}
		else if (xTo < xOri){
			directionX = -1;
		}
		yAttack = yTo+directionY;
		xAttack = xTo+directionX;
		while(!stop){
			if (!(yAttack < 0 || yAttack > 4 ||xAttack < 0 || xAttack > 8)){
				if(board[yAttack][xAttack] == enemy){
					board[yAttack][xAttack] = '_';
				}
				else{
					stop = true;
					System.out.println("finished eating");
				}
				yAttack += directionY;
				xAttack += directionX;
			}
			else{
				stop = true;
				System.out.println("eat out of bound");
			}
		}
		/*
		// check next Piece
		//while loop to eat pieces in a row or column or diagonal until cant
		if(move == 'R' && (board[yTo-1][xTo] =='G' || board[yTo][xTo-1] =='G' ||board[yTo-1][xTo-1] =='G')){

				board[yTo][xTo-1] = '_';
				System.out.println("Lost green");
				greenPiece--;
			
		}*/
	
		
		
	}
	
	public boolean validate(int from, int to, char color){
		int yOri = from/10;
		int xOri = from%10;
		
		int yTo = to/10;
		int xTo = to%10;



		//out of bounds things
		if(yOri < 0 || yOri >4 || xOri < 0 || xOri >8 ||
		yTo < 0 || yTo > 4	|| xTo < 0 || xTo > 8	){
			System.out.println("Over boundaries");
			return false;
		}
		
		else if(board[yOri][xOri] != color){
			System.out.println("Invalid piece selected");
			return false;
		}
		
		else if( yOri == yTo && xOri == xTo ){
			System.out.println("You must move");
			return false;
		}
		//move only one
		else if( board[yTo][xTo] != '_' ){
			System.out.println("Already filled fam");
			return false;
		}

		if (((yOri+xOri) & 1) == 0){
			//even is black
			System.out.println("BLACK");
			if (yTo == yOri + 1 || yTo == yOri - 1 || xTo == xOri + 1 || xTo == xOri - 1){
				return true;
			}
			else{
				System.out.println("Too far");
				return false;
			}
		}
		else{
			//odd is white
			System.out.println("WHITE");
			if (((yTo == yOri + 1 || yTo == yOri - 1) && xTo == xOri) || ((xTo == xOri + 1 || xTo == xOri - 1) && yTo == yOri)){
				return true;
			}
			else{
				System.out.println("Too far");
				return false;
			}
			
		}	
		
		
		//return true;
	}

	public static void main(String[] args) throws IOException {
		
		Main n = new Main();
		n.printBoard();
		
		while(!n.gameOver()){
			n.moving();
			n.printBoard();
			//System.out.println(n.greenPiece);
			//System.out.println(n.redPiece);
		}
		
	}

}

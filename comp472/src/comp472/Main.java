package comp472;

//ASSUME PERFECT USER ATM

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
	
	for(int j =0; j<col; j++){
	board[0][j] = 'R';
	board[1][j] = 'R';
	board[2][5]= 'R';
	board[2][6]= 'R';
	board[2][7]= 'R';
	board[2][8]= 'R';
	//}
	
	//for(int i =0; i<col; i++){
		board[3][j] = 'G';
		board[4][j] = 'G';
		board[2][0]= 'G';
		board[2][1]= 'G';
		board[2][2]= 'G';
		board[2][3]= 'G';
		//}
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
		if(Math.abs(xTo)-xOri == 2){
			board[(xOri+xTo)/2][(yOri+yTo)/2]='_';
			if(move == 'R')
				redPiece--;
				else
					greenPiece--;
				
				
			}
		}
	
	public void moving() throws IOException{
		
		Scanner k = new Scanner(System.in);
		 if( move == 'R')
			 System.out.println("\nRed's turn");
		 else
			 System.out.println("\nGreen's turn");
		 
		 
		 boolean moved = false;
		 
		 while(!moved){
			System.out.println("Enter Piece that you are at now(ie x = 2, y =5. so enter 25)");
			 int moveFrom = k.nextInt();
				System.out.println("Enter Piece you want to move to(ie x = 2, y =5. so enter 25)");
			 int moveTo = k.nextInt();
			 
			 makeMove(moveFrom,moveTo);
			 moved = true;
			 
			 
		 }
		 
		 
		 if (move == 'R')
			    move = 'G';
			else
			    move = 'R';
		    
		 
	}
	
	 public boolean gameOver() {
			return (redPiece == 0 || greenPiece == 0);
		    }
	
	

	public static void main(String[] args) throws IOException {
		
		Main n = new Main();
		n.printBoard();
		
		while(!n.gameOver()){
			n.moving();
			n.printBoard();
		}
		
	}

}

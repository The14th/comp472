package comp472;

import java.io.*;
import java.util.*;

public class BonzeeRules {

	private int row = 5;
	private int col = 9;
	private int defense = 0;
	private char[][] board;
	private char turn;
	int redPiece;
	int greenPiece;

	//Initial settings
	public BonzeeRules() {

		board = new char[row][col];
		redPiece = 22;
		greenPiece = 22;
		turn = 'G';

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j] = '_';
			}
		}

		for (int j = 0; j < col; j++) {
			// Main Board
			/*
			  board[0][j] = 'R'; board[1][j] = 'R'; board[2][5] = 'R';
			 board[2][6] = 'R'; board[2][7] = 'R'; board[2][8] = 'R';
			  
			 board[3][j] = 'G'; board[4][j] = 'G'; board[2][0] = 'G';
			 board[2][1] = 'G'; board[2][2] = 'G'; board[2][3] = 'G';
			 
*/
			// Testing
			board[0][2] = 'G';
			board[0][7] = 'G';
			board[1][3] = 'G';
			//board[1][5] = 'G';
			//board[2][4] = 'R';
			board[2][5] = 'R';
		    //	board[0][5] = 'G';
			board[2][2] = 'G';
			board[2][3] = 'G';
			board[3][5] = 'G';
			board[2][7] = 'G';
			board[4][3] = 'G';
			board[4][5] = 'G';
			board[4][7] = 'G';

		}
	}
	
	//Gets input from player to make the move
	public void enterMove(int from, int to) {
		// Take first digit and then last digit
		int yOri = from / 10;
		int xOri = from % 10;

		int yTo = to / 10;
		int xTo = to % 10;

		board[yOri][xOri] = '_';
		board[yTo][xTo] = turn;

	}
	
	//iterate through board, or all G or R peices and check if you can move
		//Not sure if need this 
		/*
		public void pickMove() {
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					
					int a = Integer.parseInt(i+""+j);
					System.out.println(a);
					validateMove(a,a+1,turn);
					validateMove(a+1,a,turn);
					validateMove(a,a-1,turn);
					validateMove(a-1,a,turn);
					//can create an array of moves that are good
				}

			}
		}
		
		*/
		
		//NEED TO LOOP THIS FOR ALL POSITIONS
		//This method checks all possible moves from a position and determines if IT can move and 
		// if it can attack it will take the max pieces it can eat and move that way
		public void move(int from, int to, char turn) {

			// Not sure if I need these for this method
			int yOri = from / 10;
			int xOri = from % 10;
			int yTo = to / 10;
			int xTo = to % 10;

			System.out.println("Moving from " + from + " to " + to);
			System.out.println("Validating possible moves..");
			System.out.println();
			// boolean stop = false;

			// UP meaning down
			int up = from + 10;
			// DOWN meaning up
			int down = from - 10;
			// LEFT
			int left = from - 1;
			// RIGHT
			int right = from + 1;

			if (validateMove(from, up, turn) == false) {
				System.out.println("No");

			} else {
				System.out.println("Can move up");
				checkerM(from, up, turn);
				System.out.println();

			}

			if (validateMove(from, down, turn) == false) {
				System.out.println("No");

			} else {
				System.out.println("Can move down");
				checkerM(from, down, turn);
				System.out.println();

			}

			if (validateMove(from, left, turn) == false) {
				System.out.println("No");

			} else {
				System.out.println("Can move left");
				checkerM(from, left, turn);
				System.out.println();

			}

			if (validateMove(from, right, turn) == false) {
				System.out.println("No");

			} else {
				System.out.println("Can move right");
				checkerM(from, right, turn);
				System.out.println();
				;

			}
			// IF you are here then ALL 4 moves cant happen then move to another
			// position on the board
			System.out.println("nothing");

		}
		// Not to execute move, just count the pieces it can eat
		public void checkerM(int from, int to, char color) {
			int deathC = 0;
			char enemy = 'a';
			if (color == 'G') {
				enemy = 'R';
			} else {
				enemy = 'G';
			}
			boolean stop = false;
			boolean forward = false;
			boolean def = true;
			int directionY = 0; // -1: up/1: down
			int directionX = 0; // -1: left/1: right;
			int yOri = from / 10;
			int xOri = from % 10;
			int xAttack = 0;
			int yAttack = 0;

			int yTo = to / 10;
			int xTo = to % 10;

			if (yTo > yOri) {
				directionY = 1;
			} else if (yTo < yOri) {
				directionY = -1;
			}
			if (xTo > xOri) {
				directionX = 1;
			} else if (xTo < xOri) {
				directionX = -1;
			}
			yAttack = yTo + directionY;
			xAttack = xTo + directionX;
			// forward attack
			while (!stop) {
				if (!(yAttack < 0 || yAttack > 4 || xAttack < 0 || xAttack > 8)) {
					if (board[yAttack][xAttack] == enemy) {
						deathC++;
						forward = true;
						def = false;
						//board[yAttack][xAttack] = '_';
					} else {
						stop = true;
					}
					yAttack += directionY;
					xAttack += directionX;
				} else {
					stop = true;
				}
			}
			// backwards attack
			stop = false;
			directionX = -directionX;
			directionY = -directionY;
			yAttack = yOri + directionY;
			xAttack = xOri + directionX;

			if (forward == false) {
				while (!stop) {
					if (!(yAttack < 0 || yAttack > 4 || xAttack < 0 || xAttack > 8)) {
						if (board[yAttack][xAttack] == enemy) {
							deathC++;
							def = false;
							//board[yAttack][xAttack] = '_';
						} else {
							stop = true;
						}
						yAttack += directionY;
						xAttack += directionX;
					} else {
						stop = true;
					}
				}
			}
			if (enemy == 'G') {
				
				System.out.println("Pieces that can be consumed: " +deathC);
			} else {
				
				System.out.println("Pieces that can be consumed: "+deathC);
			}

			if (def) {
				defense++;
				System.out.println("Defensive move");
			} else {
				defense = 0;
			}
			System.out.println("Finished attack/defense phase");

			if (defense >= 10) {
				gameOver(2);
			}
		}
		
	
	
	
	
	//Intiates the move entered
	public void makeMove() {
		int over = 0;

		Scanner k = new Scanner(System.in);
		if (turn == 'R')
			System.out.println("Red's turn:\n");
		else
			System.out.println("Green's turn:\n");

		boolean moved = false;

		while (!moved) {

			String regex = "([A-E]|[a-e])[1-9]";
			
			
			System.out.println("(FROM)Enter Piece that you are at now(ie y = 2, x =5. so enter 25)");
			String inputFrom = k.nextLine();
			while (!(inputFrom.matches(regex))) {
				System.out.println("(FROM)Enter Piece that you are at now(ie y = 2, x =5. so enter 25)");
				inputFrom = k.nextLine();
				over++;
				if (over >= 1)
					gameOver(1);
			}

			int moveFrom = inputConverter(inputFrom);
			
			System.out.println("(TO)Enter Piece you want to move to(ie y = 2, x =5. so enter 25)");
			String inputTo = k.nextLine();
			while (!(inputTo.matches(regex))) {
				System.out.println("(TO)Enter Piece you want to move to(ie y = 2, x =5. so enter 25)");
				inputTo = k.nextLine();
				over++;
				if (over >= 1)
					gameOver(1);
			}
			int moveTo = inputConverter(inputTo);
			
			//AI MOVE
			move(moveFrom,moveTo,turn);
	

			if (validateMove(moveFrom, moveTo, turn)) {
				attackMove(moveFrom, moveTo, turn);
				enterMove(moveFrom, moveTo);

				moved = true;
				break;
			} else {
				over++;
			}
			if (over >= 2)
				gameOver(1);

		}

		if (turn == 'R')
			turn = 'G';
		else
			turn = 'R';

	}
	
	private int inputConverter(String input) {
		System.out.println("Raw input: " + input);
		
		char rowInp = input.charAt(0);
		int y = 0;
		String colInp = ""+input.charAt(1);
		
		if (rowInp == 'A' || rowInp == 'a'){
			y = 0;
		}
		if (rowInp == 'B' || rowInp == 'b'){
			y = 1;
		}
		if (rowInp == 'C' || rowInp == 'c'){
			y = 2;
		}
		if (rowInp == 'D' || rowInp == 'd'){
			y = 3;
		}
		if (rowInp == 'E' || rowInp == 'e'){
			y = 4;
		}
		
		int x = Integer.parseInt(colInp);
		x = x-1;
		System.out.println("Row: " + y);
		System.out.println("Column: " + x);
		
		int move = y*10 + x;
				
		System.out.println("Total: " + move);
		
		return move;
	}

	//Handles attacking
	public void attackMove(int from, int to, char color) {
		int deathC = 0;
		char enemy = 'a';
		if (color == 'G') {
			enemy = 'R';
		} else {
			enemy = 'G';
		}
		boolean stop = false;
		boolean forward = false;
		boolean def = true;
		int directionY = 0; // -1: up/1: down
		int directionX = 0; // -1: left/1: right;
		int yOri = from / 10;
		int xOri = from % 10;
		int xAttack = 0;
		int yAttack = 0;

		int yTo = to / 10;
		int xTo = to % 10;

		if (yTo > yOri) {
			directionY = 1;
		} else if (yTo < yOri) {
			directionY = -1;
		}
		if (xTo > xOri) {
			directionX = 1;
		} else if (xTo < xOri) {
			directionX = -1;
		}
		yAttack = yTo + directionY;
		xAttack = xTo + directionX;
		// forward attack
		while (!stop) {
			if (!(yAttack < 0 || yAttack > 4 || xAttack < 0 || xAttack > 8)) {
				if (board[yAttack][xAttack] == enemy) {
					deathC++;
					forward = true;
					def = false;
					board[yAttack][xAttack] = '_';
				} else {
					stop = true;
				}
				yAttack += directionY;
				xAttack += directionX;
			} else {
				stop = true;
			}
		}
		// backwards attack
		stop = false;
		directionX = -directionX;
		directionY = -directionY;
		yAttack = yOri + directionY;
		xAttack = xOri + directionX;

		if (forward == false) {
			while (!stop) {
				if (!(yAttack < 0 || yAttack > 4 || xAttack < 0 || xAttack > 8)) {
					if (board[yAttack][xAttack] == enemy) {
						deathC++;
						def = false;
						board[yAttack][xAttack] = '_';
					} else {
						stop = true;
					}
					yAttack += directionY;
					xAttack += directionX;
				} else {
					stop = true;
				}
			}
		}
		if (enemy == 'G') {
			greenPiece -= deathC;
		} else {
			redPiece -= deathC;
		}

		if (def) {
			defense++;
			System.out.println("Defensive move");
		} else {
			defense = 0;
		}
		System.out.println("Finished attack/defense phase");

		if (defense >= 10) {
			gameOver(2);
		}
	}

	//Validates moves entered
	public boolean validateMove(int from, int to, char color) {
		int yOri = from / 10;
		int xOri = from % 10;

		int yTo = to / 10;
		int xTo = to % 10;

		// out of bounds things
		if (yOri < 0 || yOri > 4 || xOri < 0 || xOri > 8 || yTo < 0 || yTo > 4 || xTo < 0 || xTo > 8) {
			System.out.println("Over boundaries");
			return false;
		}

		else if (board[yOri][xOri] != color) {
			System.out.println("Invalid piece selected");
			return false;
		}

		else if (yOri == yTo && xOri == xTo) {
			System.out.println("You must move");
			return false;
		} else if (board[yTo][xTo] != '_') {
			System.out.println("Tile already filled");
			return false;
		}

		if (((yOri + xOri) & 1) == 0) {
			// even is black
			if (yTo == yOri + 1 || yTo == yOri - 1 || xTo == xOri + 1 || xTo == xOri - 1) {
				return true;
			} else {
				System.out.println("Too far");
				return false;
			}
		} else {
			// odd is white
			if (((yTo == yOri + 1 || yTo == yOri - 1) && xTo == xOri)
					|| ((xTo == xOri + 1 || xTo == xOri - 1) && yTo == yOri)) {
				return true;
			} else {
				System.out.println("White may only move horizontally or vertically by one space");
				return false;
			}

		}
	}
	
	//Game over situations
	public boolean gameOver(int forced) {

		if (forced == 1) {
			System.out.println("\nToo many illegal moves");
			if (turn == 'R')
				System.out.println("GREEN WINS BY DEFAULT");
			else
				System.out.println("RED WINS BY DEFAULT");
			System.out.println("GAME OVER");
			System.exit(0);
		}

		if (forced == 2) {
			System.out.println("TOO MANY DEFENSIVE MOVES: DRAW");
			System.out.println("GAME OVER");
			System.exit(0);
		}
		if (redPiece == 0) {
			System.out.println("GREEN WINS");
			System.out.println("GAME OVER");
		}

		if (greenPiece == 0) {
			System.out.println("RED WINS");
			System.out.println("GAME OVER");
		}

		return false;
	}
	

	
	//Prints board
	public void printBoard() {
		System.out.println("-------------------------------");
		System.out.println("  1 2 3 4 5 6 7 8 9");
		for (int i = 0; i < row; i++) {
			if (i == 0)
				System.out.print("A" + " ");
			if (i == 1)
				System.out.print("B" + " ");
			if (i == 2)
				System.out.print("C" + " ");
			if (i == 3)
				System.out.print("D" + " ");
			if (i == 4)
				System.out.print("E" + " ");
			for (int j = 0; j < col; j++) {
				System.out.print(board[i][j] + " ");
			}
			if (i == 0)
				System.out.print("A" + " ");
			if (i == 1)
				System.out.print("B" + " ");
			if (i == 2)
				System.out.print("C" + " ");
			if (i == 3)
				System.out.print("D" + " ");
			if (i == 4)
				System.out.print("E" + " ");
			System.out.println();
		}
		System.out.println("  1 2 3 4 5 6 7 8 9");
		System.out.println("");

	}

}

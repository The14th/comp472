package comp472;

import java.util.*;

public class BonzeeRules {

	private int row = 5;
	private int col = 9;
	private int defense = 0;
	private char[][] board;
	private char[][] oldBoard;
	private char[][] midBoard;
	private char[][] mid2Board;
	List<Integer> minimaxMove = new ArrayList<Integer>();
	List<Integer> fMove = new ArrayList<Integer>();
	List<Integer> tMove = new ArrayList<Integer>();
	
	List<Integer> gHori = new ArrayList<Integer>();
	List<Integer> gVerti = new ArrayList<Integer>();
	List<Integer> rHori = new ArrayList<Integer>();
	List<Integer> rVerti = new ArrayList<Integer>();
	
	private char turn;
	char player1, player2;
	int redPiece;
	int greenPiece;
	boolean next = false;
	int max;
	int depth = 0;
	int tempMax = 0;
	int tempMin = 1000000;
	int tempFrom = 0;
	int tempTo= 0;
	int hMax = 0;
	int hMin = 0;
	
	int rootFrom = 0;
	int rootTo = 0;
	
	boolean valid = false;
	
	// Initial settings
	public BonzeeRules() {

		board = new char[row][col];
		oldBoard = new char[row][col];
		midBoard = new char[row][col];
		mid2Board = new char[row][col];
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
			
			  board[0][j] = 'R'; board[1][j] = 'R'; board[2][5] = 'R';
			  board[2][6] = 'R'; board[2][7] = 'R'; board[2][8] = 'R';
			  
			  board[3][j] = 'G'; board[4][j] = 'G'; board[2][0] = 'G';
			  board[2][1] = 'G'; board[2][2] = 'G'; board[2][3] = 'G';
			  
			/*
			// Testing
			board[0][0] = 'G';
		//	board[0][2] = 'G';
		//	board[0][7] = 'G';
			board[0][2] = 'R';
			board[0][3] = 'R';
			board[2][0] = 'R';
		//	board[2][2] = 'R';
			board[1][5] = 'G';
			// board[2][4] = 'R';
			// board[2][5] = 'R';
			// board[0][5] = 'G';
			// board[2][2] = 'G';
			// board[2][3] = 'G';
			 board[3][5] = 'R';
			// board[2][7] = 'G';
			// board[4][3] = 'G';
			// board[4][5] = 'G';
			// board[4][7] = 'G';
			 board[0][8] = 'G';
			 board[2][8] = 'R';
			 board[3][8] = 'R';
			 board[4][8] = 'R';
			  */

		}
	}

	// Gets input from player to make the move
	public void enterMove(int from, int to) {
		// Take first digit and then last digit
		int yOri = from / 10;
		int xOri = from % 10;

		int yTo = to / 10;
		int xTo = to % 10;

		board[yOri][xOri] = '_';
		board[yTo][xTo] = turn;

	}

	// This method checks all possible moves from a position and determines if
	// IT can move and
	// if it can attack it will take the max pieces it can eat and move that way
	public int hFunc(){	
		
		int h=0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(board[i][j]=='R'){
					//System.out.println("R at: " +i+ " " +j);
					rHori.add((i+1));
					rVerti.add((j+1));
					
				}else if(board[i][j] == 'G'){
					//System.out.println("G at: " +i+ " " +j);
					gHori.add((i+1));
					gVerti.add((j+1));
				}
			}
		}
		
		int sumRH=0;
		int sumRV = 0;
		for(int i=0; i<rHori.size();i++){
			sumRH += rHori.get(i);
			
		}
		for(int i=0; i<rVerti.size();i++){
			sumRV += rVerti.get(i);
			
		}
		
		int sumGH=0;
		int sumGV = 0;
		for(int i=0; i<gHori.size();i++){
			sumGH += gHori.get(i);
			
		}
		for(int i=0; i<gVerti.size();i++){
			sumGV += gVerti.get(i);
			
		}
		
		//+green -red
		//Green wants MAX h RED wants MIN h
		h = 100*sumGH + 50*sumGV -100*sumRH -50*sumRV;
		
		System.out.println("Total e(N) is: "+h);
		gVerti.clear();
		gHori.clear();
		rVerti.clear();
		rHori.clear();
		
		return h;
		

	}
	//public void miniMaxL(int from, int to, char turn){
	public int miniMaxL(int from){
				
		int rTempP = redPiece;
		int gTempP = greenPiece;
		
		depth++;
		System.out.println("dept" + depth);
		
		valid = false;

		int upleft = from + 10 - 1;
		// UP meaning down
		int up = from + 10;
		
		int upright = from + 10 - 1;
		
		int downleft = from - 10 - 1;
		// DOWN meaning up
		int down = from - 10;
		
		int downright = from - 10 + 1;
		// LEFT
		int left = from - 1;
		// RIGHT
		int right = from + 1;
		
		//???
		max = 0;

		
		if (validateAI(from, upleft, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			attackMoveF(from,upleft,turn);
			enterMove(from, upleft);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = upleft;
			}
			if (turn == 'G'){
				//here
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					//hh
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = upleft;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = upleft;
				}		
			}
			
		}
		
		if (validateAI(from, upright, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			attackMoveF(from,upright,turn);
			enterMove(from, upright);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = upright;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = upright;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = upright;
				}
			}
			//next depth
			printBoard();
			
		}
		
		if (validateAI(from, downleft, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;	
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			attackMoveF(from,downleft,turn);
			enterMove(from, downleft);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = downleft;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = downleft;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = downleft;
				}
			}
			
		}
		
		if (validateAI(from, downright, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;	
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			attackMoveF(from,downright,turn);
			enterMove(from, downright);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = downright;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				//here1
				else{
					hMax = hFunc();
				}
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = downright;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = downright;
				}
			}
			
		}
		
		if (validateAI(from, up, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			attackMoveF(from,up,turn);
			enterMove(from, up);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = up;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = up;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = up;
				}
			}
			
		}

		

		if (validateAI(from, down, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			//System.out.println("Can move down");
			attackMoveF(from,down,turn);
			enterMove(from, down);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = down;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = down;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = down;
				}
			}

		}


		if (validateAI(from, left, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			//System.out.println("Can move left");
			attackMoveF(from,left,turn);
			enterMove(from, left);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = left;
			}
			if (turn == 'G'){
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = left;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = left;
				}
			}
			

		}

		

		if (validateAI(from, right, turn) == false) {
			//System.out.println("No");

		} else {
			valid = true;
			for (int i = 0; i < board.length;i++){
				midBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			//System.out.println("Can move right");

			attackMoveF(from,right,turn);
			enterMove(from, right);
			printBoard();
			if (depth == 1){
				rootFrom = from;
				rootTo = right;
			}
			if (turn == 'G'){
				System.out.println("Can move right");
				if (depth <= 2){
					turn = 'R';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'G';
				}
				hMax = hFunc();
				if (tempMax <= hMax){ 
					tempMax = hMax;
					tempFrom = from;
					tempTo = right;
				}
			}
			if (turn == 'R'){
				if (depth <= 2){
					turn = 'G';
					checkAllMove();
					if (depth == 2){
						for (int i = 0; i < board.length;i++){
							board[i] = Arrays.copyOf(midBoard[i], midBoard[i].length);
						}
					}
					turn = 'R';
				}
				hMin = hFunc();
				if (tempMin >= hMin){ 
					tempMin = hMin;
					tempFrom = from;
					tempTo = right;
				}
			}
		}

		if (depth == 1){
			for (int i = 0; i < board.length;i++){
				board[i] = Arrays.copyOf(oldBoard[i], oldBoard[i].length);
			}
			//board = oldBoard.clone();
			redPiece = rTempP;
			greenPiece = gTempP;
		}
		//System.out.println("----------------------------");
		depth--;
		
		return 0;
	}

	//public void miniMax(int from, int to) {
	public void miniMax(int from) {
		
		//miniMaxL(from, to);
		miniMaxL(from);
		
		if (depth == 2){
			if (valid){
				//midbo
				for (int i = 0; i < board.length;i++){
					board[i] = Arrays.copyOf(mid2Board[i], mid2Board[i].length);
				}
				System.out.println("idiot");
				printBoard();
				System.out.print("oy");
				if (turn == 'G')
					minimaxMove.add(tempMax);
				if (turn == 'R')
					minimaxMove.add(tempMin);
				fMove.add(rootFrom);
				tMove.add(rootTo);
			}
			/*
			else{
				if (turn == 'G')
					minimaxMove.add(-1);
				if (turn == 'R')
					minimaxMove.add(1000000000);
				
			}*/
		}
		
		
		//depth = 0;
		
	}

	// Not to execute move, just count the pieces it can eat
	public int checkerM(int from, int to, char color) {
		int deathC = 0;
		char enemy = 'a';
		if (color == 'G') {
			enemy = 'R';
		} else {
			enemy = 'G';
		}
		boolean stop = false;
		boolean forward = false;
		// boolean def = true;
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
					// def = false;
					// board[yAttack][xAttack] = '_';
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
						// def = false;
						// board[yAttack][xAttack] = '_';
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

			//System.out.println("Pieces that can be consumed: " + deathC);
			max = deathC;
			//System.out.println("Max is : " + max);
		} else {
			max = deathC;
			//System.out.println("Max from attack method: " + max);
			
		}

		return max;

	}

	// Intiates the move entered
	public void makeMove() {
		
		
		for (int i = 0; i < board.length;i++){
			oldBoard[i] = Arrays.copyOf(board[i], board[i].length);
		}
		int over = 0;

		Scanner k = new Scanner(System.in);
			
		clearList();
		
		//Green's turn
		printNumP();
		System.out.println("Green's turn:\n");
		turn = 'G';
		//human start
		if (player1 == 'G'){
			boolean moved = false;
			while (!moved) {

				String regex = "([A-E]|[a-e])[1-9]";
	
				System.out.println("(FROM)Enter Piece that you are at now");
				String inputFrom = k.nextLine();
				while (!(inputFrom.matches(regex))) {
					System.out.println("(FROM)Enter Piece that you are at now");
					inputFrom = k.nextLine();
					over++;
					if (over >= 1)
						gameOver(1);
				}
	
				int moveFrom = inputConverter(inputFrom);
	
				System.out.println("(TO)Enter Piece you want to move to)");
				String inputTo = k.nextLine();
				while (!(inputTo.matches(regex))) {
					System.out.println("(TO)Enter Piece you want to move to");
					inputTo = k.nextLine();
					over++;
					if (over >= 1)
						gameOver(1);
				}
				int moveTo = inputConverter(inputTo);
				 
				 if (validateMove(moveFrom, moveTo, turn)) { 
					  attackMove(moveFrom, moveTo, turn); 
					  enterMove(moveFrom, moveTo);
					  next = true;
					  moved = true; 
					  break; 
				  } 
				  else { 
				  over++; 
				 } 
				 if (over >= 2)
				  gameOver(1);
				 
			}
			
			gameOver(0);
			
			//switch turn
			for (int i = 0; i < board.length;i++){
				oldBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			printBoard();
			printNumP();
			System.out.println("Red's turn:\n");
			turn = 'R';
			checkAllMove();
			
			Object obj;
			if (player2 == 'G')
				obj = Collections.max(minimaxMove);
			else
				obj = Collections.min(minimaxMove);
			//System.out.println(obj);

			int index = minimaxMove.indexOf(obj);
			int aiFrom = fMove.get(index);
			int aiTo = tMove.get(index);
		
			System.out.println("From position is " + aiFrom + " and To position is " + aiTo);
			
			attackMove(aiFrom,aiTo,turn);
			enterMove(aiFrom, aiTo);
			
			hFunc();
			
			gameOver(0);
			
		}//end human start case
		
		//AI start
		else{
			checkAllMove();

			Object obj = Collections.max(minimaxMove);
			//System.out.println(obj);

			int index = minimaxMove.indexOf(obj);
			int aiFrom = fMove.get(index);
			int aiTo = tMove.get(index);

			//System.out.println("Index is: " + index);
			System.out.println("From position is " + aiFrom + " and To position is " + aiTo);
			
			attackMove(aiFrom,aiTo,turn);
			enterMove(aiFrom, aiTo);
			hFunc();
			
			gameOver(0);
			
			//switch turn
			for (int i = 0; i < board.length;i++){
				oldBoard[i] = Arrays.copyOf(board[i], board[i].length);
			}
			printBoard();
			printNumP();
			System.out.println("Red's turn:\n");
			turn = 'R';
			boolean moved = false;
			while (!moved) {
							
				
				String regex = "([A-E]|[a-e])[1-9]";
	
				System.out.println("(FROM)Enter Piece that you are at now");
				String inputFrom = k.nextLine();
				while (!(inputFrom.matches(regex))) {
					System.out.println("(FROM)Enter Piece that you are at now");
					inputFrom = k.nextLine();
					over++;
					if (over >= 1)
						gameOver(1);
				}
	
				int moveFrom = inputConverter(inputFrom);
	
				System.out.println("(TO)Enter Piece you want to move to");
				String inputTo = k.nextLine();
				while (!(inputTo.matches(regex))) {
					System.out.println("(TO)Enter Piece you want to move to");
					inputTo = k.nextLine();
					over++;
					if (over >= 1)
						gameOver(1);
				}
				int moveTo = inputConverter(inputTo);
				 
				 if (validateMove(moveFrom, moveTo, turn)) { 
					  attackMove(moveFrom, moveTo, turn); 
					  enterMove(moveFrom, moveTo);
					  next = true;
					  moved = true; 
					  break; 
				  } 
				  else { 
				  over++; 
				 } 
				 if (over >= 2)
				  gameOver(1);
				 
			}
			
			gameOver(0);
		}
		
		tempMax = 0;
		tempMin = 1000000;
		tempFrom = 0;
		tempTo= 0;
		hMax = 0;
		hMin = 0;
		
		rootFrom = 0;
		rootTo = 0;
	}

	private void checkAllMove() {
		
		
		for (int i = 0; i < board.length;i++){
			mid2Board[i] = Arrays.copyOf(board[i], board[i].length);
		}
			System.out.println("deptddd" + depth);
			// AI MOVE
			//int start = 00;
			//int end = 01;


				for (int i = 0; i < row; i++) {
					for (int j = 0; j < col; j++) {

						if (board[i][j] == turn) {
						

							//System.out.println("Current max is: " + max);
							//miniMax(start, end);
							int from = i* 10 + j;
							miniMax(from);
							
						}
/*
						if (end == 48) {

							System.out.println("! AI THINKING DONE !");
							
						}
						if (start == 8 && end == 9) {

							start = 00;
							end = 01;
							end = end + 10;
						} else if (start == 18 && end == 19) {
							start = 00;
							end = 01;
							end = end + 20;
						} else if (start == 28 && end == 29) {
							start = 00;
							end = 01;
							end = end + 30;
						} else if (start == 38 && end == 39) {
							start = 00;
							end = 01;
							end = end + 40;
						} else {
							start = end;
							end = start + 1;
						}*/
					}
				}
		}
	

	private int inputConverter(String input) {
	

		char rowInp = input.charAt(0);
		int y = 0;
		String colInp = "" + input.charAt(1);

		if (rowInp == 'A' || rowInp == 'a') {
			y = 0;
		}
		if (rowInp == 'B' || rowInp == 'b') {
			y = 1;
		}
		if (rowInp == 'C' || rowInp == 'c') {
			y = 2;
		}
		if (rowInp == 'D' || rowInp == 'd') {
			y = 3;
		}
		if (rowInp == 'E' || rowInp == 'e') {
			y = 4;
		}

		int x = Integer.parseInt(colInp);
		x = x - 1;


		int move = y * 10 + x;


		return move;
	}

	// Handles attacking
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

	public void attackMoveF(int from, int to, char color) {

		int deathC = 0;
		char enemy = 'a';
		if (color == 'G') {
			enemy = 'R';
		} else {
			enemy = 'G';
		}
		boolean stop = false;
		boolean forward = false;
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
		System.out.println("Finished attack/defense phase");
	}
	
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
	
	// Validates moves entered
	public boolean validateAI(int from, int to, char color) {
		int yOri = from / 10;
		int xOri = from % 10;

		int yTo = to / 10;
		int xTo = to % 10;

		// out of bounds things
		if (yOri < 0 || yOri > 4 || xOri < 0 || xOri > 8 || yTo < 0 || yTo > 4 || xTo < 0 || xTo > 8) {
			//System.out.println("Over boundaries");
			return false;
		}

		else if (board[yOri][xOri] != color) {
			//System.out.println("Invalid piece selected");
			return false;
		}

		else if (yOri == yTo && xOri == xTo) {
			//System.out.println("You must move");
			return false;
		} else if (board[yTo][xTo] != '_') {
			//System.out.println("Tile already filled");
			return false;
		}

		if (((yOri + xOri) & 1) == 0) {
			// even is black
			if (yTo == yOri + 1 || yTo == yOri - 1 || xTo == xOri + 1 || xTo == xOri - 1) {
				return true;
			} else {
				//System.out.println("Too far");
				return false;
			}
		} else {
			// odd is white
			if (((yTo == yOri + 1 || yTo == yOri - 1) && xTo == xOri)
					|| ((xTo == xOri + 1 || xTo == xOri - 1) && yTo == yOri)) {
				return true;
			} else {
				//System.out.println("White may only move horizontally or vertically by one space");
				return false;
			}

		}
	}

	// Game over situations
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
			System.exit(0);
		}

		if (greenPiece == 0) {
			System.out.println("RED WINS");
			System.out.println("GAME OVER");
			System.exit(0);
		}

		return false;
	}

	// Prints board
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
	
	public void whosWho(){
		//Player1 will alwways be human
		//player2 will be Ai
		System.out.println("Human player is: ");
		System.out.println("1 for R");
		System.out.println("2 for G");
		Scanner i = new Scanner(System.in);
		int playerC = i.nextInt();
		if(playerC == 1){
			
			//human is R and AI is G
			player1='R';
			player2='G';
			System.out.println("You have chosen to be red and the AI will be green");
			
		}else{
			
		 // human is G and AI is R
		player1= 'G';
		player2= 'R';
		System.out.println("You have chosen to be green and the AI will be red");
		}
			
	}
	
	public void clearList(){
		minimaxMove.clear();
		fMove.clear();
		tMove.clear();
	}
	public void printNumP(){
		System.out.println("Green Pieces left: " +greenPiece);
		System.out.println("Red Pieces left: "+redPiece);
	}
	

}

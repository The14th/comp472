package comp472;

public class BonzeeDriver {
	
	public static void main(String[] args) {
		System.out.println("Welcome to Bonzee v1.0");
		BonzeeRules n = new BonzeeRules();
		n.printBoard();

		while (!n.gameOver(0)) {
			n.makeMove();
			n.printBoard();
			System.out.println("Green Pieces left: " +n.greenPiece);
			System.out.println("Red Pieces left: "+n.redPiece);
		}

	}

}

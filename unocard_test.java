package practice;
import java.util.Random;

public class unocard_test {

	public static void main(String[] args) {

		Unodeck drawDeck = new Unodeck();
		Unodeck discardDeck = new Unodeck();
		
		drawDeck.fillDeck();
		drawDeck.shuffleDeck();
		//drawDeck.displayDeck();

		Player player1 = new Player(drawDeck);
		player1.displayHand();	
		
		discardDeck.displayDeck();
		
		
	}

}































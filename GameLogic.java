package practice;

public class GameLogic {

	public static void main(String[] args) {

		/* make the draw and discard decks (as stacks) */
		Unodeck drawDeck = new Unodeck(); 
		Unodeck discardDeck = new Unodeck();
		/* fill the draw deck and shuffle it */
		drawDeck.fillDeck();
		drawDeck.shuffleDeck();

		/* create the 2 players */
		Player player1 = new Player(drawDeck);
		Player player2 = new Player(drawDeck);
		
		/* display the players hands */
		player1.displayHand();	
		player2.displayHand();
		
		//drawDeck.displayDeck();
		//discardDeck.pushCard(drawDeck.popCard());	
		//discardDeck.displayDeck();
		
		
	}
}

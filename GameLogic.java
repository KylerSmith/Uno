package practice;

public class GameLogic {
	
	

		
	public static void main(String[] args) {	
		
		GameLogic gl = new GameLogic();
		
	}
		
	public GameLogic() {

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
		
		
		/* Flip the first card from the draw deck to discard deck, if it is an action card */
		discardDeck.pushCard(drawDeck.popCard());
		
		/* while there is no winner */
		while (!isWinner(player1, player2)) {
			
			//playCard(Player pPlayer1, Player pPlayer2, Unodeck pDrawDeck, Unodeck pDiscardDeck);
			
		}
		
		
		
		
		
		
		//drawDeck.displayDeck();
		//discardDeck.pushCard(drawDeck.popCard());	
		discardDeck.displayDeck();
	}


	
	
	
	public static boolean isWinner(Player p1, Player p2) {
		boolean returnValue = false; // return
		
		if (p1.handSize == 0 || p2.handSize == 0) {
			returnValue = true;
		}
		
		return false;
	}
	
	
	public static void playCard(Player pPlayer1, Player pPlayer2, Unodeck pDrawDeck, Unodeck pDiscardDeck) {
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package UnoVersion_04;

public class Player {
	
	Unocard[] hand = new Unocard[20];
	int handSize = 0;
	static int playerNum = 0;
	String playerName; // Simply 'Player 1' or 'Player 2'
	static int playerTurn; // If this is a 0, then it is player 2's turn, if it is a 1, then it is player 1's turn
	
//===================================================================

/* Player constructor */	
	public Player(Unodeck d) {
		++playerNum;
		playerName = "Player" + playerNum;
		
		for (int i = 0; i <= 4; ++i) {
			hand[handSize] = d.popCard();
			++handSize;
		}
	}
	
	
//===================================================================
// Define methods
	
	public void displayHand() {
		System.out.println("");
		for (int i = 0; i < handSize; ++i) {
			if (hand[i].getValue() <= 5) {
				/* displays the color and number if its a number card */
				System.out.print("[ " + hand[i].getColor() + " " + hand[i].getValue() + " ] ");
			} else {
				/* displays the color and the action if its an action card */
				System.out.print("[ " + hand[i].getColor() + " " + hand[i].getAction() + " ] ");
			}
		}	
	}
	
// ===================================================================
	
	public int getHandSize(){
		return handSize;
	}

// ===================================================================	
	
	public String [] getCardsInHand(){
		String [] cards = new String[handSize];
		
		for(int i = 0; i < handSize; i++){
			
			if (hand[i].getValue() <= 5) {
				cards[i] = hand[i].getColor() + "," + hand[i].getValue();
			} else {
				cards[i] = hand[i].getColor() + "," + hand[i].getAction();
			}		
			//System.out.println(cards[i]);
		}
		return cards;
	}
	
// ===================================================================
	
	public String sendCardsInHand(String [] theCards){
		String CardsInHand = "";
		for(int i = 0; i < theCards.length; i++){
			CardsInHand+=theCards[i]+":";	
		}
		return CardsInHand;		
	}
	
	
// ==================================================================
	public String getName(){
		return playerName;
	}
	
}


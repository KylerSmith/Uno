package practice;

public class Player {
	
	Unocard[] hand = new Unocard[20];
	int handSize = 0;
	static int playerNum = 0;
	String playerName;
	
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
	
	public void displayHand() {
		for (int i = 0; i < handSize; ++i) {
			if (hand[i].value <= 5) {
				/* displays the color and number if its a number card */
				System.out.print("[ " + hand[i].color + " " + hand[i].value + " ] ");
			} else {
				/* displays the color and the action if its an action card */
				System.out.print("[ " + hand[i].color + " " + hand[i].action + " ] ");
			}
		}
		
	}
	
	
	
	

}

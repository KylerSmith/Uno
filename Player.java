package practice;

public class Player {
	
	Unocard[] hand = new Unocard[98];
	int handSize = 0;
	static int playerNum = 0;
	String playerName;
	
	public Player() {
		++playerNum;
		playerName = "Player" + playerNum;
	}
	
	public void drawCards(int amountToDraw, Unocard[] deck) {
		
	}
	

}

package practice;

import java.util.Random;

/* cards 0-5
 * 2 reverse (card # 6,7), 
 * 2 draw two (card # 8, 9) 
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 */

public class Unodeck {
	
	final int MAX_DECK_SIZE = 82;

	Unocard[] deck = new Unocard[MAX_DECK_SIZE];
	int deckSize = 0;
	Unocard top = deck[deckSize];
	boolean empty = true;
	
	
	//==================================================================================

	
	public void displayDeck() {
		for (int i = 0; i < deck.length; ++i) {
			System.out.println(deck[i].color + " " + deck[i].value);
		}
	}
	
	//==================================================================================
	
	public void fillDeck() {
		
		/* chooses the value of the cards */
		int cardNum = 0; 
		/* numOfEaColor will determine how many number cards we use */
		int numOfEaColor = 9;
		/* color increment keeps track of what color to assign */
		int colorInc = 0;

		// for loop begin
		for (int i = 0; i < deck.length; ++i) {
			
			/* new Card to put into the deck */
			Unocard newCard = new Unocard();
			
			/* conditional set the values of the cards */
			if (cardNum > numOfEaColor) {
				cardNum = cardNum - (numOfEaColor + 1);
				++colorInc;
			}
			
			/* chooses the color of the card */
			if (colorInc >= 6) {
				newCard.color = "yellow";
			} else if (colorInc >= 4) {
				newCard.color = "blue";
			}else if (colorInc >= 2) {
				newCard.color = "green";
			} else {
				newCard.color = "red";
			}
			
			newCard.value = cardNum;
			
			/* If statements set the 2 wildcards */
			if (i == deck.length - 2) {
				newCard.value = 10;
				newCard.color = "black";
			} 
			if (i == deck.length - 1) {
				newCard.value = 11;
				newCard.color = "black";
			} 
			
			deck[i] = newCard;
			
			++cardNum;
		} // for loop end
		/* after the deck is filled, it is the max size */
		deckSize = MAX_DECK_SIZE;
		empty = false;
		
	}
	
	
	
//==================================================================================
/* function that shuffles all of the cards */
	public void shuffleDeck() {
		
		int count = 0, thoroughness = 200, randCardPos;
		//Unocard tmp;
		Random randomNum = new Random();
		
		while (count < thoroughness) {
			Unocard tmp = new Unocard();
			
			randCardPos = randomNum.nextInt(deck.length);
			
			/* algo to perform swap */
				tmp.color = deck[randCardPos].color;
				tmp.value = deck[randCardPos].value;
				deck[randCardPos] = deck[0];
				deck[0] = tmp;
				++count;
			}
		}
	
	
//==================================================================================
	
	public void pushCard(Unocard c) {
		
		
	}
	
	
	
	
}

















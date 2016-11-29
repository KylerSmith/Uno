package UnoVersion_05;

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
	
	
	//==================================================================================

	public Unocard peekCard() {
		Unocard returnCard  = deck[deckSize - 1]; // deckSize - 1
		return returnCard;	
	}
	
	//==================================================================================

	
	public Unocard popCard() {
		Unocard returnCard;
		returnCard = deck[deckSize - 1];
		deckSize--;
		return returnCard;
	}
	
	//==================================================================================
	
	public void pushCard(Unocard c) {
		deck[deckSize] = c;
		deckSize++;
	}
	
	//==================================================================================

	
	
	
		
	//==================================================================================
	
	public void displayDeck() {
		System.out.println("");
		for (int i = 0; i < deckSize; ++i) {
			
			System.out.print(i + ": ");
			
			if ((deck[i].getValue() <= 5) && (deckSize > 0)) {
				/* displays the color and number if its a number card */
				System.out.println(deck[i].getColor() + " " + deck[i].getValue());
			} else {
				/* displays the color and the action if its an action card */
				System.out.println(deck[i].getColor() + " " + deck[i].getAction());
			}	
		}
	}
	
	//==================================================================================
	
	public void fillDeck() {
		
		/* chooses the value of the cards */
		int cardNum = 0; 
		/* numOfEaColor will determine how many number cards we use */
		int numOfEaColor = 8;
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
				newCard.setColor("yellow");;
			} else if (colorInc >= 4) {
				newCard.setColor("blue");
			}else if (colorInc >= 2) {
				newCard.setColor("green");
			} else {
				newCard.setColor("red");
			}
			
			newCard.setValue(cardNum);
			
			/* If statements set the 2 wildcards */
			if (i == deck.length - 2) {
				newCard.setValue(9);
				newCard.setColor("black");
				newCard.setAction("wild");
			} 
			if (i == deck.length - 1) {
				newCard.setValue(10);
				newCard.setColor("black");
				newCard.setAction("wild");
			} 
			
			/* if the card is higher than a 5, set it's action */
	    	if (newCard.getValue() > 5) {
	    		if (newCard.getValue() == 6) {
	    			newCard.setAction("skip");
	    		} else if (newCard.getValue() == 7) {
	    			newCard.setAction("reverse");
	    		} else if (newCard.getValue() == 8) {
	    			newCard.setAction("draw two");
	    		}
	    	}
			
			deck[i] = newCard;
			++cardNum;
		} // for loop end
		/* after the deck is filled, it is the max size */
		deckSize = MAX_DECK_SIZE;
		
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
				tmp.setColor(deck[randCardPos].getColor());
				tmp.setValue(deck[randCardPos].getValue());
				tmp.setAction(deck[randCardPos].getAction());
				deck[randCardPos] = deck[0];
				deck[0] = tmp;
				++count;
			}
		}	
	
//==================================================================================
	public void validateStart(){
	    boolean flag = false;
	    while (!flag) { 
	    	if (deck[deckSize - 1].getValue() > 5) { // If an action card, shuffle
	    		//System.out.println("Initial value = " + deck[deckSize - 1].getValue());
	    		shuffleDeck();
	    		//System.out.println("After shuffle value = " + deck[deckSize - 1].getValue());
	        } else flag = true; 					// else exit 
	    }
	}
	
	
}

















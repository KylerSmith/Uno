

import java.util.Random;

/* cards 0-5
 * 2 reverse (card # 6,7), 
 * 2 draw two (card # 8, 9) 
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 */

public class Unodeck
{
	public static void main(String [] args)
	{
		Unodeck deck= new Unodeck();
		deck.fillDeck();
	}
	
	final int MAX_DECK_SIZE = 74;

	Unocard[] deck = new Unocard[MAX_DECK_SIZE];
	int deckSize =MAX_DECK_SIZE ;
	
	//==================================================================================
	
	public void fillDeck() 
	{
		String [] colors = {"red","blue","green","yellow"};
		String [] actions = {"skip","reverse","draw two","wild"};
		int [] nums = {0,1,2,3,4,5};
		int numIndex=0;
		int colorIndex=0;
		int actIndex=0;
		
		 
		for(int j=0;j<4;j++)
		{
			
			for(int i=0;i<18;i++)
			{
				Unocard card = new Unocard();
				card.setColor(colors[colorIndex]);
				//Set Number Cards
				if(i<12)
				{	
					card.setValue(nums[numIndex]);
					numIndex++;
					if(numIndex==6)numIndex=0;
				}
				else //Set Action Cards
				{
					card.setValue(6);
					card.setColor(colors[colorIndex]);
					card.setAction(actions[actIndex]);
					actIndex++;
					if(actIndex==3)actIndex=0;
				}
			
			deck[i]=card;
			deck[i].displayCard();
			}
			colorIndex++;
		}
		
		//Make Wild Cards
		Unocard card = new Unocard();
		card.setValue(6);
		card.setAction("wild");
		deck[72]=card;
	
		card = new Unocard();
		card.setValue(6);
		card.setAction("wild");
		deck[73]=card;
		
				
	}
		
	
}		
		 // for loop end
		/* after the deck is filled, it is the max size */
		
		
















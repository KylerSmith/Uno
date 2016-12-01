package UnoVersion_08;

public class fsdfsa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Unodeck draw = new Unodeck();
		Unodeck dis = new Unodeck();
		
		draw.fillDeck();	
		draw.shuffleDeck();
		
		draw.displayDeck();
		draw.validateStart();
		
		dis.pushCard(draw.popCard());

		System.out.println("\nplay: " + dis.peekCard());
		
		Player p1 = new Player(draw);
		p1.displayHand();
		Player p2 = new Player(draw);
		p2.displayHand();
		
		
		
		
		

	}

}


public class NumberCard extends Card {
	
	// constructors
	NumberCard() {
		setColor("black");
		number = 0;
	}
	
	NumberCard(String pColor){
		setColor(pColor);
		number = 0;
	}
	
	NumberCard(String pColor, int pNumber){
		setColor(pColor);
		number = pNumber;
	}
	
	// properties
	private int number;
	
	// accessors and mutators
	public int getNumber() { return number; }
	public void setNumber(int pNumber) { number = pNumber; }
	
}

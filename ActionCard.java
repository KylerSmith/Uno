
public class ActionCard extends Card {
	
	// constructor for action card
	ActionCard() {
		setColor("black");
		action = null;
	}
	
	ActionCard(String pColor){
		setColor(pColor);
		action = null;
	}
	
	ActionCard(String pColor, String pAction) {
		setColor(pColor);
		action = pAction;
	}
	
	
	// properties
	private String action;
	
	// accessors and mutators
	public String getAction() { return action; }
	public void setAction(String pAction) { action = pAction; }
	
	
	
}

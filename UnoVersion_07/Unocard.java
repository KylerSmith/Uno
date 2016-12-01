package UnoVersion_07;
import java.util.Random;

/* cards 0-5
 * 2 reverse (card # 6,7), 
 * 2 draw two (card # 8, 9) 
 * 2 skip of each color (card # 10, 11)
 * and 2 wild (card # 12, 13)
 * 98 cards total
 */

public class Unocard {
	
	private String color; // color of the card
    private int value; // value of the card
    private Random randomNum;
    private String action = "NUMBER_CARD";
    
    // constructor to set number card
    public Unocard(String pColor, int pValue) {
    	color = pColor;
    	value = pValue;
    }
    // constructor to set action card
    public Unocard(String pColor, String pAction) {
    	color = pColor;
    	action = pAction;
    }
    
    // General Constructor
    public Unocard() {
    	color = "black";
    	value = -1;
    }
    
    
    //=====================================================

    // Setters and Getters
    
    public String getAction() {
    	return action;
    }
    
    public void setAction(String pAction) {
    	action = pAction;
    }
    
    public int getValue() {
    	return value;
    }
    
    public void setValue(int pValue) {
    	value = pValue;
    }
    
    public String getColor() {
    	return color;
    }
    
    public void setColor(String pColor) {
    	color = pColor;
    }
    
    public String toString() {
    	if (value < 5) {
    		return color + "," + value;
    	} else {
    		return color + "," + action;
    	}
    }
    
} // End UnoCard Class
//===================================================================
















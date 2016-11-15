package practice;
import java.util.Random;

/*
 * If the card is a 10 it is a draw 2
 * If the card is an 11 it is a reverse
 * If the card is a 12 it is a skip
 * If the card is a 13 it is a wild card (did not include wild draw 4)
 * */

public class Unocard {
	
	private String color; // color of the card
    private int value; // value of the card
    private Random randomNum;
    private String action = null;
    
    public Unocard(String pColor, int pValue) {
    	color = pColor;
    	value = pValue;
    }
   
    public Unocard() {
    	color = "black";
    	value = -1;
    }
    
    
    //=====================================================

    // setters and getters
    
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
}

















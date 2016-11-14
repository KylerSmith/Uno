package practice;
import java.util.Random;

/*
 * If the card is a 10 it is a draw 2
 * If the card is an 11 it is a reverse
 * If the card is a 12 it is a skip
 * If the card is a 13 it is a wild card (did not include wild draw 4)
 * */

public class Unocard {
	
	public String color; // color of the card
    public int value; // value of the card
    private Random randomNum;
    
    public Unocard(String pColor, int pValue) {
    	color = pColor;
    	value = pValue;
    }
    
    public Unocard() {
    	color = "black";
    	value = 100;
    }
}

















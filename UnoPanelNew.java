package practice;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class UnoPanelNew extends JFrame {

	// Instantiate game variables
	// Public
	String inputFromServer = "";
	String forHand = "";
	public int handSize = 5;
	public String [] theCardsInHand = new String[20];
	public String [] selectedCard = new String[2];
	String selectedCardNumber;
	Color theSelectedCardColor = Color.GRAY;
	
	static boolean myTurn;
	
	// Private
	private DataOutputStream toServer;
	private DataInputStream fromServer;	
	private String host = "localhost";
	private int port = 8000;
	
	// Jframe 
	private JPanel contentPane;
	
	// Start of Main ===============================================
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() { // Start of run ====================
				try {
					UnoPanelNew frame = new UnoPanelNew();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End of run ========================================
		});
	} // End of main ===============================================
	
	// Create the frame ============================================
	public UnoPanelNew() {
	    try {

	        // Create a socket to connect to the server
	        Socket socket = new Socket(host, port); // localhost:8000

	        // Create IO streams to input/output data from the server
	        fromServer = new DataInputStream(socket.getInputStream());
	        toServer =  new DataOutputStream(socket.getOutputStream() );
	      }
	      catch (IOException ex) {
	        System.out.println(ex.toString());
	      }
	}
	
	public void run() {
		/*
		 * Get player name from server
		 * Determine if you are player 1 or player 2
		 * - if player 2, myTurn = false;
		 * - else myTurn = true;
		 * Wait for another player to join
		 * After other player has joined, continue to play
		 * Player 1:
		 * 		Play or Draw
		 * 		Send move to Server
		 * 			Can send 2 types
		 * 			- Play
		 * 				- Send card to server
		 * 			- Draw
		 * 				- Draw will receive data
		 * 		Wait for data from Server
		 * 			Data will come standard every time
		 * 			- Status of game (win or lose)
		 * 			- boolean true or false to determine turn
		 * 			- Number of cards in opponents hand
		 * 			- Top card on discard
		 * Player 2:
		 * 		Wait for data from Server
		 * 			Data will come standard every time
		 * 			- Status of game (win or lose)
		 * 			- boolean true or false to determine turn
		 * 			- Number of cards in opponents hand
		 * 			- Top card on discard
		 * 			
		 * 		Play or Draw
		 * 		Send move to Server
		 * 			Can send 2 types
		 * 			- Play
		 * 				- Send card to server
		 * 			- Draw
		 * 				- Draw will receive data
		 */
	}
	
	// These functions should be in the GameLogic.java file
	private void playOrDraw() {
		// whichever player will be able to play or draw a card
	}
	
	private boolean checkPlay(Unocard playedCard){
		// If card is a wild, matches number, or color, return true
		// If it does not meet above requirements, show error message
		return false;
	}
	
	private void sendPlay() throws IOException {
		// sends card information to the Server
	}
	
	
}

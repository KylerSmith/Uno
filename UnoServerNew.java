package practice;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class UnoServerNew extends JFrame
						  implements UnoConstants {
	// Start of Main ===============================================
	public static void main(String[] args){
		UnoServerNew frame = new UnoServerNew();
	}
	// End of Main =================================================
	
	// Define UnoServerNew =========================================
	public UnoServerNew() {
		private JTextArea jta = new JTextArea();
		
		
			
		// Create GUI for server ===================================
		// Place text area on the frame
	    setLayout(new BorderLayout());
	    add(new JScrollPane(jta), BorderLayout.CENTER);
	    setTitle("UNOSERVER");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!
		
		// Try-Catch block
	    try {
	    	/* Create a server socket
	    	 * Number a session
	    	 * Ready to create a session for every 2 players
	    	 * 	While loop
	    	 * Connect Player 1
	    	 * Notify that the player is Player 1
	    	 * Connect to Player 2
	    	 * Notify that the player is Player 2
	    	 * Display this session and increment session number
	    	 * Create a new thread for this session of 2 players
	    	 * Start a new thread
	    	 */
	    	
	    } catch(IOException e){
	    	System.err.println(e);
	    }
	}
}

class HandleASession implements Runnable, UnoConstants {
	private Socket player1;
	private Socket player2;
	
	// Instantiation ===========================================
		// Public 
			public String [] discardTopCard = new String[2]; // hold value and color of top discard card
		
		// Private
			// None
			
		// Objects
			// Instantiate decks
			Unodeck drawDeck = new Unodeck(); 
			Unodeck discardDeck = new Unodeck();	
		
		// Data Streams declaration
			private DataInputStream fromPlayer1;
			private DataOutputStream toPlayer1;
			private DataInputStream fromPlayer2;
			private DataOutputStream toPlayer2;
		
		// Continue to play?? Not needed
			
		// Construct a thread
		public HandleASession(Socket player1, Socket player2) {
			this.player1 = player1;
			this.player2 = player2;
			
			// Initialize hands
		} // End HandleASession Definition
		
		
		// Implement the run() method for the thread ===============
		public void run() {
			try {
				
				/* Create data input and output streams
				 * Write anything to let player 1 to start
				 * Continuously serve the players and determine and report
				 * 	While loop
				 * 	(Basically everything in this loop will call the functions defined in
				 * 	GameLogic.java)
				 */
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
		
		// Send the move/play to the other player
		/* private void sendMove(DataOutputStream out, int row, int column)
      	 *		throws IOException {
		 *	    out.writeInt(row); // Send row index
		 *	    out.writeInt(column); // Send column index
  		 *	}
		 * 
		 */	
}




package UnoVersion_04;
import java.io.*;
import java.net.*;
import javax.swing.*;

import java.awt.*;
import java.util.*;

public class UnoServerNew extends JFrame implements UnoConstants
{
	//INITS
	private int port = 8000;
	// Start of Main ===============================================
	public static void main(String[] args)
	{
		UnoServerNew frame = new UnoServerNew();
	}
	// End of Main =================================================
	
	// Define UnoServerNew =========================================
	public UnoServerNew() 
	{
		
		JTextArea jta = new JTextArea();
			
		// Create GUI for server ===================================
		// Place text area on the frame
	    setLayout(new BorderLayout());
	    add(new JScrollPane(jta), BorderLayout.CENTER);
	    setTitle("UNOSERVER");
	    setSize(500, 300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(true); // It is necessary to show the frame here!
		
		// Try-Catch block
	    try 
	    { 	
			// Create a server socket//
			ServerSocket serverSocket = new ServerSocket(port);
			// Session ID
			int sessionNo=1;
			// Ready to create a session for every 2 players
			while (true) {
			    jta.append(new Date() +
			      ": Wait for players to join session " + sessionNo + '\n');
			 // Connect Player 1
			Socket player1 = serverSocket.accept(); 
			jta.append(new Date() + ": Player 1 joined session " +
			        sessionNo + '\n');
			jta.append("Player 1's IP address" +
			player1.getInetAddress().getHostAddress() + '\n');
			// Notify that the player is Player 1
			    new DataOutputStream(
			    player1.getOutputStream()).writeInt(PLAYER1);
			
			// Connect to player 2
			Socket player2 = serverSocket.accept();
			
			jta.append(new Date() +
			": Player 2 joined session " + sessionNo + '\n');
			jta.append("Player 2's IP address" +
			player2.getInetAddress().getHostAddress() + '\n');
			
			// Notify that the player is Player 2
			new DataOutputStream(
			player2.getOutputStream()).writeInt(PLAYER2);
			
			// Display this session and increment session number
			jta.append(new Date() + ": Start a thread for session " +
			sessionNo++ + '\n');
			
			// Create a new thread for this session of two players
			HandleASession task = new HandleASession(player1, player2);
			
			// Start the new thread
			new Thread(task).start();
	    	}
	    } 
	    catch(IOException e)
	    {
	    	System.err.println(e);
	    }
	}
}

class HandleASession implements Runnable, UnoConstants 
{
	private Socket player1socket;
	private Socket player2socket;
		
	// Instantiation ===========================================
		// Public 
			public String [] discardTopCard = new String[2]; // hold value and color of top discard card
		
		// Private
			// None
			
		// Objects
			// Instantiate decks
			Unodeck drawDeck = new Unodeck(); 
			Unodeck discardDeck = new Unodeck();	
			// Declare Players
			public Player player1;//= new Player(drawDeck);
			public Player player2;// = new Player(drawDeck);
		
		// Data Streams declaration
			private DataInputStream fromPlayer1;
			private DataOutputStream toPlayer1;
			private DataInputStream fromPlayer2;
			private DataOutputStream toPlayer2;
		
		// Continue to play?? Not needed
			
		// Construct a thread
		public HandleASession(Socket player1, Socket player2) {
			this.player1socket = player1;
			this.player2socket = player2;
			
		System.out.println("Thread created");	
			// Initialize hands
		} // End HandleASession Definition
		
		
		// Implement the run() method for the thread ===============
		public void run() {
			try {
				
				
				
				System.out.println("runfilldeck");
				drawDeck.fillDeck();
				drawDeck.shuffleDeck();
				
				player1= new Player(drawDeck);
				player2= new Player(drawDeck);
				drawDeck.validateStart();
				discardDeck.pushCard(drawDeck.popCard());
				
				// Create data input and output streams
				DataInputStream fromPlayer1 = new DataInputStream(player1socket.getInputStream());
				DataOutputStream toPlayer1 = new DataOutputStream(player1socket.getOutputStream());;
				DataInputStream fromPlayer2 = new DataInputStream(player2socket.getInputStream());;
				DataOutputStream toPlayer2 = new DataOutputStream(player2socket.getOutputStream());;;
				
				
				/*
				 toPlayer1.writeUTF(player1.playerName); // send player1 name to client
				 toPlayer1.flush();
				 
				 toPlayer2.writeUTF(player2.playerName); // send player2 name to client
				 toPlayer2.flush();
				 
				 toPlayer1.writeUTF("Your turn, please select a card to play."); // send message to client
				 toPlayer1.flush();
				 
				 toPlayer2.writeUTF("It's Player 1's turn. Please wait."); // send message to client
				 toPlayer2.flush();
				*/
				 // Continuously serve the players and determine and report
				 while(true){ 
					 // Game logic functions and methods here
					 
					 /* Receive a play from Player 1
					  * 	If Play:
					  * 	- Push received card to top card of discard
					  * 	If Draw
					  * 	- pop card from draw deck and send to Player 1
					  * Check if Player 1 wins
					  * 	- True = break
					  * 	- False = continue
					  * Check if deck is empty
					  * 	- True = shuffle
					  * 	- False = continue
					  * Let player 2 know it is his/her turn
					  * 	- Send a boolean value
					  * Send player 1's move to player 2
					  * 	- Status of game (win or lose)
					  *		- boolean true or false to determine turn
					  * 	- Number of cards in opponents hand
					  * 	- Top card on discard
					  * 
					  * 
					  * Receive a play from Player 2
					  * 	If Play:
					  * 	- Push received card to top card of discard
					  * 	If Draw
					  * 	- pop card from draw deck and send to Player 2
					  * Check if Player 2 wins
					  * 	- True = break
					  * 	- False = continue
					  * Check if deck is empty
					  * 	- True = shuffle
					  * 	- False = continue
					  * Let player 1 know it is his/her turn
					  * 	- Send a boolean value
					  * Send player 2's move to player 2
					  * 	- Status of game (win or lose)
					  *		- boolean true or false to determine turn
					  * 	- Number of cards in opponents hand
					  * 	- Top card on discard
					  * 
					  * Repeat!
					  */
				 }
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
		
		// Send the move/play to the other player functions
		
		private void sendPlay(DataOutputStream output, String color, int value) throws IOException {
			// Send data to client - Card passed should go onto the top of discard
			output.writeUTF(color);
			output.writeUTF(Integer.toString(value));
			output.flush();
		}
		
		private boolean checkWin() {
			// If the players handSize == 0, then they won!
			return false;
		}
		
		private boolean isEmpty() {
			// Check to see if Draw Deck is empty, if it is, then pop all cards
			// from discard to draw and shuffle
			return false;
		}
		

		
		/* private void sendMove(DataOutputStream out, int row, int column)
      	 *		throws IOException {
		 *	    out.writeInt(row); // Send row index
		 *	    out.writeInt(column); // Send column index
  		 *	}
		 * 
		 */	
}
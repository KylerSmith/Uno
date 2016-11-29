package UnoVersion_05;
import java.io.*;
import java.net.*;
import javax.swing.*;

import java.awt.*;
import java.util.*;

public class UnoServerNew extends JFrame implements UnoConstants {
	//INITS
	private int port = 8000;
	// Start of Main ===============================================
	public static void main(String[] args)
	{
		UnoServerNew frame = new UnoServerNew();
	}
	// End of Main =================================================
	
	// Define UnoServerNew =========================================
	public UnoServerNew() {
		
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
	    try { 	
			// Create a server socket//
			ServerSocket serverSocket = new ServerSocket(port);
			// Session ID
			int sessionNo=1;
			// Ready to create a session for every 2 players
			while (true) {
			    jta.append(new Date() +
			      ": Wait for players to join session " + sessionNo + '\n');
			    
// Connect Player 1 ===========================================
			Socket player1 = serverSocket.accept(); 
			jta.append(new Date() + ": Player 1 joined session " +
			        sessionNo + '\n');
			jta.append("Player 1's IP address" +
			player1.getInetAddress().getHostAddress() + '\n');
			
			// Notify that the player is Player 1
			    new DataOutputStream(
			    player1.getOutputStream()).writeInt(PLAYER1);
			
// Connect to player 2 ===========================================
			Socket player2 = serverSocket.accept();
			
			jta.append(new Date() +
			": Player 2 joined session " + sessionNo + '\n');
			jta.append("Player 2's IP address" +
			player2.getInetAddress().getHostAddress() + '\n');
			
			// Notify that the player is Player 2
			new DataOutputStream(
			player2.getOutputStream()).writeInt(PLAYER2);
			
			
// Display this session and increment session number ===============
			jta.append(new Date() + ": Start a thread for session " +
			sessionNo++ + '\n');
			
			// Create a new thread for this session of two players
			HandleASession task = new HandleASession(player1, player2);
			// Start the new thread
			new Thread(task).start();
	    	}
	    } 
	    catch(IOException e) {
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
	
	// hold value and color of top discard card
	public String [] discardTopCard = new String[2]; 
		
		// Private
			// None
			
		// Objects

		// Declare Players
		public Player player1; // new Player(drawDeck);
		public Player player2; // new Player(drawDeck);
				
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
				
				
				// Instantiate decks
				Unodeck drawDeck = new Unodeck(); 
				Unodeck discardDeck = new Unodeck();	

				System.out.println("runFillDeck/shuffleDeck");
				drawDeck.fillDeck();
				drawDeck.shuffleDeck();
				
				
				player1 = new Player(drawDeck);
				player2 = new Player(drawDeck);
				drawDeck.validateStart();
				discardDeck.pushCard(drawDeck.popCard());
				
				// Create data input and output streams
				DataInputStream fromPlayer1 = new DataInputStream(player1socket.getInputStream());
				DataOutputStream toPlayer1 = new DataOutputStream(player1socket.getOutputStream());
				DataInputStream fromPlayer2 = new DataInputStream(player2socket.getInputStream());
				DataOutputStream toPlayer2 = new DataOutputStream(player2socket.getOutputStream());
				
				
				sendInitial(fromPlayer1, toPlayer1, player2.getHandSize(), discardDeck.peekCard().toString(), 
							player1.sendCardsInHand(player1.getCardsInHand()));
				sendInitial(fromPlayer2, toPlayer2, player1.getHandSize(), discardDeck.peekCard().toString(), 
							player2.sendCardsInHand(player2.getCardsInHand()));
				
				
				
				 // Continuously serve the players and determine and report
				 while(true){ 
					 
					 // get the index from the client
					 getPlay(player1, player2, fromPlayer1, toPlayer1, discardDeck); // int indexReceived = fromPlayer1.readInt();

					 //sendPlay(player2, player1);
					 
					 
					 
					 
					 
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
		
		
//============================================================

		// test KAS 11/27
		public void sendInitial(DataInputStream fromPlayer, DataOutputStream toPlayer, int opponentCardAmt,
								String firstDiscard, String playerHand) {

			// send opponent card amount
			try {
				toPlayer.writeInt(opponentCardAmt);
				toPlayer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			// send the first discard to the client
			try {
				toPlayer.writeUTF(firstDiscard);
				toPlayer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// send the first discard to the client
			try {
				toPlayer.writeUTF(playerHand);
				toPlayer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		

//============================================================

		private void sendPlay(Player player, Player opponent, DataOutputStream toOpponent, 
				Unodeck discardDeck) throws IOException {
			
			toOpponent.writeUTF(discardDeck.peekCard().toString());
			
		}

		
		
		
//============================================================

		// Get the move/play from a player
		
		private void getPlay(Player player, Player opponent, DataInputStream fromPlayer, DataOutputStream toPlayer, Unodeck discardDeck) throws IOException {
			
			int check = fromPlayer.readInt();
			
			if (check == PLAYCARD) {
			
				// Send data to client - Card passed should go onto the top of discard
				int indexReceived = fromPlayer.readInt();
				// push that card from player's hand to the discardDeck
				discardDeck.pushCard(player1.hand[indexReceived]);
				// Send Data function will:
				// Send player hand ========================================
				try {
					toPlayer.writeUTF(player.sendCardsInHand(player.getCardsInHand())); // Send the new hand
					toPlayer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// send top discard
				// send the first discard to the client
				try {
					toPlayer.writeUTF(discardDeck.peekCard().toString());
					toPlayer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// send opponent handSize
				/*try {
					toPlayer.writeInt(opponent.getHandSize());
					toPlayer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				*/
			
			}
			// Opponent handsize is playerObject.handSize;
		}


//============================================================

		
		private boolean checkWin() {
			// If the players handSize == 0, then they won!
			return false;
		}
		
//============================================================

private void sendPlay(DataOutputStream toOpponent, Unodeck discardDeck) throws IOException {

	// if opponent played card, sent to the player
	System.out.println("Send this card to the client: " + discardDeck.peekCard().toString());
	toOpponent.writeUTF(discardDeck.peekCard().toString());

}
		
//============================================================

		
		private boolean isEmpty() {
			// Check to see if Draw Deck is empty, if it is, then pop all cards
			// from discard to draw and shuffle
			return false;
		}


		

//============================================================
		
		
		/* private void sendMove(DataOutputStream out, int row, int column)
      	 *		throws IOException {
		 *	    out.writeInt(row); // Send row index
		 *	    out.writeInt(column); // Send column index
  		 *	}
		 * 
		 */	
}
























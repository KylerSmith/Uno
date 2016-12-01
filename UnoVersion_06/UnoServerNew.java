package UnoVersion_06;
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
	boolean game = true;
		
	// Instantiation ===========================================
		// Public 
	
	// hold value and color of top discard card
	public String [] discardTopCard = new String[2]; 
		
		// private
		// values for checking win
		private boolean continueToPlay = true;

		// Declare Players
		public Player player1; // new Player(drawDeck);
		public Player player2; // new Player(drawDeck);
				
		// Construct a thread
		public HandleASession(Socket player1, Socket player2) {
			this.player1socket = player1;
			this.player2socket = player2;
			
			System.out.println("Thread created");				
			
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
				
//				_______________________________________________________________________________	    
//				||||| sends info to UnoPanel:631 respective to player number |||||
//				_______________________________________________________________________________	
		
				// send the initial setup to both clients
				sendInitial(fromPlayer1, toPlayer1, player2.getHandSize(), discardDeck.peekCard().toString(), 
							player1.sendCardsInHand(player1.getCardsInHand()));
				sendInitial(fromPlayer2, toPlayer2, player1.getHandSize(), discardDeck.peekCard().toString(), 
							player2.sendCardsInHand(player2.getCardsInHand()));
				
				 // Continuously serve the players and determine and report
				 while(true){ 
					 
					 
					 
					 // get the play from player1
					 play(player1, player2, fromPlayer1, toPlayer1, discardDeck, drawDeck, toPlayer2);
					 
					 play(player2, player1, fromPlayer2, toPlayer2, discardDeck, drawDeck, toPlayer1);
					 
				 }
				
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}
		
		
		
		
		
		
//============================================================

		// Get the move from a player
		
		private void play(Player player, Player opponent, DataInputStream fromPlayer, 
				DataOutputStream toPlayer, Unodeck discardDeck, Unodeck drawDeck, DataOutputStream toOpponent)  throws IOException {
			
			int newStatus = 0;
			// get the value for what button the player choose, (draw(3) or play(4)).
			int status = fromPlayer.readInt(); // UnoServer:482 | UnoServer:511, respective to status
			
			// run this if they want to play a card
			if (status == PLAYCARD) {
			
				// Send data to client - Card passed should go onto the top of discard
				int indexReceived = fromPlayer.readInt(); // UnoPanel:489
											
				// push that card from player's hand to the discardDeck
				discardDeck.pushCard(player1.hand[indexReceived]);
				
				// take the card out of the server hand
				player1.updateHandAfterPlay(indexReceived);

				 // Send the new hand, with one less card
				toPlayer.writeUTF(player.sendCardsInHand(player.getCardsInHand())); // UnoPanel:492
				toPlayer.flush();
				
				// send the first discard to the player 
				toPlayer.writeUTF(discardDeck.peekCard().toString()); // UnoPanel:486
				toPlayer.flush();	

				
			} else if(status == DRAW) {	
				
				System.out.print("HERE::: ");
				player.displayHand();

				// updates the player's hand on the server
				player.updateHandAfterDraw(drawDeck.popCard());
				
				// send the card in the hand as a string 
				toPlayer.writeUTF(player.sendCardsInHand(player.getCardsInHand())); // UnoPanel:511
				toPlayer.flush();
				
				System.out.print("HERE::: ");
				player.displayHand();
			}
			
			
			
			
			// check to see if anyone has won
			if (player1.getHandSize() == 0) {
				// all of player1's cards are gone, they win!
				newStatus = PLAYER1_WON;
				continueToPlay = false;
			} else if (player2.getHandSize() == 0) {
				// all of player2's cards are gone, they win!
				newStatus = PLAYER2_WON;
				continueToPlay = false;
			} if (drawDeck.deckSize == 1) {
				newStatus = DRAW_GAME;
			}
			
			
			
			// send the PLAY to the opponent
			sendMove(toOpponent, discardDeck, player.getHandSize(), newStatus); 
		}
		
//============================================================
		
		// overload send play for play
		private void sendMove(DataOutputStream toOpponent, Unodeck discardDeck, int newHandSize, int status) throws IOException {
			
			if (status == PLAYER1_WON || status == PLAYER2_WON || status == DRAW_GAME) {
				System.out.println("GAME_OVER");
			} else {
				
				// SENDS THE STATUS OF THE GAME
				toOpponent.writeInt(status);
				
				// if opponent played card, send to the player, card that is tobe checked against
				System.out.println("Send this card to the client: " + discardDeck.peekCard().toString());
				toOpponent.writeUTF(discardDeck.peekCard().toString());
				
				toOpponent.writeInt(newHandSize);
				toOpponent.flush();

			}
		}

		
//============================================================

		// test KAS 11/27
		public void sendInitial(DataInputStream fromPlayer, DataOutputStream toPlayer, int opponentCardAmt,
								String firstDiscard, String playerHand) {

			// send opponent card amount
			try {
				// send opponents card amount
				toPlayer.writeInt(opponentCardAmt); // UnoPanel:683
				toPlayer.flush();

				// send the first card that's discarded
				toPlayer.writeUTF(firstDiscard); // UnoPanel:687
				toPlayer.flush();

				// send the players hand to themself
				toPlayer.writeUTF(playerHand); // UnoPanel:690
				toPlayer.flush();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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
























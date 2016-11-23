package UnoVersion_04;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class UnoServer extends JFrame {

  // Text area for displaying contents
  private JTextArea jta = new JTextArea();
  
  // Makes decks
  Unodeck drawDeck = new Unodeck(); 
  Unodeck discardDeck = new Unodeck();
  
  // TRESOR - Variable to Hold top discard
  public String [] discardTopCard = new String[2];
   
  public static void main(String[] args) {
    new UnoServer();
  }

  public UnoServer() {

	  // fill the deck and shuffles the cards
	  drawDeck.fillDeck();
	  drawDeck.shuffleDeck();
	  
    // Place text area on the frame
    setLayout(new BorderLayout());
    add(new JScrollPane(jta), BorderLayout.CENTER);
    setTitle("UNOSERVER");
    setSize(500, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true); // It is necessary to show the frame here!

    try {

      // Create a server socket
      ServerSocket serverSocket = new ServerSocket(8000);
      jta.append("UnoServer started at " + new Date() + '\n');

      // Number a client
      int clientNo = 1;

      while (true) {

        // Listen for a new connection request
        Socket socket = serverSocket.accept();

        // Display the client number
        jta.append("Starting thread for client " + clientNo + " at " + new Date() + '\n');

        // Find the client's host name, and IP address
        InetAddress inetAddress = socket.getInetAddress();
        jta.append("Client " + clientNo + "'s host name is " + inetAddress.getHostName() + "\n");
        jta.append("Client " + clientNo + "'s IP Address is " + inetAddress.getHostAddress() + "\n");

        // Create a new thread for the connection
        HandleAClient task = new HandleAClient(socket);

        // Start the new thread
        new Thread(task).start();

        // Increment clientNo
        clientNo++;
      }
    }
    catch(IOException ex) {
      System.err.println(ex);
    }
  }

  // Inner class
  // Define the thread class for handling new connection
  class HandleAClient implements Runnable {

	  
	  public Player player = new Player(drawDeck);
	  private Socket socket; // A connected socket

    /** Construct a thread */
	  public HandleAClient(Socket socket) {
      this.socket = socket;
    }

    /** Run a thread */
    public void run() {
      try {
 
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        
        // put the first card from the draw deck to the discard deck
        discardDeck.pushCard(drawDeck.popCard());
        
        // send the player name to the client
        outputToClient.writeUTF(this.player.playerName);
        outputToClient.flush();
        
        // current discard to test against played cards
        discardTopCard = discardDeck.peekCard().toString().split(",");
        
        // look at the top card 
        outputToClient.writeUTF(discardDeck.peekCard().toString());
        outputToClient.flush();
    	
    	// takes the cards in the hand and sends them as string to client
    	outputToClient.writeUTF(this.player.sendCardsInHand(this.player.getCardsInHand()));
    	outputToClient.flush();
    	
        // Continuously serve the client
        while (true) {
    	// code that the server constantly runs. =================================        	

        	String clientInput = inputFromClient.readUTF();
        	
        	// TRESOR - Added Player Name or Input from Client - this.player.getName()
        	System.out.println(this.player.getName() +" "+clientInput);
        	
        	String [] parsedInput = clientInput.split(":");

        	// check the signature passed for draw card
        	if (parsedInput[0].equalsIgnoreCase("draw pressed")) {
        		
        		// if it is a draw card, we pop the card and put it in the hand
        		this.player.hand[this.player.getHandSize()] = drawDeck.popCard();
        		this.player.handSize += 1; // increment the hand by 1
        		
        		// send the whole hand to the client for display 
        		outputToClient.writeUTF(this.player.sendCardsInHand(this.player.getCardsInHand()));
        		outputToClient.flush();
        		 // if sig is playedCard
        	} else if(parsedInput[0].equals("playedCard")){
        		
        		//  TRESOR -Parse the played card by , (card received in form "yellow,5")
        		String [] cardPlayed = parsedInput[1].split(",");
        		
        		//  TRESOR -Check Played Card against top card on discard deck
        		if(cardPlayed[0].equals(discardTopCard[0]) || cardPlayed[1].equals(discardTopCard[1])){
        			
            		//  TRESOR -Notify Valid Move to Client if card is valid
            		outputToClient.writeUTF("validMove");
            		outputToClient.flush();
            		
            		//  TRESOR -Add the card to discard deck
            		String newDiscardColor = cardPlayed[0];
            		String newDiscardValue = "";
            		
            		if(Character.isDigit(cardPlayed[1].charAt(0))){
            			
            			Unocard newDicard = new Unocard(cardPlayed[0], Integer.parseInt(cardPlayed[1]));	
            			
            			discardDeck.pushCard(newDicard);
            	        
            	        outputToClient.writeUTF(discardDeck.peekCard().toString());
            	        System.out.println(discardDeck.peekCard().toString()+"Hahha");
            	        outputToClient.flush();
            			
            		}else{
            			
            			Unocard newDicard = new Unocard(cardPlayed[0], cardPlayed[1]);
            			
            			discardDeck.pushCard(newDicard);
            	        
            	        outputToClient.writeUTF(discardDeck.peekCard().toString());
            	        System.out.println(discardDeck.peekCard().toString()+"Hahha");
            	        outputToClient.flush();
            		}
            		
            		//  TRESOR - Otherwise, notify of invalid card if played card is Invalid
            	}else{
            		
            		outputToClient.writeUTF("invalidMove");
            		outputToClient.flush();
            	}
        		
        		
        	}
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        }
       // end code that the server constantly runs. ============================
      }
      catch(IOException e) {
        System.err.println(e);
      }
    }
  }	  
}






















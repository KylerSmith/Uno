package UnoVersion_05;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
import java.io.BufferedReader;

public class UnoPanelForWindows extends JFrame implements UnoConstants {

	// Instantiate game variables
		// Public
		String inputFromServer = "";
		String forHand = "";
		public int handSize = 5;
		public String [] theCardsInHand = new String[20];
		public String [] selectedCard = new String[2];
		String selectedCardNumber;
		Color theSelectedCardColor = Color.GRAY;
		
		int player;
		
		// while playing variables
		static boolean myTurn;
		boolean waiting = true;
		String currentSelectedCard; // should be in the form (color,value)
		boolean continueToPlay = true;
		boolean isValidPlay = false;
		
		// Private
		private DataOutputStream toServer;
		private DataInputStream fromServer;	
		private String host = "localhost";
		private int port = 8000;
		
		// Jframe 
		private JPanel contentPane;
		
		
		// Runtime variables
		// Initiation variables
		String opponentName;
		int opponentCardCount;
		String topDiscardCard;
		String playersHand;
		static boolean gameStarted = false;
	
		
		// Start of Main ===============================================
		public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
				public void run() { // Start of run ====================
					try {
						UnoPanelForWindows frame = new UnoPanelForWindows();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // End of run ========================================
			});
		} // End of main ===============================================
	
	// Create the frame ============================================
	public UnoPanelForWindows() {
				
		// Creates main window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Panels ====================================================
		
		JPanel GameBoardPanel = new JPanel();
		GameBoardPanel.setBackground(Color.LIGHT_GRAY);
		GameBoardPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameBoardPanel);
		GameBoardPanel.setLayout(null);
		
		JPanel selectedCardColor = new JPanel();
		selectedCardColor.setBackground(Color.GREEN);
		selectedCardColor.setBounds(507, 302, 67, 138);
		GameBoardPanel.add(selectedCardColor);
		selectedCardColor.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBounds(445, 322, 67, 138);
		GameBoardPanel.add(panel_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_5.setBounds(564, 322, 61, 138);
		GameBoardPanel.add(panel_5);
		
		JPanel topDiscardColor = new JPanel();
		topDiscardColor.setBackground(Color.GRAY);
		topDiscardColor.setBounds(103, 249, 159, 245);
		GameBoardPanel.add(topDiscardColor);
		topDiscardColor.setLayout(null);
		
		JPanel GameMenuPanel = new JPanel();
		GameMenuPanel.setBackground(new Color(0, 255, 0));
		GameMenuPanel.setForeground(new Color(0, 255, 0));
		GameMenuPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameMenuPanel);
		GameMenuPanel.setLayout(null);
		GameMenuPanel.setOpaque(false);
		
		JPanel HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(HelpPanel);
		
		// Buttons ===================================================
				JButton btnGame = new JButton("Game");
				
				// Main Screen Help
				JButton btnHelp = new JButton("Help");
				btnHelp.setBounds(0, 47, 64, 38);
				contentPane.add(btnHelp);
				
			    // Main Screen Exit	
				JButton btnExit = new JButton("Exit");
				btnExit.setBounds(0, 90, 64, 38);
				contentPane.add(btnExit);
				
				// Game button
				btnGame.setBounds(0, 6, 64, 38);
				contentPane.add(btnGame);
				
				JButton drawButton = new JButton("Draw");
				drawButton.setForeground(UIManager.getColor("Button.light"));
				drawButton.setBackground(UIManager.getColor("Button.light"));
				drawButton.setBounds(799, 249, 159, 245);
				GameBoardPanel.add(drawButton);
				
				JButton connect = new JButton("Find a game");
				connect.setBounds(389, 342, 287, 82);
					
				JButton play = new JButton("Play Game");
				play.setBounds(389, 342, 287, 82);
				
				// play this card button
				JButton btnPlaythiscard = new JButton("PlayThisCard");
				btnPlaythiscard.setBounds(490, 530, 117, 29);
				GameBoardPanel.add(btnPlaythiscard);
				
				GameBoardPanel.setVisible(false);	
				// Add play button to GameMenuPanel
				GameMenuPanel.add(play);	
				GameMenuPanel.setVisible(true);
				HelpPanel.setVisible(false);
		
		//Images ============================================
		
				BufferedImage drawButtonImage = null;
				
				try {	
					drawButtonImage = ImageIO.read(new File("C:\\Class\\SER215\\Project\\Pics\\UI\\FaceDown.png"));
				} catch (IOException e) {
				}
				
				Image theResizedCardImageForDrawButton = 
				drawButtonImage.getScaledInstance(drawButton.getWidth(), drawButton.getHeight(),Image.SCALE_DEFAULT);
			    
				ImageIcon theDrawButtonIcon = new ImageIcon(theResizedCardImageForDrawButton);

				drawButton.setIcon(theDrawButtonIcon);
				
				BufferedImage flippedCardImage = null;
				try {	
					flippedCardImage = ImageIO.read(new File("C:\\Class\\SER215\\Project\\Pics\\UI\\FaceDown.png"));
				} catch (IOException e) {
				}
				
				

		
		// Labels ====================================================
		JLabel selectedCardNumber = new JLabel("New label");
		selectedCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCardNumber.setBounds(6, 54, 55, 43);
		selectedCardColor.add(selectedCardNumber);
		
		JLabel selectedCardInHand = new JLabel("");
		selectedCardInHand.setBounds(493, 267, 92, 171);
		//selectedCardInHand.setIcon(card);
		GameBoardPanel.add(selectedCardInHand);
		
		JLabel otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);
		
		JLabel topDiscardNumber = new JLabel("No Cards played");
		topDiscardNumber.setBounds(29, 67, 103, 71);
		topDiscardColor.add(topDiscardNumber);
		
		JLabel topDiscard = new JLabel("");
		topDiscard.setBounds(123, 249, 159, 245);
		GameBoardPanel.add(topDiscard);
		
		JLabel GameMenuLabel = new JLabel("New label");
		GameMenuLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\GameMenu.jpg"));
		GameMenuLabel.setBounds(0, 0, 1060, 590);
		GameMenuPanel.add(GameMenuLabel);
		
		// other player hand size
		JLabel otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		GameBoardPanel.add(otherPlayerhandSize);
		
		System.out.println("backgroundlabel made");
		JLabel BackgroundLabel = new JLabel(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\Background.jpg"));
		BackgroundLabel.setBounds(0, 0, 1060, 590);
		GameBoardPanel.add(BackgroundLabel);
		
		
		// Slider =========================================
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);
		
		// Mandatory Image Outliers
		Image theResizedCardImageForFlippedCards = 
		flippedCardImage.getScaledInstance(selectedCardInHand.getWidth(), selectedCardInHand.getHeight(),Image.SCALE_DEFAULT);
			    
		ImageIcon theFlippedCardIcon = new ImageIcon(theResizedCardImageForFlippedCards);
		
		JLabel leftCard = new JLabel("");
		leftCard.setBounds(420, 306, 92, 171);
		GameBoardPanel.add(leftCard);
		leftCard.setIcon(theFlippedCardIcon);
		
		JLabel rightCard = new JLabel("");
		rightCard.setBounds(566, 306, 92, 171);
		GameBoardPanel.add(rightCard);
		rightCard.setIcon(theFlippedCardIcon);
		
// Action Listeners =========================================
		// Action listener if user wants to exit
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close dialog code
				JDialog.setDefaultLookAndFeelDecorated(true);
			    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit game",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.NO_OPTION) {
			      System.out.println("No button clicked");
			    } else if (response == JOptionPane.YES_OPTION) {
			    	System.exit(0);
			    	try {
			    		toServer.writeBoolean(false);
					} catch (IOException e1) {
						e1.printStackTrace();
					}   	
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			      System.out.println("JOptionPane closed");
			    } // End of close dialog code
			}
		});
		
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				HelpPanel.setVisible(false); //panel_1 is red
				
				if(gameStarted){
					GameMenuPanel.setVisible(false);
					GameBoardPanel.setVisible(true); // panel_2 is blue
				}else{
					GameMenuPanel.setVisible(true); // panel is green
					GameBoardPanel.setVisible(false);
				}
			}
		});
		
		btnPlaythiscard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				validatePlay(currentSelectedCard, topDiscardCard);
				if(isValidPlay){
					//send the index of the chosen card to the server to evaluate which card to play
					sendPlay(slider.getValue()-1, slider, playersHand, btnPlaythiscard, drawButton);
				} else {
					//invalid card error dialog
					String [] e1 = topDiscardCard.split(",");
					String eColor =e1[0];
					String eVal = e1[1];
					JOptionPane.showMessageDialog(null, 
							"Please play a card that's either" + eColor + "Or is " + eVal,
							"Invalid Card",
							JOptionPane.ERROR_MESSAGE);
				}
				isValidPlay=false;
			
			}
		});
		
		// drawButton will act everytime a card is drawn.
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				//PlayDraw
				try{
				//Send to server that client wants to draw a card
				toServer.writeInt(DRAW);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				try{
					//Send to server that client wants to draw a card
					playersHand = fromServer.readUTF();
					System.out.println("Cards read from server: " + playersHand);
					slider.setMaximum(slider.getMaximum()+1);
					myTurn=false;
					}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				
				
			}
			
		});
		
		// play goes to the panel with the game
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				gameStarted = true;
				play.setVisible(false);
				GameMenuPanel.setVisible(false);
				HelpPanel.setVisible(false);
				GameBoardPanel.setVisible(true);
				try {
					forHand = fromServer.readUTF();
					System.out.println(forHand); // prints to the console the hand
					
					
					String [] middleCard = new String[2];
					middleCard = theCardsInHand[2].split(",");
					
					selectedCardNumber.setText(middleCard[1]);
					
					if(middleCard[0].equals("yellow")){
						theSelectedCardColor = Color.yellow;
						selectedCardColor.setBackground(theSelectedCardColor);
						selectedCardNumber.setForeground(Color.black);
					}else if(middleCard[0].equals("black")){
						theSelectedCardColor = Color.black;
						selectedCardColor.setBackground(theSelectedCardColor);
						selectedCardNumber.setForeground(Color.white);
					}else if(middleCard[0].equals("blue")){
						theSelectedCardColor = Color.blue;
						selectedCardColor.setBackground(theSelectedCardColor);
						selectedCardNumber.setForeground(Color.black);
					}else if(middleCard[0].equals("green")){
						theSelectedCardColor = Color.green;
						selectedCardColor.setBackground(theSelectedCardColor);
						selectedCardNumber.setForeground(Color.black);
					}else if(middleCard[0].equals("red")){
						theSelectedCardColor = Color.red;
						selectedCardColor.setBackground(theSelectedCardColor);
						selectedCardNumber.setForeground(Color.black);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		
		// play goes to the panel with the game
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				// --------------- Connect to server ----------------------------
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
			    // -----------------------------------------------------------------

			    receiveInitialData(otherPlayerName, otherPlayerhandSize, btnPlaythiscard, drawButton);

			    // find a game button disappears, play button appears -GUI-
				connect.setVisible(false);
				connect.setEnabled(false);
				GameMenuPanel.add(play);
				play.setVisible(true);
			}
		});		

		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				GameMenuPanel.setVisible(false); // panel is green
				HelpPanel.setVisible(true); //panel_1 is red
				GameBoardPanel.setVisible(false);
				
			}
		});
		
		slider.setMaximum(handSize);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
			
				String [] thisCardProperties = new String[2];
				
				for(int i = 0; i < handSize - 1; i++){
					thisCardProperties = theCardsInHand[i].split(",");
					if(slider.getValue() == (i+1)){
						
						//theSelectedcardPosition.setText("Card "+(i+1));
						String thisCardNumber = thisCardProperties[1];
						String thisCardColor = thisCardProperties[0];
						
						// gives the selected card a value to check against
						selectedCard[0] = thisCardColor;
						selectedCard[1] = thisCardNumber;
						
						if(thisCardColor.equals("yellow")){
							theSelectedCardColor = Color.yellow;
							selectedCardColor.setBackground(theSelectedCardColor);
							selectedCardNumber.setForeground(Color.black);
						}else if(thisCardColor.equals("black")){
							theSelectedCardColor = Color.black;
							selectedCardColor.setBackground(theSelectedCardColor);
							selectedCardNumber.setForeground(Color.white);
						}else if(thisCardColor.equals("blue")){
							theSelectedCardColor = Color.blue;
							selectedCardColor.setBackground(theSelectedCardColor);
							selectedCardNumber.setForeground(Color.black);
						}else if(thisCardColor.equals("green")){
							theSelectedCardColor = Color.green;
							selectedCardColor.setBackground(theSelectedCardColor);
							selectedCardNumber.setForeground(Color.black);
						}else if(thisCardColor.equals("red")){
							theSelectedCardColor = Color.red;
							selectedCardColor.setBackground(theSelectedCardColor);
							selectedCardNumber.setForeground(Color.black);
						}
						selectedCardNumber.setText(thisCardNumber);
					} // End original if
				} // End for loop	
			}
		    });

		
		
		//  ******************************** GAME SCREEN COMPONENTS *************************************
		}
	
	
// Begin defining functions ==========================================================	
	
	public void receiveInitialData(JLabel pOtherPlayerName, JLabel pOtherPlayerhandSize, JButton btnPlay, JButton draw) {
		 // set the player to the player number they are
	    try {
			player = fromServer.readInt();
		    // set the opponents label according to who started first
		    if (player == PLAYER1) {
		    	pOtherPlayerName.setText("Player 2");
		    	myTurn = true; // set the first turn to player 1
		    } else {
		    	pOtherPlayerName.setText("Player 1");
		    	myTurn = false; // set the first turn to player1
		    	btnPlay.setEnabled(myTurn);
		    	draw.setEnabled(myTurn);		    	
		    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	    // set the card amount for opponent from the server
	    try {
	    	opponentCardCount = fromServer.readInt();
	    	pOtherPlayerhandSize.setText(Integer.toString(opponentCardCount));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    // read the first card sent to the client
	    try {
	    	// GOOD- returns the correct card that was initially discarded
	    	topDiscardCard = fromServer.readUTF();
	    	System.out.println("card to play:\n" + topDiscardCard);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    
	    // GOOD- read the players hand and puts into string
	    try {
	    	playersHand = fromServer.readUTF();
	    	System.out.println("Player hand:\n" + playersHand);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	// Opposite Player name			x UTF
	// Opposite player card count	x INT
	// top Discard Card				x UTF
	// Player playersHand			x UTF	
	
// ==========================================================
	// These functions should be in the GameLogic.java file

	private void validatePlay(String pCurrentCard, String pLastPlayedCard) {
		String [] played = pCurrentCard.split(",");
		String [] checkAgainst = pLastPlayedCard.split(",");
		
		// checks the current selected card against the card last played
		if (played[0].equals(checkAgainst[0])) {
			System.out.println("Colors match!");
			isValidPlay = true;
		} else if (played[1].equals(checkAgainst[1])) {
			System.out.println("Values match!");
			isValidPlay = true;
		} else if (played[0].equals("black")) {
			System.out.println("wildCard played!");
			isValidPlay = true;
			// select a new color to play
		} else {
			System.out.println("Invalid play!");
			isValidPlay = false;
		}
	}
// ==========================================================
	private void playOrDraw() {
		// whichever player will be able to play or draw a card
	}
	
// ==========================================================
	private boolean checkPlay(Unocard playedCard){
		// If card is a wild, matches number, or color, return true
		// If it does not meet above requirements, show error message
		return false;
	}	
// ==========================================================
	// sends card information to the Server
	private void sendPlay(int index, JSlider slider, String hand, JButton btnPlay, JButton draw) {
		// send over that play button was pressed
		try {
			toServer.writeInt(PLAYCARD);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// sends the index of the card in the hand to the server
		try {
			System.out.println("index sent: " + index);
			toServer.writeInt(index);
		} catch (IOException e){
			e.printStackTrace();
		}		
		 		
	}
// ==========================================================
// run function
public void run() throws IOException, InterruptedException  {
				
		System.out.println("RUN FUNCTION!");
	
	/*
	 * Get player name from server						X DONE in recieve data
	 * Determine if you are player 1 or player 2		X DONE in recieve data
	 * - if player 2, myTurn = false;					X Client checks turn		
	 * - else myTurn = true;							^^^
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

// ==========================================================
}

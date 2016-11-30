package UnoVersion_05;

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

public class UnoPanelTresor extends JFrame implements UnoConstants {

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
						UnopanelNewTwo frame = new UnopanelNewTwo();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // End of run ========================================
			});
		} // End of main ===============================================
	
	// Create the frame ============================================
	public UnoPanelTresor() {
				
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
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.DARK_GRAY);
		panel_6.setBounds(387, 343, 67, 146);
		GameBoardPanel.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(UIManager.getColor("Label.disabledShadow"));
		panel_7.setBounds(618, 343, 67, 146);
		GameBoardPanel.add(panel_7);
		
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
		
		JPanel HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(HelpPanel);
		
		// Labels ====================================================
		JLabel selectedCardNumber = new JLabel("New label");
		selectedCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCardNumber.setBounds(6, 54, 55, 43);
		selectedCardColor.add(selectedCardNumber);
		
		JLabel otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);
		
		JLabel topDiscardNumber = new JLabel("No Cards played");
		topDiscardNumber.setBounds(29, 67, 103, 71);
		topDiscardColor.add(topDiscardNumber);
		
		JLabel GameMenuLabel = new JLabel("New label");
		GameMenuLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\GameMenu.jpg"));
		GameMenuLabel.setBounds(0, 0, 1060, 590);
		GameMenuPanel.add(GameMenuLabel);
		
		// other player hand size
		JLabel otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		GameBoardPanel.add(otherPlayerhandSize);
		
		JLabel BackgroundLabel = new JLabel("New label");
		BackgroundLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\background.jpg"));
		BackgroundLabel.setBounds(0, 0, 1060, 590);
		GameBoardPanel.add(BackgroundLabel);
		
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
		
		// Slider =========================================
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);
		
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
				 try {
					 // send user selected card to server to check if it's valid
					toServer.writeUTF("playedCard:"+selectedCard[0]+","+selectedCard[1]);
					// Get response from server
					inputFromServer = fromServer.readUTF().toString();
					// If server response says invalid 
					if(inputFromServer.equals("invalidMove")){
						//Invalid Card Error dialog
						JOptionPane.showMessageDialog(null,
						    "Please play a card that's either "+topDiscardNumber.getText()+
						    " Or is "+getColorName(topDiscardColor.getBackground()),
						    "Invalid Card",	JOptionPane.ERROR_MESSAGE);
					// If server response says valid 
					}else if(inputFromServer.equals("validMove")){					
						try {
						// Get the new Discard and show it on gui
							String [] newDiscardTop = fromServer.readUTF().split(",");
							topDiscardColor.setBackground(getColor(newDiscardTop[0]));
							topDiscardNumber.setText(newDiscardTop[1]);
						} catch (IOException e2) {
							e2.printStackTrace();
						}
					}
				} catch (IOException e1) {
				e1.printStackTrace();
				}
			}
		});
		
		// drawButton will act everytime a card is drawn.
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// display clients cards
					for(int i = 0; i < handSize; ++i){
						System.out.println(theCardsInHand[i]);
					}
					// send signature to the server, letting them know the draw card button was pressed
					toServer.writeUTF("draw pressed");
					String newHand = fromServer.readUTF();
					System.out.println(newHand);
					// copy over the array from the server to the client
					String [] h = newHand.split(":");
					++handSize;
					for(int i = 0; i < handSize; i++){	
						theCardsInHand[i] = h[i]; 
					}					
					// sets the slider
					slider.setMaximum(handSize);
					slider.setValue(handSize);
					System.out.println(handSize);
					// display clients cards
					for(int i = 0; i < handSize; ++i){
						System.out.println(theCardsInHand[i]);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
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
					theCardsInHand = initiatehand(forHand);
					
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

			    receiveInitialData(otherPlayerName, otherPlayerhandSize);

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
// display logic ===========================================================		
		
		String firstStr = "";
		String [] receivedCard = new String[2];
		
		// read player name and print it out
		try {
			firstStr = fromServer.readUTF();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// set the other players name
		otherPlayerName.setText(firstStr);

		// gets the initial discard to display
		try {
			firstStr = fromServer.readUTF();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		receivedCard = firstStr.split(",");
		String cardColor = receivedCard[0];
		String cardVal = receivedCard[1];
		
		initiateDiscard(cardColor, cardVal, topDiscardColor, topDiscardNumber);
		slider.setMaximum(handSize);
		
		//  ******************************** GAME SCREEN COMPONENTS *************************************
		}
	
	
// Begin defining functions ==========================================================	
	
	public void receiveInitialData(JLabel pOtherPlayerName, JLabel pOtherPlayerhandSize) {
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
		    }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	    // set the card amount for opponent from the server
	    try {
	    	opponentCardCount = fromServer.readInt();
	    	pOtherPlayerhandSize.setText(Integer.toString(opponentCardCount));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    // read the first card sent to the client
	    try {
	    	// GOOD- returns the correct card that was initially discarded
	    	topDiscardCard = fromServer.readUTF();
	    	System.out.println("card to play:\n" + topDiscardCard);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    // GOOD- read the players hand and puts into string
	    try {
	    	playersHand = fromServer.readUTF();
	    	System.out.println("Player hand:\n" + playersHand);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// Opposite Player name			x UTF
	// Opposite player card count	x INT
	// top Discard Card				x UTF
	// Player playersHand			x UTF	
	
// ==========================================================
	
	// Initiate Discard Deck
	public void initiateDiscard(String color, String value, JPanel theCardBackground, JLabel theCardValue){
		
		if(color.equals("yellow")){
			theCardBackground.setBackground(Color.yellow);
			theCardValue.setForeground(Color.black);
		}else if(color.equals("black")){
			theCardBackground.setBackground(Color.black);
			theCardValue.setForeground(Color.white);
		}else if(color.equals("blue")){
			theCardBackground.setBackground(Color.blue);
			theCardValue.setForeground(Color.black);
		}else if(color.equals("green")){
			theCardBackground.setBackground(Color.green);
			theCardValue.setForeground(Color.black);
		}else if(color.equals("red")){
			theCardBackground.setBackground(Color.red);
			theCardValue.setForeground(Color.black);
		}
		theCardValue.setText(value);
	}

// ==========================================================
	
	// initiate hand
	public String [] initiatehand(String theCardsInfo){
		String [] forHands = new String[20];
		String [] theHand = theCardsInfo.split(":");
		for(int i = 0; i < handSize; i++){
			forHands[i] = theHand[i]; 
		}
		return forHands;
	}

// ==========================================================
	
	public String getColorName(Color theColor){		
		System.out.println(theColor);
		String colorName = "";
		if(theColor.equals(Color.GREEN)){
			colorName = "Green";
		}else if(theColor.equals(Color.YELLOW)){
			colorName = "Yellow";
		}else if(theColor.equals(Color.BLUE)){
			colorName = "Blue";
		}else if(theColor.equals(Color.BLACK)){
			colorName = "Black";
		}else if(theColor.equals(Color.RED)){
			colorName = "Red";
		}
		return colorName;
	}
	
// ==========================================================
	
	public Color getColor(String thisCardColor){
		Color myColor = Color.GRAY;
		if(thisCardColor.equals("yellow")){
			myColor = Color.yellow;;
		}else if(thisCardColor.equals("black")){
			myColor = Color.black;
		}else if(thisCardColor.equals("blue")){
			myColor = Color.blue;
		}else if(thisCardColor.equals("green")){
			myColor = Color.green;
		}else if(thisCardColor.equals("red")){
			myColor = Color.red;
		}
		return myColor;	
	}
}

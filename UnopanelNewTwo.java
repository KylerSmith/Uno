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

public class UnopanelNewTwo extends JFrame {

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
	static boolean gameStarted = false;
	
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
	int playerCardCount;
	String topDiscardCard;
	String playersHand;
	
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
	public UnopanelNewTwo() {
		
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnGame = new JButton("Game");
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		// main screen help
			JButton btnHelp = new JButton("Help");
		
			btnHelp.setBounds(0, 47, 64, 38);
			contentPane.add(btnHelp);
		// main screen exit	
			JButton btnExit = new JButton("Exit");
			
			btnExit.setBounds(0, 90, 64, 38);
			contentPane.add(btnExit);
			
			// action listener if user wants to exit
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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    	
				    } else if (response == JOptionPane.CLOSED_OPTION) {
				      System.out.println("JOptionPane closed");
				    }
				    // End of close dialog code
				    
				}
				
			});
		
		// button to play game
		btnGame.setBounds(0, 6, 64, 38);
		contentPane.add(btnGame);
		
		
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
		
		JLabel selectedCardNumber = new JLabel("New label");
		selectedCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCardNumber.setBounds(6, 54, 55, 43);
		selectedCardColor.add(selectedCardNumber);
		
		JButton drawButton = new JButton("Draw");
		drawButton.setForeground(UIManager.getColor("Button.light"));
		drawButton.setBackground(UIManager.getColor("Button.light"));
		drawButton.setBounds(799, 249, 159, 245);
		GameBoardPanel.add(drawButton);
		
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);
		
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
		
		JLabel otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);
		
		JPanel topDiscardColor = new JPanel();
		topDiscardColor.setBackground(Color.GRAY);
		topDiscardColor.setBounds(103, 249, 159, 245);
		GameBoardPanel.add(topDiscardColor);
		topDiscardColor.setLayout(null);
		
		JLabel topDiscardNumber = new JLabel("No Cards played");
		topDiscardNumber.setBounds(29, 67, 103, 71);
		topDiscardColor.add(topDiscardNumber);
		
		GameBoardPanel.setVisible(false);
	
		JPanel GameMenuPanel = new JPanel();
		GameMenuPanel.setBackground(new Color(0, 255, 0));
		GameMenuPanel.setForeground(new Color(0, 255, 0));
		GameMenuPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameMenuPanel);
		GameMenuPanel.setLayout(null);
		
		
			JButton play = new JButton("Play Game");
			play.setBounds(389, 342, 287, 82);
			
			GameMenuPanel.add(play);
			
			JLabel GameMenuLabel = new JLabel("New label");
			GameMenuLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\GameMenu.jpg"));
			GameMenuLabel.setBounds(0, 0, 1060, 590);
			GameMenuPanel.add(GameMenuLabel);
			
			GameMenuPanel.setVisible(true);
		
		JPanel HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(HelpPanel);
		
		HelpPanel.setVisible(false);
		
		// ACTION LISTENERS ===============================================
		
		
				// drawButton will act everytime a card is drawn.
				drawButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						// TODO:
					}
				});

		// =============================================================
				
				// play goes to the panel with the game
				play.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e){
					
						gameStarted = true;
						play.setVisible(false);
						GameMenuPanel.setVisible(false);
						HelpPanel.setVisible(false);
						GameBoardPanel.setVisible(true);
						
						// ------ Connect to server -----------
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
					    // -------------------------
						// Get initial data
					    //receiveInitialData();
					    //parseInitialData();
					    
					    
					    
					}
				});
				
				
		// =============================================================

				btnHelp.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {	
						GameMenuPanel.setVisible(false); // panel is green
						HelpPanel.setVisible(true); //panel_1 is red
						GameBoardPanel.setVisible(false);
						
					}
				});
				
		// =============================================================

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
				
				// other player hand size
				JLabel otherPlayerhandSize = new JLabel("handSize");
				otherPlayerhandSize.setBounds(451, 102, 61, 16);
				GameBoardPanel.add(otherPlayerhandSize);
						
				// pay this card button
				JButton btnPlaythiscard = new JButton("PlayThisCard");
				btnPlaythiscard.setBounds(490, 530, 117, 29);
				GameBoardPanel.add(btnPlaythiscard);
	
				
				
				
				// play this card button
				btnPlaythiscard.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO:
					}
				});
				
				
				
				slider.setMaximum(handSize);
				
				JLabel BackgroundLabel = new JLabel("New label");
				BackgroundLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\background.jpg"));
				BackgroundLabel.setBounds(0, 0, 1060, 590);
				GameBoardPanel.add(BackgroundLabel);
				
				// action listener for the slider
				slider.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						
						String [] thisCardProperties = new String[2];
						
						for(int i = 0; i < handSize - 1; i++){
							
							thisCardProperties = theCardsInHand[i].split(",");
							
							//System.out.println(thisCardProperties[0]+" "+thisCardProperties[1]);
									
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
								
							}
							
						}
						
						
						
					}
				});
			//  ******************************** GAME SCREEN COMPONENTS *************************************
				
				
			}
			
			
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
			
		// ==========================================================	
			
			public void receiveInitialData() {
				try {
					opponentName = fromServer.readUTF();
					playerCardCount = fromServer.readInt();
					topDiscardCard = fromServer.readUTF();
					playersHand = fromServer.readUTF();
					
				} catch (IOException ex){
					System.err.println(ex);
				}	
			}
			
			public void parseInitialData() {
				// opponentName is fine
				// playerCardCount is fine
				String [] discardTopCard = new String[2]; // hold value and color of top discard card
				discardTopCard = topDiscardCard.split(",");
				
				String [] initialHand = new String[5];
				initialHand = playersHand.split(":");
				
				//color,value:color,value:color,value
			}
			
			// Opposite Player name			x UTF
		    // Opposite player card count	x INT
		    // top Discard Card				x UTF
		    // Player playersHand			x UTF (will be parsed differently)
			
	public void run() 
	{
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

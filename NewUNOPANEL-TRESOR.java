package practice;

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

public class UNOPANEL extends JFrame implements UnoConstants {

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
					UNOPANEL frame = new UNOPANEL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} // End of run ========================================
		});
	} // End of main ===============================================
	
	// Create the frame ============================================
	public UNOPANEL() {
		
	    
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//===============================================================================================================================
		
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
		GameBoardPanel.setBackground(Color.WHITE);
		GameBoardPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameBoardPanel);
		GameBoardPanel.setLayout(null);
		GameBoardPanel.setOpaque(false);
		
		JLabel selectedCardInHand = new JLabel("");
		selectedCardInHand.setBounds(493, 267, 92, 171);
		//selectedCardInHand.setIcon(card);
		GameBoardPanel.add(selectedCardInHand);
		
		JButton drawButton = new JButton("");
		drawButton.setForeground(UIManager.getColor("Button.light"));
		drawButton.setBackground(UIManager.getColor("Button.light"));
		drawButton.setBounds(799, 249, 159, 245);
		GameBoardPanel.add(drawButton);
		
		BufferedImage drawButtonImage = null;
		
		try {
			
			drawButtonImage = ImageIO.read(new File("/Users/TreyZor/Downloads/gameCards/FaceDown.png"));
			
		} catch (IOException e) {
			
		}
		
		Image theResizedCardImageForDrawButton = 
				drawButtonImage.getScaledInstance(drawButton.getWidth(), drawButton.getHeight(),Image.SCALE_DEFAULT);
	    
		ImageIcon theDrawButtonIcon = new ImageIcon(theResizedCardImageForDrawButton);

		drawButton.setIcon(theDrawButtonIcon);
		
		BufferedImage flippedCardImage = null;
		
		try {
			
			flippedCardImage = ImageIO.read(new File("/Users/TreyZor/Downloads/gameCards/FaceDown.png"));
			
		} catch (IOException e) {
			
		}
		
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
		
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);

		
		JLabel otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setForeground(Color.white);
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);
		
		GameBoardPanel.setVisible(false);
	
		JPanel GameMenuPanel = new JPanel();
		GameMenuPanel.setBackground(new Color(0, 255, 0));
		GameMenuPanel.setForeground(new Color(0, 255, 0));
		GameMenuPanel.setBounds(0, 0, 1166, 596);
		contentPane.add(GameMenuPanel);
		GameMenuPanel.setLayout(null);
		GameMenuPanel.setOpaque(false);
		
		
			JButton connect = new JButton("Find a game");
			connect.setBounds(389, 342, 287, 82);
		
			JButton play = new JButton("Play Game");
			play.setBounds(389, 342, 287, 82);		
			GameMenuPanel.add(connect);	
			
			GameMenuPanel.add(play);
			play.setVisible(false);
			
			JLabel gameMenuBackground = new JLabel(new ImageIcon("/Users/TreyZor/Downloads/gameCards/GameMenu.jpg"));
			gameMenuBackground.setBounds(0, 0, 1166, 596);
			
			GameMenuPanel.add(gameMenuBackground);
			
			
//			JLabel GameMenuLabel = new JLabel("New label2");
//			GameMenuLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\GameMenu.jpg"));
//			GameMenuLabel.setBounds(0, 0, 1060, 590);
//			GameMenuPanel.add(GameMenuLabel);
//			
//			GameMenuPanel.setVisible(true);
		
		JPanel HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1060, 584);
		HelpPanel.setOpaque(false);
		contentPane.add(HelpPanel);
		
		// other player hand size
		JLabel otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setForeground(Color.white);
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		GameBoardPanel.add(otherPlayerhandSize);
				
		// pay this card button
		JButton btnPlaythiscard = new JButton("PlayThisCard");
		btnPlaythiscard.setBounds(490, 530, 117, 29);
		GameBoardPanel.add(btnPlaythiscard);
		
		
		
		HelpPanel.setVisible(false);
		
// ACTION LISTENERS ===============================================
		
		
				// drawButton will act everytime a card is drawn.
				drawButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
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
					    
						// this is the code that runs until there is a winner!
						try {
							run();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				
// =============================================================
				
// =============================================================
				JLabel topDiscard = new JLabel("");
				topDiscard.setBounds(123, 249, 159, 245);
				GameBoardPanel.add(topDiscard);
// ============================================================
				
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

					    receiveInitialData(otherPlayerName, otherPlayerhandSize, topDiscard,selectedCardInHand);

					    // find a game button disappears, play button appears -GUI-
						connect.setVisible(false);
						connect.setEnabled(false);
						play.setVisible(true);
						
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
				

// =============================================================
				
				// play this card button
				btnPlaythiscard.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						validatePlay(currentSelectedCard, topDiscardCard);
						if (isValidPlay) {
							// send the index of the choosen card to the server to evaluate which card to play
							sendPlay(slider.getValue() - 1);
						} else {
							//Invalid Card Error dialog
							String [] e1 = topDiscardCard.split(",");
							String eColor = e1[0];
							String eVal = e1[1];
							JOptionPane.showMessageDialog(null,
							    "Please play a card that's either " + eColor +" Or is " + eVal,
							    "Invalid Card",
							    JOptionPane.ERROR_MESSAGE);
						}
						isValidPlay = false;
					}
				});
				
// =============================================================
			
				slider.setMaximum(handSize);
				
				
				
				// ============================================================= GAME BACKGROUND ================================================
				
						JLabel gameBackground = new JLabel(new ImageIcon("/Users/TreyZor/Downloads/gameCards/Background.jpg"));
						gameBackground.setBounds(0, 0, 1166, 596);
						contentPane.add(gameBackground);
				
				// action listener for the slider
				slider.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						
						// should return the card in the hand @ that pos
						String []  cardsInHand = playersHand.split(":");
						currentSelectedCard =  cardsInHand[slider.getValue() - 1];
						// print the current selected card
						System.out.println(currentSelectedCard);
				    	
				    	// =================================== MIDDLE CARD INITIATE ===============================
				    	
				    	BufferedImage cardImage = null;
						try {
							
							cardImage = ImageIO.read(new File("/Users/TreyZor/Downloads/gameCards/"+currentSelectedCard+".jpg"));
							
						} catch (IOException e1) {
							
						}
						Image theResizedCardImage = 
								cardImage .getScaledInstance(selectedCardInHand.getWidth(), selectedCardInHand.getHeight(),Image.SCALE_DEFAULT);
					    
						ImageIcon theCardIcon = new ImageIcon(theResizedCardImage);
						selectedCardInHand.setIcon(theCardIcon);
						
						// ========================================================================================
						
						
					}
				});	
				
			} // end constructor
	
			
// ==========================================================	
	
// ======================== WILDCARD COLOR PICKER FUNCTION ================================
		public String pickColor(){
			
			Object[] possibilities = {"green", "yellow", "red", "blue"};
			String s = (String)JOptionPane.showInputDialog(
			                    null,null,
			                    "Pick Color",
			                    JOptionPane.PLAIN_MESSAGE,
			                    null,
			                    possibilities,
			                    "blue");
			if ((s != null) && (s.length() > 0)) {
			    return s;
			}
			return "none";
		}
			
	// recieve all the initial values from the server
	
			public void receiveInitialData(JLabel pOtherPlayerName, JLabel pOtherPlayerhandSize, JLabel topDiscard, JLabel selectedCardInHand) {
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
					// TODO Auto-generated catch block
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
 			    	
 			    	
 			    	
 			    	// =============================================== SET FIRST TOP DISCARD CARD ======================================
 			    	BufferedImage topDiscardImage = null;
					try {
						
						topDiscardImage = ImageIO.read(new File("/Users/TreyZor/Downloads/gameCards/"+topDiscardCard+".jpg"));
						
					} catch (IOException e1) {
						
					}
					Image theResizeddiscardImage = 
							topDiscardImage.getScaledInstance(topDiscard.getWidth(), topDiscard.getHeight(),Image.SCALE_DEFAULT);
				    
					ImageIcon theCardIcon = new ImageIcon(theResizeddiscardImage);
					topDiscard.setIcon(theCardIcon);
					
					// =================================================================================================================
 			    	
 			    	
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			    
			    // GOOD- read the players hand and puts into string
			    try {
			    	playersHand = fromServer.readUTF();
			    	System.out.println("Player hand:\n" + playersHand);
			    	
			    	String [] cardsInHand = playersHand.split(":");
			    	String theCard = cardsInHand[cardsInHand.length/2];
			    	
			    	// =================================== MIDDLE CARD INITIATE ===============================
			    	
			    	BufferedImage cardImage = null;
					try {
						
						cardImage = ImageIO.read(new File("/Users/TreyZor/Downloads/gameCards/"+theCard+".jpg"));
						
					} catch (IOException e) {
						
					}
					Image theResizedCardImage = 
							cardImage .getScaledInstance(selectedCardInHand.getWidth(), selectedCardInHand.getHeight(),Image.SCALE_DEFAULT);
				    
					ImageIcon theCardIcon = new ImageIcon(theResizedCardImage);
					selectedCardInHand.setIcon(theCardIcon);
					
					// ========================================================================================
			    	
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
	
	private void playOrDraw() {
		// whichever player will be able to play or draw a card
	}
	
	private boolean checkPlay(Unocard playedCard){
		// If card is a wild, matches number, or color, return true
		// If it does not meet above requirements, show error message
		return false;
	}
	
	// sends card information to the Server
	private void sendPlay(int index) {
		// sends the index of the card in the hand to the server
		try {
			System.out.println("index sent: " + index);
			toServer.writeInt(index);
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
	

	
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
	
	
	
}

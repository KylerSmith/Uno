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
import java.io.BufferedReader;

public class UnopanelNewTwo extends JFrame implements UnoConstants {

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
		
		
			JButton connect = new JButton("Find a game");
			connect.setBounds(389, 342, 287, 82);
		
			JButton play = new JButton("Play Game");
			play.setBounds(389, 342, 287, 82);		
			GameMenuPanel.add(connect);		
			
			
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
		
		// other player hand size
		JLabel otherPlayerhandSize = new JLabel("handSize");
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
					    
						// this is the code that runs until there is a winner!
						try {
							// update the players about whats happening
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
							sendPlay(slider.getValue() - 1, slider, playersHand, btnPlaythiscard, drawButton);
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
				
				// action listener for the slider
				slider.addChangeListener(new ChangeListener() {
					@Override
					public void stateChanged(ChangeEvent e) {
						
						// should return the card in the hand @ that pos
						String [] h = playersHand.split(":");
						currentSelectedCard = h[slider.getValue() - 1];
						// print the current selected card
						System.out.println(currentSelectedCard);
					}
				});	
				
				
				

				
				
				
				
			} // end constructor
	
	
	//3245435243857698324873264587632784657893264873426457823465
	
	
	// run function
	public void run() throws IOException, InterruptedException  {
				
		System.out.println("RUN FUNCTION!");
		
		
		
		
		
		

		
		
		
		
		// if you are player1, it should be your turn

		// if you are player2, you should wait for player1's move
		
		
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
	
	
	//2343675834765897362489763298274682791648792364873645345
	
	
	
			
// ==========================================================	
			
	// recieve all the initial values from the server
	
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

	
	
//================================================================================	

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
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		
		
	}
	

	
//================================================================================	

	

	//================================================================================	
	
		/** update the slider, and the hand to have one less card */
		private void updateAfterPlay(JSlider slider, String hand, JButton btnPlay, JButton draw) {
			
			if (myTurn == true) {
				// new hand recieved from the server in the form color,val:color,val: ... etc.
				try {
					hand = fromServer.readUTF();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// recieve newDiscard
				try {
					topDiscardCard = fromServer.readUTF();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			} else if (myTurn == false) {
				try {
					topDiscardCard = fromServer.readUTF();
					System.out.println("Opponent played: ");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// opponentsHandSize
//			try {
//				hand = fromServer.readUTF();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			 // update the slider size
			slider.setMaximum(slider.getMaximum() - 1);
			myTurn = !myTurn;
			// switch the button enable
			btnPlay.setEnabled(myTurn);
			draw.setEnabled(myTurn);
			
		}
	
	
}

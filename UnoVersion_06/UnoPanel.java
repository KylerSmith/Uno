package UnoVersion_06;


// 11/29 -1:05AM

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

public class UnoPanel extends JFrame implements UnoConstants, Runnable {

	// Instantiate game variables
		public int handSize = 5;
		int player;
		
		// while playing variables
		static boolean myTurn = false;
		boolean waiting = true;
		String currentSelectedCard; // should be in the form (color,value)
		boolean continueToPlay = true;
		boolean isValidPlay = false;
		Thread thread;
		
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
		private int status;
		private int checkStatus;
		
		/** had to make all these up here to manipulate in the functions */
		// panels
		JPanel GameBoardPanel;
		JPanel selectedCardColor;
		JPanel panel_3;
		JPanel panel_5;
		JPanel panel_6;
		JPanel panel_7;
		JPanel topDiscardColor;
		JPanel GameMenuPanel;
		JPanel HelpPanel;
		
		// labels
		JLabel selectedCardNumber;
		JLabel otherPlayerName;
		JLabel topDiscardNumber;
		JLabel GameMenuLabel;
		JLabel otherPlayerhandSize;
		JLabel BackgroundLabel;
		
		// buttons
		JButton btnGame;
		JButton btnHelp;
		JButton btnExit;
		JButton drawButton;
		JButton connect;
		JButton play;
		JButton btnPlaythiscard;
		
		// slider
		JSlider slider;
		
		
		// Start of Main ===============================================
		public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
				public void run() { // Start of run ====================
					try {
						UnoPanel frame = new UnoPanel();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} // End of run ========================================
			});
		} // End of main ===============================================
		
		
	public UnoPanel() {
		Init();
	}

	// Create the frame ============================================
	public void Init() {
		
		// Creates main window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1066, 618);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Panels ====================================================
		
		GameBoardPanel = new JPanel();
		GameBoardPanel.setBackground(Color.LIGHT_GRAY);
		GameBoardPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameBoardPanel);
		GameBoardPanel.setLayout(null);
		
		selectedCardColor = new JPanel();
		selectedCardColor.setBackground(Color.GREEN);
		selectedCardColor.setBounds(507, 302, 67, 138);
		GameBoardPanel.add(selectedCardColor);
		selectedCardColor.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBounds(445, 322, 67, 138);
		GameBoardPanel.add(panel_3);
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_5.setBounds(564, 322, 61, 138);
		GameBoardPanel.add(panel_5);
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.DARK_GRAY);
		panel_6.setBounds(387, 343, 67, 146);
		GameBoardPanel.add(panel_6);
		
		panel_7 = new JPanel();
		panel_7.setBackground(UIManager.getColor("Label.disabledShadow"));
		panel_7.setBounds(618, 343, 67, 146);
		GameBoardPanel.add(panel_7);
		
		topDiscardColor = new JPanel();
		topDiscardColor.setBackground(Color.GRAY);
		topDiscardColor.setBounds(103, 249, 159, 245);
		GameBoardPanel.add(topDiscardColor);
		topDiscardColor.setLayout(null);
		
		GameMenuPanel = new JPanel();
		GameMenuPanel.setBackground(new Color(0, 255, 0));
		GameMenuPanel.setForeground(new Color(0, 255, 0));
		GameMenuPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(GameMenuPanel);
		GameMenuPanel.setLayout(null);
		
		HelpPanel = new JPanel();
		HelpPanel.setForeground(Color.GREEN);
		HelpPanel.setBackground(new Color(255, 69, 0));
		HelpPanel.setBounds(0, 0, 1060, 584);
		contentPane.add(HelpPanel);
		
		// Labels ====================================================
		selectedCardNumber = new JLabel("New label");
		selectedCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCardNumber.setBounds(6, 54, 55, 43);
		selectedCardColor.add(selectedCardNumber);
		
		otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		GameBoardPanel.add(otherPlayerName);
		
		topDiscardNumber = new JLabel("No Cards played");
		topDiscardNumber.setBounds(29, 67, 103, 71);
		topDiscardColor.add(topDiscardNumber);
		
		GameMenuLabel = new JLabel("New label");
		GameMenuLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\GameMenu.jpg"));
		GameMenuLabel.setBounds(0, 0, 1060, 590);
		GameMenuPanel.add(GameMenuLabel);
		
		// other player hand size
		otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		GameBoardPanel.add(otherPlayerhandSize);
		
		BackgroundLabel = new JLabel("New label");
		BackgroundLabel.setIcon(new ImageIcon("C:\\Class\\SER215\\Project\\Pics\\UI\\background.jpg"));
		BackgroundLabel.setBounds(0, 0, 1060, 590);
		GameBoardPanel.add(BackgroundLabel);
		
		// Buttons ===================================================
		btnGame = new JButton("Game");
		
		// Main Screen Help
		btnHelp = new JButton("Help");
		btnHelp.setBounds(0, 47, 64, 38);
		contentPane.add(btnHelp);
		
	    // Main Screen Exit	
		btnExit = new JButton("Exit");
		btnExit.setBounds(0, 90, 64, 38);
		contentPane.add(btnExit);
		
		// Game button
		btnGame.setBounds(0, 6, 64, 38);
		contentPane.add(btnGame);
		
		drawButton = new JButton("Draw");
		drawButton.setForeground(UIManager.getColor("Button.light"));
		drawButton.setBackground(UIManager.getColor("Button.light"));
		drawButton.setBounds(799, 249, 159, 245);
		GameBoardPanel.add(drawButton);
		
		connect = new JButton("Find a game");
		connect.setBounds(389, 342, 287, 82);
			
		play = new JButton("Play Game");
		play.setBounds(389, 342, 287, 82);
		
		// play this card button
		btnPlaythiscard = new JButton("PlayThisCard");
		btnPlaythiscard.setBounds(490, 530, 117, 29);
		GameBoardPanel.add(btnPlaythiscard);
		
		GameBoardPanel.setVisible(false);	
		// Add play button to GameMenuPanel
		// GameMenuPanel.add(connect); // may take off
		GameMenuPanel.add(play);	
		GameMenuPanel.setVisible(true);
		HelpPanel.setVisible(false);
		
		// Slider =========================================
		slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		GameBoardPanel.add(slider);		
		
// Action Listeners ============================================================================
		
		
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
		
		
//------------------------------------------------------------------------------------

		
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
		
		
//------------------------------------------------------------------------------------

		
		btnPlaythiscard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// check to make sure its a valid play
				validatePlay(currentSelectedCard, topDiscardCard);
				
				// client wants to play this card and its valid
				if (isValidPlay) {
					status = PLAYCARD;
					myTurn = false;
					waiting = false;
				
				} else { // Invalid Card Error dialog
					
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
		
		
//------------------------------------------------------------------------------------

		
		// drawButton will act everytime a card is drawn.
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status = DRAW;
				myTurn = false;
				waiting = false;
			}
		});
		
//------------------------------------------------------------------------------------

		
		// play goes to the panel with the game
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// brings up the game panel
				gameStarted = true;
				play.setVisible(false);
				GameMenuPanel.setVisible(false);
				HelpPanel.setVisible(false);
				GameBoardPanel.setVisible(true);				
			
				connectToServer();
				
			}
		});	
		
		
//------------------------------------------------------------------------------------
		
		// play goes to the panel with the game -DEPRICATED
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){

			    // find a game button disappears, play button appears -GUI-
				connect.setVisible(false);
				connect.setEnabled(false);
				GameMenuPanel.add(play);
				play.setVisible(true);
								
			}
		});	

		
//------------------------------------------------------------------------------------
		

		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				GameMenuPanel.setVisible(false); // panel is green
				HelpPanel.setVisible(true); //panel_1 is red
				GameBoardPanel.setVisible(false);
				
			}
		});

		
//------------------------------------------------------------------------------------ 
		
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
		
		
	} 
	
// Begin defining functions ==========================================================	
	


	@Override
	public void run() {
				
		receiveInitialData();
		
		try {

			while (continueToPlay) {
				
				System.out.print("\nINSIDE WHILE LOOP\n");
				
				
				if (player == PLAYER1) {
					
					System.out.println("wait for player 1 to make a move");
					// wait for player 1 to make a move
					waitForPlayerAction();
					
					
					
					// Send the move to the server
			        sendMove(); 
			        
			        // recieve update from server of player2's move
					receiveInfoFromServer();

					
				} else if (player == PLAYER2) {
					
					System.out.println("Waiting to recieve to move from server");
					// Receive info from the server
					receiveInfoFromServer();
					
					// Wait for player 2 to move
					waitForPlayerAction();
				
					// Send player 2's move to the server
					sendMove(); 
				}
			}
		} catch (IOException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}	
	
	
//------------------------------------------------------------------------------------

	
	private void sendMove() {
		
		if (status == PLAYCARD) { // Play card
			
			try {
				// Send status to server that client wants to play a card
				toServer.writeInt(PLAYCARD); // UnoServer:176, path:1
				
				// send the index of the card to play to the server
				toServer.writeInt(slider.getValue() - 1); // UnoServer:182
				
				// read the new hand after the play
				playersHand = fromServer.readUTF(); // UnoServer:191
				
				// get the new topDiscard
				topDiscardCard = fromServer.readUTF(); // UnoServer:195
				
				// test *******************
				System.out.println("New top discard after play: " + topDiscardCard);
				
				// decrememnt the hand size
				--handSize;
				slider.setMaximum(handSize - 1);
				
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
			
		} else if (status == DRAW) { // PlayDraw
			
			try {
				//Send to server that client wants to draw a card
				toServer.writeInt(DRAW); // UnoServer:176, path:2
				
				//Send to server that client wants to draw a card
				playersHand = fromServer.readUTF(); // UnoServer:216
				
				// test to see if they hand updated correctly
				System.out.println("Cards read from server after draw: " + playersHand);
				
				// increase the slider to 
				slider.setMaximum(slider.getMaximum() + 1);
				
				// display the updated hand size
				otherPlayerhandSize.setText(Integer.toString(fromServer.readInt())); // UnoServer:221
				
				
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		
		// change the turn to the opponent
		myTurn = !myTurn;
		
	}
	
	
	
//------------------------------------------------------------------------------------

	
	public void receiveInfoFromServer() throws IOException {
		
		// determine game status from server
		checkStatus = fromServer.readInt();
		
		
		
		
		
	}
	
	
	
	
	
	
//------------------------------------------------------------------------------------

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
//		try {
//			hand = fromServer.readUTF();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	 // update the slider size
	slider.setMaximum(slider.getMaximum() - 1);
	// switch the button enable
	btnPlay.setEnabled(myTurn);
	draw.setEnabled(myTurn);
	
}
	
//------------------------------------------------------------------------------------

		private void waitForPlayerAction() throws InterruptedException {
		    while (waiting) {
		    	Thread.sleep(100);
		    }
		    	waiting = true;
		}

		
		
//------------------------------------------------------------------------------------
		
		
		private void connectToServer() {
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
		    
		    // Control the game on a separate thread
		    Thread thread = new Thread(this);
		    thread.start();
		    
		    // -----------------------------------------------------------------

		}

//------------------------------------------------------------------------------------

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
		
		

	
//------------------------------------------------------------------------------------

	
	public void receiveInitialData() {
		
		// set the player to the player number they are
	    try {
			player = fromServer.readInt(); // UnoServer:52 | UnoServer:64
		    // set the opponents label and turn
		    if (player == PLAYER1) {
		    	otherPlayerName.setText("Player 2");
		    	myTurn = true; // set player1's turn to true

		    } else {
		    	otherPlayerName.setText("Player 1");
		    	btnPlaythiscard.setEnabled(myTurn);
				drawButton.setEnabled(myTurn);
		    }

		    // recieve card amount of opponent from server
	    	opponentCardCount = fromServer.readInt(); // UnoServer:277
	    	otherPlayerhandSize.setText(Integer.toString(opponentCardCount));

	    	// recieves the correct card that was initially discarded
	    	topDiscardCard = fromServer.readUTF(); // UnoServer:280
		    System.out.println("card to play:\n" + topDiscardCard);

		    // recieves the players delt hand
	    	playersHand = fromServer.readUTF(); // UnoServer:283
	    	System.out.println("Player hand:\n" + playersHand);
	    	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

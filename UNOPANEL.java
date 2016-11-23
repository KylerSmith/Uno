package UnoVersion_04;

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

public class UNOPANEL extends JFrame {

	private JPanel contentPane;
	static boolean gameStarted = false;
	String selectedCardNumber;
	Color theSelectedCardColor = Color.GRAY; 
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	String forHand = "";
	public String [] theCardsInHand = new String[20];
	public String [] selectedCard = new String[2];
	public int handSize = 5;
	String inputFromServer = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UNOPANEL frame = new UNOPANEL();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public UNOPANEL() {
		
		
		
		// -------------------------- CLIENT SETUP ------------------------------------------
		
				// KAS 11/11
			    try {

			        // Create a socket to connect to the server
			        Socket socket = new Socket("localhost", 8000); // localhost

			        // Socket socket = new Socket("98.167.208.37", 8000); // raspberrpi

			        // Create an input stream to receive data from the server
			        fromServer = new DataInputStream( socket.getInputStream() );

			        // Create an output stream to send data to the server
			        toServer =  new DataOutputStream( socket.getOutputStream() );
			      }
			      catch (IOException ex) {
			        System.out.println(ex.toString());
			      }	
				
		// ------------------------------------- END CLIENT SETUP ----------------------------------		
		
		
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
			JButton btnHelp = new JButton("Help");
			
			btnHelp.setBounds(0, 47, 64, 38);
			contentPane.add(btnHelp);
			
			
		
			JButton btnExit = new JButton("Exit");
			
			
			btnExit.setBounds(0, 90, 64, 38);
			contentPane.add(btnExit);
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
		btnGame.setBounds(0, 6, 64, 38);
		contentPane.add(btnGame);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 6, 1060, 584);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel selectedCardColor = new JPanel();
		selectedCardColor.setBackground(Color.GREEN);
		selectedCardColor.setBounds(507, 302, 67, 138);
		panel_2.add(selectedCardColor);
		selectedCardColor.setLayout(null);
		
		JLabel selectedCardNumber = new JLabel("New label");
		selectedCardNumber.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCardNumber.setBounds(6, 54, 55, 43);
		selectedCardColor.add(selectedCardNumber);
		
		JButton drawButton = new JButton("Draw");
		drawButton.setForeground(UIManager.getColor("Button.light"));
		drawButton.setBackground(UIManager.getColor("Button.light"));
		drawButton.setBounds(799, 249, 159, 245);
		panel_2.add(drawButton);
		
		JSlider slider = new JSlider();
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(3);
		slider.setBounds(449, 489, 190, 29);
		panel_2.add(slider);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.setBounds(445, 322, 67, 138);
		panel_2.add(panel_3);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_5.setBounds(564, 322, 61, 138);
		panel_2.add(panel_5);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.DARK_GRAY);
		panel_6.setBounds(387, 343, 67, 146);
		panel_2.add(panel_6);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(UIManager.getColor("Label.disabledShadow"));
		panel_7.setBounds(618, 343, 67, 146);
		panel_2.add(panel_7);
		
		JLabel otherPlayerName = new JLabel("No player has joined...");
		otherPlayerName.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		otherPlayerName.setBounds(419, 6, 201, 131);
		panel_2.add(otherPlayerName);
		
		JPanel topDiscardColor = new JPanel();
		topDiscardColor.setBackground(Color.GRAY);
		topDiscardColor.setBounds(103, 249, 159, 245);
		panel_2.add(topDiscardColor);
		topDiscardColor.setLayout(null);
		
		JLabel topDiscardNumber = new JLabel("No Cards played");
		topDiscardNumber.setBounds(29, 67, 103, 71);
		topDiscardColor.add(topDiscardNumber);
		
		panel_2.setVisible(false);
	
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setForeground(new Color(0, 255, 0));
		panel.setBounds(0, 6, 1060, 584);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
			JButton play = new JButton("Play Game");
			play.setBounds(389, 342, 287, 82);
			
			panel.add(play);
			
			panel.setVisible(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.GREEN);
		panel_1.setBackground(new Color(255, 69, 0));
		panel_1.setBounds(0, 6, 1060, 584);
		contentPane.add(panel_1);
		
		panel_1.setVisible(false);
		
// ACTION LISTENERS ===============================================
		
		
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

// =============================================================
		
		// play goes to the panel with the game
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
			
				gameStarted = true;
				play.setVisible(false);
				panel.setVisible(false);
				panel_1.setVisible(false);
				panel_2.setVisible(true);
				
				try {
					forHand = fromServer.readUTF();
					System.out.println(forHand); // prints to the console the hand
					theCardsInHand = initiatehand(forHand);
					
					// display cards to console
//					for(int i = 0; i < handSize; i++){
//						System.out.println(theCardsInHand[i]);
//					}
					
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
		
		
// =============================================================

		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				panel.setVisible(false); // panel is green
				panel_1.setVisible(true); //panel_1 is red
				panel_2.setVisible(false);
				
			}
		});
		
// =============================================================

		
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				panel_1.setVisible(false); //panel_1 is red
				
				if(gameStarted){
					panel.setVisible(false);
					panel_2.setVisible(true); // panel_2 is blue
				}else{
					panel.setVisible(true); // panel is green
					panel_2.setVisible(false);
				}
			}
		});
		
		JLabel otherPlayerhandSize = new JLabel("handSize");
		otherPlayerhandSize.setBounds(451, 102, 61, 16);
		panel_2.add(otherPlayerhandSize);
						
		JButton btnPlaythiscard = new JButton("PlayThisCard");
		btnPlaythiscard.setBounds(490, 530, 117, 29);
		panel_2.add(btnPlaythiscard);
				
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
							    "Please play a card that's either "+topDiscardNumber.getText()+" Or is "+getColorName(topDiscardColor.getBackground()),
							    "Invalid Card",
							    JOptionPane.ERROR_MESSAGE);
							
						// If server response says valid 
						}else if(inputFromServer.equals("validMove")){
							
							try {
								// Get the new Discard and show it on gui
								String [] newDiscardTop = fromServer.readUTF().split(",");
								
								topDiscardColor.setBackground(getColor(newDiscardTop[0]));
								topDiscardNumber.setText(newDiscardTop[1]);
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						}
				
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		slider.setMaximum(handSize);
		
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				
//				String currentCardColor, currentCardNumber;
//				String [] colors = new String[handSize];
//				String [] numbers = new String[handSize];
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
	
}

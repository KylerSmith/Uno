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

public class UNOPANEL extends JFrame {

	private JPanel contentPane;
	static boolean gameStarted = false;
	Color theSelectedCardColor = Color.GRAY; 
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	String selectedCardNumber;
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
		
		JButton button = new JButton("Draw");
		button.setForeground(UIManager.getColor("Button.light"));
		button.setBackground(UIManager.getColor("Button.light"));
		button.setBounds(799, 249, 159, 245);
		panel_2.add(button);
		
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
		
		JLabel otherPlayerName = new JLabel("Player 2");
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
		
			JButton play = new JButton("Play");
			play.setBounds(389, 342, 287, 82);
			
			panel.add(play);
			
			panel.setVisible(true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.GREEN);
		panel_1.setBackground(new Color(255, 69, 0));
		panel_1.setBounds(0, 6, 1060, 584);
		contentPane.add(panel_1);
		
		panel_1.setVisible(false);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				
				
				
				
				gameStarted = true;
				play.setVisible(false);
				panel.setVisible(false);
				panel_1.setVisible(false);
				panel_2.setVisible(true);
			}
		});
		
		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				panel.setVisible(false); // panel is green
				panel_1.setVisible(true); //panel_1 is red
				panel_2.setVisible(false);
				
			}
		});
		
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
				
// logic ===========================================================		
		
		String firstStr = "";
		String [] receivedCard = new String[2];
		
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
				

				topDiscardColor.setBackground(theSelectedCardColor);
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
}

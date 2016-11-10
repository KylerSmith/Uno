package uno;

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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class UNOPANEL extends JFrame {

	private JPanel contentPane;
	static boolean gameStarted = false;

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
		btnGame.setBounds(0, 6, 64, 38);
		contentPane.add(btnGame);
		
		JButton btnHelp = new JButton("Help");
		
		btnHelp.setBounds(0, 47, 64, 38);
		contentPane.add(btnHelp);
		JButton btnExit = new JButton("Exit");
		
		
		btnExit.setBounds(0, 90, 64, 38);
		contentPane.add(btnExit);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 255, 0));
		panel.setForeground(new Color(0, 255, 0));
		panel.setBounds(0, 6, 1060, 584);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(Color.GREEN);
		panel_1.setBackground(new Color(255, 69, 0));
		panel_1.setBounds(0, 6, 1060, 584);
		contentPane.add(panel_1);
		panel.setLayout(null);
	
		JButton play = new JButton("Play");
		play.setBounds(389, 342, 287, 82);
		
		panel.add(play);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(30, 144, 255));
		panel_2.setBounds(0, 0, 1060, 584);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		panel.setVisible(true);
		panel_1.setVisible(false);
		panel_2.setVisible(false);
		
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.setBounds(82, 201, 124, 214);
		panel_2.add(btnDiscard);
		
		JButton btnDraw = new JButton("Draw");
		btnDraw.setBounds(659, 201, 124, 214);
		panel_2.add(btnDraw);
		
		JSlider slider = new JSlider();
		slider.setValue(5);
		slider.setBounds(352, 524, 190, 29);
		panel_2.add(slider);
		
		JLabel lblPlayerCards = new JLabel("Player Cards");
		lblPlayerCards.setBackground(new Color(175, 238, 238));
		lblPlayerCards.setBounds(387, 320, 134, 206);
		panel_2.add(lblPlayerCards);
		
		JLabel lblNewLabel = new JLabel("Player Cards1");
		lblNewLabel.setForeground(new Color(0, 255, 0));
		lblNewLabel.setBackground(new Color(127, 255, 0));
		lblNewLabel.setBounds(414, 299, 145, 214);
		panel_2.add(lblNewLabel);
		
		btnHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				panel.setVisible(false); // panel is green
				panel_1.setVisible(true); //panel_1 is red
				panel_2.setVisible(false); // panel
				
			}
		});
		
		btnGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				panel_1.setVisible(false); //panel_1 is red
				
				if(gameStarted){
					panel.setVisible(false);
					panel_2.setVisible(true); // panel
				}else{
					panel.setVisible(true); // panel is green
					panel_2.setVisible(false); // panel
				}
			}
		});
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog.setDefaultLookAndFeelDecorated(true);
			    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit game",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.NO_OPTION) {
			      System.out.println("No button clicked");
			    } else if (response == JOptionPane.YES_OPTION) {
			      System.out.println("Yes button clicked");
			    } else if (response == JOptionPane.CLOSED_OPTION) {
			      System.out.println("JOptionPane closed");
			    }
				
			}
		});
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				gameStarted = true;
				play.setVisible(false);
				panel_2.setVisible(true);
			}
		});
		
		//  ******************************** GAME SCREEN COMPONENTS *************************************
		
		
		
	}
}

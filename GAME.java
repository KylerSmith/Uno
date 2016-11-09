package uno;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class GAME extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GAME frame = new GAME();
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
	public GAME() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.setBounds(213, 143, 144, 221);
		contentPane.add(btnDiscard);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(6, 486, 117, 36);
		contentPane.add(btnHelp);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(977, 486, 117, 36);
		contentPane.add(btnExit);
		
		JSlider slider = new JSlider();
		slider.setBounds(417, 486, 310, 29);
		contentPane.add(slider);
		
		JLabel lblNewLabel = new JLabel("playerCards");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setBounds(510, 258, 133, 216);
		contentPane.add(lblNewLabel);
		
		JLabel lblPlayer_1 = new JLabel("3");
		lblPlayer_1.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_1.setBounds(6, 96, 70, 120);
		contentPane.add(lblPlayer_1);
		
		JLabel lblPlayer_2 = new JLabel("");
		lblPlayer_2.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_2.setBounds(147, 6, 133, 71);
		contentPane.add(lblPlayer_2);
		
		JLabel lblPlayer_3 = new JLabel("");
		lblPlayer_3.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_3.setBounds(417, 6, 133, 71);
		contentPane.add(lblPlayer_3);
		
		JLabel lblPlayer_4 = new JLabel("");
		lblPlayer_4.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_4.setBounds(722, 6, 133, 71);
		contentPane.add(lblPlayer_4);
		
		JLabel lblPlayer_5 = new JLabel("");
		lblPlayer_5.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_5.setBounds(1024, 96, 70, 120);
		contentPane.add(lblPlayer_5);
		
		JLabel lblPlayer_6 = new JLabel("");
		lblPlayer_6.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer_6.setBounds(1024, 258, 70, 120);
		contentPane.add(lblPlayer_6);
		
		JButton btnDraw = new JButton("Draw");
		btnDraw.setBounds(763, 143, 144, 221);
		contentPane.add(btnDraw);
		
		JLabel lblPlayernum = new JLabel("#");
		lblPlayernum.setBounds(394, 458, 61, 16);
		contentPane.add(lblPlayernum);
		
		JLabel label_1 = new JLabel("#");
		label_1.setBounds(105, 148, 61, 16);
		contentPane.add(label_1);
		
		JLabel lblPlayernum_1 = new JLabel("#");
		lblPlayernum_1.setBounds(105, 310, 61, 16);
		contentPane.add(lblPlayernum_1);
		
		JLabel label_3 = new JLabel("#");
		label_3.setBounds(240, 33, 61, 16);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("#");
		label_4.setBounds(524, 33, 61, 16);
		contentPane.add(label_4);
		
		JLabel lblNewLabel_1 = new JLabel("#");
		lblNewLabel_1.setBounds(836, 33, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblPlayer = new JLabel("player 2");
		lblPlayer.setIcon(new ImageIcon("/Users/TreyZor/Downloads/rsz_user.png"));
		lblPlayer.setBounds(6, 258, 70, 120);
		contentPane.add(lblPlayer);
		
		JLabel label = new JLabel("#");
		label.setBounds(979, 162, 45, 16);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("#");
		label_2.setBounds(978, 310, 39, 16);
		contentPane.add(label_2);
	}
}

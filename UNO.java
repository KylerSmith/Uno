package uno;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;

public class UNO extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UNO frame = new UNO();
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
	public UNO() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1061, 544);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 139, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("/Users/TreyZor/Documents/workspace/UNO/src/uno/uno.gif"));
		lblNewLabel.setBounds(470, 6, 162, 220);
		contentPane.add(lblNewLabel);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPlay.setBounds(108, 305, 200, 71);
		btnPlay.setBackground(new Color(123,234,145));
		btnPlay.setOpaque(true);
		btnPlay.setBorderPainted(false);
		contentPane.add(btnPlay);
		btnPlay.addActionListener((ActionListener) this);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setBounds(442, 305, 200, 71);
		contentPane.add(btnInstructions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(754, 305, 200, 71);
		contentPane.add(btnExit);
	}
}

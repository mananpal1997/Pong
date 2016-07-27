package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongPlay extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel jContentPane = new JPanel();
	PongBoard panel = null;

	public PongBoard getPanel() {
		if (panel == null)
			panel = new PongBoard();
		return panel;
	}

	public PongPlay(){
		super();

		JOptionPane.showMessageDialog(this, "Intructions :\n"
				+ "1. Player 1 Controls : W(up) & S(down)\n"
				+ "2. Player 2 Controls : ↑(up) & ↓(down)\n\n"
				+ "First to score 10 points WINS !");

		setResizable(false);
		setBounds(new Rectangle(312, 184, 250, 250));
		setSize(new Dimension(250, 250));

		jContentPane.setLayout(new BorderLayout());
		jContentPane.add(getPanel(), BorderLayout.CENTER);
		setContentPane(jContentPane);
		setTitle("Pong");

		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				panel.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e){
				panel.keyReleased(e);
			}
		});
	}

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				PongPlay game_play = new PongPlay();
				game_play.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				game_play.setVisible(true);
			}
		});
	}
}
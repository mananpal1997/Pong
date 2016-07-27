package game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PongBoard extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int pong_x=10, pong_y=100;
	int  p1_x=10, p1_y=100;
	int p2_x=230, p2_y=100;
	int up=5, left=-5, down=-5, right=5;
	int width, height;
	boolean in_game, game_over;
	boolean p1_up, p1_down, p2_up, p2_down;
	Thread thread;
	String p1,p2;
	int score1=-1, score2=-1;

	public PongBoard(){
		p1=JOptionPane.showInputDialog(null, "Enter name of Player 1 :" , "INPUT", -1);
		while(p1.length()==0){
			JOptionPane.showMessageDialog(null, "NO NAME ENTERED !", "ERROR", 2);
			p1=JOptionPane.showInputDialog(null, "Enter name of Player 1 :" , "INPUT", -1);
		}

		p2=JOptionPane.showInputDialog(null, "Enter name of Player 2 :" , "INPUT", -1);
		while(p2.length()==0 || p2.equals(p1)){
			if(p2.length()==0)
				JOptionPane.showMessageDialog(null, "NO NAME ENTERED !", "ERROR", 2);
			else
				JOptionPane.showMessageDialog(null, "NAME CAN'T BE SAME TO THAT OF PLAYER 1 !", "ERROR", 2);
			p2=JOptionPane.showInputDialog(null, "Enter name of Player 2 :" , "INPUT", -1);
		}

		in_game=true;
		thread=new Thread(this);
		thread.start();
	}

	@Override
	public void paintComponent(Graphics g){
		setOpaque(false);
		super.paintComponent(g);

		// draw ball
		g.setColor(Color.BLACK);
		g.fillOval(pong_x, pong_y, 10,10);

		// draw bars
		g.fillRect(p1_x, p1_y, 10, 25);
		g.fillRect(p2_x, p2_y, 10, 25);

		// score-board
		g.drawString(p1+" : "+score1, 25, 10);
		g.drawString(p2+" : "+score2, 150, 10);

		if(game_over){
			g.drawString("Game Over!", 100, 125);

			if(score1>score2)
				g.drawString(p1+" wins!", 100, 100);
			else
				g.drawString(p2+" wins!", 100, 100);
		}
	}

	public void draw_pong(int x, int y){
		pong_x=x;
		pong_y=y;
		this.width=this.getWidth();
		this.height=this.getHeight();
		repaint();
	}

	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			p1_up=true;
			break;
		case KeyEvent.VK_S:
			p1_down=true;
			break;
		case KeyEvent.VK_UP:
			p2_up=true;
			break;
		case KeyEvent.VK_DOWN:
			p2_down=true;
			break;
		}
	}

	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_W:
			p1_up=false;
			break;
		case KeyEvent.VK_S:
			p1_down=false;
			break;
		case KeyEvent.VK_UP:
			p2_up=false;
			break;
		case KeyEvent.VK_DOWN:
			p2_down=false;
			break;
		case KeyEvent.VK_P:
			thread.suspend();
			break;
		case KeyEvent.VK_R:
			thread.resume();
			break;
		}
	}

	public void move_p1(){
		if(p1_up && p1_y>=0)
			p1_y+=down;
		if(p1_down && p1_y<=(this.getHeight()-25))
			p1_y+=up;
		draw_p1(p1_y);
	}

	public void draw_p1(int y){
		this.p1_y=y;
		repaint();
	}

	public void move_p2(){
		if(p2_up && p2_y>=0)
			p2_y+=down;
		if(p2_down && p2_y<=(this.getHeight()-25))
			p2_y+=up;
		draw_p2(p2_y);
	}

	public void draw_p2(int y){
		this.p2_y=y;
		repaint();
	}

	public void run(){
		boolean pong_r=false, pong_d=false;

		while(true){
			if(in_game){
				if(pong_r){
					pong_x+=right;
					if(pong_x>=(width-10))
						pong_r=false;
				}
				else{
					pong_x+=left;
					if(pong_x<=0)
						pong_r=true;
				}

				if(pong_d){
					pong_y+=up;
					if(pong_y>=(height-10))
						pong_d=false;
				}
				else{
					pong_y+=down;
					if(pong_y<=0)
						pong_d=true;
				}

				draw_pong(pong_x, pong_y);

				try{
					Thread.sleep(35);
				}
				catch(Exception e) {}

				move_p1();
				move_p2();

				if(pong_x>=(width-10))
					score1++;
				else if(pong_x==0)
					score2++;

				if(score1==10 || score2==10){
					in_game=false;
					game_over=true;
				}

				// collision
				if(pong_x==10 && pong_y>=p1_y && pong_y<=(p1_y+25))
					pong_r=true;
				if(pong_x==p2_x-5 && pong_y>=p2_y && pong_y<=(p2_y+25))
					pong_r=false;
			}
		}
	}
}
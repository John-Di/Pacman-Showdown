import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener
{
	final private Random rand = new Random();
	final private Color ROYAL_BLUE = new Color(61,145,237);

	boolean aiMode = false, chompa = true;

	private Pacman[] players = {new Pacman(), new Pacman(323, 15, ROYAL_BLUE, 5)};
	private Pill pill = new Pill();

	final
	private Timer repainter = new Timer(16, new Repainter()),
			ai = new Timer(1500, new AutoAImode()),
			chomping = new Timer(75, new Chomper());
	
	final private JButton playAgain = new JButton("Play Again?");

	public GamePanel()
	{
		setVisible(true);
		setFocusable(true);
		setSize(500,500);
		repainter.start();
		ai.start();
		chomping.start();

		addKeyListener(this);
	}

	private void loopAround(Pacman p)
	{
		if(p.getX() > getWidth())
		{
			p.setX(-p.getDiameter());
		}
		else if(p.getY() > getHeight())
		{
			p.setY(-p.getDiameter());
		}
		if((p.getX() + p.getDiameter()) < 0)
		{
			p.setX(getWidth() - 1);
		}
		else if((p.getY() + p.getDiameter()) < 0)
		{
			p.setY(getHeight() - 1);
		}        	

	}

	private void walkaWalka(Pacman p)
	{
		//Walka Walka
		if(p.isMoving())
		{
			if(p.getColor() != Color.RED)
			{
				if(p.getDirection() == 3)
				{
					p.setX(p.getX() + p.getVelocity());
				}
				else if(p.getDirection() == 2)
				{
					p.setX(p.getX() - p.getVelocity());
				}
				else if(p.getDirection() == 1)
				{
					p.setY(p.getY() + p.getVelocity());
				}
				else if(p.getDirection() == 0)
				{
					p.setY(p.getY() - p.getVelocity());
				}
			}
			else //AI movement
			{

				if(!(((p.getX() + p.getRadius()) > pill.getCenterX()) &&  (getX() < p.getCenterX())))
				{
					p.facingRight();
					p.setX(p.getX() + p.getVelocity());
				}
				else if(!(((p.getX() + p.getRadius()) > pill.getCenterX()) && (p.getX() + p.getRadius() / 3 * 2 < pill.getCenterX())))
				{
					p.facingLeft();
					p.setX(p.getX() - p.getVelocity());
				}
				else if(!(((p.getY() + p.getRadius()) > pill.getCenterY()) &&  (p.getY() < pill.getCenterY())))
				{
					p.facingDown();
					p.setY(p.getY() + p.getVelocity());
				}
				else if(!(((p.getY() + p.getRadius()) < pill.getCenterY()) && ((p.getY() + p.getDiameter()) > pill.getCenterY())))
				{
					p.facingUp();
					p.setY(p.getY() - p.getVelocity());
				}

			}              

		}
		else
		{
			p.openMouth();
		}

	}
	private void chompaChompa(Pacman p)
	{
		//Chompa Chompa
		if(chompa)
		{
			p.openMouth();
		}
		else
		{
			p.closeMouth();
		}         	

	}

	private void plotNewCoordinates(Pacman p)
	{
		loopAround(p);

		walkaWalka(p);

		if(p.isMoving())
		{
			chompaChompa(p);    
		}
	}

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		remove(playAgain);
		repainter.start();

		addKeyListener(this);

		setBackground(Color.BLACK);

		//IF PLAYER ATE A BALL

		for(int i = 0; i < players.length; i++)
		{
			if(players[i].atePill(pill))   
			{
				players[i].updatePoints();
				pill = new Pill(
						rand.nextInt((getWidth() - pill.getDiameter()) - 0 + 1) + 0,
						rand.nextInt((getHeight() - pill.getDiameter()) - 0 + 1) + 0);
			}
			else
			{
				g.setColor(Color.white);
				g.fillArc(pill.getX(),
						pill.getY(),
						pill.getDiameter(),
						pill.getDiameter(),
						0, 360);
			}
		}

		//Draw Pacmen

		for(Pacman p: players)
		{        	  
			plotNewCoordinates(p); 

			g.setColor(p.getColor());
			g.fillArc(p.getX(),
					p.getY(),
					p.getDiameter(),
					p.getDiameter(),
					p.getUpperLip(),
					p.getBodyArc());        	  
		}



		int xString = (5);
		int yStringYellow = (getHeight() - 25);
		int yStringBlue = (getHeight() - 10);

		//Print Score

		if(players[0].getColor() != Color.RED)
		{
			g.setColor(Color.YELLOW);
			g.setFont(new Font("SansSerif", Font.BOLD, 13));
			g.drawString(players[0].toString(), xString, yStringYellow);

			g.setColor(ROYAL_BLUE);
			g.setFont(new Font("SansSerif", Font.BOLD, 13));
			g.drawString(players[1].toString(), xString, yStringBlue);
		}
		else
		{
			g.setColor(Color.YELLOW);
			g.setFont(new Font("SansSerif", Font.BOLD, 13));
			g.drawString(players[1].toString(), xString, yStringYellow);

			g.setColor(players[0].getColor());
			g.setFont(new Font("SansSerif", Font.BOLD, 13));
			g.drawString(players[0].toString(), xString, yStringBlue);
		}

		//End Game conclusion



		if(players[0].getScore() == 1500 || players[1].getScore() == 1500)
		{
			repainter.stop();
			ai.stop();
			chomping.stop();
			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());

			String winner = "";
			String color = "";

			repainter.stop();
			g.setFont(new Font("SansSerif", Font.BOLD, 60));
			winner = " WINS!";

			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());

			if(players[0].getScore() == 1500 && players[1].getScore() == 1500)
			{
				g.setColor(Color.ORANGE);
				winner = "DRAW!";
				g.drawString(winner,getWidth()/4,getHeight()/2);
			}
			else if(players[1].getScore() == 1500)
			{
				g.setColor(players[1].getColor());

				if(aiMode == true)
				{
					color = "Yellow";
					g.drawString(color,getWidth()/4,getHeight()/3 + 20);
				}
				else
				{
					color = "Blue";
					g.drawString(color,getWidth()/4 + 35,getHeight()/3 + 20);
				}


				g.setColor(Color.WHITE);
				g.drawString(winner,getWidth()/4,getHeight()/2 + 40);
			}
			else if(players[0].getScore() == 1500)
			{
				g.setColor(players[0].getColor());

				if(aiMode)
				{
					color = "Red";
					g.drawString(color,getWidth()/4 + 35,getHeight()/3 + 20);
				}
				else
				{
					color = "Yellow";
					g.drawString(color,getWidth()/4,getHeight()/3 + 20);
				}


				g.setColor(Color.WHITE);
				g.drawString(winner,getWidth()/4,getHeight()/2 + 40);
			}

			playAgain.addActionListener(new PlayAgainAction());
			playAgain.setBounds(150, 200, 100, 30);
			playAgain.requestFocusInWindow();
			this.add(playAgain);

		}
	}

	//KeyListener

	public void keyPressed(KeyEvent e)
	{        	         	 
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			players[1].facingRight();
			players[1].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			players[1].facingLeft();
			players[1].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			players[1].facingUp();
			players[1].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			players[1].facingDown();
			players[1].setMotion(true);
		}


		if((e.getKeyCode() == KeyEvent.VK_UP && 
				e.getKeyCode() == KeyEvent.VK_DOWN) ||
				(e.getKeyCode() == KeyEvent.VK_RIGHT && 
				e.getKeyCode() == KeyEvent.VK_LEFT))
		{
			players[1].setMotion(false);
		}

		if(e.getKeyCode() == KeyEvent.VK_D)
		{
			players[0].facingRight();
			players[0].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_A)
		{
			players[0].facingLeft();
			players[0].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_W)
		{
			players[0].facingUp();
			players[0].setMotion(true);
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			players[0].facingDown();
			players[0].setMotion(true);
		}

		if(e.getKeyCode() == KeyEvent.VK_ENTER && !players[0].isMoving() && !players[1].isMoving())
		{
			players[0] = new Pacman(323, 15, Color.RED, 4);
			players[1] = new Pacman();

			players[0].setMotion(true);
			aiMode = true;

		}

		if((e.getKeyCode() == KeyEvent.VK_W && 
				e.getKeyCode() == KeyEvent.VK_S) ||
				(e.getKeyCode() == KeyEvent.VK_D && 
				e.getKeyCode() == KeyEvent.VK_A))
		{
			players[0].setMotion(false);
		}


	}
	public void keyReleased(KeyEvent e)
	{

		if(e.getKeyCode() == KeyEvent.VK_UP && 
				e.getKeyCode() == KeyEvent.VK_DOWN &&
				e.getKeyCode() == KeyEvent.VK_RIGHT && 
				e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			players[1].setMotion(false);
		}

		if(e.getKeyCode() == KeyEvent.VK_W && 
				e.getKeyCode() == KeyEvent.VK_S &&
				e.getKeyCode() == KeyEvent.VK_D && 
				e.getKeyCode() == KeyEvent.VK_A)
		{
			players[0].setMotion(false);
		}
	}
	public void keyTyped(KeyEvent e)
	{


	}

	//Action listeners
	class Repainter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			repaint();
		}
	}

	class Chomper implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			chompa = !chompa;
			repaint();
		}
	}

	class AutoAImode implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(!players[0].isMoving() && !players[1].isMoving())
			{
				players[0] = new Pacman(323, 15, Color.RED, 4);
				players[1] = new Pacman();

				players[0].setMotion(true);
				aiMode = true;

			}
		}
	}


	class PlayAgainAction implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			players[0] = new Pacman();
			players[1] = new Pacman(323, 15, ROYAL_BLUE, 5);
			pill = new Pill();

			aiMode = false;
			repainter.start();
			ai.start();
			chomping.start();
		}
	}
}

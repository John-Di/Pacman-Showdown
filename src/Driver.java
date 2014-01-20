
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.util.Random;

public class Driver extends JFrame
{

	public Driver()
	{
		add(new GamePanel());
	}

	public static void main(String[] args)
	{
		Driver frame = new Driver();
		frame.setTitle("PacMan Showdown");
		frame.setResizable(false);
		frame.setSize(400,300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


}

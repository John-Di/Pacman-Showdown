import java.awt.Color;


public abstract class Drawable
{

	protected int xPos,
				  yPos,
				  radius;
	
	private Color color;
	
	public Drawable(int x, int y, int rad, Color color)
	{
		xPos = x;
        yPos = y;
        radius = rad;		
        this.color = color;
	}
	
	public int getX()
	{
		return xPos;
	}

	public void setX(int x)
	{
		this.xPos = x;
	}

	public int getY()
	{
		return yPos;
	}

	public void setY(int y)
	{
		this.yPos = y;
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}
	
	public int getDiameter()
	{
		return this.radius * 2;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public int getCenterX()
	{
		return xPos + radius;
	}
	
	public int getCenterY()
	{
		return yPos + radius;
	}
}

import java.awt.Color;


public class Pill extends Drawable
{    
    public Pill()
    {
    	this(Color.WHITE);   	
    }
	
	public Pill(Color color)
	{
		this(187, 100, color);
	}
	
	public Pill(int x, int y)
	{
		this(x, y, Color.WHITE);	
	}
	
	public Pill(int x, int y, Color color)
	{
		super(x, y, 10, color);	
	}
}

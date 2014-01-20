import java.awt.Color;


public class Pacman extends Drawable
{
	
	private int upperLip,
				bodyArc,
				points,
				velocity;
	
	private enum Direction {UP, DOWN, LEFT, RIGHT};
	private Direction direction;

	private boolean isMoving = true;
	
	public Pacman()
	{
		this(Color.YELLOW);
	}
	
	public Pacman(Color color)
	{
		this(0, 15, color, 5);
        facingRight();
        openMouth();
		
	}
	
	public Pacman(int x, int y, Color color, int velocity)
	{
		super(x, y, 35, color);
		facingLeft(); //This is bad for more than 2 players, too lazy for work around
		openMouth();
		this.velocity = velocity;
        isMoving = false;
		points = 0;
		
	}
	
	public void facingLeft()
	{
		this.upperLip = 225;
		this.bodyArc = 275;
		
		direction = Direction.LEFT;
	}
	
	public void facingRight()
	{
		this.upperLip = 45;
		this.bodyArc = 275;
		
		direction = Direction.RIGHT;
	}
	
	public void facingUp()
	{
		this.upperLip = 135;
		this.bodyArc = 275;
		
		direction = Direction.UP;
	}	
	
	public void facingDown()
	{
		this.upperLip = 315;
		this.bodyArc = 275;
		
		direction = Direction.DOWN;
	}	
	
	public void openMouth()
	{
		switch(direction)
		{
			case LEFT: 	facingLeft(); 	break;
			case RIGHT: facingRight(); 	break;
			case UP: 	facingUp(); 	break;
			case DOWN: 	facingDown(); 	break;
		}
	}
	
	public void closeMouth()
	{
		this.upperLip = 0;
		this.bodyArc = 360;
	}
	

	public int getUpperLip()
	{
		return upperLip;
	}

	public void setUpperLip(int upperLip)
	{
		this.upperLip = upperLip;
	}

	public int getBodyArc()
	{
		return bodyArc;
	}

	public void setBodyArc(int bodyArc)
	{
		this.bodyArc = bodyArc;
	}

	public int getScore()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}
	
	public void updatePoints()
	{
		this.points += 100;
	}

	public int getDirection()
	{
		return direction.ordinal();		
	}
	
	public void setDirection(int ordinal)
	{
		this.direction = Direction.values()[ordinal];
	
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
	public void setMotion(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
	
	public int getVelocity()
	{
		return this.velocity;
	}
	
	public boolean eatGoingDown(Pill p)
	{
		return 	   (getY() + getRadius()) > p.getCenterY() 
				&&  getY() < p.getCenterY() 
				&& (getX() + getDiameter()) > (p.getCenterX()) 
				&&  getX() < p.getCenterX();
	}
	
	public boolean eatGoingUp(Pill p)
	{
		return 	   (getY() + getRadius()) < p.getCenterY() 
			    && (getY() + getDiameter()) > p.getCenterY() 
			    && (getX() + getDiameter()) > (p.getCenterX()) 
			    &&  getX() < p.getCenterX();
	}
	
	public boolean eatGoingLeft(Pill p)
	{
		return     (getY() + getDiameter()) > (p.getCenterY()) 
				&&  getY() < p.getCenterY() 
				&& (getX() + getRadius()) < p.getCenterX() 
				&& (getX() + getDiameter()) > p.getCenterX();
	         
	}
	
	public boolean eatGoingRight(Pill p)
	{
		return     (getY() + getDiameter()) > (p.getCenterY()) 
				&&  getY() < p.getCenterY() 
				&& (getX() + getRadius()) > p.getCenterX() 
				&&  getX() < p.getCenterX();
	}
	
	public boolean atePill(Pill p)
	{
		boolean downEat = direction == Direction.DOWN && eatGoingDown(p),							
				upEat = direction == Direction.UP && eatGoingUp(p),					    
				rightEat = direction == Direction.RIGHT	&& eatGoingRight(p),						
				leftEat	= direction == Direction.LEFT && eatGoingLeft(p);
		
		return ( downEat || upEat || rightEat || leftEat);
		
	}
	
	public void resetScore()
	{
		this.points = 0;
	}
	
	public String toString()
	{
		return "Score: " + getScore();		
	}
}

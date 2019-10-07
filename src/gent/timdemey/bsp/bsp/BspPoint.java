package gent.timdemey.bsp.bsp;

public class BspPoint
{
	public final double x, y;
	
	public BspPoint (double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public BspPoint add (double x, double y)
	{
		return new BspPoint (this.x + x, this.y + y);
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}

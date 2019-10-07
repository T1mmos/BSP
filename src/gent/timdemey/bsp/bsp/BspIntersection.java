package gent.timdemey.bsp.bsp;

public class BspIntersection
{
	public final BspPoint point;
	public final double t1;
	public final double t2;
	
	public BspIntersection (BspPoint point, double t1, double t2)
	{
		this.point = point;
		this.t1 = t1;
		this.t2 = t2;
	}
}

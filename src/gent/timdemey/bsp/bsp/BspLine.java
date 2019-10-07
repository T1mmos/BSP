package gent.timdemey.bsp.bsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a BSP line, internally using parameterized line definitions
 * so it works for vertical and horizontal lines as well. (thus: x = x1 + s*dx,
 * y = y1 + s*dy).
 * 
 * @author timdm
 *
 */
public class BspLine
{
	public final BspPoint p1, p2;
	private final double dx, dy;
	private final double nx, ny;
	private final double t_min;
	private final double t_max;
	
	public BspLine(BspPoint p1, BspPoint p2)
	{
		this.p1 = p1;
		this.p2 = p2;
		
		this.dx = p2.x - p1.x;
		this.dy = p2.y - p1.y;
		this.nx = -dy;
		this.ny = dx;
		
		this.t_min = 0.0;   // x1 == x1 + t_min * dx, y1 == y1 + t_min * dy
		this.t_max = 1.0; // x2 == x1 + t_max * dx, y2 == y1 + t_max * dy
	}
	
	public static BspLine getLine (BspPoint p, double dx, double dy)
	{
		BspPoint p2 = p.add(dx, dy);
		return new BspLine(p, p2);
	}
	
	public BspIntersection intersect (BspLine splitter) 
	{
		BspLine l1 = this;
		BspLine l2 = splitter;
		
		double denom = (l1.dx * l2.dy - l2.dx * l1.dy) + 0.0;
		
		if (denom == 0.0) 
		{
			return null; // parallel
		}
		else
		{
			double t_l1 = (l2.dx * (l1.p1.y - l2.p1.y) + l2.dy * (l2.p1.x - l1.p1.x)) / denom + 0.0;
			double t_l2 = (l1.dx * (l2.p1.y - l1.p1.y) + l1.dy * (l1.p1.x - l2.p1.x)) / (-denom) + 0.0;
			
			BspPoint isecPoint = p1.add(t_l1 * dx, t_l1 * dy);
			return new BspIntersection(isecPoint, t_l1, t_l2);
		}
	}
	
	/**
	 * Splits the current BspLine into 1 or 2 BspLines, where the splitting 
	 * point is determined by the given BspLine.
	 * @param x
	 * @param y
	 * @return
	 */
	public BspLine[] split(BspLine splitLine)
	{
		BspLine o = splitLine;
		
		// get the normals on splitLine, going through p1 and p2
		BspLine normal_p1 = getLine(p1, o.nx, o.ny);
		BspLine normal_p2 = getLine(p2, o.nx, o.ny);
		
		// intersect the normals with this
		BspIntersection isec_normal1 = normal_p1.intersect(splitLine);
		BspIntersection isec_normal2 = normal_p2.intersect(splitLine);
		
		BspLine[] splits = new BspLine[2];
		boolean p1Left = isec_normal1.t1 < 0;
		boolean p1Right = isec_normal1.t1 > 0;
		boolean p2Left = isec_normal2.t1 < 0;
		boolean p2Right = isec_normal2.t1 > 0;
		
		// - Assign completely to left side if no point is completely on the right side
		//   So if both points are found on the splitter, the splittee is assigned to the left side
		// - Assign completely to right side if no point is found on the left side
		// - All other cases: one point left, one point right, so we must split
		if (!p2Right && !p1Right)
		{
			splits[0] = this;
			splits[1] = null;
		}		
		else if (!p1Left && !p2Left)
		{
			splits[0] = null;
			splits[1] = this;
		}
		else 
		{
			BspIntersection isec = this.intersect(splitLine);
			if (p1Left)
			{
				splits[0] = new BspLine(p1, isec.point);
				splits[1] = new BspLine(isec.point, p2);
			}
			else
			{
				splits[0] = new BspLine(isec.point, p2);
				splits[1] = new BspLine(p1, isec.point);
			}				
		}
		return splits;		
	}
	
	@Override
	public String toString()
	{
		return "[" + p1 + "->" + p2 + "]";
	}
}

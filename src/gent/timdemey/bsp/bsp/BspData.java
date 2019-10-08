package gent.timdemey.bsp.bsp;

import java.util.ArrayList;
import java.util.List;

public class BspData
{
	public static final List<BspLine> inputLines = CreateBspLines(
	
		new double[][]
		{
			{3,2},{4,3},
			{2,1},{2,3},
			{0,0},{1,1}
		}
	);
	
	public static final BspTree bspTree = BspTree.build(inputLines);
	
	public static List<BspLine> CreateBspLines(double [][] points)
	{
		if (points == null)
		{
			throw new NullPointerException();
		}
		if (points.length % 2 == 1)
		{
			throw new IllegalArgumentException("Points must come in pairs");
		}
				
		List<BspLine> bspLines = new ArrayList<>();
		for (int i = 0; i < points.length; i += 2)
		{
			double[] p1 = points[i];
			double[] p2 = points[i + 1];
			
			BspLine bspLine = new BspLine(new BspPoint(p1[0], p1[1]), new BspPoint(p2[0], p2[1])); 
			bspLines.add(bspLine);
		}
		
		return bspLines;
	}
	
}

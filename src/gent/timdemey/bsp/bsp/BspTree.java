package gent.timdemey.bsp.bsp;

import java.util.ArrayList;
import java.util.List;

public class BspTree
{
	public final BspTree left, right;
	public final BspLine bspLine;
	
	private BspTree (BspLine bspLine, BspTree left, BspTree right)
	{
		this.bspLine = bspLine;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Builds a BSP tree, given all the (not yet splitted) BspLines lines in the 
	 * recursive space.
	 * @param bspData
	 * @return
	 */
	public static BspTree build (List<BspLine> bspLines)
	{
		// pick a BSP line that will divide the space
		BspLine splitter = bspLines.remove(bspLines.size() - 1);
		
		if (bspLines.size() == 0)
		{
			return new BspTree(splitter, null, null);
		}
		
		List<BspLine> left = new ArrayList<>();
		List<BspLine> right = new ArrayList<>();
		
		for (BspLine bspLine : bspLines)
		{
			BspLine[] splits = bspLine.split(splitter);
			if (splits[0] != null)
			{
				left.add(splits[0]);
			}
			if (splits[1] != null)
			{
				right.add(splits[1]);
			}
		}
		
		BspTree leftNode = left.size() > 0 ? build(left) : null;
		BspTree rightNode = right.size() > 0 ? build(right) : null;
		
		return new BspTree(splitter, leftNode, rightNode);
	}
}

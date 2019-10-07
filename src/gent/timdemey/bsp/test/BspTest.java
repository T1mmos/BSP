package gent.timdemey.bsp.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import gent.timdemey.bsp.bsp.BspIntersection;
import gent.timdemey.bsp.bsp.BspLine;
import gent.timdemey.bsp.bsp.BspTree;
import gent.timdemey.bsp.bsp.BspPoint;

class BspTest
{

	@Test
	void TestBspLineIntersect_Regular()
	{
		// two lines perpendicular to each other, the two diagonals
		// of the unit square
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(0.0, 1.0);		
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNotNull(isec);
		assertEquals(0.5, isec.point.x);
		assertEquals(0.5, isec.point.y);
		assertEquals(0.5, isec.t1);
		assertEquals(0.5, isec.t2);
	}
	
	@Test
	void TestBspLineIntersect_1Vert()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(0.0, 1.0); 

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(0.0, 1.0);		
		
		BspLine bspLine1 = new BspLine(p1, p2); // vertical
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNotNull(isec);
		assertEquals(0.0, isec.point.x);
		assertEquals(1.0, isec.point.y);
		assertEquals(1.0, isec.t1);
		assertEquals(1.0, isec.t2);
	}
	
	@Test
	void TestBspLineIntersect_1Hor()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 0.0); 

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(0.0, 1.0);		
		
		BspLine bspLine1 = new BspLine(p1, p2); // horizontal
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNotNull(isec);
		assertEquals(1.0, isec.point.x);
		assertEquals(0.0, isec.point.y);
		assertEquals(1.0, isec.t1);
		assertEquals(0.0, isec.t2);
	}
	
	@Test
	void TestBspLineIntersect_2Vert()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0); 

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(1.0, 1.0);		
		
		BspLine bspLine1 = new BspLine(p1, p2); 
		BspLine bspLine2 = new BspLine(p3, p4); // vertical
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNotNull(isec);
		assertEquals(1.0, isec.point.x);
		assertEquals(1.0, isec.point.y);
		assertEquals(1.0, isec.t1);
		assertEquals(1.0, isec.t2);
	}
	
	@Test
	void TestBspLineIntersect_2Hor()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0); 

		BspPoint p3 = new BspPoint(0.0, 1.0);
		BspPoint p4 = new BspPoint(1.0, 1.0);		
		
		BspLine bspLine1 = new BspLine(p1, p2); 
		BspLine bspLine2 = new BspLine(p3, p4); // horizontal
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNotNull(isec);
		assertEquals(1.0, isec.point.x);
		assertEquals(1.0, isec.point.y);
		assertEquals(1.0, isec.t1);
		assertEquals(1.0, isec.t2);
	}
	
	@Test
	void TestBspLineIntersect_Parallel_Regular()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(0.0, 1.0);
		BspPoint p4 = new BspPoint(1.0, 2.0);	
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNull(isec);
	}
	
	@Test
	void TestBspLineIntersect_Parallel_Vert()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(0.0, 1.0);

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(1.0, 1.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNull(isec);
	}
	
	@Test
	void TestBspLineIntersect_Parallel_Hor()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 0.0);

		BspPoint p3 = new BspPoint(0.0, 1.0);
		BspPoint p4 = new BspPoint(1.0, 1.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspIntersection isec = bspLine1.intersect(bspLine2);
		assertNull(isec);
	}
	
	@Test
	void TestBspLineSplit_Regular_LeftRight()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(0.0, 1.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects 2 lines, the splitting point to be 0.5,0.5
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNotNull(splits[0]);
		assertNotNull(splits[1]);
		
		BspLine left = splits[0];
		BspLine right = splits[1];
			
		// left bspline
		assertEquals(0.5, left.p1.x);
		assertEquals(0.5, left.p1.y);
		assertEquals(0.0, left.p2.x);
		assertEquals(1.0, left.p2.y);
		
		// right bspline
		assertEquals(1.0, right.p1.x);
		assertEquals(0.0, right.p1.y);
		assertEquals(0.5, right.p2.x);
		assertEquals(0.5, right.p2.y);
	}
	
	@Test
	void TestBspLineSplit_Regular_Left()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(0.0, 1.0);
		BspPoint p4 = new BspPoint(1.0, 2.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects only left 
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNotNull(splits[0]);
		assertNull(splits[1]);
		
		BspLine left = splits[0];
			
		// left bspline
		assertEquals(0.0, left.p1.x);
		assertEquals(1.0, left.p1.y);
		assertEquals(1.0, left.p2.x);
		assertEquals(2.0, left.p2.y);		
	}
	
	@Test
	void TestBspLineSplit_Regular_Right()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(1.0, 0.0);
		BspPoint p4 = new BspPoint(2.0, 1.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects only right 
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNull(splits[0]);
		assertNotNull(splits[1]);
		
		BspLine right = splits[1];
			
		// left bspline
		assertEquals(1.0, right.p1.x);
		assertEquals(0.0, right.p1.y);
		assertEquals(2.0, right.p2.x);
		assertEquals(1.0, right.p2.y);		
	}
	
	@Test
	void TestBspLineSplit_Regular_SameLine()
	{
		BspPoint p1 = new BspPoint(0.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(2.0, 2.0);
		BspPoint p4 = new BspPoint(3.0, 3.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects 1 line, must be assigned to the right side
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNotNull(splits[0]);
		assertNull(splits[1]);
		
		BspLine left = splits[0];
					
		// right bspline
		assertEquals(2.0, left.p1.x);
		assertEquals(2.0, left.p1.y);
		assertEquals(3.0, left.p2.x);
		assertEquals(3.0, left.p2.y);
	}
	
	@Test
	void TestBspLineSplit_1Ver_LeftRight()
	{
		BspPoint p1 = new BspPoint(1.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(0.0, 4.0);
		BspPoint p4 = new BspPoint(4.0, 0.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects 2 lines, the splitting point to be 1,3
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNotNull(splits[0]);
		assertNotNull(splits[1]);
		
		BspLine left = splits[0];
		BspLine right = splits[1];
			
		// left bspline
		assertEquals(0.0, left.p1.x);
		assertEquals(4.0, left.p1.y);
		assertEquals(1.0, left.p2.x);
		assertEquals(3.0, left.p2.y);
		
		// right bspline
		assertEquals(1.0, right.p1.x);
		assertEquals(3.0, right.p1.y);
		assertEquals(4.0, right.p2.x);
		assertEquals(0.0, right.p2.y);
	}
	
	@Test
	void TestBspLineSplit_1Ver_Left()
	{
		BspPoint p1 = new BspPoint(1.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(0.0, 1.0);
		BspPoint p4 = new BspPoint(1.0, 0.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects 1 lines, left
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNotNull(splits[0]);
		assertNull(splits[1]);
		
		BspLine left = splits[0];
	
		// left bspline
		assertEquals(0.0, left.p1.x);
		assertEquals(1.0, left.p1.y);
		assertEquals(1.0, left.p2.x);
		assertEquals(0.0, left.p2.y);		
	}
	
	@Test
	void TestBspLineSplit_1Ver_Right()
	{
		BspPoint p1 = new BspPoint(1.0, 0.0);
		BspPoint p2 = new BspPoint(1.0, 1.0);

		BspPoint p3 = new BspPoint(1.0, 1.0);
		BspPoint p4 = new BspPoint(2.0, 0.0);
		
		BspLine bspLine1 = new BspLine(p1, p2);
		BspLine bspLine2 = new BspLine(p3, p4);
		
		BspLine[] splits = bspLine2.split(bspLine1);
		
		// expects 1 lines, right
		assertNotNull(splits);
		assertEquals(2, splits.length);
		assertNull(splits[0]);
		assertNotNull(splits[1]);
		
		BspLine right = splits[1];
	
		// left bspline
		assertEquals(1.0, right.p1.x);
		assertEquals(1.0, right.p1.y);
		assertEquals(2.0, right.p2.x);
		assertEquals(0.0, right.p2.y);		
	}
	
	@Test
	void TestBspLine_BuildTree()
	{
		double [][] points = new double[][]
		{
			{3,2},{4,3},
			{2,1},{2,3},
			{0,0},{1,1}
		};
		
		List<BspLine> bspLines = new ArrayList<>();
		for (int i = 0; i < points.length; i += 2)
		{
			double[] p1 = points[i];
			double[] p2 = points[i + 1];
			
			BspLine bspLine = new BspLine(new BspPoint(p1[0], p1[1]), new BspPoint(p2[0], p2[1])); 
			bspLines.add(bspLine);
		}
		
		BspTree bspNode = BspTree.build(bspLines);

		assertNotNull(bspNode);	
	}
}

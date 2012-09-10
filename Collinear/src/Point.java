/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

	// compare points by slope
	public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator(); // YOUR DEFINITION HERE

	private final int x; // x coordinate
	private final int y; // y coordinate
	
	/**
	 * Compares points by the slopes they make with the invoking point (x0, y0). 
	 * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0). 
	 * Treats horizontal, vertical, and degenerate line segments as in the slopeTo() method.
	 */
	private final class SlopeOrderComparator implements Comparator<Point> {
		Point invokingPoint = Point.this;		
		
		@Override
		public int compare(Point o1, Point o2) {
			double slopeToO1 = invokingPoint.slopeTo(o1);
			double slopeToO2 = invokingPoint.slopeTo(o2);
			
			if (slopeToO1 < slopeToO2) return -1;
			if (slopeToO1 > slopeToO2) return 1;
			return 0; // remember, total ordering guarantees 0 will be returned when slopes are equal.
		}
	}

	/**
	 * Creates the point (x, y).
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Plot this point to standard drawing.
	 */
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	/**
	 * Draws line between this point and that point to standard drawing.
	 * 
	 * @param that
	 */
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	/**
	 * Returns the slope between the invoking point (x0, y0) and the argument point (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0).<br> 
	 * Special cases:
	 * <ul>
	 * <li>the slope of a horizontal line segment is treated as positive zero [added 7/29],</li>
	 * <li>the slope of a vertical line segment is treated as positive infinity,</li>
	 * <li>the slope of a degenerate line segment (between a point and itself) is treated as negative infinity.</li>
	 * </ul> 
	 * 
	 * @param that
	 * @return
	 */
	// slope between this point and that point
	public double slopeTo(Point that) {
		double numerator = (double) that.y - y; // y1 - y0
		double denominator = (double) that.x - x; // x1 - x0
		
		double slope = numerator / denominator;
		if (Double.isNaN(slope)) return Double.NEGATIVE_INFINITY; // this == that
		
		if (denominator == 0D) return Double.POSITIVE_INFINITY; // vertical line
		if (numerator == 0D) return 0.0; // horizontal line

		return slope;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * Compares points by their y-coordinates, breaking ties by their x-coordinates. 
	 * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		if ((y < that.y) || ((y == that.y) && (x < that.x))) // (y0 < y1) OR [(y0 = y1) AND (x0 < x1)]
			return -1;
		if ((x == that.x) && (y == that.y)) // same point
			return 0;
		return 1;
	}

	/* (non-Javadoc)
	 * Return string representation of this point.
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}

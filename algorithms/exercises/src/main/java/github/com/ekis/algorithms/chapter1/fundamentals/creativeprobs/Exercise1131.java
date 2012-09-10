package github.com.ekis.algorithms.chapter1.fundamentals.creativeprobs;

import static java.lang.Math.*;

import java.awt.Color;

import edu.princeton.stdlib.StdDraw;

/**
 * 1.1.31  Random connections.  
 * Write a program that takes as command-line arguments an integer N and a double value p (between 0 and 1), 
 * plots N equally spaced dots of size .05 on the circumference of a circle, 
 * and then, with probability p for each pair of points, draws a gray line connecting them.
 * 
 * @author Erik
 *
 */
public class Exercise1131 {
	
	private static Circle circle = new Circle(0, 0, 2);
	
	public static void main(String args[]) {
		drawRndConn(10, 1);
	}
	
	/**
	 * Draws a circle with given parameters and draws lines between equidistant points on the circumference.<br>
	 * Each line has a p probability of being drawn.
	 * 
	 * @param N number of equidistant points
	 * @param p probability each line is drawn
	 */
	private static void drawRndConn(int N, double p) {
		if (p < 0 || p > 1) throw new IllegalArgumentException("Value of p must be in [0, 1].");
		
		StdDraw.setXscale(-3, 3);
		StdDraw.setYscale(-3, 3);
		
		StdDraw.circle(circle.getCenter().getX(), circle.getCenter().getY(), circle.getR());
		Point[] points = getEquidistantPoints(circle, N);
		
		// TODO optimize this quadratic algorithm - surely we can do better....	
		for (int i = 0; i < points.length; i++) { // iterate and draw points on the circle;
			StdDraw.setPenColor(Color.RED);
			StdDraw.setPenRadius(0.011);
			StdDraw.point(points[i].getX(), points[i].getY());
			
			StdDraw.setPenRadius(0.001);
			StdDraw.setPenColor(Color.GRAY);
			for (int j = (i + 1); j < points.length; j++) {
				if (p >= Math.random()) { // draw line with probability p
					StdDraw.line(points[i].getX(), points[i].getY(), points[j].getX(), points[j].getY());
				}
			}
		}
		
		StdDraw.show();
	}
	
	/**
	 * Retrieves an array of equidistant {@link Point}s.<br>
	 * Each point is an element of given {@link Circle}.
	 * 
	 * @param circle {@link Circle} which contains {@link Point}s.
	 * @param noPoints number of equidistant points on a circle
	 * @return an array of equidistant {@link Point}s.
	 */
	private static Point[] getEquidistantPoints(Circle circle, int noPoints) {
		Point[] result = new Point[noPoints];
		
		double constAngle = (2 * PI) / noPoints;
		double currentAngle = 0;
		
		for (int i = 0; i < noPoints; i++) {
			double x = circle.getCenter().getX() + sin(currentAngle) * circle.getR();
			double y = circle.getCenter().getY() + cos(currentAngle) * circle.getR();
			
			Point point = new Point(x, y);
			result[i] = point;
			
			currentAngle += constAngle;
		}
		
		return result;
	}
	
	/**
	 * Represents a circle with a center {@link Point} and radius.
	 * 
	 * @author Erik
	 *
	 */
	private static class Circle {
		private Point center;
		private double r;
		
		public Circle(double x, double y, double r) {
			super();
			if (x >= 0 && y >= 0 && r > 0) {
				center = new Point(x, y);
				this.r = r;
			} else {
				throw new IllegalArgumentException("x, y must be >= 0; r must be > 0!");
			}
		}
		
		public Point getCenter() {
			return center;
		}
		
		public double getR() {
			return r;
		}
	}
	
	/**
	 * Describes a point in a Cartesian coordinates.
	 * 
	 * @author Erik
	 *
	 */
	private static class Point {
		private double x;
		private double y;
		
		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
	}
}
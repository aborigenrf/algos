import java.awt.Color;
import java.util.Arrays;

/**
 * @author Erik
 * 
 */
public class Brute {

	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/input100.txt";

	private int[] inputCoord = null;
	private Point[] points = null;
	private static final String MARKER = " -> ";

	public Brute(int[] inputCoord) {
		this.inputCoord = inputCoord;
		points = new Point[inputCoord[0]]; // first entry in input file
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] inputCoord = In.readInts(INPUT);

		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.005);
		StdDraw.setPenColor(StdDraw.BOOK_RED);
		StdDraw.show(0);

		Brute b = new Brute(inputCoord);
		b.loadPoints();
		b.processPoints();

		// display to screen all at once
		StdDraw.show(0);
	}

	private void loadPoints() {
		int pointNo = inputCoord[0];

		int j = 1;
		// load points from input
		for (int i = 1; i <= pointNo; i++) {
			Point point = null;

			if (i == 1) {
				point = new Point(inputCoord[i], inputCoord[i + 1]);
			} else {
				point = new Point(inputCoord[j], inputCoord[j + 1]);
			}
			points[i - 1] = point;

			// System.out.println((i - 1) + " -> " + Arrays.toString(points));
			j += 2;
		}
		// System.out.println("***********");
	}

	private void processPoints() {
		int len = points.length;
		Arrays.sort(points);

		for (int i = 0; i < len; i++) { // p-points
			Point p = points[i];
			p.draw();

			for (int j = i + 1; j < len; j++) { // q-points
				Point q = points[j];
				q.draw();
				// StdOut.println("P -- Q analysis");
				Double pqSlope = analyzePoint(p, q);
				if (pqSlope == null)
					break;

				for (int k = j + 1; k < len; k++) { // r-points
					Point r = points[k];
					r.draw();
					// StdOut.println("P -- R analysis");
					Double prSlope = analyzePoint(p, r);
					if (prSlope == null)
						break;

					if (pqSlope.equals(prSlope)) { // reduce N^4 to N^3
						for (int l = k + 1; l < len; l++) { // s-points
							Point s = points[l];
							s.draw();
							// StdOut.println("P -- S analysis");
							Double psSlope = analyzePoint(p, s);
							if (psSlope == null)
								break;
							if (pqSlope.equals(psSlope)) {
								StdOut.println(p.toString() + MARKER + q.toString() + MARKER + r.toString() + MARKER + s.toString());
								p.drawTo(s);
							}
						}
					}
				}
			}
		}
	}

	private Double analyzePoint(Point x, Point y) {
		// StdOut.println("Analyze points: X" + x.toString() + "; Y" + y.toString() + "....");

		int xLesserThanY = x.compareTo(y);
		if (xLesserThanY == -1) {
			return x.slopeTo(y);
		} else {
			// StdOut.println("Returning null - x.compareTo(y) is: " + x.compareTo(y));
			return null;
		}

	}
}
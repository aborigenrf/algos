import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erik
 * 
 */
public class Fast {

	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/equidistant.txt";

	private int[] inputCoord = null;
	private Point[] loadedPoints = null;
	private Point[] processedPoints = null;
	private static final String MARKER = " -> ";

	private void loadPoints() {
		int pointNo = inputCoord[0];

		int j = 1;
		// load loadedPoints from input
		for (int i = 1; i <= pointNo; i++) {
			Point point = null;

			if (i == 1) {
				point = new Point(inputCoord[i], inputCoord[i + 1]);
			} else {
				point = new Point(inputCoord[j], inputCoord[j + 1]);
			}
			loadedPoints[i - 1] = point;
			processedPoints[i - 1] = point;

			j += 2;
		}
		Arrays.sort(loadedPoints); // sort initially loaded array
		//Arrays.sort(processedPoints);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] inputCoord = In.readInts(INPUT);
		//int[] inputCoord = In.readInts(args[0]);

		// rescale coordinates and turn on animation mode
//		StdDraw.setXscale(0, 32768);
//		StdDraw.setYscale(0, 32768);
//		StdDraw.show(0);

		Fast f = new Fast();
		f.inputCoord = inputCoord;
		f.loadedPoints = new Point[inputCoord[0]]; // first entry in input file
		f.processedPoints = new Point[inputCoord[0]]; // construct array which will be subsequently sorted via comparator on each analysis run of points
		f.loadPoints();
		f.processPoints();

		// display to screen all at once
//		StdDraw.show(0);
	}

	private void processPoints() {
		int len = loadedPoints.length;
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < len; i++) {
			Point p = loadedPoints[i];
			Point q = null;
			Point previousPoint = null;
			List<Point> printOut = null;
			
			Arrays.sort(processedPoints, p.SLOPE_ORDER); // consider omitting the sort in iteration 0, rather than copying the loadedPoints, since they're both the same in this iteration

			Double previousSlope = null;
			Double currentSlope = null;
			
			for (int j = 1; j < len; j++) { // len - 1 prevents x.slopeTo(x) ?
				q = processedPoints[j];
				currentSlope = p.slopeTo(q);
				
				System.out.print("P -> " + p + ", ");
				System.out.print("X -> " + previousPoint + ", ");
				System.out.print("Q -> " + q + "; ");
				System.out.print("currentSlope -> " + currentSlope + ", ");
				System.out.println("previousSlope -> "  + previousSlope);
				
				previousSlope = currentSlope;
			}
			printList(printOut, sb);
		}
		StdOut.print(sb.toString());
	}
	
	/**
	 * @param printOut
	 * @param sb
	 */
	private void printList(List<Point> printOut, StringBuilder sb) {
		if (printOut != null) {
			Collections.sort(printOut);
			for (int k = 0; k < printOut.size(); k++) {
				sb.append(printOut.get(k));
				if (k != printOut.size() - 1) sb.append(MARKER);
				if (k == printOut.size() - 1) {
					sb.append("; Line slope -> " + printOut.get(0).slopeTo(printOut.get(k)));
//					printOut.get(0).drawTo(printOut.get(k));
					sb.append("\n");
				}
			}
			printOut = null;
		}
	}
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Erik
 * 
 */
public class Fast {

	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/equidistant.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/horizontal5.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/input40.txt";

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
		// Arrays.sort(processedPoints);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] inputCoord = In.readInts(INPUT);
		//int[] inputCoord = In.readInts(args[0]);

		// rescale coordinates and turn on animation mode
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.show(0);

		Fast f = new Fast();
		f.inputCoord = inputCoord;
		f.loadedPoints = new Point[inputCoord[0]]; // first entry in input file
		f.processedPoints = new Point[inputCoord[0]]; // construct array which will be subsequently sorted via comparator on each analysis run of
														// points
		f.loadPoints();
		f.processPoints();

		// display to screen all at once
		StdDraw.show(0);
	}

	private void processPoints() {
		int len = loadedPoints.length;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < len; i++) {
			Point p = loadedPoints[i];
			Point q = null;
			Point previousPoint = null;
			List<Point> printOut = null;
			
			copyLoadedToProcessed();
			Arrays.sort(processedPoints, i, len, p.SLOPE_ORDER);

			Double previousSlope = null;
			Double currentSlope = null;

			boolean firstEqualFound = false; // set to TRUE if we found equal slopes for the first time AND printed them out on the console
			boolean slopeChanged = false; // set to TRUE if slope changed while traversing current set of points
			boolean breakOut = false;

			for (int j = i + 1; j < len; j++) { // len - 1 prevents x.slopeTo(x) ?
				q = processedPoints[j];
				if (j != 1) {
					previousPoint = processedPoints[j - 1];
					previousSlope = p.slopeTo(previousPoint);
				}
				currentSlope = p.slopeTo(q);

//				System.out.print("P -> " + p + ", ");
//				System.out.print("X -> " + previousPoint + ", ");
//				System.out.print("Q -> " + q + "; ");
//				System.out.print("currentSlope -> " + currentSlope + ", ");
//				System.out.println("previousSlope -> " + previousSlope);
				
				boolean alreadyPrinted = false; // set to TRUE if result has already been printed out to console
				if (previousSlope != null) {
					if (currentSlope.equals(previousSlope) && !firstEqualFound) { // first encounter of equal slopes, print the whole line segment
						// check whether this segment is a sub
						for (int k = i - 1; k >= 0; k--) {
							Point b = processedPoints[k];
							if (currentSlope.equals(q.slopeTo(b))) {
								printList(printOut, sb);
								//printOut = null;
								breakOut = true;
								System.out.println("****** HIT 1 with P -> " + p + ", Q -> " + q + ", B -> " + b + ", currentSlope -> " + currentSlope + ", q.slopeTo(b) -> " + q.slopeTo(b));
								break;
							}
						}
						if (breakOut) {
							sb.delete(0, sb.length());
							break;
						}
						if (printOut != null) { // print previous list contents if list is not null
							printList(printOut, sb);
						}
						printOut = new ArrayList<Point>();

						firstEqualFound = true;
						printOut.add(p);
						printOut.add(previousPoint);
						printOut.add(q);
						alreadyPrinted = true;
						slopeChanged = false;
					}
				}
				if (currentSlope.equals(previousSlope) && firstEqualFound && !alreadyPrinted) { // non-first encounter of equal slopes, just print the found segment (the rest should already be printed)
					// check whether this segment is a sub
					for (int k = i - 1; k >= 0; k--) {
						Point b = processedPoints[k];
						if (currentSlope.equals(q.slopeTo(b))) {
							breakOut = true;
							System.out.println("****** HIT 2 with P -> " + p + ", Q -> " + q + ", B -> " + b + ", currentSlope -> " + currentSlope + ", q.slopeTo(b) -> " + q.slopeTo(b));
							break;
						}
					}
					if (breakOut) {
						sb.delete(0, sb.length());
						break;
					}
					printOut.add(q);
					alreadyPrinted = true;
					slopeChanged = false;
				}
				if (!currentSlope.equals(previousSlope)) { // if slopes differ and we know
					if (!slopeChanged && (firstEqualFound || alreadyPrinted)) { // if check passes, we know that previous j-iteration printed
																				// something out;
						// since the slope didn't change in previous iteration, and has changed in this iteration,
						// we have to mark slope from slopeMapKey as found and non printable in future analysis.
						// we also signal the slope has changed so that we don't hit the map with needless put ops.
						slopeChanged = true;
					}
					firstEqualFound = false;
					alreadyPrinted = false;
				}

				previousSlope = currentSlope;
			}
			printList(printOut, sb);
			StdOut.print(sb.toString());
			sb.delete(0, sb.length());
		}
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
				if (k != printOut.size() - 1)
					sb.append(MARKER);
				if (k == printOut.size() - 1) {
					sb.append("; Line slope -> " + printOut.get(0).slopeTo(printOut.get(k)));
					printOut.get(0).drawTo(printOut.get(k));
					sb.append("\n");
				}
			}
			printOut = null;
		}
	}
	
	private void copyLoadedToProcessed() {
		for (int i = 0; i < loadedPoints.length; i++) {
			processedPoints[i] = loadedPoints[i];
		}
	}
}
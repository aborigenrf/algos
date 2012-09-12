import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erik
 * 
 */
public class Fast {

	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/input6.txt";

	private int[] inputCoord = null;
	private Point[] loadedPoints = null;
	private Point[] processedPoints = null;
	private static final String MARKER = " -> ";

	/**
	 * 
	 */
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
		// Arrays.sort(loadedPoints); // sort initially loaded array
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
//		StdDraw.setPenRadius(0.005);
//		// StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
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
		
		//Set<Double> slopeSet = new HashSet<Double>();
		Map<Double, Boolean> slopeMap = new HashMap<Double, Boolean>(); // boolean value TRUE denotes whether line segments with given slope should be omitted from console printout

		for (int i = 0; i < len; i++) {
//			System.out.println("LP -> " + Arrays.toString(loadedPoints));
			Point p = loadedPoints[i];
			Point q = null;
			Point previousPoint = null;
			
			Arrays.sort(processedPoints, 0, 1, p.SLOPE_ORDER); // consider omitting the sort in iteration 0, rather than copying the loadedPoints, since they're both the same in this iteration
//			System.out.println("PP -> " + Arrays.toString(processedPoints));

			Double previousSlope = null;
			Double currentSlope = null;
			Double slopeMapKey = null;
			
			boolean firstEqualFound = false; // set to TRUE if we found equal slopes for the first time AND printed them out on the console
			boolean slopeChanged = false; // set to TRUE if slope changed while traversing current set of points
			
			for (int j = 1; j < len; j++) { // len - 1 prevents x.slopeTo(x) ?
				//Arrays.sort(processedPoints, i, j, p.SLOPE_ORDER);
//				System.out.println("PP** -> " + Arrays.toString(processedPoints));
				
				q = processedPoints[j];
				if (j != 1) {
					previousPoint = processedPoints[j - 1];
					previousSlope = p.slopeTo(previousPoint);
				}
				currentSlope = p.slopeTo(q);
				
				boolean alreadyPrinted = false; // set to TRUE if result has already been printed out to console 
				if (previousSlope != null) {
					if (currentSlope.equals(previousSlope) && !firstEqualFound) { // first encounter of equal slopes, print the whole line segment
						Boolean shouldBeSkipped = slopeMap.get(currentSlope);
						if (shouldBeSkipped == null || !shouldBeSkipped) {
							firstEqualFound = true;
							// StdOut.print(p + MARKER + previousPoint + MARKER + q);
							sb.append(p);
							sb.append(MARKER);
							sb.append(previousPoint);
							sb.append(MARKER);
							sb.append(q);
							slopeMap.put(currentSlope, false);
							slopeMapKey = currentSlope;
							alreadyPrinted = true;
							slopeChanged = false;
						}
					}
					if (currentSlope.equals(previousSlope) && firstEqualFound && !alreadyPrinted) { // non-first encounter of equal slopes, just print the found segment (the rest should already be printed)
						Boolean shouldBeSkipped = slopeMap.get(currentSlope);
						if (shouldBeSkipped == null || !shouldBeSkipped) {
							// StdOut.print(MARKER + q);
							sb.append(MARKER);
							sb.append(q);
							alreadyPrinted = true;
							slopeChanged = false;
						}
					}
					if (!currentSlope.equals(previousSlope)) { // if slopes differ and we know
						if (!slopeChanged && (firstEqualFound || alreadyPrinted)) { // if check passes, we know that previous j-iteration printed something out; 
											 // since the slope didn't change in previous iteration, and has changed in this iteration,
											 // we have to mark slope from slopeMapKey as found and non printable in future analysis.
											 // we also signal the slope has changed so that we don't hit the map with needless put ops.
							slopeMap.put(slopeMapKey, true);
							slopeChanged = true;
							sb.append("\n");
						}
//						if (alreadyPrinted) { // result has already printed out and we found a different slope, so reset boolean so that we can print out a new sequence of equal slopes
							firstEqualFound = false;
							alreadyPrinted = false;
//						}
					}
					if (j == len - 1 && alreadyPrinted) {
						sb.append("\n");
					}
				}
				
//				System.out.print("P -> " + p + ", ");
//				System.out.print("X -> " + previousPoint + ", ");
//				System.out.print("Q -> " + q + "; ");
//				System.out.print("currentSlope -> " + currentSlope + ", ");
//				System.out.println("previousSlope -> "  + previousSlope);
				
				previousSlope = currentSlope;
			}
			// System.out.println();
			//  System.out.println("Iteration " + i + " done.");
		}
		StdOut.print(sb.toString());
	}
}
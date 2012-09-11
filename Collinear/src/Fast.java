import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erik
 * 
 */
public class Fast {

	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/input8.txt";

	private int[] inputCoord = null;
	private Point[] loadedPoints = null;
	private Point[] processedPoints = null;
	private static final String MARKER = " -> ";

	/**
	 * @param inputCoord
	 */
	public Fast(int[] inputCoord) {
		this.inputCoord = inputCoord;
		loadedPoints = new Point[inputCoord[0]]; // first entry in input file
		processedPoints = new Point[inputCoord[0]]; // construct array which will be subsequently sorted via comparator on each analysis run of point
		loadPoints();
	}

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
		//Arrays.sort(loadedPoints); // sort initially loaded array
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] inputCoord = In.readInts(INPUT);

		// rescale coordinates and turn on animation mode
		// StdDraw.setXscale(0, 32768);
		// StdDraw.setYscale(0, 32768);
		// StdDraw.setPenRadius(0.005);
		// StdDraw.setPenColor(StdDraw.BOOK_RED);
		// StdDraw.show(0);

		Fast fast = new Fast(inputCoord);
		// fast.loadPoints();
		fast.processPoints();

		// display to screen all at once
		// StdDraw.show(0);
	}

	private void processPoints() {
		int len = loadedPoints.length;
		
		Set<Double> slopeSet = new HashSet<Double>();

		for (int i = 0; i < len; i++) {
			System.out.println("LP -> " + Arrays.toString(loadedPoints));
			Point p = loadedPoints[i];
			Point q = null;
			Point previousPoint = null;
			
			Arrays.sort(processedPoints, p.SLOPE_ORDER); // consider omitting the sort in iteration 0, rather than copying the loadedPoints, since they're both the same in this iteration
			System.out.println("PP -> " + Arrays.toString(processedPoints));

			Double previousSlope = null;
			Double currentSlope = null;
			
			for (int j = 1; j < len; j++) { // len - 1 prevents x.slopeTo(x) ?
				q = processedPoints[j];
				if (j != 1) {
					previousPoint = processedPoints[j - 1];
					previousSlope = p.slopeTo(previousPoint);
				}
				currentSlope = p.slopeTo(q);
				
				System.out.print("P -> " + p + ", ");
				System.out.print("X -> " + previousPoint + ", ");
				System.out.print("Q -> " + q + "; ");
				System.out.print("currentSlope -> " + currentSlope + ", ");
				System.out.println("previousSlope -> "  + previousSlope);
				
				previousSlope = currentSlope;
			}
			System.out.println();
			System.out.println("Iteration " + i + " done.");
		}
	}
	
}
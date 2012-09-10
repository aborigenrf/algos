import java.util.Arrays;

/**
 * @author Erik
 *
 */
public class Fast {
	
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/collinear/input100.txt";

	private int[] inputCoord = null;
	private Point[] loadedPoints = null;
	private Point[] processedPoints = null;
	private static final String MARKER = " -> ";
	
	public Fast(int[] inputCoord) {
		this.inputCoord = inputCoord;
		loadedPoints = new Point[inputCoord[0]]; // first entry in input file
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] inputCoord = In.readInts(INPUT);

		// rescale coordinates and turn on animation mode
//		StdDraw.setXscale(0, 32768);
//		StdDraw.setYscale(0, 32768);
//		StdDraw.setPenRadius(0.005);
//		StdDraw.setPenColor(StdDraw.BOOK_RED);
//		StdDraw.show(0);

		Fast fast = new Fast(inputCoord);
		fast.loadPoints();
		fast.processPoints();

		// display to screen all at once
		//StdDraw.show(0);
	}
	
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

			j += 2;
		}
		Arrays.sort(loadedPoints); // sort initially loaded array
	}
	
	private void processPoints() {
		int len = loadedPoints.length;
		// processedPoints = new Point[len];
		
		for (int i = 0; i < len; i++) {
			processedPoints = loadedPoints;
			
			System.out.println("LP -> " + Arrays.toString(loadedPoints));
			System.out.println("PP -> " + Arrays.toString(processedPoints));
			//Point p = loadedPoints[i];
			
			System.out.println("Iteration " + i + " done.");
		}
	}
}
/**
 * @author Erik
 * 
 */
public class PercolationStats {

	/**
	 * Lattice size.
	 */
	private int N;
	/**
	 * Number of experimental repetitions.
	 */
	private int T;

	/**
	 * Contains open/close ratios - each cell contains a single run result.
	 */
	private double[] openCloseRatios;
	private double mean;
	private double stdDev;

	/**
	 * Perform T independent computational experiments on an N-by-N grid
	 * 
	 * @param N
	 * @param T
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new IllegalArgumentException("N, T must be > 1.");
		this.N = N;
		this.T = T;
		runStatsAnalysis();
	}

	/**
	 * Sample mean of percolation threshold
	 * 
	 * @return
	 */
	public double mean() {
		mean = StdStats.mean(openCloseRatios);
		return mean;
	}

	/**
	 * Sample standard deviation of percolation threshold
	 * 
	 * @return
	 */
	public double stddev() {
		if (T == 1)
			return Double.NaN;

		stdDev = StdStats.stddev(openCloseRatios);
		return stdDev;
	}

	/**
	 * Performs experimentation and statistical analysis.
	 */
	private void runStatsAnalysis() {
		openCloseRatios = new double[T + 1];

		for (int i = 0; i <= T; i++) {
			// StdOut.println("**** ITERATION " + i + " ****");
			Percolation perc = new Percolation(N);
			int simRun = 0;
			boolean hasPercolated = false;

			while (!hasPercolated) {
				int row = StdRandom.uniform(1, N + 1);
				int col = StdRandom.uniform(1, N + 1);
				// StdOut.print("Run " + simRun + " at (" + row + ", " + col +
				// ") -> ");
				if (perc.isOpen(row, col)) {
					// StdOut.println(" Site already open, skipping...");
					continue;
				} else {
					// StdOut.println("Opening site at (" + row + ", " + col +
					// ")");
					perc.open(row, col);
					simRun++;
				}
				if (perc.percolates()) {
					hasPercolated = true;
					// StdOut.println("Percolates!");
				}
			}
			double ratio = (double) simRun / (N * N);
			// StdOut.println("Storing open/close ratio for iteration " + i +
			// ". Ratio is " + simRun + "/" + (N * N)+ " = " + ratio);
			openCloseRatios[i] = ratio;
		}
		mean();
		stddev();
	}

	/**
	 * Test client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
//		int N = 20; // matrix size
//		int T = 1000; // no of experiments
		int N = Integer.valueOf(args[0]);
		int T = Integer.valueOf(args[1]);

		PercolationStats stats = new PercolationStats(N, T);
		double numerator = (1.96 * Math.sqrt(stats.stdDev));
		double denominator = Math.sqrt(T);
		double fraction = numerator / denominator;

		double[][] confidenceInterval = new double[1][2];
		confidenceInterval[0][0] = (stats.mean - fraction);
		confidenceInterval[0][1] = (stats.mean + fraction);

		StdOut.println("mean                    = " + stats.mean);
		StdOut.println("stddev                  = " + stats.stdDev);
		StdOut.println("95% confidence interval = " + confidenceInterval[0][0]
				+ ", " + confidenceInterval[0][1]);
	}
}
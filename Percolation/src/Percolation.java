/**
 * @author Erik Kis
 * 
 */
public class Percolation {

	/**
	 * Holds open sites.
	 */
	private boolean[][] connectivityMatrix;
	/**
	 * Union-find data structure matrix rank.
	 */
	private int N;
	/**
	 * Connectivity matrix rank, plus two additional virtual rows.
	 */
	private int M;
	/**
	 * Union-find data structure mapped from 2D matrix.
	 */
	private WeightedQuickUnionUF uf;
	private int ufSize;

	/**
	 * Create N-by-N grid, with all sites blocked
	 * 
	 * @param N
	 */
	public Percolation(int N) {
		if (N < 0)
			throw new IllegalArgumentException(
					"Invalid matrix range - must be >= 0.");

		this.N = N;
		this.M = (N + 1); // connectivity matrix has a left-side sentinel
							// column, plus two additional rows initialized to
							// true, to simulate virtual top and bottom
		ufSize = (N * N) + 2;
		initializeMatrices();
	}

	/**
	 * Open site (row i, column j) if it is not already open.
	 * 
	 * @param i
	 * @param j
	 */
	public void open(int i, int j) {
		validateIdx(i, j);

		// check whether site is open;
		// if not, perform union with all adjacent open sites
		boolean isOpen = isOpen(i, j);
		if (!isOpen) {
			openSite(i, j);
		}
	}

	/**
	 * Is site (row i, column j) open?
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isOpen(int i, int j) {
		validateIdx(i, j);
		// return boolean value from connectivity matrix
		return connectivityMatrix[i][j];
	}

	/**
	 * Is site (row i, column j) full?
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {
		validateIdx(i, j);
		int siteIdx = mapToVector(i, j);
		return uf.connected(0, siteIdx);
	}

	/**
	 * Does the system percolate?
	 * 
	 * @return
	 */
	public boolean percolates() {
		return uf.connected(0, ufSize - 1);
	}

	/**
	 * Opens a given site(i, j).<br>
	 * It does so mapping the site's planar coordinates to vector coordinates
	 * (UF data structure is a vector). Additionally, it registers the open site
	 * in the connectivity matrix and performs union with all adjoining open
	 * sites.
	 * 
	 * @param i
	 * @param j
	 */
	private void openSite(int i, int j) {
		// open site
		connectivityMatrix[i][j] = true;

		// connect site to its open neighbours
		int uf1DIdx = mapToVector(i, j);

		// left
		if (connectivityMatrix[i][j - 1]) {
			int left1DIdx = mapToVector(i, j - 1);
			union(uf1DIdx, left1DIdx);
		}
		// right
		if (connectivityMatrix[i][j + 1]) {
			int right1DIdx = mapToVector(i, j + 1);
			union(uf1DIdx, right1DIdx);
		}
		// up
		if (connectivityMatrix[i - 1][j]) {
			int up1DIdx = mapToVector(i - 1, j);
			union(uf1DIdx, up1DIdx);
		}
		// down
		if (connectivityMatrix[i + 1][j]) {
			int bottom1DIdx = mapToVector(i + 1, j);
			union(uf1DIdx, bottom1DIdx);
		}
	}

	/**
	 * Maps 2D array coordinates to 1D union-find data-structure.
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	private int mapToVector(int row, int col) {
		row--;
		col--;
		int offset = (row * N + col) + 1;
		return offset;
	}

	/**
	 * Performs UF union operation, taking care of virtual top and bottom sites.
	 * 
	 * @param current
	 * @param neighbour
	 */
	private void union(int current, int neighbour) {
		if (neighbour < 0) {
			neighbour = 0; // connect to virtual top
			// StdOut.println("Connecting to virtual top....");
		}
		if (neighbour > ufSize - 1) {
			neighbour = ufSize - 1; // connect to virtual bottom
			// StdOut.println("Connecting to virtual bottom....");
		}
		if (!uf.connected(neighbour, current)) {
			// StdOut.println("Connect current (" + current + ") to neighbour ("
			// + neighbour + ")....");
			uf.union(neighbour, current);
		}
	}

	/**
	 * Validates planar indices values.
	 * 
	 * @param i
	 * @param j
	 */
	private void validateIdx(int i, int j) {
		if (i <= 0 || i > N)
			throw new IndexOutOfBoundsException("Row index " + i
					+ " out of bounds");
		if (j <= 0 || j > N)
			throw new IndexOutOfBoundsException("Column index " + j
					+ " out of bounds");
	}

	/**
	 * Initializes UF data structure and connects bottom/row virtual sites.
	 */
	private void initializeMatrices() {
		uf = new WeightedQuickUnionUF(ufSize);
		// [rows][cols]
		connectivityMatrix = new boolean[M + 1][M + 1];

		// connect virtual top with top row
		for (int i = 1; i <= N; i++) {
			connectivityMatrix[0][i] = true;
		}

		// connect virtual bottom with bottom row
		int bottomRowElementIdx = ufSize - 2;
		int bottomLimit = Math.abs(N - bottomRowElementIdx);
		int boolMatrixColIdx = N;

		for (int i = bottomRowElementIdx; i > bottomLimit; i--) {
			connectivityMatrix[M][boolMatrixColIdx--] = true;
		}
	}

	/**
	 * Test client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Percolation p = new Percolation(3);
		p.open(1, 1);
		p.open(1, 2);
		p.open(2, 2);
		p.open(3, 2);
		StdOut.println("Connected (1, 1) to (1, 2) -> "
				+ p.uf.connected(p.mapToVector(1, 1), p.mapToVector(1, 2)));
		StdOut.println("Connected (1, 1) to (2, 2) -> "
				+ p.uf.connected(p.mapToVector(1, 1), p.mapToVector(2, 2)));
		StdOut.println("Connected (1, 1) to (3, 2) -> "
				+ p.uf.connected(p.mapToVector(1, 1), p.mapToVector(3, 2)));
		StdOut.println("Is (3, 2) full -> " + p.isFull(3, 2));
		StdOut.println("Percolates -> " + p.percolates());
	}
}

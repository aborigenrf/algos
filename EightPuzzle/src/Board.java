/**
 * @author Erik
 *
 */
public class Board {
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/a.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle02.txt";
	
	private int N; // board dimension
	private int hammingDistance;
	private int manhattanDistance;
	private int[] zero; // current zero tile coordinates
	private int[][] tiles; // current board tiles
	private int[][] goalTiles; // target board tiles
	private Queue<Board> neigbours; // contains all neighbouring boards (those that can be reached in one move from the dequeued search node)
	
	/**
	 * Construct a {@link Board} instance from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
	 * 
	 * @param blocks
	 */
	public Board(int[][] blocks) { 
		this.N = blocks.length;
		this.tiles = deepCloneArray(blocks);
		this.zero = new int[2];
		constructGoalBoard();
		calculateHammingDistance();
		calculateManhattanDistance();
	};
	
	/**
	 * @return board dimension N
	 */
	public int dimension() {
		return N;
	};

	/**
	 * @return number of blocks out of place
	 */
	public int hamming() { // 
		return hammingDistance;
	};

	/**
	 * @return sum of Manhattan distances between blocks and goal
	 */
	public int manhattan() {
		return manhattanDistance;
	};

	/**
	 * @return is this board the goal board?
	 */
	public boolean isGoal() {
		Board goalBoard = new Board(goalTiles);
		return this.equals(goalBoard);
	};

	/**
	 * Returns a twin board of this {@link Board}. 
	 * Essentially, we just try to swap first two elements - if we find a zero in these two, just jump to next row and perform swap there. 
	 * 
	 * @return a board obtained by exchanging two adjacent blocks in the same row
	 */
	public Board twin() {
		int[][] tilesClone = deepCloneArray(tiles);
		boolean fullBreak = false;
		for (int i = 0; i < N; i++) {
			if (fullBreak) break;
			for (int j = 0; j < N;) { // just try to swap first two elements; if we stumble upon zero, swap next row pair;
				if (tilesClone[i][j] == 0 || tilesClone[i][j + 1] == 0) {
					break;
				}
				int temp = tilesClone[i][j];
				tilesClone[i][j] = tilesClone[i][j + 1];
				tilesClone[i][j + 1] = temp;
				fullBreak = true;
				break;
			}
			if (fullBreak) break;
		}
		return new Board(tilesClone);
	};
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object y) {
		if (y == null) return false; // null check
		if (y == this) return true; // reference check
		if (y.getClass() != this.getClass()) return false; // instance check
		Board that = (Board) y;
		if (this.tiles.length != that.tiles.length) return false; // matrix dimension check
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (this.tiles[i][j] != that.tiles[i][j]) return false; // cell value check
		return true;
	};

	/**
	 * @return all neighboring boards
	 */
	public Iterable<Board> neighbors() {
		this.neigbours = new Queue<Board>();
		populateNeighbourQueue();
		return neigbours;
	};

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() { // string representation of the board (in the output format specified below)
		return constructFormattedArray(tiles);
	};
	
	// -------- PRIVATE METHODS
	
	/**
	 * Constructs a board with values {1, 2....N}. Stored in {@link Board} local variable.
	 */
	private void constructGoalBoard() {
		goalTiles = new int[N][N];
		int k = 1;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == 0) { // while constructing goal board, detect initial coordinates of zero element
					zero[0] = i;
					zero[1] = j;
				}
				if (k < N*N) goalTiles[i][j] = k++;
				else goalTiles[i][j] = 0;
			}
	}
	
	/**
	 * Pretty-prints a given array.
	 * 
	 * @param array to be pretty-printed.
	 * @return pretty-printed array as a {@link String}
	 */
	private String constructFormattedArray(int[][] array) {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", array[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	}
	
	/**
	 * Calculates sum of Hamming distances for this board and stores it in private field to promote immutability.
	 */
	private void calculateHammingDistance() {
		int count = 0;
		int detected;
		int expected;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (i == j && i == (N - 1)) break; // if last value in matrix, break immediately; if 0 is detected anywhere else, it's already counted in Hamming; if not, we know it's here and we know it's ok 
				detected = tiles[i][j];
				expected = goalTiles[i][j]; 
				
				if (detected != expected) count++;
			}
		hammingDistance = count;
	}
	
	/**
	 * Calculates sum of Manhattan distances for this board and stores it in private field to promote immutability.
	 */
	private void calculateManhattanDistance() {
		int manhattanDistanceSum = 0;
		for (int x = 0; x < N; x++) // x-dimension, traversing rows (i)
			for (int y = 0; y < N; y++) { // y-dimension, traversing cols (j)
				int value = tiles[x][y];
				if (value != 0) { // we don't compute MD for element 0
					int targetX = (value - 1) / N; // expected x-coordinate (row)
					int targetY = (value - 1) % N; // expected y-coordinate (col)
					int dx = x - targetX; // x-distance to expected coordinate
					int dy = y - targetY; // y-distance to expected coordinate
					manhattanDistanceSum += Math.abs(dx) + Math.abs(dy); 
				} 
			}
		manhattanDistance = manhattanDistanceSum;
	}
	
	// ------ NEIGHBOUR RETRIEVAL
	/**
	 * Populates instance queue of neighbour boards by constructing boards containing swapped zero element with adjacent elements. 
	 */
	private void populateNeighbourQueue() {
		if (swapUp()) {
			int[][] neighbour = deepCloneArray(tiles);
			swapDown(); // return board to previous position
			enqueueNeighbour(neighbour);
		}
		if (swapDown()) {
			int[][] neighbour = deepCloneArray(tiles);
			swapUp(); // return board to previous position
			enqueueNeighbour(neighbour);
		}
		if (swapLeft()) {
			int[][] neighbour = deepCloneArray(tiles);
			swapRight(); // return board to previous position
			enqueueNeighbour(neighbour);
		}
		if (swapRight()) {
			int[][] neighbour = deepCloneArray(tiles);
			swapLeft(); // return board to previous position
			enqueueNeighbour(neighbour);
		}
	}
	
	/**
	 * Creates a new neigbour {@link Board} and enqueues it.
	 * 
	 * @param neighbour
	 */
	private void enqueueNeighbour(int[][] neighbour) {
		Board board = new Board(neighbour);
		neigbours.enqueue(board);
	}
	
	private int[][] deepCloneArray(int[][] array) {
		int[][] newArray = new int[N][];
		for (int i = 0; i < N; i++) {
			newArray[i] = array[i].clone();
		}
		return newArray;
	}
	
	// ------- PERFORM SWAPS
	/**
	 * Swaps zero element with element directly above it.
	 * 
	 * @return TRUE if swap was successfull, FALSE if swap failed due to exceeded array limits
	 */
	private boolean swapUp() { // swap zero element to (i - 1) position
		return swapZeroWith(zero[0] - 1, zero[1]);
	}
	
	/**
	 * Swaps zero element with element directly below it.
	 * 
	 * @return TRUE if swap was successfull, FALSE if swap failed due to exceeded array limits
	 */
	private boolean swapDown() { // swap zero element to (i + 1) position
		return swapZeroWith(zero[0] + 1, zero[1]);
	}
	
	/**
	 * Swaps zero element with element directly left of it.
	 * 
	 * @return TRUE if swap was successfull, FALSE if swap failed due to exceeded array limits
	 */
	private boolean swapLeft() { // swap zero element to (j - 1) position
		return swapZeroWith(zero[0], zero[1] - 1);
	}
	
	/**
	 * Swaps zero element with element directly right of it.
	 * 
	 * @return TRUE if swap was successfull, FALSE if swap failed due to exceeded array limits
	 */
	private boolean swapRight() { // swap zero element to (j + 1) position
		return swapZeroWith(zero[0], zero[1] + 1);
	}
	
	/**
	 * Performs actually zero-element swapping and array boundary checks.
	 * 
	 * @param x x-coordinate of array element that zero-element is swapped with
	 * @param y y-coordinate of array element that zero-element is swapped with
	 * @return TRUE if swap was successfull, FALSE if swap failed due to exceeded array limits
	 */
	private boolean swapZeroWith(int x, int y) {
		int i = zero[0];
		int j = zero[1];
		
		if (y == j) { // swap horizontally (move on x-axis)
			if (x == (i - 1) && x >= 0) { // swap up and check we don't go over board
				int temp = tiles[i - 1][j];
				tiles[i - 1][j] = 0;
				tiles[i][j] = temp;
				zero[0]--; // update zero x-coord
				return true;
			} else if (x == (i + 1) && x <= (N - 1)) { // swap down and check we don't go over board
				int temp = tiles[i + 1][j];
				tiles[i + 1][j] = 0;
				tiles[i][j] = temp;
				zero[0]++; // update zero x-coord
				return true;
			} else { // if-checks failed, we wanted to move outside board
				return false;
			}
		} else if (x == i) { // swap vertically (move on y-axis)
			if (y == (j - 1) && y >= 0) { // swap left and check we don't go over board
				int temp = tiles[i][j - 1];
				tiles[i][j - 1] = 0;
				tiles[i][j] = temp;
				zero[1]--; // update zero y-coord
				return true;
			} else if (y == (j + 1) && y <= (N - 1)) { // swap right and check we don't go over board
				int temp = tiles[i][j + 1];
				tiles[i][j + 1] = 0;
				tiles[i][j] = temp;
				zero[1]++; // update zero y-coord
				return true;
			} else { // if-checks failed, we wanted to move outside board
				return false;
			}
		} else { // this is not supposed to happen - ever
			throw new IllegalArgumentException("Illegal zero swap!");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("PUZZLE UNIT TEST");
//		
//		// swapXXX() unit tests
//		int[][] blocks = readFromFile();
//		Board initial = new Board(blocks);
//		System.out.println("PRE-SWAP UP -> " + initial.toString());
//		initial.swapUp();
//		System.out.println("MID-SWAP UP -> " + initial.toString());
//		initial.swapUp();
//		System.out.println("POST-SWAP UP -> " + initial.toString());
//		
//		blocks = readFromFile();
//		initial = new Board(blocks);
//		System.out.println("PRE-SWAP DOWN -> " + initial.toString());
//		initial.swapDown();
//		System.out.println("MID-SWAP DOWN -> " + initial.toString());
//		initial.swapDown();
//		System.out.println("POST-SWAP DOWN -> " + initial.toString());
//		
//		blocks = readFromFile();
//		initial = new Board(blocks);
//		System.out.println("PRE-SWAP LEFT -> " + initial.toString());
//		initial.swapLeft();
//		System.out.println("MID-SWAP LEFT -> " + initial.toString());
//		initial.swapLeft();
//		System.out.println("POST-SWAP LEFT -> " + initial.toString());
//		
//		blocks = readFromFile();
//		initial = new Board(blocks);
//		System.out.println("PRE-SWAP RIGHT -> " + initial.toString());
//		initial.swapRight();
//		System.out.println("MID-SWAP RIGHT -> " + initial.toString());
//		initial.swapRight();
//		System.out.println("POST-SWAP RIGHT -> " + initial.toString());
//		
//		// neighbour queue unit test
//		blocks = readFromFile();
//		initial = new Board(blocks);
//		Queue<Board> testQueue = (Queue<Board>) initial.neighbors();
//		for (Board i: testQueue) {
//			System.out.println("Board dequeued -> " + i.toString());
//		}
//		
//		// twin() unit test
//		blocks = readFromFile();
//		initial = new Board(blocks);
//		System.out.println("ORIGINAL -> " + initial.toString());
//		System.out.println("TWIN     -> " + initial.twin().toString());
	}
	
	/**
	 * Reads input file and returns an array of integers.
	 * 
	 * @return
	 */
//	private static int[][] readFromFile() {
//		In in = new In(INPUT);
//		int N = in.readInt();
//		int[][] blocks = new int[N][N];
//		for (int i = 0; i < N; i++)
//			for (int j = 0; j < N; j++)
//				blocks[i][j] = in.readInt();
//		
//		return blocks;
//	}
}
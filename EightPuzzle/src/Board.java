import java.util.Arrays;

/**
 * @author Erik
 *
 */
public class Board {
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/a.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle02.txt";
	
	private int N; // board dimension
	private int[] zero; // current zero tile coordinates
	private int[][] tiles; // current board tiles
	private int[][] goalTiles; // target board tiles
	private Queue<Board> neigbours; // contains all neighbouring boards (those that can be reached in one move from the dequeued search node)
	
	public Board(int[][] blocks) {// construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
		this.tiles = blocks;
		this.zero = new int[2];
		this.N = tiles.length;
		constructGoalBoard();
	};
	
	public int dimension() {
		return N; // board dimension N
	};

	public int hamming() { // number of blocks out of place
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
		return count;
	};

	public int manhattan() { // sum of Manhattan distances between blocks and goal
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
		return manhattanDistanceSum;
	};

	public boolean isGoal() {
		return tiles.equals(goalTiles); // is this board the goal board?
	};

	public Board twin() {
		return null; // a board obtained by exchanging two adjacent blocks in the same row
	};

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

	public Iterable<Board> neighbors() { // all neighboring boards
		neigbours = new Queue<Board>();
		return neigbours;
	};

	public String toString() { // string representation of the board (in the output format specified below)
		return constructFormattedArray(tiles);
	};
	
	// -------- PRIVATE METHODS
	
	private void constructGoalBoard() {
		goalTiles = new int[N][N];
		int k = 1;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == 0) { // while constructing goal board, detect initial coordinates of zero element
					zero[0] = i;
					zero[1] = j;
					System.out.println("Zero element detected at -> (" + zero[0] + "," + zero[1] + ")");
				}
				if (k < N*N) 
					goalTiles[i][j] = k++;
				else
					goalTiles[i][j] = 0;
			}
//		System.out.println("Goal Board:");
//		System.out.println(constructFormattedArray(goalTiles));
	}
	
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
	
	private void swapUp() { // swap zero element to (i - 1) position
		System.out.println("PRE-SWAP UP ->" + this.toString());
		int i = zero[0];
		int j = zero[1];
		if ((i - 1) >= 0) {
			int temp = tiles[i - 1][j];
			tiles[i - 1][j] = 0;
			tiles[i][j] = temp;
			zero[0]--; // update new zero coordinate
		} else {
			System.out.println("Up limit reached");
		}
		System.out.println("POST-SWAP UP ->" + this.toString());
	}
	
	private void swapDown() { // swap zero element to (i + 1) position
		System.out.println("PRE-SWAP DOWN ->" + this.toString());
		int i = zero[0];
		int j = zero[1];
		if ((i + 1) <= (N - 1)) {
			int temp = tiles[i + 1][j];
			tiles[i + 1][j] = 0;
			tiles[i][j] = temp;
			zero[0]++; // update new zero coordinate
		} else {
			System.out.println("Down limit reached");
		}
		System.out.println("POST-SWAP DOWN ->" + this.toString());
	}
	
	private void swapLeft() { // swap zero element to (j - 1) position
		System.out.println("PRE-SWAP LEFT ->" + this.toString());
		int i = zero[0];
		int j = zero[1];
		if ((j - 1) >= 0) {
			int temp = tiles[i][j - 1];
			tiles[i][j - 1] = 0;
			tiles[i][j] = temp;
			zero[1]--; // update new zero coordinate
		} else {
			System.out.println("Left limit reached");
		}
		System.out.println("POST-SWAP LEFT ->" + this.toString());
	}
	
	private void swapRight() { // swap zero element to (j + 1) position
		System.out.println("PRE-SWAP RIGHT ->" + this.toString());
		int i = zero[0];
		int j = zero[1];
		if ((j + 1) <= (N - 1)) {
			int temp = tiles[i][j + 1];
			tiles[i][j + 1] = 0;
			tiles[i][j] = temp;
			zero[1]++; // update new zero coordinate
		} else {
			System.out.println("Right limit reached");
		}
		System.out.println("POST-SWAP RIGHT ->" + this.toString());
	}
	
	public static void main(String[] args) {
		System.out.println("PUZZLE UNIT TEST");
		int[][] blocks = readFromFile();
		
		Board initial = new Board(blocks);
		initial.swapUp();
	}
	
	private static int[][] readFromFile() {
		In in = new In(INPUT);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		
		return blocks;
	}
}
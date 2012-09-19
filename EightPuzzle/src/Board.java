import java.util.Arrays;

/**
 * @author Erik
 *
 */
public class Board {
	private int N;
	private int[][] tiles;
	private int[][] goalBoard;
	
	public Board(int[][] blocks) {// construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
		this.tiles = blocks;
		this.N = tiles.length;
		constructGoalBoard();
		System.out.println("XXXX -> " + Arrays.deepToString(goalBoard));
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
				expected = goalBoard[i][j]; 
				
				if (detected != expected) count++;
			}
		return count;
	};

	public int manhattan() { // sum of Manhattan distances between blocks and goal
		int manhattanDistanceSum = 0;
		for (int x = 0; x < N; x++) // x-dimension, traversing rows
			for (int y = 0; y < N; y++) { // y-dimension, traversing cols
				int value = tiles[x][y];
				if (value != 0) { // we don't compute MD for element 0
					int targetX = (value - 1) / N; // expected x-coordinate (row)
					int targetY = (value - 1) % N; // expected y-coordinate (col)
//					System.out.println("Value " + value + " referent coordinates (" + targetX + ", " + targetY + ")");
					int dx = x - targetX; 
					int dy = y - targetY;
//					System.out.println("Value " + value + " detected at coordinates (" + x + ", " + y + "); dx, dy -> " + dx + ", " + dy);
//					System.out.println("Calculated manhattan distance -> " + (Math.abs(dx) + Math.abs(dy)));
//					System.out.println("Manhattan distance sum state prior -> " + manhattanDistanceSum);
					manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
//					System.out.println("Manhattan distance sum state post -> " + manhattanDistanceSum);
//					System.out.println();
				} 
			}
		return manhattanDistanceSum;
	};

	public boolean isGoal() {
		return tiles.equals(goalBoard); // is this board the goal board?
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

	public Iterable<Board> neighbors() {
		return null; // all neighboring boards
	};

	public String toString() { // string representation of the board (in the output format specified below)
		return constructFormattedArray(tiles);
	};
	
	// -------- PRIVATE METHODS
	
	private void constructGoalBoard() {
		goalBoard = new int[N][N];
		int k = 1;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (k < N*N) {
					goalBoard[i][j] = k++;
					//System.out.println("Value: " + goalBoard[i][j] + " has coordinates: X -> " + ((goalBoard[i][j]-1) / N) + ", Y -> " + ((goalBoard[i][j]-1) % N));
				}
				else
					goalBoard[i][j] = 0;
			}
		System.out.println("Goal Board:");
		System.out.println(constructFormattedArray(goalBoard));
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

	public static void main(String[] args) {

	}
}
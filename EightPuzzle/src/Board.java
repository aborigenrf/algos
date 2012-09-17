/**
 * @author Erik
 *
 */
public class Board {
	private int N = 0;
	private int[][] tiles;
	
	public Board(int[][] blocks) {// construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
		this.tiles = blocks;
	};
	
	public int dimension() {
		return 0; // board dimension N
	};

	public int hamming() {
		return 0; // number of blocks out of place
	};

	public int manhattan() {
		return 0; // sum of Manhattan distances between blocks and goal
	};

	public boolean isGoal() {
		return false; // is this board the goal board?
	};

	public Board twin() {
		return null; // a board obtained by exchanging two adjacent blocks in the same row
	};

	public boolean equals(Object y) {
		return false; // does this board equal y?
	};

	public Iterable<Board> neighbors() {
		return null; // all neighboring boards
	};

	public String toString() { // string representation of the board (in the output format specified below)
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", tiles[i][j]));
			}
			s.append("\n");
		}
		return s.toString();
	};

	public static void main(String[] args) {

	}
}
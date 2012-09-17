
/**
 * @author Erik
 * 
 */
public class Solver {

	private static final String INPUT = "";

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)

	};
	
	private class SearchNode {
		private int moves; // the number of moves made to reach the board
		private Board board;
		private SearchNode previousNode; // previous search node
	};

	public boolean isSolvable() {
		return false; // is the initial board solvable?
	};

	public int moves() {
		return 0; // min number of moves to solve initial board; -1 if no solution
	};

	public Iterable<Board> solution() {
		return null; // sequence of boards in a shortest solution; null if no solution
	};

	public static void main(String[] args) { // solve a slider puzzle (given below)
		// create initial board from file
		// In in = new In(args[0]);
		In in = new In(INPUT);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);

		// solve the puzzle
		Solver solver = new Solver(initial);

		// print solution to standard output
		if (!solver.isSolvable())
			StdOut.println("No solution possible");
		else {
			StdOut.println("Minimum number of moves = " + solver.moves());
			for (Board board : solver.solution())
				StdOut.println(board);
		}
	}
}
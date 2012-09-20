/**
 * @author Erik
 * 
 */
public class Solver {
	
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt"; // 2x2
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle04.txt"; // 3x3 (example from assignment paper)
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle00.txt"; // 10x10
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/a.txt";
	
	private int moves; // number of moves made for this particular puzzle
	private Board initialBoard; // contains Board received from Solver constructor
	private MinPQ<SearchNode> openSet; // the set of tentative nodes to be evaluated, initially containing the start search node
	private MinPQ<SearchNode> twinBoardSet; // used to detect infeasible puzzles
	private Queue<Board> solutionPath;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		this.initialBoard = initial;
		this.openSet = new MinPQ<SearchNode>();
		this.twinBoardSet = new MinPQ<SearchNode>();
		this.solutionPath = new Queue<Board>();
	};
	
	private class SearchNode {
		private int searchNodeMoves; // the number of moves made to reach the board
		private Board board;
		private SearchNode previousNode = null; // previous search node
	};
	
	public boolean isSolvable() {
		return false; // is the initial board solvable?
	};

	public int moves() {
		return 0; // min number of moves to solve initial board; -1 if no solution
	};

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if no solution
		SearchNode startSN = constructInitialSN();
		
		return solutionPath; 
	};
	
	// ------ PRIVATE METHODS
	private SearchNode constructInitialSN() {
		SearchNode searchNode = new SearchNode();
		searchNode.searchNodeMoves = 0;
		searchNode.board = this.initialBoard;
		return searchNode;
	}

	public static void main(String[] args) { // solve a slider puzzle (given below)
		// create initial board from file
		// In in = new In(args[0]);
		System.out.println("SOLVER UNIT TEST");
		In in = new In(INPUT);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);
		System.out.println("Board dimension -> " + initial.toString());
		System.out.println("Hamming sum -> " + initial.hamming());
		System.out.println("Manhattan sum -> " + initial.manhattan());

		// solve the puzzle
		// Solver solver = new Solver(initial);

		// print solution to standard output
//		if (!solver.isSolvable())
//			StdOut.println("No solution possible");
//		else {
//			StdOut.println("Minimum number of moves = " + solver.moves());
//			for (Board board : solver.solution())
//				StdOut.println(board);
//		}
	}
}
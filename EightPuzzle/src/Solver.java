import java.util.Iterator;

/**
 * @author Erik
 * 
 */
public class Solver {
	
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt"; // 2x2
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle04.txt"; // 3x3 (example from assignment paper)
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle36.txt"; // 10x10
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle3x3-unsolvable.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/a.txt";
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle11.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle20.txt";
	
	private int moves; // number of moves made for this particular puzzle
	private Board initialBoard; // contains Board received from Solver constructor
//	private Board twinBoard; // contains a twin of initialBoard used to detect solution infeasibilty
	private MinPQ<SearchNode> openSet; // priority queue of tentative nodes to be evaluated, initially containing the start search node
//	private SET<SearchNode> closedSet; // set of visited search nodes
//	private MinPQ<SearchNode> twinSet; // used to detect infeasible puzzles
	private SearchNode finalSearchNode;
	private Queue<Board> solutionPath;
	
	private boolean solvable = true;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		this.initialBoard = initial;
		this.openSet = new MinPQ<SearchNode>();
//		this.closedSet = new SET<SearchNode>();
//		this.twinSet = new MinPQ<SearchNode>();
//		this.twinBoard = initialBoard.twin();
		this.moves = 1;
		solvePuzzle();
	};
	
	private class SearchNode implements Comparable<SearchNode> {
		private int searchNodeMoves; // the number of moves made to reach the board
		private Board board;
		private SearchNode previousNode; // previous search node
		
		public SearchNode(int moves, Board board, SearchNode previousNode) {
			this.searchNodeMoves = moves;
			this.board = board;
			this.previousNode = previousNode;
		}
		
		@Override
		public int compareTo(SearchNode that) {
			int thisPriority = this.board.manhattan() + searchNodeMoves;
			int thatPriority = that.board.manhattan() + searchNodeMoves + 1;
			
			if (thisPriority < thatPriority) 
				return -1;
			if (thisPriority > thatPriority) 
				return 1;
			return 0;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
//		@Override
//		public String toString() {
//			return "SearchNode [searchNodeMoves=\n" + searchNodeMoves + ",\n board=" + board.toString() + ",\n previousNode=\n" + previousNode.board.toString() + "]";
//		}
	};
	
	public boolean isSolvable() { // is the initial board solvable?
		return solvable; 
	};

	public int moves() { // min number of moves to solve initial board; -1 if no solution
		if (!solvable) return -1;
		return moves;
	};

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if no solution
		moves = getSolutionMoves(finalSearchNode);
		if (!solvable) return null;
		
		if (solutionPath == null) {
			solutionPath = new Queue<Board>();
			getSolutionBoards(finalSearchNode);
		}
		return solutionPath; 
	};
	
	// ------ PRIVATE METHODS
	private void solvePuzzle() {
		SearchNode dequeuedNode = null;
		
		if (openSet.isEmpty()) { // inital node construction
			dequeuedNode = new SearchNode(0, initialBoard, null); 
			openSet.insert(dequeuedNode);
//			openSet.insert(new SearchNode(0, twinBoard, null));
		}
		
		while (!openSet.isEmpty()) {
			dequeuedNode = openSet.delMin(); // remove least-cost board
			if (dequeuedNode.board.isGoal()) { // puzzle solving is complete
				finalSearchNode = dequeuedNode;
				solution();
				return;
			}
			
//			StdOut.print("---> Selected path to solution at move " + dequeuedNode.searchNodeMoves + " <--- \n");
//			printPath(dequeuedNode);
//			StdOut.println("-------> END SELECT <-------");
			
			Iterator<Board> neighbours = dequeuedNode.board.neighbors().iterator(); // get neighbours of currently de-queued node
			while (neighbours.hasNext()) {
				Board neighbourBoard = neighbours.next();
				int neighbourMove = dequeuedNode.searchNodeMoves + 1;
				
				SearchNode neighbourNode = new SearchNode(neighbourMove, neighbourBoard, dequeuedNode);
				
//				printPath(neighbourNode);
				
				if (dequeuedNode.previousNode != null && neighbourNode.board.equals(dequeuedNode.previousNode.board)) {
//					StdOut.println("Neighbour node SKIPPED and NOT added to PQ!");
					continue;
				}
				openSet.insert(neighbourNode);
//				StdOut.println("Neighbour node added to priority queue.");
//				StdOut.println();
			}
			
//			StdOut.println("======== CURRENT OPEN SET STATE ==========");
//			
//			for (SearchNode sn : openSet) {
//				printPath(sn);
//			}
//			
//			StdOut.println("********************* STEP " + dequeuedNode.searchNodeMoves + " COMPLETED *****************");
		}
	}
	
	/**
	 * @param searchNode
	 * @return
	 */
	private int getSolutionMoves(SearchNode searchNode) {
		if (searchNode.previousNode == null) {
//			if (searchNode.board.equals(twinBoard)) {
//				solvable = false;
//			}
			return 0;
		}
		return 1 + getSolutionMoves(searchNode.previousNode);
	}
	
	/**
	 * @param searchNode
	 */
	private void getSolutionBoards(SearchNode searchNode) {
		if (searchNode.previousNode != null) {
			getSolutionBoards(searchNode.previousNode);
		}
		solutionPath.enqueue(searchNode.board);
	}
	
	private void printPath(SearchNode node) {
		StdOut.print("Priority = " + (node.board.manhattan() + node.searchNodeMoves) + "\n");
		StdOut.print("Moves = " + node.searchNodeMoves + "\n");
		StdOut.print("Manhattan = " + node.board.manhattan() + "\n");
		StdOut.print(node.board.toString());
	}

	public static void main(String[] args) { // solve a slider puzzle (given below)
		// create initial board from file
//		In in = new In(args[0]);
//		System.out.println("SOLVER UNIT TEST");
		In in = new In(INPUT);
		int N = in.readInt();
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				blocks[i][j] = in.readInt();
		Board initial = new Board(blocks);
//		System.out.println("Board dimension -> " + initial.toString());
//		System.out.println("Hamming sum -> " + initial.hamming());
//		System.out.println("Manhattan sum -> " + initial.manhattan());
//		System.out.println("*** STARTING SOLUTION ***");

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
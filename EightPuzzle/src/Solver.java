import java.util.Iterator;

/**
 * @author Erik
 * 
 */
public class Solver {
	
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt"; // 2x2
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle04.txt"; // 3x3 (example from assignment paper)
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle07.txt"; // 10x10
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle3x3-impossible.txt";
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
		solvePuzzle();
	};
	
	private class SearchNode implements Comparable<SearchNode> {
		private int searchNodeMoves; // the number of moves made to reach the board
		private Board board;
		private SearchNode previousNode; // previous search node
		
		@Override
		public int compareTo(SearchNode that) {
			int thisCost = this.board.manhattan() + searchNodeMoves;
			int thatCost = that.board.manhattan() + searchNodeMoves;
			if (thisCost < thatCost) 
				return -1;
			if (thisCost > thatCost) 
				return 1;
			return 0;
		}
	};
	
	public boolean isSolvable() {
		return true; // is the initial board solvable?
	};

	public int moves() {
		return moves; // min number of moves to solve initial board; -1 if no solution
	};

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if no solution
		StdOut.println("Returning solution....");
		return solutionPath; 
	};
	
	// ------ PRIVATE METHODS
	private void solvePuzzle() {
		if (moves == 0) { // construct initial search node
			SearchNode startSN = constructSearchNode(0, initialBoard, null); 
			openSet.insert(startSN);
		}
		
		SearchNode leastCostNode = null;
		
		do { // while solution is not found...
			leastCostNode = openSet.delMin(); // dequeue node with least cost
			if (leastCostNode == null) throw new IllegalStateException("Dequeued a null node from priority queue!");
			
			StdOut.print("Selected path to solution at move " + moves + " ...\n");
			printPath(leastCostNode);
			StdOut.println();
			
			solutionPath.enqueue(leastCostNode.board); // mark this node's board as part of solution
			
			Iterable<Board> neighbours = leastCostNode.board.neighbors(); 
			Iterator<Board> neighboursIterator = neighbours.iterator(); // generate all least-cost node neighbours and enqueue them on the priority queue
			StdOut.println("Printing neighbours....");
			while (neighboursIterator.hasNext()) {
				Board neighbourBoard = neighboursIterator.next();
				// critical optimization - to reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node,
				// don't enqueue a neighbor if its board is the same as the board of the previous search node
				if (!leastCostNode.board.equals(neighbourBoard)) { 
					SearchNode searchNode = constructSearchNode(moves, neighbourBoard, leastCostNode);
					printPath(searchNode);
					StdOut.println();
					openSet.insert(searchNode);
				} else {
					System.out.println("CRIT/OPT -- LC Node board -> " + leastCostNode.board.toString() + " || Neighbour board -> " + neighbourBoard.toString());
				}
			}
			moves++;
			StdOut.println("********************* STEP " + moves + " COMPLETED *****************");
		} while (!leastCostNode.board.isGoal());
	}
	
	private void printPath(SearchNode node) {
		StdOut.print("Priority = " + (node.board.manhattan() + moves) + "\n");
		StdOut.print("Moves = " + moves + "\n");
		StdOut.print("Manhattan = " + node.board.manhattan() + "\n");
		StdOut.print(node.board.toString());
	}
	
	private SearchNode constructSearchNode(int moves, Board board, SearchNode previousNode) {
		SearchNode searchNode = new SearchNode();
		searchNode.searchNodeMoves = moves;
		searchNode.board = board;
		searchNode.previousNode = previousNode;
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
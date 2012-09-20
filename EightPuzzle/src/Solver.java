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
	
	private int moves; // number of moves made for this particular puzzle
	private Board initialBoard; // contains Board received from Solver constructor
	private Board twinBoard; // contains a twin of initialBoard used to detect solution infeasibilty
	private MinPQ<SearchNode> openSet; // the set of tentative nodes to be evaluated, initially containing the start search node
	private MinPQ<SearchNode> twinSet; // used to detect infeasible puzzles
	private SearchNode finalSearchNode;
	private Queue<Board> solutionPath;
	
	private boolean solvable = true;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		this.initialBoard = initial;
		this.openSet = new MinPQ<SearchNode>();
		this.twinSet = new MinPQ<SearchNode>();
//		this.solutionPath = new Queue<Board>();
		this.twinBoard = initial.twin();
		this.moves = 1;
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

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
//		@Override
//		public String toString() {
//			return "SearchNode [searchNodeMoves=" + searchNodeMoves + ",\n board=" + board.toString() + ",\n previousNode=" + previousNode.board.toString() + "]";
//		}
	};
	
	public boolean isSolvable() { // is the initial board solvable?
		return solvable; 
	};

	public int moves() { // min number of moves to solve initial board; -1 if no solution
		if (!solvable) return -1;
		return getSolutionMoves(finalSearchNode);
	};

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if no solution
		if (!solvable) return null;
		solutionPath = new Queue<Board>();
		getSolutionBoards(finalSearchNode);
		return solutionPath; 
	};
	
	// ------ PRIVATE METHODS
	private void solvePuzzle() {
		if (moves == 1) { // construct initial search nodes
			SearchNode startSN = constructSearchNode(0, initialBoard, null); 
			openSet.insert(startSN);
//			SearchNode startTwinSN = constructSearchNode(0, twinBoard, null);
//			twinSet.insert(startTwinSN);
		}
		
		SearchNode leastCostNode = null;
//		SearchNode twinLeastCostNode = null;
		
		do { // while solution is not found...
			leastCostNode = openSet.delMin(); // dequeue node with least cost
//			twinLeastCostNode = twinSet.delMin(); 
			
			if (leastCostNode == null) throw new IllegalStateException("Dequeued a null node from priority queue!");
//			if (twinLeastCostNode == null) throw new IllegalStateException("Dequeued a null twin node from priority queue!");
			
			if (leastCostNode.board.isGoal()) { // solution found, so save the search node so that we can reconstruct solution
				finalSearchNode = leastCostNode;
			}
			
			// check if twin board is actually the solution; if it is, then the board solution is infeasible
//			if (twinLeastCostNode.board.isGoal()) {
//				solvable = false;
//				break;
//			}
			
			StdOut.print("---> Selected path to solution at move " + moves + " <--- \n");
			printPath(leastCostNode);
			StdOut.println("-------> END SELECT <-------");
			
			// advance target board solution
			Iterator<Board> neighboursIterator = leastCostNode.board.neighbors().iterator(); // generate all least-cost node neighbours and enqueue them on the priority queue
//			StdOut.println("Printing neighbours....");
			while (neighboursIterator.hasNext()) {
				Board neighbourBoard = neighboursIterator.next();
				// critical optimization - to reduce unnecessary exploration of useless search nodes, when considering the neighbors of a search node,
				// don't enqueue a neighbor if its board is the same as the board of the previous search node
				if (leastCostNode.previousNode == null) {
					SearchNode searchNode = constructSearchNode(moves, neighbourBoard, leastCostNode);
					openSet.insert(searchNode);
				} else if (!leastCostNode.previousNode.board.equals(neighbourBoard)) { 
					SearchNode searchNode = constructSearchNode(moves, neighbourBoard, leastCostNode);
//					printPath(searchNode);
//					StdOut.println();
					openSet.insert(searchNode);
				} 
			}
			
//			StdOut.println("*****");
//			StdOut.print("Selected TWIN path to solution at move " + moves + " ...\n");
//			printPath(twinLeastCostNode);
//			StdOut.println();
			
			// advance twin board solution
//			Iterator<Board> twinNeighboursIterator = twinLeastCostNode.board.neighbors().iterator();
//			StdOut.println("Printing TWIN neighbours....");
//			while (twinNeighboursIterator.hasNext()) {
//				Board twinNeighbourBoard = twinNeighboursIterator.next();
//				if (twinLeastCostNode.previousNode == null) {
//					SearchNode twinSearchNode = constructSearchNode(moves, twinNeighbourBoard, twinLeastCostNode);
//					printPath(twinSearchNode);
//					StdOut.println();
//					twinSet.insert(twinSearchNode);
//				} else if (!twinLeastCostNode.previousNode.board.equals(twinNeighbourBoard)) {
//					SearchNode twinSearchNode = constructSearchNode(moves, twinNeighbourBoard, twinLeastCostNode);
//					printPath(twinSearchNode);
//					StdOut.println();
//					twinSet.insert(twinSearchNode);
//				}
//			}
			
			for (SearchNode sn : openSet) {
				printPath(sn);
			}
			StdOut.println("********************* STEP " + moves + " COMPLETED *****************");
			
			moves++;
		} while (!leastCostNode.board.isGoal() || solvable == false);
	}
	
	private void printPath(SearchNode node) {
		StdOut.print("Priority = " + (node.board.manhattan() + node.searchNodeMoves) + "\n");
		StdOut.print("Moves = " + node.searchNodeMoves + "\n");
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
	
	private int getSolutionMoves(SearchNode searchNode) {
		if (searchNode.previousNode == null) return 0;
		return 1 + getSolutionMoves(searchNode.previousNode);
	}
	
	private void getSolutionBoards(SearchNode searchNode) {
		if (searchNode.previousNode != null) {
			getSolutionBoards(searchNode.previousNode);
		}
		solutionPath.enqueue(searchNode.board);
	}

	public static void main(String[] args) { // solve a slider puzzle (given below)
		// create initial board from file
		// In in = new In(args[0]);
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
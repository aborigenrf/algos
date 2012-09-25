import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.TreeSet;

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
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle17.txt";
	private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle05.txt";
	//private static final String INPUT = "/Dev/git_repo/algorithms_repo/algos/z-algs4-common/data-sets/8puzzle/puzzle20.txt";
	
	private int moves; // number of moves made for this particular puzzle
	private Board initialBoard; // contains Board received from Solver constructor
//	private Board twinBoard; // contains a twin of initialBoard used to detect solution infeasibilty
	private MinPQ<SearchNode> openSet; // priority queue of tentative nodes to be evaluated, initially containing the start search node
//	private SET<SearchNode> closedSet; // set of visited search nodes
//	private MinPQ<SearchNode> twinSet; // used to detect infeasible puzzles
	private TreeSet<SearchNode> cameFrom;
	private SearchNode finalSearchNode;
	private Queue<Board> solutionPath;
	
	private boolean solvable = true;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		this.initialBoard = initial;
		this.openSet = new MinPQ<SearchNode>();
//		this.closedSet = new SET<SearchNode>();
//		this.twinSet = new MinPQ<SearchNode>();
//		this.twinBoard = initialBoard.twin();
		this.cameFrom = new TreeSet<SearchNode>();
		this.moves = 0;
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
			int thisNode = this.board.manhattan() + searchNodeMoves;
			int thatNode = that.board.manhattan() + searchNodeMoves + 1;
			
			if (thisNode < thatNode) 
				return -1;
			if (thisNode > thatNode) 
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
		if (solvable == false) return false;
		int parity = 0;
		int N = initialBoard.dimension();
		int[][] tiles = null;
		try {
			Field f = initialBoard.getClass().getDeclaredField("tiles");
			f.setAccessible(true);
			tiles = (int[][]) f.get(initialBoard);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		int endOffset = mapToVector(N - 1, N - 1);
		for (int i = 1; i <= endOffset; i++) {
			int[] coord = mapFromVector(i);
			int row = coord[0];
			int col = coord[1];
//			System.out.println("For offset [" + i + "] coordinates are -> [" + row + ", " + col + "]");
			int pivotValue = tiles[row][col];
			for (int j = (i - 1); j >= 0; j--) {
				int[] currentCoord = mapFromVector(j);
				int r = currentCoord[0];
				int c = currentCoord[1];
				int currentValue = tiles[r][c];
//				System.out.println("Pivot value -> " + pivotValue + "; Current value -> " + currentValue);
				if (currentValue > pivotValue){
					parity++;
//					StdOut.println("Current < Pivot - parity increase");
				}
				if (currentValue == 0) { // add 1 to parity if zero-tile is in row 0 or 2 (and not in 1 or 3)
					if (r % 2 == 0) {
						parity++;
//						StdOut.println("Zero tile in even row -> " + r + "; parity increase!");
					}
				}
			}
		}

		solvable = ((parity % 2) == 0); // if disorder is even, board is solvable
		return solvable; 
	};
	
	private int mapToVector(int row, int col) {
		int N = initialBoard.dimension();
		int offset = (row * N + col);
		return offset;
	}
	
	private int[] mapFromVector(int offset) {
		int[] array = new int[2];
		int N = initialBoard.dimension();
		int row = (int)(offset / N);
		int col = offset - (row * N);
		array[0] = row;
		array[1] = col;
		return array;
	}

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
		if (!isSolvable()) {
			solvable = false;
			return;
		}
		if (openSet.isEmpty()) { // inital node construction
			dequeuedNode = new SearchNode(0, initialBoard, null); 
			openSet.insert(dequeuedNode);
//			openSet.insert(new SearchNode(0, twinBoard, null));
		}
		
		while (!openSet.isEmpty()) {
			dequeuedNode = openSet.delMin(); // remove least-cost board
			if (dequeuedNode.board.isGoal()) { // puzzle solving is complete
				cameFrom.add(dequeuedNode);
				finalSearchNode = dequeuedNode;
				solution();
				break;
			}
			
//			StdOut.print("---> Selected path to solution at move " + dequeuedNode.searchNodeMoves + " <--- \n");
//			printPath(dequeuedNode);
//			StdOut.println("-------> END SELECT <-------");
			
			Iterator<Board> neighbours = dequeuedNode.board.neighbors().iterator(); // get neighbours of currently de-queued node
			
			while (neighbours.hasNext()) {
				Board neighbourBoard = neighbours.next();
				
				if (dequeuedNode.previousNode != null && neighbourBoard.equals(dequeuedNode.previousNode.board)) {
					continue;
				}
				int neighbourMove = dequeuedNode.searchNodeMoves + 1;
				SearchNode neighbourNode = new SearchNode(neighbourMove, neighbourBoard, dequeuedNode);
				openSet.insert(neighbourNode);
//				printPath(neighbourNode);
				cameFrom.add(dequeuedNode);
				
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
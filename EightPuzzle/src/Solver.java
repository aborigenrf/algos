import java.util.Iterator;
import java.util.TreeSet;

/**
 * @author Erik
 * 
 */
public class Solver {
	
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt"; // 2x2
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle04.txt"; // 3x3 (example from assignment paper)
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle36.txt"; // 10x10
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle3x3-unsolvable.txt";
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/a.txt";
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle17.txt";
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle05.txt";
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle20.txt";
	//private static final String INPUT = "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle38.txt";
	
	private int moves; // number of moves made for this particular puzzle
	private Board initialBoard; // contains Board received from Solver constructor
	private MinPQ<SearchNode> openSet; // priority queue of tentative nodes to be evaluated, initially containing the start search node
//	private SET<SearchNode> closedSet; // set of visited search nodes
	private TreeSet<SearchNode> cameFrom;
	private SearchNode finalSearchNode;
	private Queue<Board> solutionPath;
	
	private int N; // board dimension
	private int[][] tiles = null; // stores values parsed from initialBoard
	private int[] zeroCoord = null; // stores coordinates of blank element
	
	private boolean solvable = true;

	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		this.initialBoard = initial;
		this.openSet = new MinPQ<SearchNode>();
//		this.closedSet = new SET<SearchNode>();
		this.cameFrom = new TreeSet<SearchNode>();
		this.moves = 0;
		
		this.N = initialBoard.dimension();
		this.tiles = new int[N][N];
		this.zeroCoord = new int[2];
		parseBoard();
		
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
	};
	
	public boolean isSolvable() { // is the initial board solvable?
		if (solvable == false) return false;
		int parity = 0;
		
		int endOffset = mapToVector(N - 1, N - 1);
		for (int i = 1; i <= endOffset; i++) {
			int[] coord = mapFromVector(i);
//			int row = coord[0];
//			int col = coord[1];
//			System.out.println("For offset [" + i + "] coordinates are -> [" + row + ", " + col + "]");
			int pivotValue = tiles[coord[0]][coord[1]];
			if (pivotValue == 0) continue;
			for (int j = (i - 1); j >= 0; j--) {
				int[] currentCoord = mapFromVector(j);
//				int r = currentCoord[0];
//				int c = currentCoord[1];
				int currentValue = tiles[currentCoord[0]][currentCoord[1]];
//				System.out.println("Pivot value -> " + pivotValue + "; Current value -> " + currentValue);
				if (pivotValue < currentValue){
					parity++;
//					StdOut.println("Pivot < Current - parity increase. Parity is -> " + parity);
				}
			}
		}
		if ((zeroCoord[0] & 1) == 0) { // add 1 to parity if zero-tile is in row 0 or 2 (and not in 1 or 3)
			if ((N & 1) == 0) { // thank you, Svetlin Zarev, you rule!!
				parity++;
//				StdOut.println("Zero coordinate detected at [" + zeroCoord[0] + ", " + zeroCoord[1] + " - increasing parity.");
			}
		}

//		StdOut.println("Final parity *** --> " + parity);
		solvable = ((parity & 1) == 0); // if disorder is even, board is solvable
		return solvable; 
	};
	
	public int moves() { // min number of moves to solve initial board; -1 if no solution
		if (!solvable) return -1;
		moves = getSolutionMoves(finalSearchNode);
		return moves;
	};

	public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if no solution
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
	
	private void printPath(SearchNode node) {
		StdOut.print("Priority = " + (node.board.manhattan() + node.searchNodeMoves) + "\n");
		StdOut.print("Moves = " + node.searchNodeMoves + "\n");
		StdOut.print("Manhattan = " + node.board.manhattan() + "\n");
		StdOut.print(node.board.toString());
	}
	
	private void parseBoard() {
		String boardString = initialBoard.toString();
		String delim = " ";
		String[] tokens = boardString.split(delim);
		Integer[] payload = new Integer[N*N];
		
		int k = 0;
		boolean fixed = false;
		
		for (int i = 1; i < tokens.length; i++) {
			String element = tokens[i];
			
			if (i == 1 && !fixed) { // handle glued chars at the beginning of txt file (see puzzle38 and puzzle43)
				String[] target = tokens[0].split("\n");
				if (target.length > 1) {
					element = target[1];
					fixed = true; // an ugly hack to facilitate a single, i-counter repeat (since we don't want to skip over a number when we handle this gluing
					i--; // !!
				}
			}
			
			if (!element.isEmpty() && !element.equals("\n")) {
				payload[k++] = Integer.parseInt(element.trim());
			}
		}
		
		for (int i = 0; i < payload.length; i++) {
			int[] coords = mapFromVector(i);
			int row = coords[0];
			int col = coords[1];
			
			int value = payload[i];
			if (value == 0) { // save zero element coordinates; by not assigning zero to tiles, save an array access
				zeroCoord[0] = row;
				zeroCoord[1] = col;
			} else {
				tiles[row][col] = value;
			}
		}
	}

	public static void main(String[] args) { // solve a slider puzzle (given below)
		// create initial board from file
		In in = new In(args[0]);
//		System.out.println("SOLVER UNIT TEST");
//		In in = new In(INPUT);
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
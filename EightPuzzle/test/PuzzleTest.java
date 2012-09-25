public class PuzzleTest
{
    private static String[] tests = {
    	"/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle00.txt",
    	"/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle01.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle02.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle03.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle04.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle05.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle06.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle07.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle08.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle09.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle10.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle11.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle12.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle13.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle14.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle15.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle16.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle17.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle18.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle19.txt", 
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle20.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle21.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle22.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle23.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle24.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle25.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle26.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle27.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle28.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle29.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle30.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle31.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle32.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle33.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle34.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle35.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle36.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle37.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle38.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle39.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle40.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle41.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle42.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle43.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle44.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle45.txt",
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle4x4-hard1.txt", // 38 moves
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle4x4-hard2.txt",  // 47 moves
        "/Dev/workspaces/workspace_juno/Algorithms/algos/z-algs4-common/data-sets/8puzzle/puzzle3x3-unsolvable.txt"
    };

    public static void main(String[] args) {

        String path = "";
        if (args.length > 0) path = args[0] + "\\";
        Stopwatch stopWatch = new Stopwatch();
        for (String test : tests) {
            solve(path + test);
        }
        double time = stopWatch.elapsedTime();
        StdOut.println("Total time -> " + time);
    }

    private static Board load(String filename) {
        // create board from file
        In in = new In(filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        return new Board(blocks);
    }

    private static void solve(String filename) {

        Board initial = load(filename);

        // solve the puzzle
        Stopwatch w = new Stopwatch();
        Solver solver = new Solver(initial);
        double t = w.elapsedTime();

        StdOut.printf("%s \t%d \t%f \n" ,  filename, solver.moves(), t);
    }
}
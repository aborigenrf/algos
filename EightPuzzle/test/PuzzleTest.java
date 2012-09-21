public class PuzzleTest
{
    private static String[] tests = { 
        "puzzle20.txt",
        "puzzle21.txt",
        "puzzle22.txt",
        "puzzle23.txt",
        "puzzle24.txt",
        "puzzle25.txt",
        "puzzle26.txt",
        "puzzle27.txt",
        "puzzle28.txt",
        "puzzle29.txt",
        "puzzle30.txt",
        "puzzle31.txt",
        "puzzle34.txt",
        "puzzle35.txt",
        "puzzle37.txt",
        "puzzle39.txt",
        "puzzle41.txt",
        "puzzle44.txt" 
    };

    public static void main(String[] args) {

        String path = "";
        if (args.length > 0) path = args[0] + "\\";
        for (String test : tests) {
            solve(path + test);
        }
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
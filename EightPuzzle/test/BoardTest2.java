import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BoardTest2 {

    public static int[] GOAL_BLOCKS = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    public static Board GOAL_BOARD = buildBoard(GOAL_BLOCKS);

    @Test
    public void testDimension() {
        for (int N = 2; N < 20; N++) {
            Board board = buildRandomBoard(N);
            assertEquals(N, board.dimension());
        }
    }

    @Test
    public void testHamming() {
        assertEquals(0, GOAL_BOARD.hamming());
        // As per: http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
        int[] testBlocks = {8, 1, 3, 4, 2, 0, 7, 6, 5};
        Board testBoard = buildBoard(testBlocks);
        assertEquals(5, testBoard.hamming());
    }

    @Test
    public void testManhattan() {
        Board testBoard;

        // Goal Board
        assertEquals(0, GOAL_BOARD.manhattan());

        // As per: http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
        testBoard = buildBoard(new int[]{8, 1, 3, 4, 0, 2, 7, 6, 5});
        assertEquals(10, testBoard.manhattan());

        // As per actual grading script, 2x2
        testBoard = buildBoard(new int[]{3, 0, 2, 1});
        assertEquals(5, testBoard.manhattan());

        // As per actual grading script, 3x3
        testBoard = buildBoard(new int[]{8, 2, 4, 0, 7, 5, 6, 1, 3});
        assertEquals(17, testBoard.manhattan());

        // As per actual grading script, 4x4
        testBoard = buildBoard(new int[]{9, 0, 5, 7, 12, 15, 14, 13, 1, 6, 3, 2, 11, 4, 8, 10});
        assertEquals(45, testBoard.manhattan());

        // As per actual grading script, 5x5
        testBoard = buildBoard(new int[]{12, 9, 8, 10, 18, 1, 14, 3, 19, 0, 7, 15, 6, 23, 24, 17, 4, 16, 20, 11, 21, 5, 22, 2, 13});
        assertEquals(67, testBoard.manhattan());

        // As per actual grading script, 9x9
        testBoard = buildBoard(new int[]{1, 59, 51, 18, 47, 66, 44, 32, 31, 50, 65, 54, 13, 74, 56, 30, 79, 14, 12, 8, 75, 11, 76, 72, 4, 37, 3, 5, 71, 61, 53, 21, 33, 16, 7, 64, 23, 15, 25, 28, 46, 68, 78, 70, 58, 69, 36, 40, 77, 39, 9, 55, 45, 19, 43, 0, 29, 67, 57, 41, 2, 63, 73, 27, 52, 22, 60, 20, 6, 34, 17, 38, 10, 24, 49, 42, 80, 62, 26, 48, 35});
        assertEquals(489, testBoard.manhattan());

        // As per actual grading script, 10x10
        testBoard = buildBoard(new int[]{59, 13, 66, 61, 25, 69, 63, 98, 62, 91, 72, 35, 49, 20, 24, 50, 45, 67, 38, 11, 93, 21, 8, 5, 4, 22, 82, 81, 47, 1, 87, 19, 95, 86, 40, 43, 80, 92, 12, 58, 53, 74, 70, 23, 31, 15, 85, 14, 60, 48, 3, 29, 2, 44, 17, 96, 16, 75, 42, 73, 78, 39, 56, 94, 65, 30, 89, 90, 64, 26, 84, 99, 83, 54, 33, 52, 77, 34, 46, 0, 76, 88, 32, 10, 41, 28, 9, 79, 97, 6, 55, 36, 7, 71, 68, 18, 51, 57, 37, 27,});
        assertEquals(658, testBoard.manhattan());
    }

    @Test
    public void testIsGoal() {
        assertTrue(GOAL_BOARD.isGoal());
        assertFalse(buildRandomBoard(2).isGoal());
        assertFalse(buildRandomBoard(3).isGoal());
        assertFalse(buildRandomBoard(4).isGoal());
        assertFalse(buildRandomBoard(5).isGoal());
        assertFalse(buildRandomBoard(6).isGoal());
        assertFalse(buildRandomBoard(7).isGoal());
        assertFalse(buildRandomBoard(8).isGoal());
        assertFalse(buildRandomBoard(9).isGoal());
        assertFalse(buildRandomBoard(10).isGoal());
    }

    @Test
    // My twin method simply alternates the first two available non-zero blocks.
    public void testTwin() {
        // Simple Case
        int[] expectedBlocks = {2, 1, 3, 4, 5, 6, 7, 8, 0};
        Board expectedBoard = buildBoard(expectedBlocks);
        assertEquals(expectedBoard, GOAL_BOARD.twin());
        assertEquals(expectedBoard.twin(), GOAL_BOARD);

        // A bit more complicated (small, and starts with 0, switch is made on last row)
        int[] smallOriginalBlocks = {0, 2, 3, 1};
        Board smallOriginal = buildBoard(smallOriginalBlocks);
        int[] smallExpectedBlocks = {0, 2, 1, 3};
        Board smallExpected = buildBoard(smallExpectedBlocks);
        assertEquals(smallExpected, smallOriginal.twin());
    }

    @Test
    public void testEquals() {
        assertFalse(GOAL_BOARD.equals(null));
        assertFalse(GOAL_BOARD.equals(GOAL_BOARD.dimension()));
        assertTrue(GOAL_BOARD.equals(GOAL_BOARD));
        assertTrue(GOAL_BOARD.equals(buildBoard(GOAL_BLOCKS)));
    }

    @Test
    public void testNeighbours() {
        Board startBoard = null;
        List<Board> expectedBoards = null;
        List<Board> neighbors = null;

        // Normal, Middle
        startBoard = buildBoard(new int[]{1, 2, 3, 4, 0, 5, 6, 7, 8});
        expectedBoards = Arrays.asList(new Board[]{buildBoard(new int[]{1, 0, 3, 4, 2, 5, 6, 7, 8}),
                buildBoard(new int[]{1, 2, 3, 4, 7, 5, 6, 0, 8}),
                buildBoard(new int[]{1, 2, 3, 0, 4, 5, 6, 7, 8}),
                buildBoard(new int[]{1, 2, 3, 4, 5, 0, 6, 7, 8})});
        neighbors = getAll(startBoard.neighbors());
        assertEquals(expectedBoards.size(), neighbors.size());
        assertTrue(neighbors.containsAll(expectedBoards));

        // Small, Top-Left Corner
        startBoard = buildBoard(new int[]{0, 1, 2, 3});
        expectedBoards = Arrays.asList(new Board[]{buildBoard(new int[]{1, 0, 2, 3}), buildBoard(new int[]{2, 1, 0, 3})});
        neighbors = getAll(startBoard.neighbors());
        assertEquals(expectedBoards.size(), neighbors.size());
        assertTrue(neighbors.containsAll(expectedBoards));

        // Small, Top-Right Corner
        startBoard = buildBoard(new int[]{1, 0, 2, 3});
        expectedBoards = Arrays.asList(new Board[]{buildBoard(new int[]{0, 1, 2, 3}), buildBoard(new int[]{1, 3, 2, 0})});
        neighbors = getAll(startBoard.neighbors());
        assertEquals(expectedBoards.size(), neighbors.size());
        assertTrue(neighbors.containsAll(expectedBoards));

        // Small, Bottom-Left Corner
        startBoard = buildBoard(new int[]{1, 2, 0, 3});
        expectedBoards = Arrays.asList(new Board[]{buildBoard(new int[]{0, 2, 1, 3}), buildBoard(new int[]{1, 2, 3, 0})});
        neighbors = getAll(startBoard.neighbors());
        assertEquals(expectedBoards.size(), neighbors.size());
        assertTrue(neighbors.containsAll(expectedBoards));

        // Small, Bottom-Right Corner
        startBoard = buildBoard(new int[]{1, 2, 3, 0});
        expectedBoards = Arrays.asList(new Board[]{buildBoard(new int[]{1, 2, 0, 3}), buildBoard(new int[]{1, 0, 3, 2})});
        neighbors = getAll(startBoard.neighbors());
        assertEquals(expectedBoards.size(), neighbors.size());
        assertTrue(neighbors.containsAll(expectedBoards));
    }

    @Test
    public void testToString() {
        String expectedString = "3\n" +
                " 1  2  3 \n" +
                " 4  5  6 \n" +
                " 7  8  0 \n";
        assertEquals(expectedString, GOAL_BOARD.toString());
    }

    public static List<Board> getAll(Iterable<Board> boards) {
        List<Board> result = new ArrayList<Board>();
        for (Board board : boards) {
            //System.out.println(board.toString());
            result.add(board);
        }
        return result;
    }

    public static Board buildRandomBoard(int N) {
        int size = N * N;
        int[] blocks = new int[size];
        for (int i = 0; i < size; i++) {
            blocks[i] = i;
        }
        StdRandom.shuffle(blocks);
        return buildBoard(blocks);
    }

    public static Board buildBoard(int[] data) {
        int N = (int) Math.sqrt(data.length);
        int[][] blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int dataIndex = (i * N) + j;
                blocks[i][j] = data[dataIndex];
            }
        }

        Board result = new Board(blocks);
        //System.out.println("BUILD BOARD = " + result.toString());
        return result;
    }

}
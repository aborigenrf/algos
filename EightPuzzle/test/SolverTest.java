import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class SolverTest {

    @Test
    public void testExample() {
        // puzzle04.txt
        // 1  3           1  3           1  2  3        1  2  3        1  2  3
        // 4  2  5   =>   4  2  5   =>   4     5   =>   4  5      =>   4  5  6
        // 7  8  6        7  8  6        7  8  6        7  8  6        7  8

        Board startBoard = BoardTest2.buildBoard(new int[]{1, 3, 0, 4, 2, 5, 7, 8, 6});
        List<Board> expectedBoards = Arrays.asList(new Board[]{
                startBoard,
                BoardTest2.buildBoard(new int[]{1, 3, 0, 4, 2, 5, 7, 8, 6}),
                BoardTest2.buildBoard(new int[]{1, 2, 3, 4, 0, 5, 7, 8, 6}),
                BoardTest2.buildBoard(new int[]{1, 2, 3, 4, 5, 0, 7, 8, 6}),
                BoardTest2.GOAL_BOARD});

        Solver solver = new Solver(startBoard);
        assertTrue(solver.isSolvable());
        assertEquals(4, solver.moves());
        assertSolutionEquals(expectedBoards, solver);
    }

    @Test
    public void testAlreadyGoal() {
        List<Board> expectedBoards = Arrays.asList(new Board[]{BoardTest2.GOAL_BOARD});
        Solver solver = new Solver(BoardTest2.GOAL_BOARD);
        assertTrue(solver.isSolvable());
        assertEquals(0, solver.moves());
        assertSolutionEquals(expectedBoards, solver);
    }

    @Test
    public void testInfeasible() {
        Board startBoard;
        Solver solver;

        // Goal Board, Twined
        startBoard = BoardTest2.GOAL_BOARD.twin();
        solver = new Solver(startBoard);
        assertInfeasible(solver);

        // As per http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
        startBoard = BoardTest2.buildBoard(new int[]{1, 2, 3, 4, 5, 6, 8, 7, 0});
        solver = new Solver(startBoard);
        assertInfeasible(solver);
    }

    private void assertInfeasible(Solver solver) {
        assertEquals(-1, solver.moves());
        assertFalse(solver.isSolvable());
        assertEquals(null, solver.solution());
    }

    private void assertSolutionEquals(List<Board> expectedBoards, Solver solver) {
        List<Board> actualBoards = BoardTest2.getAll(solver.solution());
        assertEquals(expectedBoards.size(), actualBoards.size());
        assertTrue(actualBoards.containsAll(expectedBoards)); // TODO Test this better

//        System.out.println("EXPECTED....");
//        System.out.print(expectedBoards.toString());
//        System.out.println("ACTUAL....");
//        System.out.print(actualBoards.toString());

    }
}
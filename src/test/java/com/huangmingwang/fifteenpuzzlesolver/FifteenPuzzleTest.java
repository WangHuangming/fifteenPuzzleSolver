package com.huangmingwang.fifteenpuzzlesolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

public class FifteenPuzzleTest {

    @Test
    public void testCloneCreatesDeepCopy() {
        FifteenPuzzle original = new FifteenPuzzle();
        original.readBoardFromFile("sample.txt");

        // Clone the original puzzle
        FifteenPuzzle cloned = original.clone();

        // Verify that the cloned puzzle is not the same instance as the original
        assertNotSame(original, cloned);

        // Verify that the board arrays are not the same instance
        assertNotSame(original.getBoard(), cloned.getBoard());

        // Verify that the contents of the board arrays are the same
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(original.getBoard()[i][j], cloned.getBoard()[i][j]);
            }
        }

        // Modify the cloned board and verify that the original board is not affected
        cloned.setBoardValue(0, 0, "Z");
        assertNotEquals(original.getBoardValue(0, 0), cloned.getBoardValue(0, 0));
    }

    @Test
    public void testReadBoardFromFile() {
        FifteenPuzzle puzzle = new FifteenPuzzle();
        puzzle.readBoardFromFile("sample.txt");

        // Verify that the board was read correctly using accessors
        assertEquals("A", puzzle.getBoardValue(0, 0));
        assertEquals("B", puzzle.getBoardValue(0, 1));
        assertEquals("C", puzzle.getBoardValue(0, 2));
        assertEquals("D", puzzle.getBoardValue(0, 3));
        assertEquals("E", puzzle.getBoardValue(1, 0));
        assertEquals("F", puzzle.getBoardValue(1, 1));
        assertEquals("G", puzzle.getBoardValue(1, 2));
        assertEquals("H", puzzle.getBoardValue(1, 3));
        assertEquals("I", puzzle.getBoardValue(2, 0));
        assertEquals("J", puzzle.getBoardValue(2, 1));
        assertEquals("K", puzzle.getBoardValue(2, 2));
        assertEquals("L", puzzle.getBoardValue(2, 3));
        assertEquals("M", puzzle.getBoardValue(3, 0));
        assertEquals("N", puzzle.getBoardValue(3, 1));
        assertEquals("O", puzzle.getBoardValue(3, 2));
        assertEquals("-", puzzle.getBoardValue(3, 3));
    }

    @Test
    public void testHeuristicFunction() {
        FifteenPuzzle puzzle = new FifteenPuzzle();
        // String[][] board = {
        //     {"A", "C", "G", "D"},
        //     {"E", "B", "-", "H"},
        //     {"I", "F", "J", "K"},
        //     {"M", "N", "O", "L"}
        // };
        puzzle.setBoardValue(0, 0, "A");
        puzzle.setBoardValue(0, 1, "C");
        puzzle.setBoardValue(0, 2, "G");
        puzzle.setBoardValue(0, 3, "D");
        puzzle.setBoardValue(1, 0, "E");
        puzzle.setBoardValue(1, 1, "B");
        puzzle.setBoardValue(1, 2, "-");
        puzzle.setBoardValue(1, 3, "H");
        puzzle.setBoardValue(2, 0, "I");
        puzzle.setBoardValue(2, 1, "F");
        puzzle.setBoardValue(2, 2, "J");
        puzzle.setBoardValue(2, 3, "K");
        puzzle.setBoardValue(3, 0, "M");
        puzzle.setBoardValue(3, 1, "N");
        puzzle.setBoardValue(3, 2, "O");
        puzzle.setBoardValue(3, 3, "L");

        // Calculate the heuristic value
        int heuristicValue = puzzle.heuristicFunction();

        // Verify the heuristic value
        assertEquals(7, heuristicValue);
    }

    // @Test
    // public void testSearchAlg() {
    //     FifteenPuzzle puzzle = new FifteenPuzzle();
    //     puzzle.readBoardFromFile("sample.txt");

    //     // // Verify that the search algorithm finds the solution
    // }


}

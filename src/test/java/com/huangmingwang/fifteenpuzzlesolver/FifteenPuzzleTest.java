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
}

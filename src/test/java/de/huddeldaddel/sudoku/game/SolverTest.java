package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SolverTest {

    @Test
    public void testSolveBlankForward() {
        final Field field = new Field();
        Solver.solve(field, NumberSequenceFactory.ASCENDING, 0);
        final String expected =
                "1,2,3,4,5,6,7,8,9,4,5,6,7,8,9,1,2,3,7,8,9,1,2,3,4,5,6,2,1,4,3,6,5,8,9,7,3,6,5,8,9,7,2,1,4,8,9,7,2,1," +
                "4,3,6,5,5,3,1,6,4,2,9,7,8,6,4,2,9,7,8,5,3,1,9,7,8,5,3,1,6,4,2";
        assertEquals(expected, field.toString());
    }

    @Test
    public void testSolveBlankReverse() {
        final Field field = new Field();
        Solver.solve(field, NumberSequenceFactory.DESCENDING, 0);
        final String expected =
                "9,8,7,6,5,4,3,2,1,6,5,4,3,2,1,9,8,7,3,2,1,9,8,7,6,5,4,8,9,6,7,4,5,2,1,3,7,4,5,2,1,3,8,9,6,2,1,3,8,9," +
                "6,7,4,5,5,7,9,4,6,8,1,3,2,4,6,8,1,3,2,5,7,9,1,3,2,5,7,9,4,6,8";
        assertEquals(expected, field.toString());
    }

    @Test
    public void testSolveBlankRandomFieldsAreUnique() {
        final Field field1 = new Field();
        Solver.solve(field1, NumberSequenceFactory.RANDOM, 0);
        final Field field2 = new Field();
        Solver.solve(field2, NumberSequenceFactory.RANDOM, 0);
        assertNotEquals(field1.toString(), field2.toString());
    }

}

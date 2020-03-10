package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldGeneratorTest {

    @Test
    public void testGenerateFields() throws IOException {
        final Field completedField = new Field(new int[][]{
                {1, 2, 6, 3, 9, 4, 5, 7, 8},
                {4, 5, 9, 1, 8, 7, 3, 6, 2},
                {8, 7, 3, 5, 2, 6, 1, 9, 4},
                {2, 6, 1, 8, 5, 3, 9, 4, 7},
                {5, 4, 7, 9, 6, 1, 2, 8, 3},
                {9, 3, 8, 4, 7, 2, 6, 5, 1},
                {6, 8, 2, 7, 3, 9, 4, 1, 5},
                {7, 9, 4, 2, 1, 5, 8, 3, 6},
                {3, 1, 5, 6, 4, 8, 7, 2, 9}
        });
        final long count = new FieldGenerator().generateFields(completedField).count();
        assertNotEquals(0L, count);
    }

}

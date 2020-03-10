package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ColumnMixFilterTest {

    @Test
    public void testFilter() {
        final int[][] grid = {
                {9, 7, 5, 8, 3, 4, 2, 6, 1},
                {4, 3, 2, 6, 5, 1, 9, 7, 8},
                {1, 6, 8, 9, 7, 2, 4, 5, 3},
                {5, 9, 6, 7, 2, 8, 1, 3, 4},
                {8, 1, 4, 5, 6, 3, 7, 2, 9},
                {7, 2, 3, 1, 4, 9, 5, 8, 6},
                {3, 8, 1, 2, 9, 7, 6, 4, 5},
                {6, 4, 7, 3, 1, 5, 8, 9, 2},
                {2, 5, 9, 4, 8, 6, 3, 1, 7}
        };
        final Field inputField = new Field(grid);
        final List<Field> result = new ColumnMixFilter().filter(inputField).collect(Collectors.toList());
        assertEquals(6, result.size());
        assertEquals(inputField.toString(), result.get(0).toString());
        for(int i=1; i<6; i++)
            assertNotEquals(inputField.toString(), result.get(i).toString());
    }

    @Test
    public void testGetRowMix() {
        final int[][] grid = {
                {9, 7, 5, 8, 3, 4, 2, 6, 1},
                {4, 3, 2, 6, 5, 1, 9, 7, 8},
                {1, 6, 8, 9, 7, 2, 4, 5, 3},
                {5, 9, 6, 7, 2, 8, 1, 3, 4},
                {8, 1, 4, 5, 6, 3, 7, 2, 9},
                {7, 2, 3, 1, 4, 9, 5, 8, 6},
                {3, 8, 1, 2, 9, 7, 6, 4, 5},
                {6, 4, 7, 3, 1, 5, 8, 9, 2},
                {2, 5, 9, 4, 8, 6, 3, 1, 7}
        };
        final Field inputField = new Field(grid);
        final Field result = ColumnMixFilter.getColumnMix(inputField, List.of(2, 1, 0));
        final Field expected = new Field(new int[][]{
                {5, 7, 9, 4, 3, 8, 1, 6, 2 },
                {2, 3, 4, 1, 5, 6, 8, 7, 9 },
                {8, 6, 1, 2, 7, 9, 3, 5, 4 },
                {6, 9, 5, 8, 2, 7, 4, 3, 1 },
                {4, 1, 8, 3, 6, 5, 9, 2, 7 },
                {3, 2, 7, 9, 4, 1, 6, 8, 5 },
                {1, 8, 3, 7, 9, 2, 5, 4, 6 },
                {7, 4, 6, 5, 1, 3, 2, 9, 8 },
                {9, 5, 2, 6, 8, 4, 7, 1, 3 }
        });
        assertEquals(expected.toString(), result.toString());
    }

}

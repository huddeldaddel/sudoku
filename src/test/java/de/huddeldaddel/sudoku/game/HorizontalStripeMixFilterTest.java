package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HorizontalStripeMixFilterTest {

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
        final List<Field> result = new HorizontalStripeMixFilter().filter(inputField).collect(Collectors.toList());
        assertEquals(6, result.size());
        assertEquals(inputField.toString(), result.get(0).toString());
        for(int i=1; i<6; i++)
            assertNotEquals(inputField.toString(), result.get(i).toString());
    }

    @Test
    public void testGetHorizontalStripeMix() {
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
        final Field result = HorizontalStripeMixFilter.getHorizontalStripeMix(inputField, List.of(2, 1, 0));
        final Field expected = new Field(new int[][]{
                {3, 8, 1, 2, 9, 7, 6, 4, 5},
                {6, 4, 7, 3, 1, 5, 8, 9, 2},
                {2, 5, 9, 4, 8, 6, 3, 1, 7},
                {5, 9, 6, 7, 2, 8, 1, 3, 4},
                {8, 1, 4, 5, 6, 3, 7, 2, 9},
                {7, 2, 3, 1, 4, 9, 5, 8, 6},
                {9, 7, 5, 8, 3, 4, 2, 6, 1},
                {4, 3, 2, 6, 5, 1, 9, 7, 8},
                {1, 6, 8, 9, 7, 2, 4, 5, 3}
        });
        assertEquals(expected.toString(), result.toString());
    }

}

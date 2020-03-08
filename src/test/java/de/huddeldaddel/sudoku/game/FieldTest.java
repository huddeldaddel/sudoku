package de.huddeldaddel.sudoku.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.huddeldaddel.sudoku.game.Field;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTest {

    @Test
    public void testCompletedValidGrid() {
        final int[][] grid = {
                {8, 3, 5, 4, 1, 6, 9, 2, 7},
                {2, 9, 6, 8, 5, 7, 4, 3, 1},
                {4, 1, 7, 2, 9, 3, 6, 5, 8},
                {5, 6, 9, 1, 3, 4, 7, 8, 2},
                {1, 2, 3, 6, 7, 8, 5, 4, 9},
                {7, 4, 8, 5, 2, 9, 1, 6, 3},
                {6, 5, 2, 7, 8, 1, 3, 9, 4},
                {9, 8, 1, 3, 4, 5, 2, 7, 6},
                {3, 7, 4, 9, 6, 2, 8, 1, 5}
        };
        final Field field = new Field(grid);
        assertTrue(field.isValid());
        assertTrue(field.isCompleted());
    }

    @Test
    public void testCompletedInvalidGrid() {
        final int[][] grid = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {9, 1, 2, 3, 4, 5, 6, 7, 8},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {5, 1, 7, 8, 9, 6, 2, 3, 4},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {2, 3, 4, 5, 6, 7, 8, 9, 1}
        };
        final Field field = new Field(grid);
        assertFalse(field.isValid());
        assertFalse(field.isCompleted());
    }

    @Test
    public void testIncompleteInvalidSubGrid() {
        final int[][] grid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 1, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5}
        };
        final Field field = new Field(grid);
        assertFalse(field.isValid());
        assertFalse(field.isCompleted());
    }

    @Test
    public void testIncompleteValidSubGrid() {
        final int[][] grid = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 5, 1, 3},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 9}
        };
        final Field field = new Field(grid);
        assertTrue(field.isValid());
        assertFalse(field.isCompleted());
    }

    @Test
    public void testJSONSerializationDeserialization() throws JsonProcessingException {
        final Field inputField = new Field(new int[][] {
                {9, 7, 5, 8, 3, 4, 2, 6, 1},
                {4, 3, 2, 6, 5, 1, 9, 7, 8},
                {1, 6, 8, 9, 7, 2, 4, 5, 3},
                {5, 9, 6, 7, 2, 8, 1, 3, 4},
                {8, 1, 4, 5, 6, 3, 7, 2, 9},
                {7, 2, 3, 1, 4, 9, 5, 8, 6},
                {3, 8, 1, 2, 9, 7, 6, 4, 5},
                {6, 4, 7, 3, 1, 5, 8, 9, 2},
                {2, 5, 9, 4, 8, 6, 3, 1, 7}
        });
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(inputField);
        final Field outputField = objectMapper.readValue(json, Field.class);
        assertEquals(inputField.toString(), outputField.toString());
    }

}

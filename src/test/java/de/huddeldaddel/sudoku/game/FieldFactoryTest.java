package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldFactoryTest {

    @Test
    public void testBuild() {
        final FieldFactory factory = new FieldFactory();
        final Field field = factory.build();
        System.out.println(field.toAsciiArtString());
        assertFalse(field.isCompleted());
        assertTrue(field.isValid());
    }

    @Test
    public void testBuildAreUnique() {
        final FieldFactory factory = new FieldFactory();
        final Field field1 = factory.build();
        final Field field2 = factory.build();
        assertNotEquals(field1.toString(), field2.toString());
    }

}

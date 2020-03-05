package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackTrackingFieldFactoryTest {

    @Test
    public void testBuild() {
        final BackTrackingFieldFactory factory = new BackTrackingFieldFactory();
        final Field field = factory.build();
        System.out.println(field.toAsciiArtString());
        assertTrue(field.isCompleted());
        assertTrue(field.isValid());
    }

    @Test
    public void testBuildAreUnique() {
        final BackTrackingFieldFactory factory = new BackTrackingFieldFactory();
        final Field field1 = factory.build();
        final Field field2 = factory.build();
        assertNotEquals(field1.toString(), field2.toString());
    }

}

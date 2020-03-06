package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldFactoryTest {

    @Test
    public void testGenerateCompletedField() {
        final FieldFactory factory = new FieldFactory();
        final Field field = factory.generateCompletedField();
        System.out.println(field.toAsciiArtString());
        assertTrue(field.isCompleted());
        assertTrue(field.isValid());
    }

    @Test
    public void testGenerateCompletedFieldsAreUnique() {
        final FieldFactory factory = new FieldFactory();
        final Field field1 = factory.generateCompletedField();
        final Field field2 = factory.generateCompletedField();
        assertNotEquals(field1.toString(), field2.toString());
    }

}

package de.huddeldaddel.sudoku.game;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class FieldFactory {

    public Field generateCompletedField() {
        final Field field = new Field();
        fillField(field, NumberSequenceFactory.RANDOM, 0);
        return field;
    }

    /**
     * Generates a completed field via back tracking.
     *
     * @param field the Field to be filled
     * @param sequenceFactory the sequence of number to be tried for each field
     * @param index the current index of the field
     * @return whether or not the field is solvable at this time
     */
    private static boolean fillField(Field field, NumberSequenceFactory sequenceFactory, int index) {
        final List<Integer> numbers = sequenceFactory.build();

        for(int number: numbers) {
            field.setCell(index % 9, index / 9, number);
            if(field.isValid() && (index < 80)) {
                final int nextIndex = findNextBlankCell(field, index + 1);
                if ((-1 != nextIndex) && fillField(field, sequenceFactory, nextIndex))
                    return true;
            }
            if((80 == index) && field.isValid() && field.isCompleted())
                return true;
        }

        field.setCell(index % 9, index / 9, 0);
        return false;
    }

    private static int findNextBlankCell(Field field, int startIndex) {
        for(int index=startIndex; index<81; index++) {
            if(0 == field.getCell(index % 9, index / 9))
                return index;
        }
        return -1;
    }

    /**
     * Remove elements so that the result is minimal field with unique solution
     *
     * @param field the Field to be minimized
     */
    private static void minimizeField(Field field) {
        final List<Integer> indexSequence = new ArrayList<>(81);
        for(int i=0; i<81; i++)
            indexSequence.add(i);
        Collections.shuffle(indexSequence);

        for(Integer index: indexSequence) {
            try {
                final Field ascClone = new Field(field);
                ascClone.setCell(index % 9, index / 9, 0);
                final Thread ascThread = new Thread(new Solver(ascClone, NumberSequenceFactory.ASCENDING));
                ascThread.start();

                final Field descClone = new Field(field);
                descClone.setCell(index % 9, index / 9, 0);
                final Thread descThread = new Thread(new Solver(descClone, NumberSequenceFactory.DESCENDING));
                descThread.start();

                ascThread.join();
                descThread.join();

                if (ascClone.toString().equals(descClone.toString()))
                    field.setCell(index % 9, index / 9, 0);
            } catch(InterruptedException ie) { }
        }
    }

    private enum NumberSequenceFactory {

        ASCENDING() {
            @Override
            List<Integer> build() {
                return ascendingSequence;
            }
        },
        DESCENDING() {
            @Override
            List<Integer> build() {
                return descendingSequence;
            }
        },
        RANDOM() {
            @Override
            List<Integer> build() {
                final List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
                Collections.shuffle(numbers);
                return numbers;
            }
        };

        private static List<Integer> ascendingSequence = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        private static List<Integer> descendingSequence = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1);
        abstract List<Integer> build();
    }

    private static class Solver implements Runnable {

        private final Field field;
        private final NumberSequenceFactory numberSequenceFactory;

        private Solver(Field field, NumberSequenceFactory numberSequenceFactory) {
            this.field = field;
            this.numberSequenceFactory = numberSequenceFactory;
        }

        @Override
        public void run() {
            final int nextBlankCell = findNextBlankCell(field, 0);
            fillField(field, numberSequenceFactory, nextBlankCell);
        }

    }

}

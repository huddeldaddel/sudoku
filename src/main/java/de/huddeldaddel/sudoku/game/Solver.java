package de.huddeldaddel.sudoku.game;

import java.util.List;

public class Solver {

    /**
     * Generates a completed field via back tracking.
     *
     * @param field the Field to be filled
     * @param sequenceFactory the sequence of number to be tried for each field
     * @param index the current index of the field
     * @return whether or not the field is solvable at this time
     */
    public static boolean solve(Field field, NumberSequenceFactory sequenceFactory, int index) {
        final List<Integer> numbers = sequenceFactory.build();

        for(int number: numbers) {
            if(Thread.currentThread().isInterrupted())
                return false;

            field.setCell(index % 9, index / 9, number);
            if(field.isValid() && (index < 80)) {
                final int nextIndex = findNextBlankCell(field, index + 1);
                if ((-1 != nextIndex) && solve(field, sequenceFactory, nextIndex))
                    return true;
            }
            if((80 == index) && field.isValid() && field.isCompleted())
                return true;
        }

        field.setCell(index % 9, index / 9, 0);
        return false;
    }

    public static int findNextBlankCell(Field field, int startIndex) {
        for(int index=startIndex; index<81; index++) {
            if(0 == field.getCell(index % 9, index / 9))
                return index;
        }
        return -1;
    }

}

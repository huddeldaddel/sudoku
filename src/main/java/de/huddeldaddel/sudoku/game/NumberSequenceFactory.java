package de.huddeldaddel.sudoku.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum NumberSequenceFactory {

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

package de.huddeldaddel.sudoku.game;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public interface FieldFilter {

    Stream<Field> filter(Field field);

    static Collection<List<Integer>> getPermutationsOfThree() {
        return List.of(
            List.of(0,1,2),
            List.of(0,2,1),
            List.of(1,0,2),
            List.of(1,2,0),
            List.of(2,0,1),
            List.of(2,1,0)
        );
    }

}

package de.huddeldaddel.sudoku.game;

import java.util.stream.Stream;

public interface FieldFilter {

    Stream<Field> filter(Field field);

}

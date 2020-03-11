package de.huddeldaddel.sudoku.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class RotationFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new RotationFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        final List<Field> result = new ArrayList<>();
        result.add(field);
        for(int i=0; i<3; i++)
            result.add(rotate90Degrees(result.get(i)));
        return result.stream();
    }

    public static Field rotate90Degrees(Field field) {
        final Field result = new Field(field);
        for(int r=0; r<9; r++) {
            final int[] row = field.getRow(r);
            for(int i=0; i<9; i++)
                result.setCell(8-r, i, row[i]);
        }
        return result;
    }

}

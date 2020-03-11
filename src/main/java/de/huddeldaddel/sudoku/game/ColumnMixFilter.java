package de.huddeldaddel.sudoku.game;

import java.util.List;
import java.util.stream.Stream;

public class ColumnMixFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new ColumnMixFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        return FieldFilter.getPermutationsOfThree().stream().map(indexes -> getColumnMix(field, indexes));
    }

    public static Field getColumnMix(Field field, List<Integer> colMix) {
        final Field result = new Field(field);
        for(int stripe=0; stripe<3; stripe++)
            for(int col=0; col<3; col++)
                for(int row=0; row<9; row++)
                    result.setCell((stripe *3) + colMix.get(col), row, field.getCell((stripe *3) + col, row));
        return result;
    }

}

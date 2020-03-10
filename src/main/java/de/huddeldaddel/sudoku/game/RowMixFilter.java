package de.huddeldaddel.sudoku.game;

import java.util.List;
import java.util.stream.Stream;

public class RowMixFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new RowMixFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        return FieldFilter.getPermutationsOfThree().stream().map(indexes -> getRowMix(field, indexes));

    }

    public static Field getRowMix(Field field, List<Integer> rowMix) {
        final Field result = new Field();
        for(int stripe=0; stripe<3; stripe++)
            for(int row=0; row<3; row++)
                for(int col=0; col<9; col++)
                    result.setCell(col, (stripe *3) + rowMix.get(row), field.getCell(col, (stripe *3) + row));
        return result;
    }

}

package de.huddeldaddel.sudoku.game;

import java.util.List;
import java.util.stream.Stream;

public class HorizontalStripeMixFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new HorizontalStripeMixFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        return FieldFilter.getPermutationsOfThree().stream().map(indexes -> getHorizontalStripeMix(field, indexes));
    }

    public static Field getHorizontalStripeMix(Field field, List<Integer> stripeMix) {
        final Field result = new Field();
        for(int stripe=0; stripe<3; stripe++) {
            int sourceRow=stripe*3;
            for(int row=stripeMix.get(stripe)*3; row<(stripeMix.get(stripe)*3)+3; row++, sourceRow++)
                 for(int col=0; col<9; col++)
                     result.setCell(col, row, field.getCell(col, sourceRow));
        }
        return result;
    }

}

package de.huddeldaddel.sudoku.game;

import java.util.List;
import java.util.stream.Stream;

public class VerticalStripeMixFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new VerticalStripeMixFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        return FieldFilter.getPermutationsOfThree().stream().map(indexes -> getVerticalStripeMix(field, indexes));
    }

    public static Field getVerticalStripeMix(Field field, List<Integer> stripeMix) {
        final Field result = new Field(field);
        for(int stripe=0; stripe<3; stripe++) {
            int sourceCol=stripe*3;
            for(int col=stripeMix.get(stripe)*3; col<(stripeMix.get(stripe)*3)+3; col++, sourceCol++)
                for(int row=0; row<9; row++)
                    result.setCell(col, row, field.getCell(sourceCol, row));
        }
        return result;
    }

}

package de.huddeldaddel.sudoku.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VerticalStripeMixFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new VerticalStripeMixFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        final Stream.Builder<Field> result = Stream.builder();
        result.add(field);
        // result.add(getVerticalStripeMix(field, List.of(0,1,2))); is equal to the input field
        result.add(getVerticalStripeMix(field, List.of(0,2,1)));
        result.add(getVerticalStripeMix(field, List.of(1,0,2)));
        result.add(getVerticalStripeMix(field, List.of(1,2,0)));
        result.add(getVerticalStripeMix(field, List.of(2,0,1)));
        result.add(getVerticalStripeMix(field, List.of(2,1,0)));
        return result.build();
    }

    public static Field getVerticalStripeMix(Field field, List<Integer> stripeMix) {
        final Field result = new Field();
        for(int stripe=0; stripe<3; stripe++) {
            int sourceCol=stripe*3;
            for(int col=stripeMix.get(stripe)*3; col<(stripeMix.get(stripe)*3)+3; col++, sourceCol++)
                for(int row=0; row<9; row++)
                    result.setCell(col, row, field.getCell(sourceCol, row));
        }
        return result;
    }

}

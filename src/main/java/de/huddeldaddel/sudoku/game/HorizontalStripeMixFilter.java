package de.huddeldaddel.sudoku.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HorizontalStripeMixFilter implements FieldFilter {

    @Override
    public Stream<Field> filter(Field field) {
        final List<Field> result = new ArrayList<>();
        result.add(field);
     // result.add(getHorizontalStripeMix(field, List.of(0,1,2))); is equal to the input field
        result.add(getHorizontalStripeMix(field, List.of(0,2,1)));
        result.add(getHorizontalStripeMix(field, List.of(1,0,2)));
        result.add(getHorizontalStripeMix(field, List.of(1,2,0)));
        result.add(getHorizontalStripeMix(field, List.of(2,0,1)));
        result.add(getHorizontalStripeMix(field, List.of(2,1,0)));
        return result.stream();
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

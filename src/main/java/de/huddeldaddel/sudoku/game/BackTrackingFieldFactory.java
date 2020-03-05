package de.huddeldaddel.sudoku.game;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class BackTrackingFieldFactory implements FieldFactory {

    @Override
    public Field build() {
        final Field field = new Field();
        fillField(field, 0);
        return field;
    }

    private boolean fillField(Field field, int index) {
        final List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,7,8,9);
        Collections.shuffle(numbers);

        for(int number: numbers) {
            field.setCell(index % 9, index / 9, number);
            if(field.isValid() && (index < 80) && fillField(field, index + 1))
                return true;
            if((80 == index) && field.isValid() && field.isCompleted())
                return true;
        }

        field.setCell(index % 9, index / 9, 0);
        return false;
    }

}

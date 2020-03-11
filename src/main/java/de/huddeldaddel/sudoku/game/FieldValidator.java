package de.huddeldaddel.sudoku.game;

import java.util.Arrays;

public class FieldValidator {

    private final Field field;

    FieldValidator(Field field) {
        this.field = field;
    }

    public boolean isCompleted() {
        return (0 == field.getEmptyCellCount()) && isValid();
    }

    public boolean isValid() {
        return areColumnsValid() &&
                areRowValid() &&
                areSubGridsValid();
    }

    private boolean areColumnsValid() {
        for(int column=0; column<9; column++)
            if(!isSectionValid(field.getColumn(column)))
                return false;
        return true;
    }

    private boolean areRowValid() {
        for(int row=0; row<9; row++)
            if(!isSectionValid(field.getRow(row)))
                return false;
        return true;
    }

    private boolean areSubGridsValid() {
        for(int column=0; column<7; column+=3)
            for(int row=0; row<7; row+=3)
                if(!isSectionValid(getSubGrid(column, row)))
                    return false;
        return true;
    }

    private int[] getSubGrid(int leftColumn, int topRow) {
        int[] section = new int[9];
        int index = 0;
        for(int column=leftColumn; column < leftColumn + 3; column++) {
            for(int row=topRow; row < topRow + 3; row++) {
                section[index] = field.getCell(column, row);
                index++;
            }
        }
        return section;
    }

    private boolean isSectionValid(int[] section) {
        Arrays.sort(section);
        int last = section[0];
        for(int index = 1; index < 9; index++) {
            if(section[index] > 0 && last == section[index])
                return false;
            last = section[index];
        }
        return true;
    }

}

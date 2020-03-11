package de.huddeldaddel.sudoku.game;

import java.util.Arrays;

public class FieldValidator {

    private final int[][] field;

    FieldValidator(int[][] field) {
        this.field = field;
    }

    public boolean isCompleted() {
        return (0 == getEmptyCellCount()) && isValid();
    }

    private int getEmptyCellCount() {
        int result = 0;
        for(int c=0; c<9; c++)
            for(int r=0; r<9; r++)
                if(0 == field[r][c])
                    result++;
        return result;
    }

    public boolean isValid() {
        return isFieldRegardingColumns() &&
                isFieldValidRegardingRows() &&
                isFieldValidRegardingBlocks();
    }

    private boolean isFieldValidRegardingBlocks() {
        for(int column=0; column<7; column+=3)
            for(int row=0; row<7; row+=3)
                if(!isSectionValid(getBlock(column, row)))
                    return false;
        return true;
    }

    private boolean isFieldRegardingColumns() {
        for(int column=0; column<9; column++)
            if(!isSectionValid(getColumn(column)))
                return false;
        return true;
    }

    private boolean isFieldValidRegardingRows() {
        for(int row=0; row<9; row++)
            if(!isSectionValid(getRow(row)))
                return false;
        return true;
    }

    /**
     * @param leftColumn the left column of the block
     * @param topRow the top row of the block
     * @return a 3x3 block of cells specified by the left and top coordinate
     */
    private int[] getBlock(int leftColumn, int topRow) {
        final int[] section = new int[9];
        int index = 0;
        for(int column=leftColumn; column < leftColumn + 3; column++) {
            for(int row=topRow; row < topRow + 3; row++) {
                section[index] = field[row][column];
                index++;
            }
        }
        return section;
    }

    private int[] getColumn(int column) {
        final int[] section = new int[9];
        for(int row=0; row<9; row++) {
            section[row] = field[row][column];
        }
        return section;
    }

    private int[] getRow(int row) {
        return Arrays.copyOf(field[row], 9);
    }

    /**
     * @param section an array containing the cells of a block / column / row.
     * @return true if the given section is valid
     */
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

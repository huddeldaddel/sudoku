package de.huddeldaddel.sudoku;

import java.util.Arrays;

public class Field {

    private final int[][] grid;

    public Field() {
        grid = new int[9][9];
        for(int c=0; c<9; c++)
            for(int r=0; r<9; r++)
                setCell(c,r,0);
    }

    public Field(int[][] grid) {
        this.grid = grid;
    }

    public int getCell(int column, int row) {
        return grid[row][column];
    }

    public void setCell(int column, int row, int value) throws IllegalArgumentException {
        if(-1 < value && 10 > value) {
            grid[row][column] = value;
        } else throw new IllegalArgumentException(value + " is out of range");
    }

    public boolean isCompleted() {
        for(int c=0; c<9; c++)
            for(int r=0; r<9; r++)
                if(0 == getCell(c, r))
                    return false;
        return isValid();
    }

    public boolean isValid() {
        return areColumnsValid() &&
                areRowValid() &&
                areSubGridsValid();
    }

    private boolean areColumnsValid() {
        for(int column=0; column<9; column++)
            if(!isSectionValid(getColumn(column)))
                return false;
        return true;
    }

    private int[] getColumn(int column) {
        int[] section = new int[9];
        for(int row=0; row<9; row++) {
            section[row] = getCell(column, row);
        }
        return section;
    }

    private boolean areRowValid() {
        for(int row=0; row<9; row++)
            if(!isSectionValid(getRow(row)))
                return false;
        return true;
    }

    private int[] getRow(int row) {
        return Arrays.copyOf(grid[row], 9);
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
                section[index] = getCell(column, row);
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

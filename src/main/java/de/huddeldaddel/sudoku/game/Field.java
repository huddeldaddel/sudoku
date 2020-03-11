package de.huddeldaddel.sudoku.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Arrays;

public class Field {

    @Id
    private String id;
    private final int[][] grid;
    private String difficulty;
    @Indexed(unique = true)
    private String identifier;

    /**
     * Creates an empty Field.
     */
    public Field() {
        grid = new int[9][9];
        for(int c=0; c<9; c++)
            for(int r=0; r<9; r++)
                setCell(c,r,0);
    }

    /**
     * Creates a Field with pre-filled number grid. Useful for tests.
     *
     * @param grid the pre-filled grid.
     */
    public Field(int[][] grid) {
        this.grid = grid;
    }

    /**
     * Creates a clone of the given Field.
     *
     * @param original the original field to be cloned
     */
    public Field(Field original) {
        difficulty = original.difficulty;
        grid = Arrays.stream(original.grid).map(int[]::clone).toArray(int[][]::new);
        id = original.id;
        identifier = original.identifier;
    }

    public int getCell(int column, int row) {
        return grid[row][column];
    }

    public void setCell(int column, int row, int value) throws IllegalArgumentException {
        if(-1 < value && 10 > value) {
            grid[row][column] = value;
        } else throw new IllegalArgumentException(value + " is out of range");
    }

    public int[][] getGrid() {
        return Arrays.stream(grid).map(int[]::clone).toArray(int[][]::new);
    }

    @JsonIgnore
    public boolean isCompleted() {
        return new FieldValidator(this).isCompleted();
    }

    @JsonIgnore
    public boolean isValid() {
        return new FieldValidator(this).isValid();
    }

    @JsonIgnore
    public int getEmptyCellCount() {
        int result = 0;
        for(int c=0; c<9; c++)
            for(int r=0; r<9; r++)
                if(0 == getCell(c, r))
                    result++;
        return result;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getIdentifier() {
        return identifier;
    }

    void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public int[] getColumn(int column) {
        int[] section = new int[9];
        for(int row=0; row<9; row++) {
            section[row] = getCell(column, row);
        }
        return section;
    }

    public int[] getRow(int row) {
        return Arrays.copyOf(grid[row], 9);
    }

    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for(int r=0; r<9; r++) {
            for(int i: getRow(r)) {
                if(0 < builder.length())
                    builder.append(",");
                builder.append(i);
            }
        }
        return builder.toString();
    }

    public String toAsciiArtString() {
        final StringBuilder builder = new StringBuilder();
        for(int r=0; r<9; r++) {
            if(3 == r || 6 == r)
                builder.append("\n------+-------+------");
            if(0 < builder.length())
                builder.append("\n");
            final int[] row = getRow(r);
            for(int c=0; c<9; c++) {
                if(3 == c || 6 == c)
                    builder.append(" |");
                if(0 < c)
                    builder.append(" ");
                builder.append(row[c]);
            }
        }
        return builder.toString();
    }

}

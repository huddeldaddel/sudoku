package de.huddeldaddel.sudoku.game;

public enum Difficulty {

    EASY(36, 50),
    MEDIUM(27, 35),
    HARD(19, 26);

    public final int minimumNumberOfClues;
    public final int maximumNumberOfClues;

    private Difficulty(int min, int max) {
        minimumNumberOfClues = min;
        maximumNumberOfClues = max;
    }

}

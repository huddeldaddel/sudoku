package de.huddeldaddel.sudoku.game;

public enum Difficulty {

    EASY(36, 50),
    MEDIUM(27, 35),
    HARD(19, 26);

    public final int minimumNumberOfClues;
    public final int maximumNumberOfClues;

    Difficulty(int min, int max) {
        minimumNumberOfClues = min;
        maximumNumberOfClues = max;
    }

    public static Difficulty getDifficultyByNumberOfClues(int clues) {
        if(clues >= Difficulty.EASY.minimumNumberOfClues)
            return Difficulty.EASY;
        if(clues <= Difficulty.HARD.maximumNumberOfClues)
            return Difficulty.HARD;
        return Difficulty.MEDIUM;
    }

}

package de.huddeldaddel.sudoku.game;

public enum Difficulty {

    TOO_EASY(51, 81),
    EASY(36, 50),
    MEDIUM(27, 35),
    HARD(18, 26),
    TOO_HARD(0, 17);

    public final int minimumNumberOfClues;
    public final int maximumNumberOfClues;

    Difficulty(int min, int max) {
        minimumNumberOfClues = min;
        maximumNumberOfClues = max;
    }

    public static Difficulty getDifficultyByNumberOfClues(int clues) {
        if(clues >= Difficulty.TOO_EASY.minimumNumberOfClues)
            return Difficulty.TOO_EASY;
        if((clues >= Difficulty.EASY.minimumNumberOfClues) && (clues <= Difficulty.EASY.maximumNumberOfClues))
            return Difficulty.EASY;
        if((clues >= Difficulty.MEDIUM.minimumNumberOfClues) && (clues <= Difficulty.MEDIUM.maximumNumberOfClues))
            return Difficulty.MEDIUM;
        if((clues >= Difficulty.HARD.minimumNumberOfClues) && (clues <= Difficulty.HARD.maximumNumberOfClues))
            return Difficulty.HARD;
        return Difficulty.TOO_HARD;
    }

}

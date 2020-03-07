package de.huddeldaddel.sudoku.game;

class RunnableSolver implements Runnable {

    private final Field field;
    private final NumberSequenceFactory numberSequenceFactory;

    RunnableSolver(Field field, NumberSequenceFactory numberSequenceFactory) {
        this.field = field;
        this.numberSequenceFactory = numberSequenceFactory;
    }

    @Override
    public void run() {
        final int nextBlankCell = Solver.findNextBlankCell(field, 0);
        Solver.solve(field, numberSequenceFactory, nextBlankCell);
    }

}

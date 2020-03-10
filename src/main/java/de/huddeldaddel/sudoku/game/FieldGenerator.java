package de.huddeldaddel.sudoku.game;

import java.util.stream.Stream;

public class FieldGenerator {

    public Stream<Field> generateFields(Field field) {
        return Stream.of(field)
                .flatMap(this::runFilterChain);
    }

    public Stream<Field> generateFields(int count) {
        return Stream.generate(this::generateCompletedRandomField)
                .peek(f -> System.out.println(f.toAsciiArtString()))
                .limit(count)
                .flatMap(this::runFilterChain);
    }

    private Stream<Field> runFilterChain(Field completedField) {
        return Stream.of(completedField)
                .flatMap(DifficultyFilter::doFilter)
                .flatMap(RotationFilter::doFilter)
                .flatMap(HorizontalStripeMixFilter::doFilter)
                .flatMap(VerticalStripeMixFilter::doFilter)
                .flatMap(ColumnMixFilter::doFilter)
                .flatMap(RowMixFilter::doFilter);
    }

    private Field generateCompletedRandomField() {
        final Field field = new Field();
        Solver.solve(field, NumberSequenceFactory.RANDOM, 0);
        return field;
    }

}

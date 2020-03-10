package de.huddeldaddel.sudoku.game;

import java.util.stream.Stream;

public class FieldGenerator {

    public void generateFields(String outputDirectory, int count) {
        final FileOutputFilter fileOutputFilter = new FileOutputFilter(outputDirectory);
        final long generatedFields = Stream
                .generate(FieldGenerator::generateCompletedRandomField)
                .limit(count)
                .flatMap(DifficultyFilter::doFilter)
                .flatMap(RotationFilter::doFilter)
                .flatMap(HorizontalStripeMixFilter::doFilter)
                .flatMap(VerticalStripeMixFilter::doFilter)
                .flatMap(ColumnMixFilter::doFilter)
                .flatMap(RowMixFilter::doFilter)
                .flatMap(fileOutputFilter::filter)
                .count();
        System.out.println(generatedFields + " have been created");
    }

    private static Field generateCompletedRandomField() {
        final Field field = new Field();
        Solver.solve(field, NumberSequenceFactory.RANDOM, 0);
        return field;
    }

}

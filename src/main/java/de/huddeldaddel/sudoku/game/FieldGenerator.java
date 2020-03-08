package de.huddeldaddel.sudoku.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FieldGenerator {

    public void generateFields(String outputDirectory) {
        final FileOutputFilter fileOutputFilter = new FileOutputFilter(outputDirectory);
        final long generatedFields = generateCompletedRandomFields(1)
                .flatMap(RotationFilter::doFilter)
                .flatMap(HorizontalStripeMixFilter::doFilter)
                .flatMap(VerticalStripeMixFilter::doFilter)
                .flatMap(fileOutputFilter::filter)
                .count();
        System.out.println(generatedFields + " have been created");
    }

    private Stream<Field> generateCompletedRandomFields(int count) {
        final List<Field> result = new ArrayList<>();
        for(int i=0; i<count; i++) {
            final Field field = new Field();
            Solver.solve(field, NumberSequenceFactory.RANDOM, 0);
            result.add(field);
        }
        return result.stream();
    }

    /**
     * Remove elements so that the result is minimal field with unique solution
     *
     * @param field the Field to be minimized
     */
    private static void minimizeField(Field field) {
        final List<Integer> indexSequence = new ArrayList<>(81);
        for(int i=0; i<81; i++)
            indexSequence.add(i);
        Collections.shuffle(indexSequence);

        for(Integer index: indexSequence) {
            try {
                final Field ascClone = new Field(field);
                ascClone.setCell(index % 9, index / 9, 0);
                final Thread ascThread = new Thread(new RunnableSolver(ascClone, NumberSequenceFactory.ASCENDING));
                ascThread.start();

                final Field descClone = new Field(field);
                descClone.setCell(index % 9, index / 9, 0);
                final Thread descThread = new Thread(new RunnableSolver(descClone, NumberSequenceFactory.DESCENDING));
                descThread.start();

                ascThread.join();
                descThread.join();

                if (ascClone.toString().equals(descClone.toString()))
                    field.setCell(index % 9, index / 9, 0);
            } catch(InterruptedException ie) { }
        }
    }

}

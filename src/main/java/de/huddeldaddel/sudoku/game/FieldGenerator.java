package de.huddeldaddel.sudoku.game;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FieldGenerator {

    public void generateFields(String outputDirectory) {
        final RotationFilter rotationFilter = new RotationFilter();
        final HorizontalStripeMixFilter horizontalStripeMixFilter = new HorizontalStripeMixFilter();
        generateCompletedRandomFields(1)
                .flatMap(rotationFilter::filter)
                .flatMap(horizontalStripeMixFilter::filter)
                .forEach(f -> writeFile(f, outputDirectory));
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

    private void writeFile(Field field, String outputDirectory) {
        final File file = Path.of(outputDirectory, field.toString().replaceAll(",", "") + ".json").toFile();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, field);
        } catch (IOException e) {
            System.out.println("oh shit");
        }
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

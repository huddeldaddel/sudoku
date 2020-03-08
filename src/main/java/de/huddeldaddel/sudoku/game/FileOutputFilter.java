package de.huddeldaddel.sudoku.game;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileOutputFilter implements FieldFilter {

    private final String outputDirectory;

    public FileOutputFilter(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public Stream<Field> filter(Field field) {
        final File file = Path.of(outputDirectory, field.getDifficulty().toString() + "-" + field.toString().replaceAll(",", "") + ".json").toFile();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, field);
        } catch (IOException e) {
            // TODO: configure logging
            System.out.println("oh shit");
        }
        return Stream.of(field);
    }

}

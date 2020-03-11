package de.huddeldaddel.sudoku.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileOutputFilter implements FieldFilter {

    private static final Log LOG = LogFactory.getLog(FieldGenerator.class);

    private final String outputDirectory;

    public FileOutputFilter(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    @Override
    public Stream<Field> filter(Field field) {
        final File file = Path.of(outputDirectory, field.getDifficulty() + "-" + field.toString().replaceAll(",", "") + ".json").toFile();
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, field);
        } catch (IOException e) {
            LOG.warn("Failed to write field to file", e);
        }
        return Stream.of(field);
    }

}

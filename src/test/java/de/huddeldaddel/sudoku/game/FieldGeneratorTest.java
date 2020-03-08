package de.huddeldaddel.sudoku.game;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FieldGeneratorTest {

    @Test
    public void testGenerateFields() throws IOException {
        final Path tempDirectory = Files.createTempDirectory("FieldGeneratorTest-");
        assertEquals(0, tempDirectory.toFile().listFiles().length);

        new FieldGenerator().generateFields(tempDirectory.toString(), 1);
        final File[] generatedFiles = tempDirectory.toFile().listFiles((file, s) -> s.toLowerCase().endsWith(".json"));

        System.out.println("Generated files:");
        for(File f: generatedFiles)
            System.out.println(" -> " + f.getAbsolutePath());
        assertNotEquals(0, generatedFiles.length);

        for(Path path : Files.walk(tempDirectory).sorted(Comparator.reverseOrder()).collect(Collectors.toList()))
            Files.deleteIfExists(path);
    }

}

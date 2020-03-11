package de.huddeldaddel.sudoku.controllers;

import de.huddeldaddel.sudoku.game.FieldGenerator;
import de.huddeldaddel.sudoku.game.MongoUploadFilter;
import de.huddeldaddel.sudoku.repositories.FieldRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FieldController {

    private final FieldGenerator fieldGenerator;
    private final FieldRepository fieldRepository;

    public FieldController(FieldGenerator fieldGenerator, FieldRepository fieldRepository) {
        this.fieldGenerator = fieldGenerator;
        this.fieldRepository = fieldRepository;

        fieldRepository.deleteAll();

        final MongoUploadFilter uploadFilter = new MongoUploadFilter(fieldRepository);
        final long count = fieldGenerator.generateFields(5).flatMap(uploadFilter::filter).count();
        System.out.println("Generated " + count + " fields");
    }

}

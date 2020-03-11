package de.huddeldaddel.sudoku.game;

import de.huddeldaddel.sudoku.repositories.FieldRepository;

import java.util.stream.Stream;

public class MongoUploadFilter implements FieldFilter {

    private final FieldRepository fieldRepository;

    public MongoUploadFilter(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Stream<Field> filter(Field field) {
        fieldRepository.insert(field);
        return Stream.of(field);
    }

}

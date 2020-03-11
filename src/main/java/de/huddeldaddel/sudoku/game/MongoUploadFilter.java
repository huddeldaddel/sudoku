package de.huddeldaddel.sudoku.game;

import de.huddeldaddel.sudoku.repositories.FieldRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.stream.Stream;

public class MongoUploadFilter implements FieldFilter {

    private static final Log LOG = LogFactory.getLog(FieldGenerator.class);

    private final FieldRepository fieldRepository;

    public MongoUploadFilter(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Stream<Field> filter(Field field) {
        if(LOG.isDebugEnabled())
            LOG.debug("Inserting field " + field.toString() + " into the database");

        fieldRepository.insert(field);
        return Stream.of(field);
    }

}

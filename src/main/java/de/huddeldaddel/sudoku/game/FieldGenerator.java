package de.huddeldaddel.sudoku.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class FieldGenerator {

    private static final Log LOG = LogFactory.getLog(FieldGenerator.class);

    public Stream<Field> generateFields(Field field) {
        return Stream.of(field)
                .flatMap(this::runFilterChain);
    }

    public Stream<Field> generateFields(int count) {
        return Stream.generate(this::generateCompletedRandomField)
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
                .flatMap(RowMixFilter::doFilter)
                .map(FieldIdentifierFilter::filter);
    }

    private Field generateCompletedRandomField() {
        final Field field = new Field();
        Solver.solve(field, NumberSequenceFactory.RANDOM, 0);
        if(LOG.isInfoEnabled())
            LOG.info("Generated random field: " + field.toString());
        return field;
    }

}

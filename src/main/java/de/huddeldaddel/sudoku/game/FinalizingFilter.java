package de.huddeldaddel.sudoku.game;

import java.util.stream.Stream;

public class FinalizingFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new FinalizingFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        field.setIdentifier(buildIdentifier(field));
        return Stream.of(field);
    }

    private static String buildIdentifier(Field field) {
        final StringBuilder builder = new StringBuilder();
        for(int r=0; r<9; r++)
            for(int i: field.getRow(r))
                builder.append(i);
        return builder.toString();
    }

}

package de.huddeldaddel.sudoku.game;

public class FieldIdentifierFilter {

    public static Field filter(Field field) {
        field.setIdentifier(buildIdentifier(field));
        return field;
    }

    private static String buildIdentifier(Field field) {
        final StringBuilder builder = new StringBuilder();
        for(int r=0; r<9; r++)
            for(int i: field.getRow(r))
                builder.append(i);
        return builder.toString();
    }

}

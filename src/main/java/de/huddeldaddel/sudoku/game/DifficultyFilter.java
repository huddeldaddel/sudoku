package de.huddeldaddel.sudoku.game;

import java.util.*;
import java.util.stream.Stream;

public class DifficultyFilter implements FieldFilter {

    public static Stream<Field> doFilter(Field field) {
        return new DifficultyFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        final Map<Difficulty, List<Field>> result = buildWeightedResultStructure();

        final List<Integer> indexSequence = generateRandomAccessSequence();
        Field reducedField = null;
        for(Integer index: indexSequence) {
            long start = System.currentTimeMillis();
            reducedField = removeClue(field, index);
            if(null != reducedField) {
                field = reducedField;
                if((Difficulty.TOO_EASY != field.getDifficulty()) && (Difficulty.TOO_HARD != field.getDifficulty()))
                    result.get(field.getDifficulty()).add(field);
            }
            long end = System.currentTimeMillis();
            if(45 < (end - start) / 1000)
                break;
        }

        return getWeightedResultStructureAsStream(result);
    }

    private Stream<Field> getWeightedResultStructureAsStream(Map<Difficulty, List<Field>> result) {
        Collections.shuffle(result.get(Difficulty.EASY));
        Collections.shuffle(result.get(Difficulty.MEDIUM));
        Collections.shuffle(result.get(Difficulty.HARD));

        final int minSize = Math.min(result.get(Difficulty.MEDIUM).size(), result.get(Difficulty.HARD).size());
        final List<Field> resultCollection = new ArrayList<>();
        resultCollection.addAll(result.get(Difficulty.EASY).subList(0, minSize));
        resultCollection.addAll(result.get(Difficulty.MEDIUM).subList(0, minSize));
        resultCollection.addAll(result.get(Difficulty.HARD).subList(0, minSize));
        return resultCollection.stream();
    }

    private static HashMap<Difficulty, List<Field>> buildWeightedResultStructure() {
        return new HashMap<>() {{
            put(Difficulty.EASY, new ArrayList<>());
            put(Difficulty.MEDIUM, new ArrayList<>());
            put(Difficulty.HARD, new ArrayList<>());
        }};
    }

    /**
     * Remove elements so that the result is minimal field with unique solution
     *
     * @param field the Field to be minimized
     * @param index the index to remove the
     */
    private static Field removeClue(Field field, int index) {
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

            if (ascClone.toString().equals(descClone.toString())) {
                final Field result = new Field(field);
                result.setCell(index % 9, index / 9, 0);
                return result;
            }
        } catch(InterruptedException ie) {
            System.out.println("Oh shit: " + ie.getMessage());
        }
        return null;
    }

    private static List<Integer> generateRandomAccessSequence() {
        final List<Integer> indexSequence = new ArrayList<>(81);
        for(int i=0; i<81; i++)
            indexSequence.add(i);
        Collections.shuffle(indexSequence);
        return indexSequence;
    }

}

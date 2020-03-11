package de.huddeldaddel.sudoku.game;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.stream.Stream;

public class DifficultyFilter implements FieldFilter {

    private static final Log LOG = LogFactory.getLog(FieldGenerator.class);

    public static Stream<Field> doFilter(Field field) {
        return new DifficultyFilter().filter(field);
    }

    @Override
    public Stream<Field> filter(Field field) {
        if(LOG.isDebugEnabled())
            LOG.debug("Starting to reduce field " + field.toString());

        final Map<Difficulty, List<Field>> result = buildWeightedResultStructure();
        final List<Integer> indexSequence = generateRandomAccessSequence();
        Field reducedField;
        for(Integer index: indexSequence) {
            final int clueNumber = 1 + field.getEmptyCellCount();

            long start = System.currentTimeMillis();
            reducedField = removeClue(field, index);
            if(null != reducedField) {
                field = reducedField;
                final Difficulty difficulty = Difficulty.getDifficultyByNumberOfClues(field.getEmptyCellCount());
                field.setDifficulty(difficulty.name());
                if((Difficulty.TOO_EASY != difficulty) && (Difficulty.TOO_HARD != difficulty))
                    result.get(difficulty).add(field);
            }

            long end = System.currentTimeMillis();
            if(LOG.isDebugEnabled())
                LOG.debug("Removing clue #" + clueNumber + " took " + (end - start) + " ms");
            if(45 < (end - start) / 1000) {
                LOG.debug("Stopping to remove clues as threshold of 45 seconds has been reached");
                break;
            }
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
        if(LOG.isInfoEnabled())
            LOG.info("Generated " + (resultCollection.size() / 3) + " easy/medium/hard fields each");
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
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

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
            stopWatch.stop();

            if(ascClone.toString().equals(descClone.toString())) {
                final Field result = new Field(field);
                result.setCell(index % 9, index / 9, 0);
                return result;
            }
        } catch(InterruptedException ie) {
            LOG.warn("Ignoring unexpected InterruptedException", ie);
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

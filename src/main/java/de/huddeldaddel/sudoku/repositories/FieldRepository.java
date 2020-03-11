package de.huddeldaddel.sudoku.repositories;

import de.huddeldaddel.sudoku.game.Field;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FieldRepository extends MongoRepository<Field, String> {

}

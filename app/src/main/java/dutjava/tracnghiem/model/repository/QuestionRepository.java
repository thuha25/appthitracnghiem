package dutjava.tracnghiem.model.repository;

import dutjava.tracnghiem.model.entity.Question;
import dutjava.tracnghiem.util.database.CrudRepository;

public class QuestionRepository extends CrudRepository<Question, Integer> {
    public QuestionRepository() {
        super(Question.class, Integer.class);
    }
}
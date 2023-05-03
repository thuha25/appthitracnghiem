package dutjava.tracnghiem.model.repository;

import dutjava.tracnghiem.model.entity.Answer;
import dutjava.tracnghiem.util.database.CrudRepository;

public class AnswerRepository extends CrudRepository<Answer, Integer> {
    public AnswerRepository() {
        super(Answer.class, Integer.class);
    }  
}

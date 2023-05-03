package dutjava.tracnghiem.model.repository;

import dutjava.tracnghiem.model.entity.Answer;
import dutjava.tracnghiem.model.utils.Repository;

public class AnswerRepository extends Repository<Answer, Integer> {
    public AnswerRepository() {
        super(Answer.class, Integer.class);
    }  
}

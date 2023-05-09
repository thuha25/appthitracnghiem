package dutjava.tracnghiem.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dutjava.tracnghiem.model.entity.Question;
import dutjava.tracnghiem.model.entity.Test_Questions;
import dutjava.tracnghiem.util.database.CrudRepository;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class TestQuestionsRepository extends CrudRepository<Test_Questions, Integer> {

    @Inject
    private QuestionRepository questionRepository;

    public TestQuestionsRepository() {
        super(Test_Questions.class, Integer.class);
    }

    public List<Question> getByTestId(int id) {
        List<Test_Questions> tqs = (List<Test_Questions>) customQuery("SELECT * FROM " + tableName + " WHERE TEST_ID=" + id);
        List<Question> questions = new ArrayList<>();
        for(Test_Questions tq : tqs) {
            Optional<Question> question = questionRepository.findById(tq.getQuestion_id());
            if(question.isEmpty())
                continue;
            questions.add(question.get());
        }
        return questions;
    }

    public void delete(int test_id, int question_id) {
        customQuery("DELETE FROM " + tableName + " WHERE TEST_ID=" + test_id + " AND QUESTION_ID=" + question_id);
    }
}

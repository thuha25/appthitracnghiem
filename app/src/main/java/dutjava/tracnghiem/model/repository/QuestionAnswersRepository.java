package dutjava.tracnghiem.model.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dutjava.tracnghiem.model.entity.Answer;
import dutjava.tracnghiem.model.entity.Question_Answers;
import dutjava.tracnghiem.util.database.CrudRepository;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class QuestionAnswersRepository extends CrudRepository<Question_Answers, Integer> {

    @Inject
    private AnswerRepository answerRepository;

    public QuestionAnswersRepository() {
        super(Question_Answers.class, Integer.class);
    }

    public List<Answer> getByQuestionId(int id) {
        List<Question_Answers> qas = (List<Question_Answers>) customQuery("SELECT * FROM " + tableName + " WHERE QUESTION_ID=" + id);
        List<Answer> answers = new ArrayList<>();
        for(Question_Answers qa : qas) {
            Optional<Answer> answer = answerRepository.findById(qa.getAnswer_id());
            if(answer.isEmpty())
                continue;
            answers.add(answer.get());
        }
        return answers;
    }

    public void delete(int question_id, int answer_id) {
        customQuery("DELETE FROM " + tableName + " WHERE QUESTION_ID=" + question_id + " AND ANSWER_ID=" + answer_id);
    }
}
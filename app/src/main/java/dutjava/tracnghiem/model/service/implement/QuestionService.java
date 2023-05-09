package dutjava.tracnghiem.model.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dutjava.tracnghiem.model.entity.Question;
import dutjava.tracnghiem.model.entity.Question_Answers;
import dutjava.tracnghiem.model.model.AnswerModel;
import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.model.repository.QuestionAnswersRepository;
import dutjava.tracnghiem.model.repository.QuestionRepository;
import dutjava.tracnghiem.model.service.IQuestionService;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class QuestionService implements IQuestionService {
    @Inject
    private QuestionRepository questionRepository;

    @Inject
    private QuestionAnswersRepository questionAnswersRepository;

    @Inject
    private AnswerService answerService;
    
    public QuestionModel toModel(Question entity) {
        List<AnswerModel> answers = questionAnswersRepository
        .getByQuestionId(entity.getId())    
        .stream()
        .map(e -> new AnswerModel(e))
        .toList();
        QuestionModel model = new QuestionModel(entity.getQuestionDescription(), entity.getPoint(), answers);
        model.setId(entity.getId());
        return model;
    }

    @Override
    public List<QuestionModel> getAll() {
        return questionRepository
        .findAll()
        .stream()
        .map(e -> toModel(e))
        .toList();
    }

    @Override
    public Optional<QuestionModel> getById(int id) {
        Optional<Question> entity = questionRepository.findById(id);
        if(entity.isEmpty())
            return Optional.empty();
        return Optional.of(toModel(entity.get()));
    }

    @Override
    public Optional<QuestionModel> save(QuestionModel model) {
        Question entity = new Question(model);
        entity = questionRepository.save(entity);
        List<AnswerModel> nAnswers = new ArrayList<>();
        for(int i = 0; i < model.getAnswers().size(); i++) {
            AnswerModel answer = model.getAnswers().get(i);
            answer = answerService.save(answer).get();
            nAnswers.add(answer);
        }
        model.setAnswers(nAnswers);
        int q_id = entity.getId();
        List<Question_Answers> nQAs = model.getAnswers().stream().map(e -> new Question_Answers(q_id, e.getId())).toList();
        List<Question_Answers> oQAs = questionAnswersRepository.getByQuestionId(q_id).stream().map(e -> new Question_Answers(q_id, e.getId())).toList();
        for(Question_Answers qa : oQAs)
            if(!nQAs.contains(qa)) {
                questionAnswersRepository.delete(qa.getQuestion_id(), qa.getAnswer_id());
                answerService.deleteById(qa.getAnswer_id());
            }
        for(Question_Answers qa : nQAs)
            if(!oQAs.contains(qa))
                questionAnswersRepository.save(qa);
        return Optional.of(toModel(entity));
    }

    @Override
    public void delete(QuestionModel model) {
        deleteById(model.getId());
    }

    @Override
    public void deleteById(int id) {
        List<Question_Answers> qas = questionAnswersRepository.getByQuestionId(id)
        .stream()
        .map(e -> new Question_Answers(id, e.getId()))
        .toList();
        qas.forEach(e -> questionAnswersRepository.delete(e.getQuestion_id(), e.getAnswer_id()));
        for(Question_Answers qa : qas)
            answerService.deleteById(qa.getAnswer_id());
        questionRepository.deleteById(id);
    }
}

package dutjava.tracnghiem.model.service.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dutjava.tracnghiem.model.entity.Test;
import dutjava.tracnghiem.model.entity.Test_Questions;
import dutjava.tracnghiem.model.model.QuestionModel;
import dutjava.tracnghiem.model.model.TestModel;
import dutjava.tracnghiem.model.repository.TestQuestionsRepository;
import dutjava.tracnghiem.model.repository.TestRepository;
import dutjava.tracnghiem.model.service.ITestService;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class TestService implements ITestService {

    @Inject
    private TestRepository testRepository;

    @Inject
    private TestQuestionsRepository testQuestionsRepository;

    @Inject
    private QuestionService questionService;

    public TestModel toModel(Test entity) {
        List<QuestionModel> questions = testQuestionsRepository
        .getByTestId(entity.getId())    
        .stream()
        .map(e -> questionService.toModel(e))
        .toList();
        TestModel test = new TestModel(entity.getName(), entity.getDescription(), questions);
        test.setId(entity.getId());
        return test;
    }

    @Override
    public List<TestModel> getAll() {
        return testRepository
        .findAll()
        .stream()
        .map(e -> toModel(e))
        .toList();
    }

    @Override
    public Optional<TestModel> getById(int id) {
        Optional<Test> entity = testRepository.findById(id);
        if(entity.isEmpty())
            return Optional.empty();
        return Optional.of(toModel(entity.get()));
    }

    @Override
    public Optional<TestModel> save(TestModel model) {
        Test entity = new Test(model);
        entity = testRepository.save(entity);
        List<QuestionModel> nAnswers = new ArrayList<>();
        for(int i = 0; i < model.getQuestions().size(); i++) {
            QuestionModel answer = model.getQuestions().get(i);
            answer = questionService.save(answer).get();
            nAnswers.add(answer);
        }
        model.setQuestions(nAnswers);
        int q_id = entity.getId();
        List<Test_Questions> nQAs = model.getQuestions().stream().map(e -> new Test_Questions(q_id, e.getId())).toList();
        List<Test_Questions> oQAs = testQuestionsRepository.getByTestId(q_id).stream().map(e -> new Test_Questions(q_id, e.getId())).toList();
        for(Test_Questions qa : oQAs)
            if(!nQAs.contains(qa)) {
                testQuestionsRepository.delete(qa.getTest_id(), qa.getQuestion_id());
                questionService.deleteById(qa.getQuestion_id());
            }
        for(Test_Questions qa : nQAs)
            if(!oQAs.contains(qa))
                testQuestionsRepository.save(qa);
        return Optional.of(toModel(entity));
    }

    @Override
    public void delete(TestModel model) {
        deleteById(model.getId());
    }

    @Override
    public void deleteById(int id) {
        List<Test_Questions> qas = testQuestionsRepository.getByTestId(id)
        .stream()
        .map(e -> new Test_Questions(id, e.getId()))
        .toList();
        qas.forEach(e -> testQuestionsRepository.delete(e.getQuestion_id(), e.getQuestion_id()));
        for(Test_Questions qa : qas)
            questionService.deleteById(qa.getQuestion_id());
        testRepository.deleteById(id);
    }  
}

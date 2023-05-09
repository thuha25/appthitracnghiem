package dutjava.tracnghiem.model.service.implement;

import java.util.List;
import java.util.Optional;

import dutjava.tracnghiem.model.entity.Answer;
import dutjava.tracnghiem.model.model.AnswerModel;
import dutjava.tracnghiem.model.repository.AnswerRepository;
import dutjava.tracnghiem.model.service.IAnswerService;
import dutjava.tracnghiem.util.dependency_injection.Inject;

public class AnswerService implements IAnswerService {
    @Inject
    private AnswerRepository answerRepository;

    public List<AnswerModel> getAll() {
        return answerRepository.findAll().stream().map(e -> new AnswerModel(e)).toList();
    }

    public Optional<AnswerModel> getById(int id) {
        Optional<Answer> entity = answerRepository.findById(id);
        if(entity.isEmpty())
            return Optional.empty();
        return Optional.of(new AnswerModel(entity.get()));
    }

    public Optional<AnswerModel> save(AnswerModel model) {
        Answer entity = new Answer(model);
        entity = answerRepository.save(entity);
        return Optional.of(new AnswerModel(entity));
    }

    @Override
    public void delete(AnswerModel model) {
        answerRepository.deleteById(model.getId());
    }

    @Override
    public void deleteById(int id) {
        answerRepository.deleteById(id);
    }
}

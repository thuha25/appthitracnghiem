package dutjava.tracnghiem.container;

import dutjava.tracnghiem.model.repository.AnswerRepository;
import dutjava.tracnghiem.model.repository.QuestionAnswersRepository;
import dutjava.tracnghiem.model.repository.QuestionRepository;
import dutjava.tracnghiem.model.repository.TestQuestionsRepository;
import dutjava.tracnghiem.model.repository.TestRepository;
import dutjava.tracnghiem.model.service.implement.AnswerService;
import dutjava.tracnghiem.model.service.implement.QuestionService;
import dutjava.tracnghiem.model.service.implement.TestService;
import dutjava.tracnghiem.util.dependency_injection.Host;
import dutjava.tracnghiem.util.dependency_injection.HostBuilder;

public class AppHost {
    static public Host host = new HostBuilder()
    .add(AnswerRepository.class, HostType.AnswerRepository)
    .add(QuestionRepository.class, HostType.QuestionRepository)
    .add(TestRepository.class, HostType.TestRepository)
    .add(QuestionAnswersRepository.class, HostType.QuestionAnswersRepository)
    .add(TestQuestionsRepository.class, HostType.TestQuestionsRepository)
    .add(AnswerService.class, HostType.AnswerService)
    .add(QuestionService.class, HostType.QuestionService)
    .add(TestService.class, HostType.TestService)
    .build();
}

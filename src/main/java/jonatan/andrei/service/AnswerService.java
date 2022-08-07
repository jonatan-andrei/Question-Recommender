package jonatan.andrei.service;

import jonatan.andrei.repository.AnswerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class AnswerService {

    @Inject
    AnswerRepository answerRepository;

    @Transactional
    public void registerBestAnswer(Long postId, boolean selected) {
        answerRepository.registerBestAnswer(postId, selected);
    }
}

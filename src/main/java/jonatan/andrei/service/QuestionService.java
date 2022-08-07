package jonatan.andrei.service;

import jonatan.andrei.model.Question;
import jonatan.andrei.repository.QuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class QuestionService {

    @Inject
    QuestionRepository questionRepository;

    @Transactional
    public void registerDuplicateQuestion(Long postId, Long duplicateQuestionId) {
        questionRepository.registerDuplicateQuestion(postId, duplicateQuestionId);
    }
}

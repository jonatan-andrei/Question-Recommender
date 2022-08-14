package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.AnswerFactory;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.AnswerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class AnswerService {

    @Inject
    AnswerRepository answerRepository;

    public Answer save(CreatePostRequestDto createPostRequestDto, User user, Question question) {
        return answerRepository.save(
                AnswerFactory.createAnswer(createPostRequestDto, question.getPostId(), user.getUserId()));
    }

    public Answer update(Answer existingAnswer, UpdatePostRequestDto updatePostRequestDto) {
        return answerRepository.save(
                AnswerFactory.overwrite(existingAnswer, updatePostRequestDto));
    }

    @Transactional
    public void registerBestAnswer(Question question, Answer answer, boolean selected) {
        if (!answer.getQuestionId().equals(question.getPostId())) {
            throw new InconsistentIntegratedDataException("Answer " + answer.getIntegrationPostId() + " is not an answer to question " + question.getIntegrationPostId());
        }

        if (selected && answerRepository.findByQuestionIdAndBestAnswer(question.getPostId(), true).isPresent()) {
            throw new InconsistentIntegratedDataException("Question " + question.getIntegrationPostId() + " already has a best answer selected");
        }

        answerRepository.registerBestAnswer(answer.getPostId(), selected);
    }
}

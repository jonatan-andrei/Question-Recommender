package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.AnswerFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.AnswerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class AnswerService {

    @Inject
    AnswerRepository answerRepository;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    public Answer save(CreatePostRequestDto createPostRequestDto, User user, Question question, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        Answer answer = answerRepository.save(
                AnswerFactory.createAnswer(createPostRequestDto, question.getPostId(), user.getUserId()));
        userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserActionType.QUESTION_ANSWERED, UserActionUpdateType.INCREASE);
        userTagService.updateNumberQuestionsByAction(user, questionTags, UserActionType.QUESTION_ANSWERED, UserActionUpdateType.INCREASE);
        return answer;
    }

    public Answer update(Answer existingAnswer, UpdatePostRequestDto updatePostRequestDto) {
        return answerRepository.save(
                AnswerFactory.overwrite(existingAnswer, updatePostRequestDto));
    }

    public Answer findById(Long postId){
        return answerRepository.findById(postId).orElse(null);
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

    public void clear() {
        answerRepository.deleteAll();
    }
}

package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.factory.AnswerCommentFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.AnswerCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class AnswerCommentService {

    @Inject
    AnswerCommentRepository answerCommentRepository;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    public AnswerComment save(CreatePostRequestDto createPostRequestDto, User user, Answer answer, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        AnswerComment answerComment = answerCommentRepository.save(
                AnswerCommentFactory.createAnswerComment(createPostRequestDto, answer.getPostId(), user.getUserId()));
        userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserActionType.QUESTION_COMMENTED, UserActionUpdateType.INCREASE);
        userTagService.updateNumberQuestionsByAction(user, questionTags, UserActionType.QUESTION_COMMENTED, UserActionUpdateType.INCREASE);
        return answerComment;
    }

    public AnswerComment update(AnswerComment existingAnswerComment, UpdatePostRequestDto updatePostRequestDto) {
        return answerCommentRepository.save(
                AnswerCommentFactory.overwrite(existingAnswerComment, updatePostRequestDto));
    }

    public void clear() {
        answerCommentRepository.deleteAll();
    }
}

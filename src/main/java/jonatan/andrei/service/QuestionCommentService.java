package jonatan.andrei.service;

import jonatan.andrei.domain.UserAction;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.factory.QuestionCommentFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.QuestionCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class QuestionCommentService {

    @Inject
    QuestionCommentRepository questionCommentRepository;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    public QuestionComment save(CreatePostRequestDto createPostRequestDto, User user, Question question, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        QuestionComment questionComment = questionCommentRepository.save(
                QuestionCommentFactory.createQuestionComment(createPostRequestDto, question.getPostId(), user.getUserId()));
        userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserAction.QUESTION_COMMENTED, UserActionUpdateType.INCREASE);
        return questionComment;
    }

    public QuestionComment update(QuestionComment existingQuestionComment, UpdatePostRequestDto updatePostRequestDto) {
        return questionCommentRepository.save(
                QuestionCommentFactory.overwrite(existingQuestionComment, updatePostRequestDto));
    }

}

package jonatan.andrei.service;

import jonatan.andrei.domain.UserAction;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.QuestionFollowerRequestDto;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.QuestionFollowerFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.QuestionFollowerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@ApplicationScoped
public class QuestionFollowerService {

    @Inject
    QuestionFollowerRepository questionFollowerRepository;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    public Question registerQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto, User user, Question question, List<QuestionCategory> questionCategories, List<QuestionTag> questionTags) {
        Optional<QuestionFollower> existingQuestionFollower = findByUserIdAndQuestionId(user, question);
        if (existingQuestionFollower.isEmpty() && questionFollowerRequestDto.isFollowed()) {
            questionFollowerRepository.save(QuestionFollowerFactory.newQuestionFollower(questionFollowerRequestDto, user, question));
            question.setFollowers(question.getFollowers() + 1);
            userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserAction.QUESTION_FOLLOWED, UserActionUpdateType.INCREASE);
        } else if (existingQuestionFollower.isPresent() && !questionFollowerRequestDto.isFollowed()) {
            questionFollowerRepository.delete(existingQuestionFollower.get());
            question.setFollowers(question.getFollowers() - 1);
            userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserAction.QUESTION_FOLLOWED, UserActionUpdateType.DECREASE);
        }
        return question;
    }

    public Optional<QuestionFollower> findByUserIdAndQuestionId(User user, Question question) {
        return questionFollowerRepository.findByUserIdAndQuestionId(user.getUserId(), question.getPostId());
    }

    public void validateQuestionFollowerRequest(QuestionFollowerRequestDto questionFollowerRequestDto) {
        if (isNull(questionFollowerRequestDto.getIntegrationQuestionId())) {
            throw new RequiredDataException("Attribute 'integrationQuestionId' is required");
        }
        if (isNull(questionFollowerRequestDto.getIntegrationUserId())) {
            throw new RequiredDataException("Attribute 'integrationUserId' is required");
        }
    }
}

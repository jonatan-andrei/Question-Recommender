package jonatan.andrei.service;

import jonatan.andrei.domain.UserAction;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.factory.UserCategoryFactory;
import jonatan.andrei.model.Post;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserCategory;
import jonatan.andrei.repository.UserCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserCategoryService {

    @Inject
    UserCategoryRepository userCategoryRepository;

    public void updateNumberQuestionsViewed(List<User> users, List<QuestionCategory> categories) {
        for (User user : users) {
            updateNumberQuestionsByAction(user, categories, UserAction.QUESTION_VIEWED, UserActionUpdateType.INCREASE);
        }
    }

    public void updateNumberQuestionsVoted(User user, Post post, List<QuestionCategory> categories, UserActionUpdateType userActionUpdateType) {
        UserAction userAction = switch (post.getPostType()) {
            case QUESTION -> UserAction.QUESTION_VOTED;
            case ANSWER -> UserAction.ANSWER_VOTED;
            case QUESTION_COMMENT, ANSWER_COMMENT -> UserAction.COMMENT_VOTED;
        };

        updateNumberQuestionsByAction(user, categories, userAction, userActionUpdateType);
    }

    public void updateNumberQuestionsByAction(User user, List<QuestionCategory> categories, UserAction userAction, UserActionUpdateType userActionUpdateType) {
        List<Long> categoriesIds = categories.stream().map(QuestionCategory::getCategoryId).collect(Collectors.toList());
        List<UserCategory> userCategories = userCategoryRepository.findByUserIdAndCategoryIdIn(user.getUserId(), categoriesIds);
        for (Long category : categoriesIds) {
            UserCategory userCategory = findOrCreateUserCategory(userCategories, user.getUserId(), category);
            switch (userAction) {
                case QUESTION_ASKED ->
                        userCategory.setNumberQuestionsAsked(userCategory.getNumberQuestionsAsked() + userActionUpdateType.getValue());
                case QUESTION_VIEWED ->
                        userCategory.setNumberQuestionsViewed(userCategory.getNumberQuestionsViewed() + userActionUpdateType.getValue());
                case QUESTION_VOTED ->
                        userCategory.setNumberQuestionsVoted(userCategory.getNumberQuestionsVoted() + userActionUpdateType.getValue());
                case QUESTION_ANSWERED ->
                        userCategory.setNumberQuestionsAnswered(userCategory.getNumberQuestionsAnswered() + userActionUpdateType.getValue());
                case QUESTION_COMMENTED ->
                        userCategory.setNumberQuestionsCommented(userCategory.getNumberQuestionsCommented() + userActionUpdateType.getValue());
                case QUESTION_FOLLOWED ->
                        userCategory.setNumberQuestionsFollowed(userCategory.getNumberQuestionsFollowed() + userActionUpdateType.getValue());
                case ANSWER_VOTED ->
                        userCategory.setNumberAnswersVoted(userCategory.getNumberAnswersVoted() + userActionUpdateType.getValue());
                case COMMENT_VOTED ->
                        userCategory.setNumberCommentsVoted(userCategory.getNumberCommentsVoted() + userActionUpdateType.getValue());
            }
        }
        userCategoryRepository.saveAll(userCategories);
    }

    private UserCategory findOrCreateUserCategory(List<UserCategory> userCategories, Long userId, Long categoryId) {
        UserCategory userCategory = userCategories.stream().filter(
                uc -> uc.getCategoryId().equals(categoryId)).findFirst().orElse(null);
        if (isNull(userCategory)) {
            userCategory = UserCategoryFactory.newUserCategory(userId, categoryId);
            userCategories.add(userCategory);
        }
        return userCategory;
    }
}

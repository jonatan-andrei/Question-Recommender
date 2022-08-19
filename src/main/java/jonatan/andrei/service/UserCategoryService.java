package jonatan.andrei.service;

import jonatan.andrei.domain.UserAction;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.UserPreference;
import jonatan.andrei.factory.UserCategoryFactory;
import jonatan.andrei.model.*;
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

    public void saveUserPreferences(User user, List<Category> categories, UserPreference userPreference) {
        List<UserCategory> userCategories = findOrCreateUserCategories(categories, user.getUserId());
        userCategories.forEach(uc -> updateUserPreference(uc, userPreference, true));

        List<UserCategory> userCategoriesToUpdateToFalse = findUserCategoriesToUpdateToFalse(user, userPreference, categories);
        userCategoriesToUpdateToFalse.forEach(uc -> updateUserPreference(uc, userPreference, false));
        userCategories.addAll(userCategoriesToUpdateToFalse);

        userCategoryRepository.saveAll(userCategories);
    }

    private void updateUserPreference(UserCategory userCategory, UserPreference userPreference, boolean active) {
        if (userPreference.equals(UserPreference.EXPLICIT)) {
            userCategory.setExplicitRecommendation(active);
        } else {
            userCategory.setIgnored(active);
        }
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

    private List<UserCategory> findOrCreateUserCategories(List<Category> categories, Long userId) {
        List<Long> categoriesIds = categories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        List<UserCategory> existingUserCategories = userCategoryRepository.findByUserIdAndCategoryIdIn(userId, categoriesIds);
        return categories.stream().map(c -> findOrCreateUserCategory(existingUserCategories, userId, c.getCategoryId()))
                .collect(Collectors.toList());
    }

    private List<UserCategory> findUserCategoriesToUpdateToFalse(User user, UserPreference userPreference, List<Category> categories) {
        List<UserCategory> existingUserCategoriesByType = UserPreference.EXPLICIT.equals(userPreference)
                ? userCategoryRepository.findByUserIdAndExplicitRecommendation(user.getUserId(), true)
                : userCategoryRepository.findByUserIdAndIgnored(user.getUserId(), true);
        List<Long> categoriesIds = categories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        return existingUserCategoriesByType.stream()
                .filter(uc -> !categoriesIds.contains(uc.getCategoryId()))
                .collect(Collectors.toList());
    }
}

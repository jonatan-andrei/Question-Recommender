package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.UserPreferenceType;
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

    @Inject
    CategoryService categoryService;
    public void updateNumberQuestionsViewed(List<User> users, List<QuestionCategory> categories) {
        for (User user : users) {
            updateNumberQuestionsByAction(user, categories, UserActionType.QUESTION_VIEWED, UserActionUpdateType.INCREASE);
        }
    }

    public void updateNumberQuestionsVoted(User user, Post post, List<QuestionCategory> categories, UserActionUpdateType userActionUpdateType, boolean upvoted) {
        UserActionType userActionType = switch (post.getPostType()) {
            case QUESTION -> upvoted ? UserActionType.QUESTION_UPVOTED : UserActionType.QUESTION_DOWNVOTED;
            case ANSWER -> upvoted ? UserActionType.ANSWER_UPVOTED : UserActionType.ANSWER_DOWNVOTED;
            case QUESTION_COMMENT, ANSWER_COMMENT ->
                    upvoted ? UserActionType.COMMENT_UPVOTED : UserActionType.COMMENT_DOWNVOTED;
        };

        updateNumberQuestionsByAction(user, categories, userActionType, userActionUpdateType);
    }

    public void updateNumberQuestionsByAction(User user, List<QuestionCategory> categories, UserActionType userActionType, UserActionUpdateType userActionUpdateType) {
        List<Long> categoriesIds = categories.stream().map(QuestionCategory::getCategoryId).collect(Collectors.toList());
        List<UserCategory> userCategories = userCategoryRepository.findByUserIdAndCategoryIdIn(user.getUserId(), categoriesIds);
        for (Long category : categoriesIds) {
            UserCategory userCategory = findOrCreateUserCategory(userCategories, user.getUserId(), category);
            switch (userActionType) {
                case QUESTION_ASKED ->
                        userCategory.setNumberQuestionsAsked(userCategory.getNumberQuestionsAsked().add(userActionUpdateType.getValue()));
                case QUESTION_VIEWED ->
                        userCategory.setNumberQuestionsViewed(userCategory.getNumberQuestionsViewed().add(userActionUpdateType.getValue()));
                case QUESTION_ANSWERED ->
                        userCategory.setNumberQuestionsAnswered(userCategory.getNumberQuestionsAnswered().add(userActionUpdateType.getValue()));
                case QUESTION_COMMENTED ->
                        userCategory.setNumberQuestionsCommented(userCategory.getNumberQuestionsCommented().add(userActionUpdateType.getValue()));
                case QUESTION_FOLLOWED ->
                        userCategory.setNumberQuestionsFollowed(userCategory.getNumberQuestionsFollowed().add(userActionUpdateType.getValue()));
                case QUESTION_UPVOTED ->
                        userCategory.setNumberQuestionsUpvoted(userCategory.getNumberQuestionsUpvoted().add(userActionUpdateType.getValue()));
                case QUESTION_DOWNVOTED ->
                        userCategory.setNumberQuestionsDownvoted(userCategory.getNumberQuestionsDownvoted().add(userActionUpdateType.getValue()));
                case ANSWER_UPVOTED ->
                        userCategory.setNumberAnswersUpvoted(userCategory.getNumberAnswersUpvoted().add(userActionUpdateType.getValue()));
                case ANSWER_DOWNVOTED ->
                        userCategory.setNumberAnswersDownvoted(userCategory.getNumberAnswersDownvoted().add(userActionUpdateType.getValue()));
                case COMMENT_UPVOTED ->
                        userCategory.setNumberCommentsUpvoted(userCategory.getNumberCommentsUpvoted().add(userActionUpdateType.getValue()));
                case COMMENT_DOWNVOTED ->
                        userCategory.setNumberCommentsDownvoted(userCategory.getNumberCommentsDownvoted().add(userActionUpdateType.getValue()));
            }
        }
        userCategoryRepository.saveAll(userCategories);
        categoryService.updateNumberQuestionsByAction(categories, userActionType, userActionUpdateType);
    }

    public void saveUserPreferences(User user, List<Category> categories, UserPreferenceType userPreference) {
        List<UserCategory> userCategories = findOrCreateUserCategories(categories, user.getUserId());
        userCategories.forEach(uc -> updateUserPreference(uc, userPreference, true));

        List<UserCategory> userCategoriesToUpdateToFalse = findUserCategoriesToUpdateToFalse(user, userPreference, categories);
        userCategoriesToUpdateToFalse.forEach(uc -> updateUserPreference(uc, userPreference, false));
        userCategories.addAll(userCategoriesToUpdateToFalse);

        userCategoryRepository.saveAll(userCategories);
    }

    private void updateUserPreference(UserCategory userCategory, UserPreferenceType userPreference, boolean active) {
        if (userPreference.equals(UserPreferenceType.EXPLICIT)) {
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

    private List<UserCategory> findUserCategoriesToUpdateToFalse(User user, UserPreferenceType userPreference, List<Category> categories) {
        List<UserCategory> existingUserCategoriesByType = UserPreferenceType.EXPLICIT.equals(userPreference)
                ? userCategoryRepository.findByUserIdAndExplicitRecommendation(user.getUserId(), true)
                : userCategoryRepository.findByUserIdAndIgnored(user.getUserId(), true);
        List<Long> categoriesIds = categories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        return existingUserCategoriesByType.stream()
                .filter(uc -> !categoriesIds.contains(uc.getCategoryId()))
                .collect(Collectors.toList());
    }

    public void clear() {
        userCategoryRepository.deleteAll();
    }
}

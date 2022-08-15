package jonatan.andrei.factory;

import jonatan.andrei.model.UserCategory;

public class UserCategoryFactory {

    public static UserCategory newUserCategory(Long userId, Long categoryId) {
        return UserCategory.builder()
                .userId(userId)
                .categoryId(categoryId)
                .numberQuestionsAsked(0)
                .numberQuestionsViewed(0)
                .numberAnswersVoted(0)
                .numberQuestionsAnswered(0)
                .numberQuestionsFollowed(0)
                .numberAnswersVoted(0)
                .numberCommentsVoted(0)
                .explicitRecommendation(false)
                .ignored(false)
                .build();
    }
}

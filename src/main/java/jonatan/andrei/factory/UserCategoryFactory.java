package jonatan.andrei.factory;

import jonatan.andrei.model.UserCategory;

public class UserCategoryFactory {

    public static UserCategory newUserCategory(Long userId, Long categoryId) {
        return UserCategory.builder()
                .userId(userId)
                .categoryId(categoryId)
                .numberQuestionsAsked(0)
                .numberQuestionsViewed(0)
                .numberQuestionsAnswered(0)
                .numberQuestionsCommented(0)
                .numberQuestionsFollowed(0)
                .numberQuestionsUpvoted(0)
                .numberAnswersUpvoted(0)
                .numberCommentsUpvoted(0)
                .numberQuestionsDownvoted(0)
                .numberAnswersDownvoted(0)
                .numberCommentsDownvoted(0)
                .explicitRecommendation(false)
                .ignored(false)
                .build();
    }
}

package jonatan.andrei.factory;

import jonatan.andrei.model.UserCategory;

import java.math.BigDecimal;

public class UserCategoryFactory {

    public static UserCategory newUserCategory(Long userId, Long categoryId) {
        return UserCategory.builder()
                .userId(userId)
                .categoryId(categoryId)
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .explicitRecommendation(false)
                .ignored(false)
                .build();
    }
}

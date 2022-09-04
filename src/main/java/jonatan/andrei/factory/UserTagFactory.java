package jonatan.andrei.factory;

import jonatan.andrei.model.UserTag;

import java.math.BigDecimal;

public class UserTagFactory {

    public static UserTag newUserTag(Long userId, Long tagId) {
        return UserTag.builder()
                .userId(userId)
                .tagId(tagId)
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .explicitRecommendation(false)
                .ignored(false)
                .build();
    }
}

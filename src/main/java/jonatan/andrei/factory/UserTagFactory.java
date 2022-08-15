package jonatan.andrei.factory;

import jonatan.andrei.model.UserTag;

public class UserTagFactory {

    public static UserTag newUserTag(Long userId, Long tagId) {
        return UserTag.builder()
                .userId(userId)
                .tagId(tagId)
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

package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Map.entry;
import static jonatan.andrei.domain.RecommendationSettingsType.*;
import static jonatan.andrei.domain.RecommendationSettingsType.MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS;

@Getter
@AllArgsConstructor
public enum RecommendationChannelType {

    RECOMMENDED_LIST(Map.ofEntries(
            entry(NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7)),
            entry(NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365)),
            entry(RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100)),
            entry(RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100)),
            entry(RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30)),
            entry(RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10)),
            entry(RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50)),
            entry(RELEVANCE_DUPLICATE_QUESTION, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.0001)),
            entry(RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(1)),
            entry(RELEVANCE_QUESTION_NUMBER_UPVOTES, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTION_NUMBER_DOWNVOTES, BigDecimal.valueOf(0)),
            entry(RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-50)),
            entry(RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20)),
            entry(RELEVANCE_USER_FOLLOWER_ASKER, BigDecimal.valueOf(0)),
            entry(RELEVANCE_USER_ALREADY_VIEWED, BigDecimal.valueOf(0)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_LIST, BigDecimal.valueOf(0)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_EMAIL, BigDecimal.valueOf(0)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_NOTIFICATION, BigDecimal.valueOf(0)),
            entry(MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10)),
            entry(RELEVANCE_EXPLICIT_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(0)),
            entry(RELEVANCE_EXPLICIT_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(300)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(0)),
            entry(DEFAULT_LENGTH, BigDecimal.valueOf(20)),
            entry(MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(20))
    )),

    RECOMMENDED_EMAIL(Map.ofEntries(
            entry(NUMBER_OF_DAYS_QUESTION_IS_RECENT, BigDecimal.valueOf(7)),
            entry(NUMBER_OF_DAYS_QUESTION_IS_RELEVANT, BigDecimal.valueOf(365)),
            entry(RELEVANCE_PUBLICATION_DATE_RECENT, BigDecimal.valueOf(100)),
            entry(RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(100)),
            entry(RELEVANCE_UPDATE_DATE_RECENT, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTION_HAS_ANSWER, BigDecimal.valueOf(-30)),
            entry(RELEVANCE_QUESTION_PER_ANSWER, BigDecimal.valueOf(-10)),
            entry(RELEVANCE_QUESTION_HAS_BEST_ANSWER, BigDecimal.valueOf(-50)),
            entry(RELEVANCE_DUPLICATE_QUESTION, BigDecimal.valueOf(-100)),
            entry(RELEVANCE_QUESTION_NUMBER_VIEWS, BigDecimal.valueOf(0.2)),
            entry(RELEVANCE_QUESTION_NUMBER_FOLLOWERS, BigDecimal.valueOf(10)),
            entry(RELEVANCE_QUESTION_NUMBER_UPVOTES, BigDecimal.valueOf(10)),
            entry(RELEVANCE_QUESTION_NUMBER_DOWNVOTES, BigDecimal.valueOf(-10)),
            entry(RELEVANCE_USER_ALREADY_ANSWERED, BigDecimal.valueOf(-25)),
            entry(RELEVANCE_USER_ALREADY_COMMENTED, BigDecimal.valueOf(-20)),
            entry(RELEVANCE_USER_FOLLOWER_ASKER, BigDecimal.valueOf(100)),
            entry(RELEVANCE_USER_ALREADY_VIEWED, BigDecimal.valueOf(-20)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_LIST, BigDecimal.valueOf(-15)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_EMAIL, BigDecimal.valueOf(-15)),
            entry(RELEVANCE_USER_ALREADY_VIEWED_IN_NOTIFICATION, BigDecimal.valueOf(-30)),
            entry(MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10)),
            entry(RELEVANCE_EXPLICIT_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(20)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-2)),
            entry(RELEVANCE_EXPLICIT_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_TAG, BigDecimal.valueOf(20)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-2)),
            entry(DEFAULT_LENGTH, BigDecimal.valueOf(5)),
            entry(MINIMUM_LENGTH, BigDecimal.valueOf(3)),
            entry(ENABLE_CHANNEL, BigDecimal.valueOf(0)),
            entry(DEFAULT_HOUR_OF_THE_DAY_TO_SEND_RECOMMENDATIONS, BigDecimal.valueOf(8)),
            entry(DAYS_OF_USER_INACTIVITY_TO_SUSPEND_RECOMMENDATIONS, BigDecimal.valueOf(60)),
            entry(MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST, BigDecimal.valueOf(10)),
            entry(MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS, BigDecimal.valueOf(60))
    )),

    QUESTION_NOTIFICATION(Map.ofEntries(
            entry(RELEVANCE_USER_FOLLOWER_ASKER, BigDecimal.valueOf(100)),
            entry(MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE, BigDecimal.valueOf(10)),
            entry(RELEVANCE_EXPLICIT_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(50)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY, BigDecimal.valueOf(20)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY, BigDecimal.valueOf(-2)),
            entry(RELEVANCE_EXPLICIT_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_ASKED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_ANSWERED_IN_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_COMMENTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_VIEWED_IN_TAG, BigDecimal.valueOf(100)),
            entry(RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_UPVOTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_ANSWERS_UPVOTED_IN_TAG, BigDecimal.valueOf(50)),
            entry(RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-5)),
            entry(RELEVANCE_COMMENTS_UPVOTED_IN_TAG, BigDecimal.valueOf(20)),
            entry(RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG, BigDecimal.valueOf(-2)),
            entry(MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER, BigDecimal.valueOf(200)),
            entry(ENABLE_CHANNEL, BigDecimal.valueOf(0)),
            entry(DAYS_OF_USER_INACTIVITY_TO_SUSPEND_RECOMMENDATIONS, BigDecimal.valueOf(30)),
            entry(MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST, BigDecimal.valueOf(10))
    ));

    private Map<RecommendationSettingsType, BigDecimal> settings;

    public static Map<RecommendationSettingsType, BigDecimal> findSettingsByChannel(RecommendationChannelType channel) {
        return Stream.of(values())
                .filter(c -> c.equals(channel))
                .findFirst().get().getSettings();
    }

}

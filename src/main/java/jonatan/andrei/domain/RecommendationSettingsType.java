package jonatan.andrei.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecommendationSettingsType {

    NUMBER_OF_DAYS_QUESTION_IS_RECENT(),
    NUMBER_OF_DAYS_QUESTION_IS_RELEVANT(),
    RELEVANCE_PUBLICATION_DATE_RECENT(),
    RELEVANCE_PUBLICATION_DATE_RELEVANT(),
    RELEVANCE_UPDATE_DATE_RECENT(),
    RELEVANCE_QUESTION_HAS_ANSWER(),
    RELEVANCE_QUESTION_PER_ANSWER(),
    RELEVANCE_QUESTION_HAS_BEST_ANSWER(),
    RELEVANCE_DUPLICATE_QUESTION(),
    RELEVANCE_QUESTION_NUMBER_VIEWS(),
    RELEVANCE_QUESTION_NUMBER_FOLLOWERS(),
    RELEVANCE_QUESTION_NUMBER_UPVOTES(),
    RELEVANCE_QUESTION_NUMBER_DOWNVOTES(),
    RELEVANCE_USER_ALREADY_ANSWERED(),
    RELEVANCE_USER_ALREADY_COMMENTED(),
    RELEVANCE_USER_FOLLOWER_ASKER(),
    RELEVANCE_USER_ALREADY_VIEWED(),
    RELEVANCE_USER_ALREADY_VIEWED_IN_LIST(),
    RELEVANCE_USER_ALREADY_VIEWED_IN_EMAIL(),
    RELEVANCE_USER_ALREADY_VIEWED_IN_NOTIFICATION(),
    MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE(),
    RELEVANCE_EXPLICIT_CATEGORY(),
    RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY(),
    RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY(),
    RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY(),
    RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY(),
    RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY(),
    RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY(),
    RELEVANCE_EXPLICIT_TAG(),
    RELEVANCE_QUESTIONS_ASKED_IN_TAG(),
    RELEVANCE_QUESTIONS_ANSWERED_IN_TAG(),
    RELEVANCE_QUESTIONS_COMMENTED_IN_TAG(),
    RELEVANCE_QUESTIONS_VIEWED_IN_TAG(),
    RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG(),
    RELEVANCE_QUESTIONS_UPVOTED_IN_TAG(),
    RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG(),
    RELEVANCE_ANSWERS_UPVOTED_IN_TAG(),
    RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG(),
    RELEVANCE_COMMENTS_UPVOTED_IN_TAG(),
    RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG(),
    DEFAULT_LENGTH(),
    MINIMUM_LENGTH(),
    MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER(),
    ENABLE_CHANNEL(),
    DEFAULT_HOUR_OF_THE_DAY_TO_SEND_RECOMMENDATIONS(),
    DAYS_OF_USER_INACTIVITY_TO_SUSPEND_RECOMMENDATIONS(),
    MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST(),
    MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS(),

    RECOMMENDATION_ALGORITHM_FOR_CATEGORY_OR_TAG();

}

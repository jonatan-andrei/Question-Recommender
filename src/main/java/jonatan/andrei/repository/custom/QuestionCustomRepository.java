package jonatan.andrei.repository.custom;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedQuestionOfPageDto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jonatan.andrei.domain.RecommendationSettingsType.*;

@ApplicationScoped
public class QuestionCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<RecommendedQuestionOfPageDto> findRecommendedList(Long userId, Integer pageNumber, Integer lengthQuestionListPage, Long recommendedListId, Map<RecommendationSettingsType, Integer> recommendationSettings, LocalDateTime dateOfRecommendations) {
        Query nativeQuery = entityManager.createNativeQuery("""
                 SELECT q.post_id, p.integration_post_id,
                 
                 -- PUBLICATION DATE RECENT SCORE
                 (GREATEST(:numberOfDaysQuestionIsRecent - EXTRACT(EPOCH FROM :dateOfRecommendations - p.publication_date)/:numberOfSecondsInDay, 0) * :relevancePublicationDateRecent / :numberOfDaysQuestionIsRecent)
                 -- eg: (GREATEST(7 - EXTRACT(EPOCH FROM now() - p.publication_date)/86400, 0) * 100 / 7)
                 -- Extract the seconds between the publish date and the current date
                 -- Considers that the date is not recent if the number of days is greater than the parameter
                 -- Multiply the value obtained by the desired importance (eg: 100) and divide by the number of days the question is recent (eg: 7)
                 
                 +
                 
                 -- PUBLICATION DATE RELEVANT SCORE
                 (GREATEST(:numberOfDaysQuestionIsRelevant - EXTRACT(EPOCH FROM :dateOfRecommendations - p.publication_date)/:numberOfSecondsInDay, 0) * :relevancePublicationDateRelevant / :numberOfDaysQuestionIsRelevant)
                 -- eg: (GREATEST(365 - EXTRACT(EPOCH FROM now() - p.publication_date)/86400, 0) * 100 / 365)
                 -- Extract the seconds between the publish date and the current date
                 -- Considers that the date is irrelevant if the number of days is greater than the parameter
                 -- Multiply the value obtained by the desired importance (eg: 100) and divide by the number of days the question is relevant (eg: 365)
                 
                 +
                 
                 -- UPDATE DATE RECENT SCORE
                 (GREATEST(:numberOfDaysQuestionIsRecent - EXTRACT(EPOCH FROM :dateOfRecommendations - p.update_date)/:numberOfSecondsInDay, 0) * :relevanceUpdateDateRecent / :numberOfDaysQuestionIsRecent)
                 -- eg: (GREATEST(7 - EXTRACT(EPOCH FROM now() - p.update_date)/86400, 0) * 50 / 7)
                 -- Extract the seconds between the update date and the current date
                 -- Considers that the date is not recent if the number of days is greater than the parameter
                 -- Multiply the value obtained by the desired importance (eg: 50) and divide by the number of days the question is recent (eg: 7)
                 
                 +
                                   
                 -- USER TAG SCORE
                 (SELECT
                    COALESCE(SUM(
                    
                    -- TAG - EXPLICIT RECOMMENDATION
                    CASE
                        WHEN ut.explicit_recommendation THEN :relevanceExplicitRecommendationTag
                        ELSE 0
                    END
                    
                    +
                    
                    -- TAG - NUMBER QUESTIONS ASKED
                    COALESCE(ut.number_questions_asked * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_asked >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsAskedInTag
                        ELSE :relevanceQuestionsAskedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_asked
                      END, 0)) / NULLIF(ufr.number_questions_asked,0),0)
                      
                    +
                    
                    -- TAG - NUMBER QUESTIONS ANSWERED
                    COALESCE(ut.number_questions_answered * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_answered >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsAnsweredInTag
                        ELSE :relevanceQuestionsAnsweredInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_answered
                      END, 0)) / NULLIF(ufr.number_questions_answered,0),0)
                      
                    +
                      
                    -- TAG - NUMBER QUESTIONS COMMENTED
                    COALESCE(ut.number_questions_commented * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_commented >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsCommentedInTag
                        ELSE :relevanceQuestionsCommentedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_commented
                      END, 0)) / NULLIF(ufr.number_questions_commented,0),0)
                      
                    +
                    
                    -- TAG - NUMBER QUESTIONS VIEWED
                    COALESCE(ut.number_questions_viewed * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_viewed >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsViewedInTag
                        ELSE :relevanceQuestionsViewedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_viewed
                      END, 0)) / NULLIF(ufr.number_questions_viewed,0),0)
                      
                    +
                    
                    -- TAG - NUMBER QUESTIONS FOLLOWED
                    COALESCE(ut.number_questions_followed * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_followed >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsFollowedInTag
                        ELSE :relevanceQuestionsFollowedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_followed
                      END, 0)) / NULLIF(ufr.number_questions_followed,0),0)
                      
                    +
                    
                    -- TAG - NUMBER QUESTIONS UPVOTED
                    COALESCE(ut.number_questions_upvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_upvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsUpvotedInTag
                        ELSE :relevanceQuestionsUpvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_upvoted
                      END, 0)) / NULLIF(ufr.number_questions_upvoted,0),0)
                      
                    +
                    
                    -- TAG - NUMBER QUESTIONS DOWNVOTED
                    COALESCE(ut.number_questions_downvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_questions_downvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceQuestionsDownvotedInTag
                        ELSE :relevanceQuestionsDownvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_questions_downvoted
                      END, 0)) / NULLIF(ufr.number_questions_downvoted,0),0)
                      
                    +
                    
                    -- TAG - NUMBER ANSWERS UPVOTED
                    COALESCE(ut.number_answers_upvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_answers_upvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceAnswersUpvotedInTag
                        ELSE :relevanceAnswersUpvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_answers_upvoted
                      END, 0)) / NULLIF(ufr.number_answers_upvoted,0),0)
                      
                    +
                    
                    -- TAG - NUMBER ANSWERS DOWNVOTED
                    COALESCE(ut.number_answers_downvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_answers_downvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceAnswersDownvotedInTag
                        ELSE :relevanceAnswersDownvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_answers_downvoted
                      END, 0)) / NULLIF(ufr.number_answers_downvoted,0),0)
                      
                    +
                    
                    -- TAG - NUMBER COMMENTS UPVOTED
                    COALESCE(ut.number_comments_upvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_comments_upvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceCommentsUpvotedInTag
                        ELSE :relevanceCommentsUpvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_comments_upvoted
                      END, 0)) / NULLIF(ufr.number_comments_upvoted,0),0)
                      
                    +
                    
                    -- TAG - NUMBER COMMENTS DOWNVOTED
                    COALESCE(ut.number_comments_downvoted * (
                    NULLIF(CASE
                        WHEN ufr.number_comments_downvoted >= :minimumOfActivitiesToConsiderMaximumScore THEN :relevanceCommentsDownvotedInTag
                        ELSE :relevanceCommentsDownvotedInTag / :minimumOfActivitiesToConsiderMaximumScore * ufr.number_comments_downvoted
                      END, 0)) / NULLIF(ufr.number_comments_downvoted,0),0)                  
                    
                    ),0)
                  
                  AS user_tag_score
                  
                  FROM question_tag qt
                  INNER JOIN user_tag ut ON qt.tag_id = ut.tag_id AND ut.user_id = :userId
                  WHERE qt.question_id = q.post_id
                  )
                 
                 AS score
                 FROM question q
                 INNER JOIN post p ON p.post_id = q.post_id
                 INNER JOIN users ufr ON ufr.user_id = :userId
                 
                 WHERE
                    
                    -- Checks that the question has not already been displayed on a previous page in the list
                    p.post_id NOT IN
                    (SELECT rlpq.question_id FROM recommended_list_page_question rlpq INNER JOIN recommended_list_page rlp
                    ON rlp.recommended_list_page_id = rlpq.recommended_list_page_id WHERE rlp.recommended_list_id = :recommendedListId
                    )
                    
                 AND 
                    p.publication_date <= :dateOfRecommendations
                    
                 AND   
                    p.hidden IS NOT TRUE
                    
                 AND
                    NOT EXISTS (SELECT 1 FROM question_category qc
                                INNER JOIN user_category uc
                                ON qc.category_id = uc.category_id
                                AND uc.user_id = :userId
                                WHERE qc.question_id = q.post_id AND uc.ignored)
                  AND
                    NOT EXISTS (SELECT 1 FROM question_tag qt
                                INNER JOIN user_tag ut
                                ON qt.tag_id = ut.tag_id
                                AND ut.user_id = :userId
                                WHERE qt.question_id = q.post_id AND ut.ignored)
                 
                 ORDER BY score DESC, p.publication_date DESC
                 LIMIT :limit OFFSET :offset
                                
                """, Tuple.class);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("dateOfRecommendations", dateOfRecommendations);
        nativeQuery.setParameter("recommendedListId", recommendedListId);
        nativeQuery.setParameter("limit", lengthQuestionListPage);
        nativeQuery.setParameter("offset", (pageNumber - 1) * lengthQuestionListPage);
        nativeQuery.setParameter("minimumOfActivitiesToConsiderMaximumScore", recommendationSettings.get(QUESTION_LIST_MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE));

        nativeQuery.setParameter("numberOfDaysQuestionIsRecent", recommendationSettings.get(QUESTION_LIST_NUMBER_OF_DAYS_QUESTION_IS_RECENT));
        nativeQuery.setParameter("numberOfDaysQuestionIsRelevant", recommendationSettings.get(QUESTION_LIST_NUMBER_OF_DAYS_QUESTION_IS_RELEVANT));
        nativeQuery.setParameter("numberOfSecondsInDay", 86400);
        nativeQuery.setParameter("relevancePublicationDateRecent", recommendationSettings.get(QUESTION_LIST_RELEVANCE_PUBLICATION_DATE_RECENT));
        nativeQuery.setParameter("relevancePublicationDateRelevant", recommendationSettings.get(QUESTION_LIST_RELEVANCE_PUBLICATION_DATE_RELEVANT));
        nativeQuery.setParameter("relevanceUpdateDateRecent", recommendationSettings.get(QUESTION_LIST_RELEVANCE_UPDATE_DATE_RECENT));

        nativeQuery.setParameter("relevanceExplicitRecommendationTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_EXPLICIT_TAG));
        nativeQuery.setParameter("relevanceQuestionsAskedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_ASKED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsAnsweredInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_ANSWERED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsCommentedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_COMMENTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsViewedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_VIEWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsFollowedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsUpvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsDownvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersUpvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_ANSWERS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersDownvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsUpvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_COMMENTS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsDownvotedInTag", recommendationSettings.get(QUESTION_LIST_RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG));

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new RecommendedQuestionOfPageDto(
                        t.get(0, BigInteger.class).longValue(),
                        t.get(1, String.class),
                        t.get(2, BigDecimal.class).setScale(2, RoundingMode.HALF_UP)
                ))
                .collect(Collectors.toList());
    }


    public Integer countForRecommendedList(Long userId, LocalDateTime dateOfRecommendations) {
        Query nativeQuery = entityManager.createNativeQuery("""
                 SELECT COUNT(*)
                 FROM question q
                 INNER JOIN post p on p.post_id = q.post_id
                 
                 WHERE
                    p.hidden IS NOT TRUE
                 AND
                    p.publication_date <= :dateOfRecommendations
                 AND
                    NOT EXISTS (SELECT 1 FROM question_category qc
                                INNER JOIN user_category uc
                                ON qc.category_id = uc.category_id
                                AND uc.user_id = :userId
                                WHERE qc.question_id = q.post_id AND uc.ignored IS TRUE)
                  AND
                    NOT EXISTS (SELECT 1 FROM question_tag qt
                                INNER JOIN user_tag ut
                                ON qt.tag_id = ut.tag_id
                                AND ut.user_id = :userId
                                WHERE qt.question_id = q.post_id AND ut.ignored IS TRUE)
                                
                """);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("dateOfRecommendations", dateOfRecommendations);
        return ((BigInteger) nativeQuery.getSingleResult()).intValue();
    }
}

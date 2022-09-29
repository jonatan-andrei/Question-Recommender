package jonatan.andrei.repository.custom;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.dto.UserToSendQuestionNotificationDto;

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
import java.util.Optional;
import java.util.stream.Collectors;

import static jonatan.andrei.domain.RecommendationSettingsType.*;

@ApplicationScoped
public class QuestionCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<UserToSendQuestionNotificationDto> findUsersToNotifyQuestion(Long questionId, Integer pageNumber, Integer lengthUsersList, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime minimumLastActivityDate) {
        Query nativeQuery = entityManager.createNativeQuery("""
                SELECT ufr.user_id, ufr.integration_user_id
                FROM users ufr
                LEFT JOIN user_follower uf ON follower_id = ufr.user_id AND uf.user_id = (SELECT user_id FROM post p WHERE p.post_id = :questionId)
                                
                WHERE :minimumScoreToSendQuestionToUser <= (
                             
                -- USER FOLLOWER ASKER
                 (CASE
                       WHEN uf.follower_id IS NOT NULL THEN :relevanceUserFollowerAsker
                       ELSE 0
                 END)
                                 
                +
                                  
                -- USER TAG SCORE
                (SELECT
                   COALESCE(SUM(
                   
                   -- TAG - EXPLICIT RECOMMENDATION
                   (CASE
                       WHEN ut.explicit_recommendation THEN :relevanceExplicitRecommendationTag
                       ELSE 0
                   END)
                   
                   """

                +

                appendRuleCategoryOrTag("ut", "t", "number_questions_asked", "relevanceQuestionsAskedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_answered", "relevanceQuestionsAnsweredInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_commented", "relevanceQuestionsCommentedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_viewed", "relevanceQuestionsViewedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_questions_followed", "relevanceQuestionsFollowedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_questions_upvoted", "relevanceQuestionsUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_questions_downvoted", "relevanceQuestionsDownvotedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_answers_upvoted", "relevanceAnswersUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_answers_downvoted", "relevanceAnswersDownvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_comments_upvoted", "relevanceCommentsUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_comments_downvoted", "relevanceCommentsDownvotedInTag")

                +

                """                                  
                           ),0)
                         
                         AS user_tag_score
                         
                         FROM user_tag ut
                         INNER JOIN question_tag qt ON qt.tag_id = ut.tag_id AND qt.question_id = :questionId
                         INNER JOIN tag t ON t.tag_id = qt.tag_id
                         INNER JOIN total_activity_system tas ON tas.post_classification_type = 'TAG'
                         WHERE ut.user_id = ufr.user_id
                         )
                         
                         +
                         
                        -- USER CATEGORY SCORE
                        (SELECT
                           COALESCE(SUM(
                           
                           -- CATEGORY - EXPLICIT RECOMMENDATION
                           (CASE
                               WHEN uc.explicit_recommendation THEN :relevanceExplicitRecommendationCategory
                               ELSE 0
                           END)
                           """

                +

                appendRuleCategoryOrTag("uc","c", "number_questions_asked", "relevanceQuestionsAskedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_answered", "relevanceQuestionsAnsweredInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_questions_commented", "relevanceQuestionsCommentedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_viewed", "relevanceQuestionsViewedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_followed", "relevanceQuestionsFollowedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_upvoted", "relevanceQuestionsUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_downvoted", "relevanceQuestionsDownvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_answers_upvoted", "relevanceAnswersUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_answers_downvoted", "relevanceAnswersDownvotedInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_comments_upvoted", "relevanceCommentsUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_comments_downvoted", "relevanceCommentsDownvotedInCategory")

                +

                """
                            ),0)
                          
                          AS user_category_score
                          
                          FROM user_category uc
                          INNER JOIN question_category qc ON qc.category_id = uc.category_id AND qc.question_id = :questionId
                          INNER JOIN category c ON c.category_id = qc.category_id
                          INNER JOIN total_activity_system tas ON tas.post_classification_type = 'CATEGORY'
                          WHERE uc.user_id = ufr.user_id
                          ))
                           
                          AND
                          
                            ufr.last_activity_date >= :minimumLastActivityDate
                           
                          AND
                                                        
                            NOT EXISTS (SELECT 1 FROM question_category qc
                                        INNER JOIN user_category uc
                                        ON qc.category_id = uc.category_id 
                                        AND qc.question_id = :questionId
                                        AND uc.user_id = ufr.user_id
                                        WHERE uc.ignored)
                                        
                          AND
                          
                            NOT EXISTS (SELECT 1 FROM question_tag qt
                                        INNER JOIN user_tag ut
                                        ON qt.tag_id = ut.tag_id
                                        AND qt.question_id = :questionId
                                        AND ut.user_id = ufr.user_id
                                        WHERE ut.ignored)
                         
                         LIMIT :limit OFFSET :offset
                                        
                        """, Tuple.class);

        nativeQuery.setParameter("questionId", questionId);
        nativeQuery.setParameter("limit", lengthUsersList);
        nativeQuery.setParameter("offset", (pageNumber - 1) * lengthUsersList);
        nativeQuery.setParameter("minimumOfActivitiesToConsiderMaximumScore", recommendationSettings.get(MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE));
        nativeQuery.setParameter("relevanceUserFollowerAsker", recommendationSettings.get(RELEVANCE_USER_FOLLOWER_ASKER));
        nativeQuery.setParameter("minimumScoreToSendQuestionToUser", recommendationSettings.get(MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER));
        nativeQuery.setParameter("minimumLastActivityDate", minimumLastActivityDate);

        nativeQuery.setParameter("relevanceExplicitRecommendationTag", recommendationSettings.get(RELEVANCE_EXPLICIT_TAG));
        nativeQuery.setParameter("relevanceQuestionsAskedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_ASKED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsAnsweredInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_ANSWERED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsCommentedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_COMMENTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsViewedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_VIEWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsFollowedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsUpvotedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsDownvotedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersUpvotedInTag", recommendationSettings.get(RELEVANCE_ANSWERS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersDownvotedInTag", recommendationSettings.get(RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsUpvotedInTag", recommendationSettings.get(RELEVANCE_COMMENTS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsDownvotedInTag", recommendationSettings.get(RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG));

        nativeQuery.setParameter("relevanceExplicitRecommendationCategory", recommendationSettings.get(RELEVANCE_EXPLICIT_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsAskedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsAnsweredInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsCommentedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsViewedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsFollowedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsUpvotedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsDownvotedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceAnswersUpvotedInCategory", recommendationSettings.get(RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceAnswersDownvotedInCategory", recommendationSettings.get(RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceCommentsUpvotedInCategory", recommendationSettings.get(RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceCommentsDownvotedInCategory", recommendationSettings.get(RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY));

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new UserToSendQuestionNotificationDto(
                        t.get(0, BigInteger.class).longValue(),
                        t.get(1, String.class)))
                .collect(Collectors.toList());
    }

    public List<RecommendedQuestionOfListDto> findRecommendedList(Long userId, Integer pageNumber, Integer lengthQuestionList, Long recommendedListId, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime dateOfRecommendations) {
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
                                 
                -- HAS ANSWERS
                (CASE
                       WHEN q.answers > 0 THEN :relevanceHasAnswers
                       ELSE 0
                 END)
                                 
                +
                                 
                -- PER ANSWER
                (:relevancePerAnswer * q.answers)
                                 
                +
                                 
                -- HAS BEST ANSWER
                (CASE
                       WHEN q.best_answer_id IS NOT NULL THEN :relevanceHasBestAnswer
                       ELSE 0
                 END)
                 
                 +
                 
                 -- DUPLICATE QUESTION
                 (CASE
                       WHEN q.duplicate_question_id IS NOT NULL THEN :relevanceDuplicateQuestion
                       ELSE 0
                 END)
                                 
                +
                                 
                -- QUESTION NUMBER VIEWS
                (q.views * :relevanceQuestionNumberViews)
                                 
                +
                                 
                -- QUESTION NUMBER FOLLOWERS
                (q.followers * :relevanceQuestionNumberFollowers)
                                 
                +
                                 
                -- QUESTION NUMBER UPVOTES
                (p.upvotes * :relevanceQuestionNumberUpvotes)
                                 
                +
                                 
                -- QUESTION NUMBER DOWNVOTES
                (p.downvotes * :relevanceQuestionNumberDownvotes)
                                 
                +
                                 
                -- USER ALREADY ANSWERED
                ((SELECT COUNT(*) FROM answer a
                INNER JOIN post pa ON pa.post_id = a.post_id
                WHERE pa.user_id = :userId AND a.question_id = q.post_id)
                * :relevanceUserAlreadyAnswered)
                                 
                +
                                 
                -- USER ALREADY COMMENTED IN QUESTION
                ((SELECT COUNT(*) FROM question_comment qc
                INNER JOIN post pc ON pc.post_id = qc.post_id
                WHERE pc.user_id = :userId AND qc.question_id = q.post_id)
                * :relevanceUserAlreadyCommented)
                                 
                +
                                 
                -- USER ALREADY COMMENTED IN ANSWERS TO THE QUESTION
                ((SELECT COUNT(*) FROM answer_comment ac
                INNER JOIN post pc ON pc.post_id = ac.post_id
                INNER JOIN answer a ON ac.answer_id = a.post_id
                WHERE pc.user_id = :userId AND a.question_id = q.post_id)
                * :relevanceUserAlreadyCommented)
                                 
                +
                                 
                -- USER FOLLOWER ASKER
                 (CASE
                       WHEN uf.follower_id IS NOT NULL THEN :relevanceUserFollowerAsker
                       ELSE 0
                 END)
                                 
                +
                                 
                -- USER ALREADY VIEWED
                (COALESCE(qv.number_of_views,0) * :relevanceUserAlreadyViewed)
                                 
                +
                                 
                -- USER ALREADY VIEWED IN LIST
                (COALESCE(qv.number_of_recommendations_in_list,0) * :relevanceUserAlreadyViewedInList)
                                 
                +
                                 
                -- USER ALREADY VIEWED IN EMAIL
                (COALESCE(qv.number_of_recommendations_in_email,0) * :relevanceUserAlreadyViewedInEmail)
                                 
                +
                                 
                -- USER ALREADY VIEWED IN NOTIFICATION
                (CASE
                       WHEN qv.recommended_in_notification IS TRUE THEN :relevanceUserAlreadyViewedInNotification
                       ELSE 0
                 END)
                                 
                +
                                  
                -- USER TAG SCORE
                (SELECT
                   COALESCE(SUM(
                   
                   -- TAG - EXPLICIT RECOMMENDATION
                   (CASE
                       WHEN ut.explicit_recommendation THEN :relevanceExplicitRecommendationTag
                       ELSE 0
                   END)
                   
                   """

                +

                appendRuleCategoryOrTag("ut", "t", "number_questions_asked", "relevanceQuestionsAskedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_questions_answered", "relevanceQuestionsAnsweredInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_commented", "relevanceQuestionsCommentedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_viewed", "relevanceQuestionsViewedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_followed", "relevanceQuestionsFollowedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_upvoted", "relevanceQuestionsUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_questions_downvoted", "relevanceQuestionsDownvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_answers_upvoted", "relevanceAnswersUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t","number_answers_downvoted", "relevanceAnswersDownvotedInTag")

                +

                appendRuleCategoryOrTag("ut","t", "number_comments_upvoted", "relevanceCommentsUpvotedInTag")

                +

                appendRuleCategoryOrTag("ut", "t", "number_comments_downvoted", "relevanceCommentsDownvotedInTag")

                +

                """                                  
                           ),0)
                         
                         AS user_tag_score
                         
                         FROM question_tag qt
                         INNER JOIN user_tag ut ON qt.tag_id = ut.tag_id AND ut.user_id = :userId
                         INNER JOIN tag t ON t.tag_id = qt.tag_id
                         INNER JOIN total_activity_system tas ON tas.post_classification_type = 'TAG'
                         WHERE qt.question_id = q.post_id
                         )
                         
                         +
                         
                        -- USER CATEGORY SCORE
                        (SELECT
                           COALESCE(SUM(
                           
                           -- CATEGORY - EXPLICIT RECOMMENDATION
                           (CASE
                               WHEN uc.explicit_recommendation THEN :relevanceExplicitRecommendationCategory
                               ELSE 0
                           END)
                           """

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_asked", "relevanceQuestionsAskedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_answered", "relevanceQuestionsAnsweredInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_questions_commented", "relevanceQuestionsCommentedInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_questions_viewed", "relevanceQuestionsViewedInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_questions_followed", "relevanceQuestionsFollowedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_upvoted", "relevanceQuestionsUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_questions_downvoted", "relevanceQuestionsDownvotedInCategory")

                +

                appendRuleCategoryOrTag("uc","c",  "number_answers_upvoted", "relevanceAnswersUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_answers_downvoted", "relevanceAnswersDownvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_comments_upvoted", "relevanceCommentsUpvotedInCategory")

                +

                appendRuleCategoryOrTag("uc", "c", "number_comments_downvoted", "relevanceCommentsDownvotedInCategory")

                +

                """
                            ),0)
                          
                          AS user_category_score
                          
                          FROM question_category qc
                          INNER JOIN user_category uc ON qc.category_id = uc.category_id AND uc.user_id = :userId
                          INNER JOIN category c ON c.category_id = qc.category_id
                          INNER JOIN total_activity_system tas ON tas.post_classification_type = 'CATEGORY'
                          WHERE qc.question_id = q.post_id
                          )
                         
                         AS score
                         FROM question q
                         INNER JOIN post p ON p.post_id = q.post_id
                         INNER JOIN users ufr ON ufr.user_id = :userId
                         LEFT JOIN question_view qv ON p.post_id = qv.question_id and qv.user_id = :userId
                         LEFT JOIN user_follower uf ON follower_id = :userId AND p.user_id = uf.user_id
                         
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
                                        INNER JOIN category c
                                        ON qc.category_id = c.category_id
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
        nativeQuery.setParameter("recommendedListId", Optional.ofNullable(recommendedListId).orElse(0L));
        nativeQuery.setParameter("limit", lengthQuestionList);
        nativeQuery.setParameter("offset", (pageNumber - 1) * lengthQuestionList);
        nativeQuery.setParameter("minimumOfActivitiesToConsiderMaximumScore", recommendationSettings.get(MINIMUM_OF_ACTIVITIES_TO_CONSIDER_MAXIMUM_SCORE));

        nativeQuery.setParameter("numberOfDaysQuestionIsRecent", recommendationSettings.get(NUMBER_OF_DAYS_QUESTION_IS_RECENT));
        nativeQuery.setParameter("numberOfDaysQuestionIsRelevant", recommendationSettings.get(NUMBER_OF_DAYS_QUESTION_IS_RELEVANT));
        nativeQuery.setParameter("numberOfSecondsInDay", 86400);
        nativeQuery.setParameter("relevancePublicationDateRecent", recommendationSettings.get(RELEVANCE_PUBLICATION_DATE_RECENT));
        nativeQuery.setParameter("relevancePublicationDateRelevant", recommendationSettings.get(RELEVANCE_PUBLICATION_DATE_RELEVANT));
        nativeQuery.setParameter("relevanceUpdateDateRecent", recommendationSettings.get(RELEVANCE_UPDATE_DATE_RECENT));
        nativeQuery.setParameter("relevanceHasAnswers", recommendationSettings.get(RELEVANCE_QUESTION_HAS_ANSWER));
        nativeQuery.setParameter("relevancePerAnswer", recommendationSettings.get(RELEVANCE_QUESTION_PER_ANSWER));
        nativeQuery.setParameter("relevanceHasBestAnswer", recommendationSettings.get(RELEVANCE_QUESTION_HAS_BEST_ANSWER));
        nativeQuery.setParameter("relevanceDuplicateQuestion", recommendationSettings.get(RELEVANCE_DUPLICATE_QUESTION));
        nativeQuery.setParameter("relevanceQuestionNumberViews", recommendationSettings.get(RELEVANCE_QUESTION_NUMBER_VIEWS));
        nativeQuery.setParameter("relevanceQuestionNumberFollowers", recommendationSettings.get(RELEVANCE_QUESTION_NUMBER_FOLLOWERS));
        nativeQuery.setParameter("relevanceQuestionNumberUpvotes", recommendationSettings.get(RELEVANCE_QUESTION_NUMBER_UPVOTES));
        nativeQuery.setParameter("relevanceQuestionNumberDownvotes", recommendationSettings.get(RELEVANCE_QUESTION_NUMBER_DOWNVOTES));
        nativeQuery.setParameter("relevanceUserAlreadyAnswered", recommendationSettings.get(RELEVANCE_USER_ALREADY_ANSWERED));
        nativeQuery.setParameter("relevanceUserAlreadyCommented", recommendationSettings.get(RELEVANCE_USER_ALREADY_COMMENTED));
        nativeQuery.setParameter("relevanceUserFollowerAsker", recommendationSettings.get(RELEVANCE_USER_FOLLOWER_ASKER));

        nativeQuery.setParameter("relevanceUserAlreadyViewed", recommendationSettings.get(RELEVANCE_USER_ALREADY_VIEWED));
        nativeQuery.setParameter("relevanceUserAlreadyViewedInList", recommendationSettings.get(RELEVANCE_USER_ALREADY_VIEWED_IN_LIST));
        nativeQuery.setParameter("relevanceUserAlreadyViewedInEmail", recommendationSettings.get(RELEVANCE_USER_ALREADY_VIEWED_IN_EMAIL));
        nativeQuery.setParameter("relevanceUserAlreadyViewedInNotification", recommendationSettings.get(RELEVANCE_USER_ALREADY_VIEWED_IN_NOTIFICATION));

        nativeQuery.setParameter("relevanceExplicitRecommendationTag", recommendationSettings.get(RELEVANCE_EXPLICIT_TAG));
        nativeQuery.setParameter("relevanceQuestionsAskedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_ASKED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsAnsweredInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_ANSWERED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsCommentedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_COMMENTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsViewedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_VIEWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsFollowedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_FOLLOWED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsUpvotedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceQuestionsDownvotedInTag", recommendationSettings.get(RELEVANCE_QUESTIONS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersUpvotedInTag", recommendationSettings.get(RELEVANCE_ANSWERS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceAnswersDownvotedInTag", recommendationSettings.get(RELEVANCE_ANSWERS_DOWNVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsUpvotedInTag", recommendationSettings.get(RELEVANCE_COMMENTS_UPVOTED_IN_TAG));
        nativeQuery.setParameter("relevanceCommentsDownvotedInTag", recommendationSettings.get(RELEVANCE_COMMENTS_DOWNVOTED_IN_TAG));

        nativeQuery.setParameter("relevanceExplicitRecommendationCategory", recommendationSettings.get(RELEVANCE_EXPLICIT_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsAskedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_ASKED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsAnsweredInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_ANSWERED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsCommentedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_COMMENTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsViewedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_VIEWED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsFollowedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_FOLLOWED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsUpvotedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceQuestionsDownvotedInCategory", recommendationSettings.get(RELEVANCE_QUESTIONS_DOWNVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceAnswersUpvotedInCategory", recommendationSettings.get(RELEVANCE_ANSWERS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceAnswersDownvotedInCategory", recommendationSettings.get(RELEVANCE_ANSWERS_DOWNVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceCommentsUpvotedInCategory", recommendationSettings.get(RELEVANCE_COMMENTS_UPVOTED_IN_CATEGORY));
        nativeQuery.setParameter("relevanceCommentsDownvotedInCategory", recommendationSettings.get(RELEVANCE_COMMENTS_DOWNVOTED_IN_CATEGORY));

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new RecommendedQuestionOfListDto(
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

    private String appendRuleCategoryOrTag(String aliasUserCategoryOrUserTag, String aliasCategoryOrTag, String columnName, String parameterName) {
        StringBuilder str = new StringBuilder();
        str.append(" + COALESCE( ");
        str.append("((COALESCE(NULLIF(" + aliasUserCategoryOrUserTag + "." + columnName + ",0) / " + "NULLIF(ufr." + columnName + ",0),0))");
        str.append(" - ");
        str.append("(COALESCE(NULLIF(" + aliasCategoryOrTag + "." + columnName + ",0) / NULLIF(tas." + columnName + ",0),0)))");
        str.append(" * (NULLIF(CASE ");
        str.append(" WHEN ufr." + columnName + " >= :minimumOfActivitiesToConsiderMaximumScore THEN :" + parameterName);
        str.append(" ELSE :" + parameterName + " / :minimumOfActivitiesToConsiderMaximumScore * ufr." + columnName);
        str.append(" END, 0)),0)");
        return str.toString();
    }
}

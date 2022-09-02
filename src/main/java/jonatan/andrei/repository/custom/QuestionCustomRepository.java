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
                 
                 -- PUBLICATION DATE SCORE
                 (GREATEST(:numberOfDaysQuestionIsRelevant - EXTRACT(EPOCH FROM :dateOfRecommendations - p.publication_date)/:numberOfSecondsInDay, :minimalRelevance) * :publicationDateRelevance / :numberOfDaysQuestionIsRelevant)
                 -- eg: (GREATEST(365 - EXTRACT(EPOCH FROM now() - p.publication_date)/86400, 1) * 100 / 365)
                 -- Extract the seconds between the publish date and the current date
                 -- Considers that the date is irrelevant if the number of days is greater than the parameter
                 -- Multiply the value obtained by the desired importance (eg: 100) and divide by the number of days the question is relevant (eg: 365)
                 
                 +
                 
                 -- CATEGORY EXPLICIT RECOMMENDATION SCORE
                 (CASE
                    WHEN uc.explicit_recommendation THEN :categoryExplicitRecommendationRelevanceQuestionListPage
                    ELSE 0
                  END)
                  
                 +
                 
                 -- TAG EXPLICIT RECOMMENDATION SCORE
                 (CASE
                    WHEN ut.explicit_recommendation THEN :tagExplicitRecommendationRelevanceQuestionListPage
                    ELSE 0
                  END)
                 
                 AS score
                 FROM question q
                 INNER JOIN post p on p.post_id = q.post_id
                 LEFT JOIN question_category qc ON q.post_id = qc.question_id
                 LEFT JOIN user_category uc ON qc.category_id = uc.category_id AND uc.user_id = :userId
                 LEFT JOIN question_tag qt ON q.post_id = qt.question_id
                 LEFT JOIN user_tag ut ON qt.tag_id = ut.tag_id AND ut.user_id = :userId
                 
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
                                WHERE qc.question_id = q.post_id AND uc.ignored IS TRUE)
                  AND
                    NOT EXISTS (SELECT 1 FROM question_tag qt
                                INNER JOIN user_tag ut
                                ON qt.tag_id = ut.tag_id
                                AND ut.user_id = :userId
                                WHERE qt.question_id = q.post_id AND ut.ignored IS TRUE)
                 
                 ORDER BY score DESC, p.publication_date DESC
                 LIMIT :limit OFFSET :offset
                                
                """, Tuple.class);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("numberOfDaysQuestionIsRelevant", recommendationSettings.get(QUESTION_LIST_NUMBER_OF_DAYS_QUESTION_IS_RELEVANT));
        nativeQuery.setParameter("dateOfRecommendations", dateOfRecommendations);
        nativeQuery.setParameter("numberOfSecondsInDay", 86400);
        nativeQuery.setParameter("minimalRelevance", 1);
        nativeQuery.setParameter("publicationDateRelevance", recommendationSettings.get(QUESTION_LIST_RELEVANCE_PUBLICATION_DATE));
        nativeQuery.setParameter("categoryExplicitRecommendationRelevanceQuestionListPage", recommendationSettings.get(QUESTION_LIST_RELEVANCE_EXPLICIT_CATEGORY));
        nativeQuery.setParameter("tagExplicitRecommendationRelevanceQuestionListPage", recommendationSettings.get(QUESTION_LIST_RELEVANCE_EXPLICIT_TAG));
        nativeQuery.setParameter("recommendedListId", recommendedListId);
        nativeQuery.setParameter("limit", lengthQuestionListPage);
        nativeQuery.setParameter("offset", (pageNumber - 1) * lengthQuestionListPage);
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

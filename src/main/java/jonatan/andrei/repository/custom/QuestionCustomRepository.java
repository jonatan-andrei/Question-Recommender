package jonatan.andrei.repository.custom;

import jonatan.andrei.dto.RecommendedQuestionOfPageDto;
import jonatan.andrei.dto.SettingsDto;

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
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<RecommendedQuestionOfPageDto> findRecommendedList(Long userId, Integer pageNumber, Integer lengthQuestionListPage, Long recommendedListId, SettingsDto settings, LocalDateTime dateOfRecommendations) {
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
                 
                 ORDER BY score DESC, p.publication_date DESC
                 LIMIT :limit OFFSET :offset
                                
                """, Tuple.class);
        nativeQuery.setParameter("userId", userId);
        nativeQuery.setParameter("numberOfDaysQuestionIsRelevant", settings.getNumberOfDaysQuestionIsRelevant());
        nativeQuery.setParameter("dateOfRecommendations", dateOfRecommendations);
        nativeQuery.setParameter("numberOfSecondsInDay", 86400);
        nativeQuery.setParameter("minimalRelevance", 1);
        nativeQuery.setParameter("publicationDateRelevance", settings.getPublicationDateRelevanceQuestionListPage());
        nativeQuery.setParameter("categoryExplicitRecommendationRelevanceQuestionListPage", settings.getCategoryExplicitRecommendationRelevanceQuestionListPage());
        nativeQuery.setParameter("tagExplicitRecommendationRelevanceQuestionListPage", settings.getTagExplicitRecommendationRelevanceQuestionListPage());
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

}

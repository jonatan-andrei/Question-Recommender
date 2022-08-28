package jonatan.andrei.repository.custom;

import jonatan.andrei.dto.RecommendedQuestionOfPageDto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<RecommendedQuestionOfPageDto> findRecommendedList() {
        Query nativeQuery = entityManager.createNativeQuery("""
                 SELECT q.post_id, p.integration_post_id,
                 
                 -- PUBLICATION DATE SCORE
                 (GREATEST(:numberOfDaysQuestionIsRelevant - EXTRACT(EPOCH FROM now() - p.publication_date)/:numberOfSecondsInDay, :minimalRelevance) * :publicationDateRelevance / :numberOfDaysQuestionIsRelevant)
                 -- eg: (GREATEST(365 - EXTRACT(EPOCH FROM now() - p.publication_date)/86400, 1) * 100 / 365)
                 -- Extract the seconds between the publish date and the current date
                 -- Considers that the date is irrelevant if the number of days is greater than the parameter
                 -- Multiply the value obtained by the desired importance (eg: 100) and divide by the number of days the question is relevant (eg: 365)
                 
                 as score
                 from question q
                 inner join post p on p.post_id = q.post_id
                 order by score desc, p.publication_date desc
                                
                """, Tuple.class);
        nativeQuery.setParameter("numberOfDaysQuestionIsRelevant", 365);
        nativeQuery.setParameter("numberOfSecondsInDay", 86400);
        nativeQuery.setParameter("minimalRelevance", 1);
        nativeQuery.setParameter("publicationDateRelevance", 100);
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

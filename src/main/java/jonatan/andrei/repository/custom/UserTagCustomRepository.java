package jonatan.andrei.repository.custom;

import jonatan.andrei.dto.UserTagDto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserTagCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<UserTagDto> findByUserId(String integrationUserId) {
        Query nativeQuery = entityManager.createNativeQuery("""
                 SELECT 
                 t.tag_id,
                 t.name,
                 ut.number_questions_asked,
                 ut.number_questions_viewed,
                 ut.number_questions_answered,
                 ut.number_questions_commented,
                 ut.number_questions_followed,
                 ut.number_questions_upvoted,
                 ut.number_questions_downvoted,
                 ut.number_answers_upvoted,
                 ut.number_answers_downvoted,
                 ut.number_comments_upvoted,
                 ut.number_comments_downvoted
                 FROM user_tag ut
                 INNER JOIN tag t
                 ON ut.tag_id = t.tag_id
                 INNER JOIN users u
                 ON ut.user_id = u.user_id
                 WHERE u.integration_user_id = :integrationUserId
                                
                """);

        nativeQuery.setParameter("integrationUserId", integrationUserId);

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new UserTagDto(
                        t.get(0, Long.class),
                        t.get(1, String.class),
                        t.get(2, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(3, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(4, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(5, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(6, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(7, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(8, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(9, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(10, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(11, BigDecimal.class).setScale(2, RoundingMode.HALF_UP),
                        t.get(12, BigDecimal.class).setScale(2, RoundingMode.HALF_UP)
                ))
                .collect(Collectors.toList());
    }

}

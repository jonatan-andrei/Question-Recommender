package jonatan.andrei.repository.custom;

import jonatan.andrei.dto.QuestionsAnsweredByUserDto;
import jonatan.andrei.dto.UserToSendRecommendedEmailDto;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    public List<QuestionsAnsweredByUserDto> findQuestionsAnsweredInPeriod(LocalDateTime startDate, LocalDateTime endDate, Integer minimumOfPreviousAnswers) {
        Query nativeQuery = entityManager.createNativeQuery("""
                 SELECT u.integration_user_id,
                 pq.integration_post_id,
                 q.followers,
                 q.best_answer_id,
                 q.answers,
                 q.tags,
                 pa.publication_date
                 FROM answer a
                 INNER JOIN post pa ON a.post_id = pa.post_id
                 INNER JOIN question q ON q.post_id = a.question_id
                 INNER JOIN post pq ON q.post_id = pq.post_id
                 INNER JOIN users u ON pa.user_id = u.user_id
                 WHERE pa.publication_date BETWEEN :startDate AND :endDate
                 AND pq.publication_date < :startDate
                 AND pa.publication_date <= (pq.publication_date + interval '90' day)
                 AND :minimumOfPreviousAnswers <= 
                        (SELECT count(*) FROM post p2 
                        WHERE p2.post_type = 'ANSWER'
                        AND p2.user_id = u.user_id
                        AND p2.publication_date < :startDate)                       
                """, Tuple.class);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("endDate", endDate);
        nativeQuery.setParameter("minimumOfPreviousAnswers", minimumOfPreviousAnswers);

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new QuestionsAnsweredByUserDto(
                        t.get(0, String.class),
                        t.get(1, String.class),
                        t.get(2, Integer.class),
                        Optional.ofNullable(t.get(3, BigInteger.class)).map(BigInteger::longValue).orElse(null),
                        t.get(4, Integer.class),
                        t.get(5, String.class),
                        t.get(6, Timestamp.class).toLocalDateTime()
                ))
                .collect(Collectors.toList());
    }

    public List<UserToSendRecommendedEmailDto> findUsersToSendRecommendedEmail(LocalDateTime startDate, Integer hour, boolean isDefaultHour, LocalDateTime minimumLastActivityDate, Integer pageNumber, Integer lengthPage) {
        Query nativeQuery = entityManager.createNativeQuery("""
                                
                SELECT u.user_id,
                u.integration_user_id
                FROM users u
                WHERE u.active
                AND u.anonymous IS FALSE
                AND u.email_notification_enable
                AND ((u.email_notification_hour IS NULL AND :isDefaultHour) OR (u.email_notification_hour IS NOT NULL AND u.email_notification_hour = :hour))
                AND u.integration_date <= :startDate
                AND u.last_activity_date >= :minimumLastActivityDate
                ORDER BY u.integration_date
                LIMIT :limit OFFSET :offset
                                
                """, Tuple.class);

        nativeQuery.setParameter("startDate", startDate);
        nativeQuery.setParameter("hour", hour);
        nativeQuery.setParameter("isDefaultHour", isDefaultHour);
        nativeQuery.setParameter("minimumLastActivityDate", minimumLastActivityDate);
        nativeQuery.setParameter("limit", lengthPage);
        nativeQuery.setParameter("offset", (pageNumber - 1) * lengthPage);

        List<Tuple> result = nativeQuery.getResultList();
        return result.stream()
                .map(t -> new UserToSendRecommendedEmailDto(
                        t.get(0, BigInteger.class).longValue(),
                        t.get(1, String.class)
                ))
                .collect(Collectors.toList());
    }
}

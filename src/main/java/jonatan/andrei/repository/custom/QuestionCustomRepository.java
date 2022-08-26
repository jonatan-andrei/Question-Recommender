package jonatan.andrei.repository.custom;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class QuestionCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> findRecommendedList() {
        Query nativeQuery = entityManager.createNativeQuery("SELECT integrationPostId FROM post p WHERE post_id > :postId");
        nativeQuery.setParameter("postId", 1);
        List<String> result = nativeQuery.getResultList();
        return result;
    }

}

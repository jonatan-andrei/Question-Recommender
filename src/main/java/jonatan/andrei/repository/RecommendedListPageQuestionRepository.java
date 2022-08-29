package jonatan.andrei.repository;

import jonatan.andrei.model.RecommendedListPageQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendedListPageQuestionRepository extends CrudRepository<RecommendedListPageQuestion, Long> {

    List<RecommendedListPageQuestion> findByRecommendedListPageId(Long recommendedListPageId);

}

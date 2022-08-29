package jonatan.andrei.repository;

import jonatan.andrei.model.RecommendedListPage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecommendedListPageRepository extends CrudRepository<RecommendedListPage, Long> {

    List<RecommendedListPage> findByRecommendedListId(Long recommendedListId);

}

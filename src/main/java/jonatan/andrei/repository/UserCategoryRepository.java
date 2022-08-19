package jonatan.andrei.repository;

import jonatan.andrei.model.UserCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserCategoryRepository extends CrudRepository<UserCategory, Long> {

    List<UserCategory> findByUserIdAndCategoryIdIn(Long userId, List<Long> categoriesIds);

   UserCategory findByUserIdAndCategoryId(Long userId, Long categoryId);

   List<UserCategory> findByUserIdAndExplicitRecommendation (Long userId, boolean explicitRecommendation);

    List<UserCategory> findByUserIdAndIgnored (Long userId, boolean ignored);

}

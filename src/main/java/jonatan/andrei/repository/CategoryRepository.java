package jonatan.andrei.repository;

import jonatan.andrei.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByIntegrationCategoryId(String integrationCategoryId);

    @Query(value = "SELECT c FROM Category c WHERE c.integrationCategoryId = :integrationCategoryId")
    Category findCategoryIdByIntegrationCategoryId(@Param("integrationCategoryId") String integrationCategoryId);

}

package jonatan.andrei.repository;

import jonatan.andrei.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByIntegrationCategoryId(String integrationCategoryId);

    List<Category> findByintegrationCategoryIdIn(List<String> integrationCategoriesIds);

    List<Category> findByCategoryIdIn(List<Long> categoriesIds);

}

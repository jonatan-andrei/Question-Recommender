package jonatan.andrei.repository;

import jonatan.andrei.model.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findByIntegrationCategoryId(String integrationCategoryId);

    List<Category> findByintegrationCategoryIdIn(List<String> integrationCategoriesIds);

    @Modifying
    @Query("UPDATE Category SET questionCount = questionCount + 1 WHERE categoryId IN :categories")
    int incrementQuestionCount(@Param("categories") List<Long> categories);

    @Modifying
    @Query("UPDATE Category SET questionCount = questionCount - 1 WHERE categoryId IN :categories")
    int decrementQuestionCount(@Param("categories") List<Long> categories);

}

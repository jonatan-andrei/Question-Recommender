package jonatan.andrei.utils;

import jonatan.andrei.model.Category;
import jonatan.andrei.repository.CategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CategoryTestUtils {

    @Inject
    CategoryRepository categoryRepository;

    public Category saveWithIntegrationCategoryId(String integrationCategoryId) {
        return categoryRepository.save(Category.builder()
                .integrationCategoryId(integrationCategoryId)
                .name(integrationCategoryId)
                .active(true)
                .questionCount(0)
                .build());
    }
}

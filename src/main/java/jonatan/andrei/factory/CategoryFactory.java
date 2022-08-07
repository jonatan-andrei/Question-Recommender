package jonatan.andrei.factory;

import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.model.Category;

public class CategoryFactory {

    public static Category newCategory(CategoryRequestDto categoryRequestDto, Long parentCategoryId) {
        return Category.builder()
                .integrationCategoryId(categoryRequestDto.getIntegrationCategoryId())
                .name(categoryRequestDto.getName())
                .description(categoryRequestDto.getDescription())
                .parentCategoryId(parentCategoryId)
                .active(categoryRequestDto.isActive())
                .questionCount(0)
                .build();
    }

    public static Category overwrite(Category existingCategory, CategoryRequestDto categoryRequestDto, Long parentCategoryId) {
        existingCategory.setParentCategoryId(parentCategoryId);
        existingCategory.setName(categoryRequestDto.getName());
        existingCategory.setDescription(categoryRequestDto.getDescription());
        existingCategory.setActive(categoryRequestDto.isActive());
        return existingCategory;
    }

}

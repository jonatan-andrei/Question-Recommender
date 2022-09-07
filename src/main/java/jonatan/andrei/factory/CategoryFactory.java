package jonatan.andrei.factory;

import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.model.Category;

import java.math.BigDecimal;

public class CategoryFactory {

    public static Category newCategory(CategoryRequestDto categoryRequestDto, Long parentCategoryId) {
        return Category.builder()
                .integrationCategoryId(categoryRequestDto.getIntegrationCategoryId())
                .name(categoryRequestDto.getName())
                .description(categoryRequestDto.getDescription())
                .parentCategoryId(parentCategoryId)
                .active(categoryRequestDto.isActive())
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
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

package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Category;
import jonatan.andrei.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class CategoryServiceTest {

    @Inject
    CategoryService categoryService;

    @Inject
    CategoryRepository categoryRepository;

    @Test
    public void saveOrUpdate_saveNewCategory() {
        // Arrange
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId("1")
                .name("Technology")
                .description("Technology related questions")
                .active(true)
                .build();

        // Act
        Category result = categoryService.saveOrUpdate(categoryRequestDto);

        // Assert
        Assertions.assertTrue(nonNull(result.getCategoryId()));
        Assertions.assertEquals(categoryRequestDto.getIntegrationCategoryId(), result.getIntegrationCategoryId());
        Assertions.assertEquals(categoryRequestDto.getName(), result.getName());
        Assertions.assertEquals(categoryRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(categoryRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_updateCategory() {
        // Arrange
        Category existingCategory = categoryRepository.save(Category.builder()
                .integrationCategoryId("1")
                .name("Technology")
                .description("Technology related questions")
                .active(true)
                .questionCount(0)
                .build());
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId(existingCategory.getIntegrationCategoryId())
                .name("Technology and Computing")
                .description("Technology and Computing related questions")
                .active(true)
                .build();

        // Act
        Category result = categoryService.saveOrUpdate(categoryRequestDto);

        // Assert
        Assertions.assertEquals(existingCategory.getCategoryId(), result.getCategoryId());
        Assertions.assertEquals(categoryRequestDto.getIntegrationCategoryId(), result.getIntegrationCategoryId());
        Assertions.assertEquals(categoryRequestDto.getName(), result.getName());
        Assertions.assertEquals(categoryRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(categoryRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_validateNameRequired() {
        // Arrange
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId("1")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            categoryService.saveOrUpdate(categoryRequestDto);
        });

        Assertions.assertEquals("Attribute 'name' is required", exception.getMessage());
    }

    @Test
    public void saveOrUpdate_validateIntegrationCategoryIdRequired() {
        // Arrange
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .name("Technology")
                .description("Technology related questions")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            categoryService.saveOrUpdate(categoryRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationCategoryId' is required", exception.getMessage());
    }

    @Test
    public void saveOrUpdate_saveNewCategoryWithParentCategoryId() {
        // Arrange
        Category parentCategory = categoryRepository.save(Category.builder()
                .integrationCategoryId("1")
                .name("Technology")
                .description("Technology related questions")
                .active(true)
                .questionCount(0)
                .build());
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId("2")
                .integrationParentCategoryId("1")
                .name("Programming")
                .active(true)
                .build();

        // Act
        Category result = categoryService.saveOrUpdate(categoryRequestDto);

        // Assert
        Assertions.assertTrue(nonNull(result.getCategoryId()));
        Assertions.assertEquals(categoryRequestDto.getIntegrationCategoryId(), result.getIntegrationCategoryId());
        Assertions.assertEquals(parentCategory.getCategoryId(), result.getParentCategoryId());
        Assertions.assertEquals(categoryRequestDto.getName(), result.getName());
        Assertions.assertEquals(categoryRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(categoryRequestDto.isActive(), result.isActive());
    }

    @Test
    public void saveOrUpdate_updateCategoryWithParentCategoryId() {
        // Arrange
        Category parentCategory = categoryRepository.save(Category.builder()
                .integrationCategoryId("1")
                .name("Technology")
                .description("Technology related questions")
                .active(true)
                .questionCount(0)
                .build());
        Category existingCategory = categoryRepository.save(Category.builder()
                .integrationCategoryId("2")
                .parentCategoryId(parentCategory.getCategoryId())
                .name("Programming")
                .description("Programming related questions")
                .active(true)
                .questionCount(0)
                .build());
        Category newParentCategory = categoryRepository.save(Category.builder()
                .integrationCategoryId("3")
                .name("Computing")
                .description("Computing related questions")
                .active(true)
                .questionCount(0)
                .build());
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId(existingCategory.getIntegrationCategoryId())
                .integrationParentCategoryId("3")
                .name("Programming")
                .description("Programming related questions")
                .active(true)
                .build();

        // Act
        Category result = categoryService.saveOrUpdate(categoryRequestDto);

        // Assert
        Assertions.assertEquals(existingCategory.getCategoryId(), result.getCategoryId());
        Assertions.assertEquals(newParentCategory.getCategoryId(), result.getParentCategoryId());
        Assertions.assertEquals(categoryRequestDto.getIntegrationCategoryId(), result.getIntegrationCategoryId());
        Assertions.assertEquals(categoryRequestDto.getName(), result.getName());
        Assertions.assertEquals(categoryRequestDto.getDescription(), result.getDescription());
        Assertions.assertEquals(categoryRequestDto.isActive(), result.isActive());
    }


    @Test
    public void saveOrUpdate_parentCategoryIdNotFound() {
        // Arrange
        CategoryRequestDto categoryRequestDto = CategoryRequestDto.builder()
                .integrationCategoryId("2")
                .integrationParentCategoryId("1")
                .name("Programming")
                .active(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            categoryService.saveOrUpdate(categoryRequestDto);
        });

        Assertions.assertEquals("Not found parent category with id 1", exception.getMessage());
    }
}

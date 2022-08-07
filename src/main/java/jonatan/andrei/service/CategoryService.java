package jonatan.andrei.service;

import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.CategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.repository.CategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    @Transactional
    public Category saveOrUpdate(CategoryRequestDto categoryRequestDto) {
        validateRequiredData(categoryRequestDto);
        Category existingCategory = categoryRepository.findByIntegrationCategoryId(categoryRequestDto.getIntegrationCategoryId());
        Long parentCategoryId = findParentCategoryId(categoryRequestDto.getIntegrationParentCategoryId());

        if (isNull(existingCategory)) {
            return categoryRepository.save(CategoryFactory.newCategory(categoryRequestDto, parentCategoryId));
        } else {
            return categoryRepository.save(CategoryFactory.overwrite(existingCategory, categoryRequestDto, parentCategoryId));
        }
    }

    @Transactional
    public List<Category> saveOrUpdate(List<CategoryRequestDto> categories) {
        return categories.stream().map(category -> saveOrUpdate(category)).collect(Collectors.toList());
    }

    private void validateRequiredData(CategoryRequestDto categoryRequestDto) {
        if (isNull(categoryRequestDto.getIntegrationCategoryId())) {
            throw new RequiredDataException("Attribute 'integrationCategoryId' is required");
        }
        if (isNull(categoryRequestDto.getName())) {
            throw new RequiredDataException("Attribute 'name' is required");
        }
    }

    private Long findParentCategoryId(String integrationParentCategoryId) {
        if (isNull(integrationParentCategoryId)) {
            return null;
        }
        return Optional.ofNullable(categoryRepository.findCategoryIdByIntegrationCategoryId(integrationParentCategoryId)).map(Category::getCategoryId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found parent category with id " + integrationParentCategoryId));
    }
}

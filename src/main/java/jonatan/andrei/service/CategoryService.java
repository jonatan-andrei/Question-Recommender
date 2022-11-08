package jonatan.andrei.service;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.dto.CategoryRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.CategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.repository.CategoryRepository;
import liquibase.pro.packaged.B;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class CategoryService {

    @Inject
    TotalActivitySystemService totalActivitySystemService;

    @Inject
    CategoryRepository categoryRepository;

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

    public List<Category> findByIntegrationCategoriesIds(List<String> integrationCategoriesIds) {
        List<Category> categories = categoryRepository.findByintegrationCategoryIdIn(integrationCategoriesIds);
        validateIfAllCategoriesWereFound(integrationCategoriesIds, categories);
        return categories;
    }

    public void updateNumberQuestionsByAction(List<QuestionCategory> questionCategories, UserActionType userActionType, UserActionUpdateType userActionUpdateType, BigDecimal value) {
        List<Long> categoriesIds = questionCategories.stream().map(QuestionCategory::getCategoryId).collect(Collectors.toList());
        List<Category> categories = categoryRepository.findByCategoryIdIn(categoriesIds);
        for (Category category : categories) {
            switch (userActionType) {
                case QUESTION_ASKED ->
                        category.setNumberQuestionsAsked(category.getNumberQuestionsAsked().add(value));
                case QUESTION_VIEWED ->
                        category.setNumberQuestionsViewed(category.getNumberQuestionsViewed().add(value));
                case QUESTION_ANSWERED ->
                        category.setNumberQuestionsAnswered(category.getNumberQuestionsAnswered().add(value));
                case QUESTION_COMMENTED ->
                        category.setNumberQuestionsCommented(category.getNumberQuestionsCommented().add(value));
                case QUESTION_FOLLOWED ->
                        category.setNumberQuestionsFollowed(category.getNumberQuestionsFollowed().add(value));
                case QUESTION_UPVOTED ->
                        category.setNumberQuestionsUpvoted(category.getNumberQuestionsUpvoted().add(value));
                case QUESTION_DOWNVOTED ->
                        category.setNumberQuestionsDownvoted(category.getNumberQuestionsDownvoted().add(value));
                case ANSWER_UPVOTED ->
                        category.setNumberAnswersUpvoted(category.getNumberAnswersUpvoted().add(value));
                case ANSWER_DOWNVOTED ->
                        category.setNumberAnswersDownvoted(category.getNumberAnswersDownvoted().add(value));
                case COMMENT_UPVOTED ->
                        category.setNumberCommentsUpvoted(category.getNumberCommentsUpvoted().add(value));
                case COMMENT_DOWNVOTED ->
                        category.setNumberCommentsDownvoted(category.getNumberCommentsDownvoted().add(value));
            }
        }
        categoryRepository.saveAll(categories);
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, userActionType, userActionUpdateType.getValue());
    }

    private void validateIfAllCategoriesWereFound(List<String> integrationCategoriesIds, List<Category> categories) {
        List<String> integrationCategoriesIdsFound = categories.stream()
                .map(Category::getIntegrationCategoryId)
                .collect(Collectors.toList());

        Optional<String> categoryNotFound = integrationCategoriesIds.stream()
                .filter(c -> !integrationCategoriesIdsFound.contains(c))
                .findFirst();

        if (categoryNotFound.isPresent()) {
            throw new InconsistentIntegratedDataException("Not found category with integrationCategoryId " + categoryNotFound.get());
        }
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
        return Optional.ofNullable(categoryRepository.findByIntegrationCategoryId(integrationParentCategoryId)).map(Category::getCategoryId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found parent category with id " + integrationParentCategoryId));
    }

    public void clear() {
        categoryRepository.deleteAll();
    }
}

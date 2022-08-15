package jonatan.andrei.service;

import jonatan.andrei.factory.QuestionCategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.repository.QuestionCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionCategoryService {

    @Inject
    CategoryService categoryService;

    @Inject
    QuestionCategoryRepository questionCategoryRepository;

    public List<QuestionCategory> findByQuestionId(Long questionId) {
        return questionCategoryRepository.findByQuestionId(questionId);
    }

    public void save(Question question, List<String> integrationCategoriesIds) {
        List<QuestionCategory> existingQuestionCategories = questionCategoryRepository.findByQuestionId(question.getPostId());
        List<Category> categories = categoryService.findByIntegrationCategoriesIds(integrationCategoriesIds);

        List<Long> existingQuestionCategoriesIds = existingQuestionCategories.stream()
                .map(QuestionCategory::getCategoryId)
                .collect(Collectors.toList());
        List<QuestionCategory> questionCategoriesToInsert = categories.stream()
                .filter(c -> !existingQuestionCategoriesIds.contains(c.getCategoryId()))
                .map(c -> QuestionCategoryFactory.newQuestionCategory(question, c))
                .collect(Collectors.toList());
        questionCategoryRepository.saveAll(questionCategoriesToInsert);
        incrementQuestionCountByQuestionCategories(questionCategoriesToInsert);

        List<Long> categoriesIds = categories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        List<QuestionCategory> questionCategoriesToDelete = existingQuestionCategories.stream()
                .filter(qc -> !categoriesIds.contains(qc.getCategoryId()))
                .collect(Collectors.toList());
        questionCategoryRepository.deleteAll(questionCategoriesToDelete);
        decrementQuestionCountByQuestionCategories(questionCategoriesToDelete);
    }

    private void incrementQuestionCountByQuestionCategories(List<QuestionCategory> questionCategories) {
        if (!questionCategories.isEmpty()) {
            categoryService.incrementQuestionCountByCategoriesIds(questionCategories.stream()
                    .map(QuestionCategory::getCategoryId).collect(Collectors.toList()));
        }
    }

    private void decrementQuestionCountByQuestionCategories(List<QuestionCategory> questionCategories) {
        if (!questionCategories.isEmpty()) {
            categoryService.decrementQuestionCountByCategoriesIds(questionCategories.stream()
                    .map(QuestionCategory::getCategoryId).collect(Collectors.toList()));
        }
    }

}

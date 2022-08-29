package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.factory.QuestionCategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class QuestionCategoryService {

    @Inject
    CategoryService categoryService;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    QuestionCategoryRepository questionCategoryRepository;

    public List<QuestionCategory> findByQuestionId(Long questionId) {
        return questionCategoryRepository.findByQuestionId(questionId);
    }

    public void save(Question question, List<String> integrationCategoriesIds, User user) {
        List<QuestionCategory> existingQuestionCategories = questionCategoryRepository.findByQuestionId(question.getPostId());
        List<Category> categories = categoryService.findByIntegrationCategoriesIds(Optional.ofNullable(integrationCategoriesIds).orElse(Collections.EMPTY_LIST));

        List<Long> existingQuestionCategoriesIds = existingQuestionCategories.stream()
                .map(QuestionCategory::getCategoryId)
                .collect(Collectors.toList());
        List<QuestionCategory> questionCategoriesToInsert = categories.stream()
                .filter(c -> !existingQuestionCategoriesIds.contains(c.getCategoryId()))
                .map(c -> QuestionCategoryFactory.newQuestionCategory(question, c))
                .collect(Collectors.toList());
        questionCategoryRepository.saveAll(questionCategoriesToInsert);
        incrementQuestionCountByQuestionCategories(questionCategoriesToInsert, user);

        List<Long> categoriesIds = categories.stream().map(Category::getCategoryId).collect(Collectors.toList());
        List<QuestionCategory> questionCategoriesToDelete = existingQuestionCategories.stream()
                .filter(qc -> !categoriesIds.contains(qc.getCategoryId()))
                .collect(Collectors.toList());
        questionCategoryRepository.deleteAll(questionCategoriesToDelete);
        decrementQuestionCountByQuestionCategories(questionCategoriesToDelete, user);
    }

    private void incrementQuestionCountByQuestionCategories(List<QuestionCategory> questionCategories, User user) {
        if (!questionCategories.isEmpty()) {
            categoryService.incrementQuestionCountByCategoriesIds(questionCategories.stream()
                    .map(QuestionCategory::getCategoryId).collect(Collectors.toList()));
            userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserActionType.QUESTION_ASKED, UserActionUpdateType.INCREASE);
        }
    }

    private void decrementQuestionCountByQuestionCategories(List<QuestionCategory> questionCategories, User user) {
        if (!questionCategories.isEmpty()) {
            categoryService.decrementQuestionCountByCategoriesIds(questionCategories.stream()
                    .map(QuestionCategory::getCategoryId).collect(Collectors.toList()));
            userCategoryService.updateNumberQuestionsByAction(user, questionCategories, UserActionType.QUESTION_ASKED, UserActionUpdateType.DECREASE);
        }
    }

}

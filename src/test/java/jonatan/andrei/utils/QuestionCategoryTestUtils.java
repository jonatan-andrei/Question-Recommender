package jonatan.andrei.utils;

import jonatan.andrei.factory.QuestionCategoryFactory;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.Question;
import jonatan.andrei.repository.CategoryRepository;
import jonatan.andrei.repository.QuestionCategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@ApplicationScoped
public class QuestionCategoryTestUtils {

    @Inject
    QuestionCategoryRepository questionCategoryRepository;

    @Inject
    CategoryRepository categoryRepository;

    public void saveQuestionCategories(Question question, List<Category> categories) {
        BigDecimal value = BigDecimal.ONE.divide(new BigDecimal(categories.size()), 2, RoundingMode.HALF_UP);
        for (Category category : categories) {
            category.setNumberQuestionsAsked(category.getNumberQuestionsAsked().add(value));
            categoryRepository.save(category);
            questionCategoryRepository.save(QuestionCategoryFactory.newQuestionCategory(question, category));
        }
    }
}

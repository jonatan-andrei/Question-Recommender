package jonatan.andrei.factory;

import jonatan.andrei.model.Category;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;

public class QuestionCategoryFactory {

    public static QuestionCategory newQuestionCategory(Question question, Category category) {
        return QuestionCategory.builder()
                .questionId(question.getPostId())
                .categoryId(category.getCategoryId())
                .build();
    }
}

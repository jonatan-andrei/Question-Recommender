package jonatan.andrei.utils;

import jonatan.andrei.model.Category;
import jonatan.andrei.repository.CategoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class CategoryTestUtils {

    @Inject
    CategoryRepository categoryRepository;

    public Category saveWithIntegrationCategoryId(String integrationCategoryId) {
        return categoryRepository.save(Category.builder()
                .integrationCategoryId(integrationCategoryId)
                .name(integrationCategoryId)
                .active(true)
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .build());
    }
}

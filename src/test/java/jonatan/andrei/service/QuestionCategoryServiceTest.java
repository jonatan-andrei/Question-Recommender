package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class QuestionCategoryServiceTest extends AbstractServiceTest {

    @Inject
    QuestionCategoryService questionCategoryService;

    @Test
    public void save_withoutQuestionCategories() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("a");
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Act
        questionCategoryService.save(question, asList(), user);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void save_questionCategoriesToInsert() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("a");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("1");
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("2");
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Act
        questionCategoryService.save(question, asList(category1.getIntegrationCategoryId(), category2.getIntegrationCategoryId()), user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertTrue(nonNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category1.getCategoryId())));
        assertEquals(BigDecimal.ONE, categoryRepository.findByIntegrationCategoryId("1").getNumberQuestionsAsked().stripTrailingZeros());
        assertTrue(nonNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category2.getCategoryId())));
        assertEquals(BigDecimal.ONE, categoryRepository.findByIntegrationCategoryId("2").getNumberQuestionsAsked().stripTrailingZeros());
    }

    @Test
    public void save_questionCategoriesToDelete() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("a");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("1");
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("2");
        Category category3 = categoryTestUtils.saveWithIntegrationCategoryId("3");
        Category category4 = categoryTestUtils.saveWithIntegrationCategoryId("4");
        questionCategoryTestUtils.saveQuestionCategories(question, asList(category1, category2, category3, category4));
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Act
        questionCategoryService.save(question, asList(category1.getIntegrationCategoryId(), category3.getIntegrationCategoryId()), user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertTrue(nonNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category1.getCategoryId())));
        assertEquals(BigDecimal.ONE, categoryRepository.findByIntegrationCategoryId("1").getNumberQuestionsAsked().stripTrailingZeros());
        assertTrue(isNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category2.getCategoryId())));
        assertEquals(BigDecimal.ZERO, categoryRepository.findByIntegrationCategoryId("2").getNumberQuestionsAsked().stripTrailingZeros());
        assertTrue(nonNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category3.getCategoryId())));
        assertEquals(BigDecimal.ONE, categoryRepository.findByIntegrationCategoryId("3").getNumberQuestionsAsked().stripTrailingZeros());
        assertTrue(isNull(questionCategoryRepository.findByQuestionIdAndCategoryId(question.getPostId(), category4.getCategoryId())));
        assertEquals(BigDecimal.ZERO, categoryRepository.findByIntegrationCategoryId("4").getNumberQuestionsAsked().stripTrailingZeros());
    }

    @Test
    public void save_categoryNotFound() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("a");
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            questionCategoryService.save(question, asList("1"), user);
        });

        Assertions.assertEquals("Not found category with integrationCategoryId 1", exception.getMessage());
    }

}

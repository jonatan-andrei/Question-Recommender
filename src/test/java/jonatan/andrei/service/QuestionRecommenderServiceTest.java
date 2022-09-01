package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.model.Question;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class QuestionRecommenderServiceTest extends AbstractServiceTest {

    @Inject
    QuestionRecommenderService questionRecommenderService;

    @Test
    public void clear() {
        // Arrange
        tagTestUtils.saveWithName("A");
        categoryTestUtils.saveWithIntegrationCategoryId("1");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());

        // Act
        questionRecommenderService.clear();

        // Assert
        assertEquals(0, tagRepository.count());
        assertEquals(0, categoryRepository.count());
        assertEquals(0, answerRepository.count());
        assertEquals(0, questionRepository.count());
        assertEquals(0, postRepository.count());
    }
}

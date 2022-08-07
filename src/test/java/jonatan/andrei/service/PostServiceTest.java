package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.DuplicateQuestionRequestDto;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Question;
import jonatan.andrei.repository.QuestionRepository;
import jonatan.andrei.utils.QuestionTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class PostServiceTest {

    @Inject
    PostService postService;

    @Inject
    QuestionTestUtils questionTestUtils;

    @Inject
    QuestionRepository questionRepository;

    @Inject
    EntityManager entityManager;

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNotNull() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        Question duplicateQuestion = questionTestUtils.saveWithIntegrationPostId("1");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId(duplicateQuestion.getIntegrationPostId())
                .build();

        // Act
        postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertEquals(duplicateQuestion.getPostId(), updatedQuestion.getDuplicateQuestionId());
    }

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNull() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId(null)
                .build();

        // Act
        postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertEquals(null, updatedQuestion.getDuplicateQuestionId());
    }

    @Test
    public void registerDuplicateQuestion_questionNotFound() {
        // Arrange
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId("2")
                .integrationDuplicateQuestionId(null)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 2", exception.getMessage());
    }

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNotFound() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId("1")
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void hideOrExposePost_hidePost() {
        // Arrange
        Question question = questionTestUtils.save();
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId(question.getIntegrationPostId())
                .hidden(true)
                .build();

        // Act
        postService.hideOrExposePost(hidePostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertTrue(updatedQuestion.isHidden());
    }

    @Test
    public void hideOrExposePost_exposePost() {
        // Arrange
        Question question = questionTestUtils.save();
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId(question.getIntegrationPostId())
                .hidden(false)
                .build();

        // Act
        postService.hideOrExposePost(hidePostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertFalse(updatedQuestion.isHidden());
    }

    @Test
    public void hideOrExposePost_validateIntegrationPostIdRequired() {
        // Arrange
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .hidden(false)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.hideOrExposePost(hidePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationPostId' is required", exception.getMessage());
    }

    @Test
    public void hideOrExposePost_notFoundPostWithIntegrationPostId() {
        // Arrange
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId("1")
                .hidden(false)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.hideOrExposePost(hidePostRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }


}

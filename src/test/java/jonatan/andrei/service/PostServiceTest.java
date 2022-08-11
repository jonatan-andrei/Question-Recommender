package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.BestAnswerRequestDto;
import jonatan.andrei.dto.DuplicateQuestionRequestDto;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.Question;
import jonatan.andrei.repository.AnswerRepository;
import jonatan.andrei.repository.QuestionRepository;
import jonatan.andrei.utils.AnswerTestUtils;
import jonatan.andrei.utils.QuestionTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class PostServiceTest {

    @Inject
    PostService postService;

    @Inject
    QuestionTestUtils questionTestUtils;

    @Inject
    AnswerTestUtils answerTestUtils;

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnswerRepository answerRepository;

    @Inject
    EntityManager entityManager;

    @Test
    public void registerBestAnswer_registerBestAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Act
        postService.registerBestAnswer(bestAnswerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Answer result = answerRepository.findById(answer.getPostId()).get();
        assertTrue(result.isBestAnswer());
    }

    @Test
    public void registerBestAnswer_unRegisterBestAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        answer.setBestAnswer(true);
        answerRepository.save(answer);
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(false)
                .build();

        // Act
        postService.registerBestAnswer(bestAnswerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Answer result = answerRepository.findById(answer.getPostId()).get();
        assertFalse(result.isBestAnswer());
    }

    @Test
    public void registerBestAnswer_questionNotFound() {
        // Arrange
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId("1")
                .integrationAnswerId("2")
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_answerNotFound() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId("2")
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 2", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_bestAnswerAlreadyRegister() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        Answer otherAnswer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("3", question.getPostId());
        otherAnswer.setBestAnswer(true);
        answerRepository.save(otherAnswer);
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Question 1 already has a best answer selected", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_notAnAnswerToQuestion() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Question otherQuestion = questionTestUtils.saveWithIntegrationPostId("2");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("3", otherQuestion.getPostId());
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Answer 3 is not an answer to question 1", exception.getMessage());
    }

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

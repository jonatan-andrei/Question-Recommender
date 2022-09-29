package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionNotificationQueue;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class QuestionNotificationQueueServiceTest extends AbstractServiceTest {

    @Inject
    QuestionNotificationQueueService questionNotificationQueueService;

    @Test
    public void generateQuestionNotifications() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");

        // Act
        questionNotificationQueueService.saveQuestionNotificationQueue(question.getPostId(), question.getIntegrationPostId(), LocalDateTime.now());

        // Assert
        QuestionNotificationQueue questionNotificationQueue = questionNotificationQueueRepository.findByQuestionId(question.getPostId());
        assertTrue(nonNull(questionNotificationQueue));
    }

    @Test
    public void generateQuestionNotifications_oldQuestion() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");

        // Act
        questionNotificationQueueService.saveQuestionNotificationQueue(question.getPostId(), question.getIntegrationPostId(), LocalDateTime.now().minusMonths(1));

        // Assert
        QuestionNotificationQueue questionNotificationQueue = questionNotificationQueueRepository.findByQuestionId(question.getPostId());
        assertTrue(isNull(questionNotificationQueue));
    }

    @Test
    public void findBySendDateIsNullAndDateWasIgnoredIsNull() {
        // Arrange
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder().questionId(1L).build());
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder().questionId(2L).sendDate(LocalDateTime.now()).build());
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder().questionId(3L).build());
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder().questionId(4L).sendDate(LocalDateTime.now()).build());

        // Act
        List<QuestionNotificationQueue> list = questionNotificationQueueService.findBySendDateIsNullAndDateWasIgnoredIsNull();

        // Assert
        assertTrue(list.stream().filter(qnq -> qnq.getQuestionId().equals(1L)).findFirst().isPresent());
        assertFalse(list.stream().filter(qnq -> qnq.getQuestionId().equals(2L)).findFirst().isPresent());
        assertTrue(list.stream().filter(qnq -> qnq.getQuestionId().equals(3L)).findFirst().isPresent());
        assertFalse(list.stream().filter(qnq -> qnq.getQuestionId().equals(4L)).findFirst().isPresent());
    }
}

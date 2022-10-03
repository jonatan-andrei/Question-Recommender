package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionNotification;
import jonatan.andrei.model.QuestionNotificationQueue;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class QuestionNotificationServiceTest extends AbstractServiceTest {

    @Inject
    QuestionNotificationService questionNotificationService;

    @Test
    public void generateQuestionNotifications() {
        // Arrange
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        Question question = questionTestUtils.saveWithIntegrationPostId("A");
        recommendationSettingsService.save(asList(RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER)
                        .value(BigDecimal.ZERO)
                        .build(),
                RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.ENABLE_CHANNEL)
                        .value(BigDecimal.ONE)
                        .build()));
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder()
                .questionId(question.getPostId())
                .integrationQuestionId(question.getIntegrationPostId())
                .build());

        // Act
        questionNotificationService.generateQuestionNotifications(IntegrationMethodType.NONE);

        // Assert
        QuestionNotification questionNotificationUser1 = questionNotificationRepository.findByQuestionIdAndUserId(question.getPostId(), user1.getUserId());
        assertTrue(nonNull(questionNotificationUser1));
        QuestionNotification questionNotificationUser2 = questionNotificationRepository.findByQuestionIdAndUserId(question.getPostId(), user2.getUserId());
        assertTrue(nonNull(questionNotificationUser2));
    }

    @Test
    public void generateQuestionNotifications_page2() {
        // Arrange
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        User user3 = userTestUtils.saveWithIntegrationUserId("3");
        Question question = questionTestUtils.saveWithIntegrationPostId("A");
        recommendationSettingsService.save(asList(RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER)
                        .value(BigDecimal.ZERO)
                        .build(),
                RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST)
                        .value(BigDecimal.valueOf(2))
                        .build(),
                RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.ENABLE_CHANNEL)
                        .value(BigDecimal.ONE)
                        .build()));
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder()
                .questionId(question.getPostId())
                .integrationQuestionId(question.getIntegrationPostId())
                .build());

        // Act
        questionNotificationService.generateQuestionNotifications(IntegrationMethodType.NONE);

        // Assert
        QuestionNotification questionNotificationUser1 = questionNotificationRepository.findByQuestionIdAndUserId(question.getPostId(), user1.getUserId());
        assertTrue(nonNull(questionNotificationUser1));
        QuestionNotification questionNotificationUser2 = questionNotificationRepository.findByQuestionIdAndUserId(question.getPostId(), user2.getUserId());
        assertTrue(nonNull(questionNotificationUser2));
        QuestionNotification questionNotificationUser3 = questionNotificationRepository.findByQuestionIdAndUserId(question.getPostId(), user3.getUserId());
        assertTrue(nonNull(questionNotificationUser3));
    }

    @Test
    public void generateQuestionNotifications_emptyList() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("A");
        recommendationSettingsService.save(asList(RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER)
                        .value(BigDecimal.ZERO)
                        .build(),
                RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.QUESTION_NOTIFICATION)
                        .name(RecommendationSettingsType.ENABLE_CHANNEL)
                        .value(BigDecimal.ONE)
                        .build()));
        questionNotificationQueueRepository.save(QuestionNotificationQueue.builder()
                .questionId(question.getPostId())
                .integrationQuestionId(question.getIntegrationPostId())
                .build());

        // Act
        questionNotificationService.generateQuestionNotifications(IntegrationMethodType.NONE);
    }
}

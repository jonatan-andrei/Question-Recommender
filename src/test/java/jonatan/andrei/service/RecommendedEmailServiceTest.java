package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import jonatan.andrei.model.RecommendedEmail;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class RecommendedEmailServiceTest extends AbstractServiceTest {

    @Inject
    RecommendedEmailService recommendedEmailService;

    @Test
    public void generateRecommendedEmails() {
        // Arrange
        questionTestUtils.saveWithIntegrationPostId("A");
        questionTestUtils.saveWithIntegrationPostId("B");
        questionTestUtils.saveWithIntegrationPostId("C");
        questionTestUtils.saveWithIntegrationPostId("D");
        questionTestUtils.saveWithIntegrationPostId("E");
        questionTestUtils.saveWithIntegrationPostId("F");
        questionTestUtils.saveWithIntegrationPostId("G");
        questionTestUtils.saveWithIntegrationPostId("H");
        questionTestUtils.saveWithIntegrationPostId("I");
        questionTestUtils.saveWithIntegrationPostId("J");
        User user = userTestUtils.saveWithIntegrationUserId("1");
        user.setLastActivityDate(LocalDateTime.now().minusDays(1));
        userRepository.save(user);
        recommendationSettingsService.save(asList(RecommendationSettingsRequestDto.builder()
                .channel(RecommendationChannelType.RECOMMENDED_EMAIL)
                .name(RecommendationSettingsType.DEFAULT_HOUR_OF_THE_DAY_TO_SEND_RECOMMENDATIONS)
                .value(BigDecimal.valueOf(LocalDateTime.now().getHour()))
                .build(),
                RecommendationSettingsRequestDto.builder()
                        .channel(RecommendationChannelType.RECOMMENDED_EMAIL)
                        .name(RecommendationSettingsType.ENABLE_CHANNEL)
                        .value(BigDecimal.ONE)
                        .build()));

        // Act
        recommendedEmailService.generateRecommendedEmails(IntegrationMethodType.NONE);

        // Assert
        assertEquals(11, recommendedEmailRepository.findAll().size());
        RecommendedEmail recommendedEmail = recommendedEmailRepository.findByIntegrationUserId("1");
        assertTrue(nonNull(recommendedEmail));
        assertEquals(5, recommendedEmailQuestionRepository.findByRecommendedEmailId(recommendedEmail.getRecommendedEmailId()).size());
    }
}

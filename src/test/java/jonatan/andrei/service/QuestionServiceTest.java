package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedQuestionOfPageDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.RecommendationSettings;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@QuarkusTest
@TestTransaction
public class QuestionServiceTest extends AbstractServiceTest {

    @Inject
    QuestionService questionService;

    @Test
    public void findRecommendedList_publicationDateRelevance() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDateAndUpdateDate("1", dateOfRecommendations.minusDays(1), LocalDateTime.now());
        Question question2 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDateAndUpdateDate("2", dateOfRecommendations.minusDays(2), LocalDateTime.now());
        Question question3 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDateAndUpdateDate("3", dateOfRecommendations.minusYears(2), dateOfRecommendations.minusYears(2));
        Question question4 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDateAndUpdateDate("4", dateOfRecommendations.minusDays(8), LocalDateTime.now());
        Question question5 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDateAndUpdateDate("5", dateOfRecommendations.minusDays(8), LocalDateTime.now().minusDays(6));
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(235.44));
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(1), question2.getIntegrationPostId(), BigDecimal.valueOf(220.88));
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(2), question4.getIntegrationPostId(), BigDecimal.valueOf(147.81));
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(3), question5.getIntegrationPostId(), BigDecimal.valueOf(104.95));
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(4), question3.getIntegrationPostId(), BigDecimal.valueOf(0.00));
    }

    private void assertRecommendedQuestionOfPageDto(RecommendedQuestionOfPageDto recommendedQuestionOfPageDto, String integrationPostId, BigDecimal score) {
        Assertions.assertEquals(recommendedQuestionOfPageDto.getIntegrationPostId(), integrationPostId);
        Assertions.assertEquals(recommendedQuestionOfPageDto.getScore().setScale(2, RoundingMode.HALF_UP), score.setScale(2, RoundingMode.HALF_UP));
    }
}

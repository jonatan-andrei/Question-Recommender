package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class RecommendationSettingsServiceTest extends AbstractServiceTest {

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Test
    public void save() {
        // Arrange
        List<RecommendationSettingsRequestDto> recommendationSettings = new ArrayList<>();
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_EXPLICIT_TAG, BigDecimal.valueOf(50), RecommendationChannelType.RECOMMENDED_LIST));
        recommendationSettings.add(new RecommendationSettingsRequestDto(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT, BigDecimal.valueOf(25), RecommendationChannelType.RECOMMENDED_LIST));

        // Act
        recommendationSettingsService.save(recommendationSettings);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Map<RecommendationSettingsType, BigDecimal> result = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        assertEquals(BigDecimal.valueOf(50).stripTrailingZeros(), result.get(RecommendationSettingsType.RELEVANCE_EXPLICIT_TAG).stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(25).stripTrailingZeros(), result.get(RecommendationSettingsType.RELEVANCE_PUBLICATION_DATE_RELEVANT).stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(-20).stripTrailingZeros(), result.get(RecommendationSettingsType.RELEVANCE_USER_ALREADY_VIEWED).stripTrailingZeros());
    }
}

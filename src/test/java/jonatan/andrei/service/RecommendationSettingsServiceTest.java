package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.RecommendationSettingsType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
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
        Map<RecommendationSettingsType, Integer> recommendationSettingsMap = new HashMap<>();
        recommendationSettingsMap.put(RecommendationSettingsType.QUESTION_LIST_RELEVANCE_EXPLICIT_TAG, 50);
        recommendationSettingsMap.put(RecommendationSettingsType.QUESTION_LIST_RELEVANCE_PUBLICATION_DATE, 25);

        // Act
        recommendationSettingsService.save(recommendationSettingsMap);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Map<RecommendationSettingsType, Integer> result = recommendationSettingsService.findRecommendationSettings();
        assertEquals(50, result.get(RecommendationSettingsType.QUESTION_LIST_RELEVANCE_EXPLICIT_TAG));
        assertEquals(25, result.get(RecommendationSettingsType.QUESTION_LIST_RELEVANCE_PUBLICATION_DATE));
        assertEquals(-20, result.get(RecommendationSettingsType.QUESTION_LIST_RELEVANCE_USER_ALREADY_VIEWED));
    }
}

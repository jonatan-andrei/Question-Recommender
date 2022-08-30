package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class RecommendedListServiceTest extends AbstractServiceTest {

    @Inject
    RecommendedListService recommendedListService;

    @Test
    public void findRecommendedList_secondPage() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 2;
        for (Integer i = 1; i <= 30; i++) {
            questionTestUtils.saveWithIntegrationPostIdAndPublicationDate(i.toString(), LocalDateTime.now().minusDays(i));
        }

        // Act
        RecommendedListResponseDto result = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, pageNumber);

        // Assert
        assertEquals(2, result.getTotalNumberOfPages());
        assertEquals(10, result.getQuestions().size());
        assertEquals("21", result.getQuestions().get(0).getIntegrationQuestionId());
        assertEquals("30", result.getQuestions().get(9).getIntegrationQuestionId());
    }

}

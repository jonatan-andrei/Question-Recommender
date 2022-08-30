package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.RecommendedQuestionOfPageDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@QuarkusTest
@TestTransaction
public class QuestionServiceTest extends AbstractServiceTest {

    @Inject
    QuestionService questionService;

//    @Test
//    public void findRecommendedList_newUserPage1() {
//        // Arrange
//        User user = userTestUtils.saveWithIntegrationUserId("A");
//        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", LocalDateTime.now().minusDays(1));
//        Question question2 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("2", LocalDateTime.now().minusDays(2));
//
//        // Act
//        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList();
//
//        // Assert
//        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(99.73));
//        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(1), question2.getIntegrationPostId(), BigDecimal.valueOf(99.45));
//    }

    private void assertRecommendedQuestionOfPageDto(RecommendedQuestionOfPageDto recommendedQuestionOfPageDto, String integrationPostId, BigDecimal score) {
        Assertions.assertEquals(recommendedQuestionOfPageDto.getIntegrationPostId(), integrationPostId);
        Assertions.assertEquals(recommendedQuestionOfPageDto.getScore(), score);
    }
}

package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.RecommendedList;
import jonatan.andrei.model.RecommendedListPage;
import jonatan.andrei.model.RecommendedListPageQuestion;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
@TestTransaction
public class RecommendedListServiceTest extends AbstractServiceTest {

    @Inject
    RecommendedListService recommendedListService;

    @Inject
    RecommendedListPageService recommendedListPageService;

    @Test
    public void findRecommendedList_firstPage() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 1;
        for (Integer i = 1; i <= 30; i++) {
            questionTestUtils.saveWithIntegrationPostIdAndPublicationDate(i.toString(), LocalDateTime.now().minusDays(i));
        }

        // Act
        RecommendedListResponseDto result = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, pageNumber);

        // Assert
        assertEquals(2, result.getTotalNumberOfPages());
        assertEquals(20, result.getQuestions().size());
        assertEquals("1", result.getQuestions().get(0).getIntegrationQuestionId());
        assertEquals("20", result.getQuestions().get(19).getIntegrationQuestionId());
        RecommendedList recommendedList = recommendedListRepository.findById(result.getRecommendedListId()).get();
        assertEquals(user.getUserId(), recommendedList.getUserId());
        assertEquals(2, recommendedList.getTotalNumberOfPages());
        assertEquals(30, recommendedList.getTotalNumberOfQuestions());
        assertEquals(lengthQuestionListPage, recommendedList.getLengthQuestionListPage());
        RecommendedListPage recommendedListPage = recommendedListPageRepository.findByRecommendedListIdAndPageNumber(recommendedList.getRecommendedListId(), pageNumber);
        assertEquals(pageNumber, recommendedListPage.getPageNumber());
        List<RecommendedListPageQuestion> recommendedQuestions = recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPage.getRecommendedListPageId());
        assertEquals(20, recommendedQuestions.size());
    }

    @Test
    public void findRecommendedList_secondPage() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 2;
        for (Integer i = 1; i <= 30; i++) {
            questionTestUtils.saveWithIntegrationPostIdAndPublicationDate(i.toString(), LocalDateTime.now().minusDays(i));
        }
        RecommendedListResponseDto firstPage = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, 1);

        // Act
        RecommendedListResponseDto result = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), firstPage.getRecommendedListId(), pageNumber);

        // Assert
        assertEquals(firstPage.getRecommendedListId(), result.getRecommendedListId());
        assertEquals(2, result.getTotalNumberOfPages());
        assertEquals(10, result.getQuestions().size());
        assertEquals("21", result.getQuestions().get(0).getIntegrationQuestionId());
        assertEquals("30", result.getQuestions().get(9).getIntegrationQuestionId());
        RecommendedList recommendedList = recommendedListRepository.findById(result.getRecommendedListId()).get();
        assertEquals(user.getUserId(), recommendedList.getUserId());
        assertEquals(2, recommendedList.getTotalNumberOfPages());
        assertEquals(30, recommendedList.getTotalNumberOfQuestions());
        assertEquals(lengthQuestionListPage, recommendedList.getLengthQuestionListPage());
        RecommendedListPage recommendedListPage = recommendedListPageRepository.findByRecommendedListIdAndPageNumber(recommendedList.getRecommendedListId(), pageNumber);
        assertEquals(pageNumber, recommendedListPage.getPageNumber());
        List<RecommendedListPageQuestion> recommendedQuestions = recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPage.getRecommendedListPageId());
        assertEquals(10, recommendedQuestions.size());
    }

    @Test
    public void findRecommendedList_pageAlreadyGenerated() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 1;
        for (Integer i = 1; i <= 30; i++) {
            questionTestUtils.saveWithIntegrationPostIdAndPublicationDate(i.toString(), LocalDateTime.now().minusDays(i));
        }
        RecommendedListResponseDto page = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, pageNumber);

        // Act
        RecommendedListResponseDto result = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), page.getRecommendedListId(), pageNumber);

        // Assert
        assertEquals(page.getRecommendedListId(), result.getRecommendedListId());
        assertEquals(2, result.getTotalNumberOfPages());
        assertEquals(20, result.getQuestions().size());
        assertEquals("1", result.getQuestions().get(0).getIntegrationQuestionId());
        assertEquals("20", result.getQuestions().get(19).getIntegrationQuestionId());
        RecommendedList recommendedList = recommendedListRepository.findById(result.getRecommendedListId()).get();
        assertEquals(user.getUserId(), recommendedList.getUserId());
        assertEquals(2, recommendedList.getTotalNumberOfPages());
        assertEquals(30, recommendedList.getTotalNumberOfQuestions());
        assertEquals(lengthQuestionListPage, recommendedList.getLengthQuestionListPage());
        RecommendedListPage recommendedListPage = recommendedListPageRepository.findByRecommendedListIdAndPageNumber(recommendedList.getRecommendedListId(), pageNumber);
        assertEquals(pageNumber, recommendedListPage.getPageNumber());
        List<RecommendedListPageQuestion> recommendedQuestions = recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPage.getRecommendedListPageId());
        assertEquals(20, recommendedQuestions.size());
    }

    @Test
    public void findRecommendedList_noResults() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 1;

        // Act
        RecommendedListResponseDto result = recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, pageNumber);

        // Assert
        assertEquals(1, result.getTotalNumberOfPages());
        assertEquals(0, result.getQuestions().size());
        RecommendedList recommendedList = recommendedListRepository.findById(result.getRecommendedListId()).get();
        assertEquals(user.getUserId(), recommendedList.getUserId());
        assertEquals(1, recommendedList.getTotalNumberOfPages());
        assertEquals(0, recommendedList.getTotalNumberOfQuestions());
        assertEquals(lengthQuestionListPage, recommendedList.getLengthQuestionListPage());
        RecommendedListPage recommendedListPage = recommendedListPageRepository.findByRecommendedListIdAndPageNumber(recommendedList.getRecommendedListId(), pageNumber);
        assertEquals(pageNumber, recommendedListPage.getPageNumber());
        List<RecommendedListPageQuestion> recommendedQuestions = recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPage.getRecommendedListPageId());
        assertEquals(0, recommendedQuestions.size());
    }

    @Test
    public void findRecommendedList_listNotFound() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = 1;
        Long recommendedListId = 1L;

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), recommendedListId, pageNumber);
        });

        Assertions.assertEquals("Not found recommended list with id 1", exception.getMessage());
    }

    @Test
    public void findRecommendedList_pageNumberIsNull() {
        // Arrange
        Integer lengthQuestionListPage = 20;
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Integer pageNumber = null;

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            recommendedListService.findRecommendedList(lengthQuestionListPage, user.getIntegrationUserId(), null, pageNumber);
        });

        Assertions.assertEquals("Attribute 'pageNumber' is required and must be greater than zero", exception.getMessage());
    }

    @Test
    public void defineRealPageNumberIgnoringExistingPages() {
        // Arrange
        List<RecommendedListPage> pages = new ArrayList<>();
        pages.add(RecommendedListPage.builder().pageNumber(1).build());
        pages.add(RecommendedListPage.builder().pageNumber(2).build());
        pages.add(RecommendedListPage.builder().pageNumber(4).build());
        pages.add(RecommendedListPage.builder().pageNumber(7).build());
        Integer pageNumber = 5;

        // Act
        Integer result = recommendedListPageService.defineRealPageNumberIgnoringExistingPages(pages, pageNumber);

        // Assert
        assertEquals(2, result);
    }

}

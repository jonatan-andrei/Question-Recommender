package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedQuestionOfPageDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.Tag;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void findRecommendedList_userTagRelevance_explicitRecommendation() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setExplicitRecommendation(true);
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(100));
    }

    @Test
    public void findRecommendedList_userTagRelevance_ignored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setIgnored(true);
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertTrue(recommendedQuestionList.isEmpty());
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsAsked() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(15));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsAnswered() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAnswered(BigDecimal.valueOf(50));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAnswered(BigDecimal.valueOf(6));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(12));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsCommented() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsCommented(BigDecimal.valueOf(30));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsCommented(BigDecimal.valueOf(15));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(25));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsViewed() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsViewed(BigDecimal.valueOf(200));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsViewed(BigDecimal.valueOf(10));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsFollowed() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsFollowed(BigDecimal.valueOf(30));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsFollowed(BigDecimal.valueOf(3));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsUpvoted(BigDecimal.valueOf(40));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsUpvoted(BigDecimal.valueOf(40));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(50));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberQuestionsDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsDownvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsDownvoted(BigDecimal.valueOf(8));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-4));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberAnswersUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberAnswersUpvoted(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberAnswersUpvoted(BigDecimal.valueOf(15));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(37.5));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberAnswersDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberAnswersDownvoted(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberAnswersDownvoted(BigDecimal.valueOf(2));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-0.5));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberCommentsUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberCommentsUpvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberCommentsUpvoted(BigDecimal.valueOf(1));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(2));
    }

    @Test
    public void findRecommendedList_userTagRelevance_numberCommentsDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberCommentsDownvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberCommentsDownvoted(BigDecimal.valueOf(5));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-1));
    }

    @Test
    public void findRecommendedList_userTagRelevance_fourTags() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag1 = tagTestUtils.saveWithName("Tag1");
        Tag tag2 = tagTestUtils.saveWithName("Tag2");
        Tag tag3 = tagTestUtils.saveWithName("Tag3");
        Tag tag4 = tagTestUtils.saveWithName("Tag4");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag1, tag2, tag3, tag4));
        UserTag userTag1 = userTagTestUtils.save(user, tag1);
        userTag1.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userTagRepository.save(userTag1);
        UserTag userTag2 = userTagTestUtils.save(user, tag2);
        userTag2.setExplicitRecommendation(true);
        userTagRepository.save(userTag2);
        UserTag userTag3 = userTagTestUtils.save(user, tag3);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertEquals(1, recommendedQuestionList.size());
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(115));
    }

    @Test
    public void findRecommendedList_userTagRelevance_minimumOfActivitiesToConsiderMaximumScore() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(5));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAsked(BigDecimal.valueOf(2));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, Integer> recommendationSettings = recommendationSettingsService.findRecommendationSettings();
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfPageDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfPageDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(10));
    }


    private void assertRecommendedQuestionOfPageDto(RecommendedQuestionOfPageDto recommendedQuestionOfPageDto, String integrationPostId, BigDecimal score) {
        Assertions.assertEquals(recommendedQuestionOfPageDto.getIntegrationPostId(), integrationPostId);
        Assertions.assertEquals(recommendedQuestionOfPageDto.getScore().setScale(2, RoundingMode.HALF_UP), score.setScale(2, RoundingMode.HALF_UP));
    }
}

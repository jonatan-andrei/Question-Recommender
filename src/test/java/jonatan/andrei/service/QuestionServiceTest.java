package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.dto.UserToSendQuestionNotificationDto;
import jonatan.andrei.factory.QuestionViewFactory;
import jonatan.andrei.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static jonatan.andrei.domain.RecommendationSettingsType.MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER;
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
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(235.44));
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(1), question2.getIntegrationPostId(), BigDecimal.valueOf(220.88));
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(2), question4.getIntegrationPostId(), BigDecimal.valueOf(147.81));
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(3), question5.getIntegrationPostId(), BigDecimal.valueOf(104.95));
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(4), question3.getIntegrationPostId(), BigDecimal.valueOf(0.00));
    }

    @Test
    public void findRecommendedList_relevanceHasAnswer() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setAnswers(1);
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-40));
    }

    @Test
    public void findRecommendedList_relevancePerAnswer() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setAnswers(8);
        questionRepository.save(question);
        entityManager.flush();
        entityManager.clear();
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-110));
    }

    @Test
    public void findRecommendedList_relevanceHasBestAnswer() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        question.setBestAnswerId(answer.getPostId());
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-50));
    }

    @Test
    public void findRecommendedList_relevanceDuplicateQuestion() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Question originalQuestion = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("2", dateOfRecommendations.minusYears(3));
        originalQuestion.setHidden(true);
        questionRepository.save(originalQuestion);
        question.setDuplicateQuestionId(originalQuestion.getPostId());
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-100));
    }

    @Test
    public void findRecommendedList_relevanceQuestionNumberViews() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setViews(10);
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(2));
    }

    @Test
    public void findRecommendedList_relevanceQuestionNumberFollowers() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setFollowers(5);
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(50));
    }

    @Test
    public void findRecommendedList_relevanceQuestionNumberUpvotes() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setUpvotes(5);
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(50));
    }

    @Test
    public void findRecommendedList_relevanceQuestionNumberDownvotes() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        question.setDownvotes(2);
        questionRepository.save(question);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-20));
    }

    @Test
    public void findRecommendedList_userAlreadyAnswered() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        answer.setUserId(user.getUserId());
        answerRepository.save(answer);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-25));
    }

    @Test
    public void findRecommendedList_userAlreadyCommented() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        QuestionComment questionComment1 = questionCommentTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserId("2", question.getPostId(), user.getUserId());
        QuestionComment questionComment2 = questionCommentTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserId("3", question.getPostId(), user.getUserId());
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("4", question.getPostId());
        AnswerComment answerComment1 = answerCommentTestUtils.saveWithIntegrationPostIdAndAnswerIdAndUserId("5", answer.getPostId(), user.getUserId());
        AnswerComment answerComment2 = answerCommentTestUtils.saveWithIntegrationPostIdAndAnswerIdAndUserId("6", answer.getPostId(), user.getUserId());
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertEquals(1, recommendedQuestionList.size());
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-80));
    }

    @Test
    public void findRecommendedList_relevanceUserFollowerAsker() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        User userFollowed = userTestUtils.saveWithIntegrationUserId("B");
        question.setUserId(userFollowed.getUserId());
        questionRepository.save(question);
        userFollowerRepository.save(UserFollower.builder()
                .userId(userFollowed.getUserId())
                .followerId(user.getUserId())
                .startDate(LocalDateTime.now())
                .build());
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(100));
    }

    @Test
    public void findRecommendedList_relevanceUserAlreadyViewed() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        questionRepository.save(question);
        QuestionView questionView = QuestionViewFactory.newQuestionView(question.getPostId(), user.getUserId());
        questionView.setNumberOfViews(10);
        questionViewRepository.save(questionView);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-200));
    }

    @Test
    public void findRecommendedList_relevanceUserAlreadyViewedInList() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        questionRepository.save(question);
        QuestionView questionView = QuestionViewFactory.newQuestionView(question.getPostId(), user.getUserId());
        questionView.setNumberOfRecommendationsInList(10);
        questionViewRepository.save(questionView);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-150));
    }

    @Test
    public void findRecommendedList_relevanceUserAlreadyViewedInEmail() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        questionRepository.save(question);
        QuestionView questionView = QuestionViewFactory.newQuestionView(question.getPostId(), user.getUserId());
        questionView.setNumberOfRecommendationsInEmail(1);
        questionViewRepository.save(questionView);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-15));
    }

    @Test
    public void findRecommendedList_relevanceUserAlreadyViewedInNotification() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        questionRepository.save(question);
        QuestionView questionView = QuestionViewFactory.newQuestionView(question.getPostId(), user.getUserId());
        questionView.setRecommendedInNotification(true);
        questionViewRepository.save(questionView);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question.getIntegrationPostId(), BigDecimal.valueOf(-30));
    }

    @Test
    public void findRecommendedList_userTagRelevance_explicitRecommendation() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setExplicitRecommendation(true);
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(100));
    }

    @Test
    public void findRecommendedList_userTagRelevance_ignored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question1, asList(tag));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setIgnored(true);
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

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
        tag.setNumberQuestionsAsked(BigDecimal.valueOf(10));
        tagRepository.save(tag);
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.valueOf(100));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(10));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAnswered(BigDecimal.valueOf(6));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(12));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsCommented(BigDecimal.valueOf(15));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(25));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsViewed(BigDecimal.valueOf(10));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsFollowed(BigDecimal.valueOf(3));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsUpvoted(BigDecimal.valueOf(40));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(50));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsDownvoted(BigDecimal.valueOf(8));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-4));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberAnswersUpvoted(BigDecimal.valueOf(15));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(37.5));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberAnswersDownvoted(BigDecimal.valueOf(2));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-0.5));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberCommentsUpvoted(BigDecimal.valueOf(1));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(2));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberCommentsDownvoted(BigDecimal.valueOf(5));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-1));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.valueOf(4));
        UserTag userTag1 = userTagTestUtils.save(user, tag1);
        userTag1.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userTagRepository.save(userTag1);
        UserTag userTag2 = userTagTestUtils.save(user, tag2);
        userTag2.setExplicitRecommendation(true);
        userTagRepository.save(userTag2);
        UserTag userTag3 = userTagTestUtils.save(user, tag3);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertEquals(1, recommendedQuestionList.size());
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(115));
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
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAsked(BigDecimal.valueOf(2));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(10));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_explicitRecommendation() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        userCategory.setExplicitRecommendation(true);
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(100));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_ignored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setIgnored(true);
        userCategoryTestUtils.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertTrue(recommendedQuestionList.isEmpty());
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsAsked() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(15));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsAnswered() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAnswered(BigDecimal.valueOf(50));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsAnswered(BigDecimal.valueOf(6));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(12));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsCommented() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsCommented(BigDecimal.valueOf(30));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsCommented(BigDecimal.valueOf(15));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(25));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsViewed() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsViewed(BigDecimal.valueOf(200));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsViewed(BigDecimal.valueOf(10));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsFollowed() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsFollowed(BigDecimal.valueOf(30));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsFollowed(BigDecimal.valueOf(3));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(5));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsUpvoted(BigDecimal.valueOf(40));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsUpvoted(BigDecimal.valueOf(40));
        userCategoryTestUtils.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(50));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberQuestionsDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsDownvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsDownvoted(BigDecimal.valueOf(8));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-4));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberAnswersUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberAnswersUpvoted(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberAnswersUpvoted(BigDecimal.valueOf(15));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(37.5));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberAnswersDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberAnswersDownvoted(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberAnswersDownvoted(BigDecimal.valueOf(2));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-0.5));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberCommentsUpvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberCommentsUpvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberCommentsUpvoted(BigDecimal.valueOf(1));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(2));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_numberCommentsDownvoted() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberCommentsDownvoted(BigDecimal.valueOf(10));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberCommentsDownvoted(BigDecimal.valueOf(5));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(-1));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_fourCategories() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("1");
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("2");
        Category category3 = categoryTestUtils.saveWithIntegrationCategoryId("3");
        Category category4 = categoryTestUtils.saveWithIntegrationCategoryId("4");
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.valueOf(4));
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category1, category2, category3, category4));
        UserCategory userCategory1 = userCategoryTestUtils.save(user, category1);
        userCategory1.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userCategoryRepository.save(userCategory1);
        UserCategory userCategory2 = userCategoryTestUtils.save(user, category2);
        userCategory2.setExplicitRecommendation(true);
        userCategoryRepository.save(userCategory2);
        UserCategory userCategory3 = userCategoryTestUtils.save(user, category3);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertEquals(1, recommendedQuestionList.size());
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(115));
    }

    @Test
    public void findRecommendedList_userCategoryRelevance_minimumOfActivitiesToConsiderMaximumScore() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(5));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("1", dateOfRecommendations.minusYears(2));
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("Category");
        questionCategoryTestUtils.saveQuestionCategories(question1, asList(category));
        totalActivitySystemService.updateNumberByAction(PostClassificationType.CATEGORY, UserActionType.QUESTION_ASKED, BigDecimal.ONE);
        UserCategory userCategory = userCategoryTestUtils.save(user, category);
        userCategory.setNumberQuestionsAsked(BigDecimal.valueOf(2));
        userCategoryRepository.save(userCategory);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        entityManager.flush();
        entityManager.clear();

        // Act
        List<RecommendedQuestionOfListDto> recommendedQuestionList = questionService.findRecommendedList(user.getUserId(), 1, 20, 1L, recommendationSettings, dateOfRecommendations);

        // Assert
        assertRecommendedQuestionOfListDto(recommendedQuestionList.get(0), question1.getIntegrationPostId(), BigDecimal.valueOf(10));
    }


    private void assertRecommendedQuestionOfListDto(RecommendedQuestionOfListDto recommendedQuestionOfListDto, String integrationPostId, BigDecimal score) {
        Assertions.assertEquals(recommendedQuestionOfListDto.getIntegrationQuestionId(), integrationPostId);
        Assertions.assertEquals(recommendedQuestionOfListDto.getScore().setScale(2, RoundingMode.HALF_UP), score.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void findUsersToNotifyQuestion_userTagRelevance_numberQuestionsAsked() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        user.setNumberQuestionsAsked(BigDecimal.valueOf(20));
        userRepository.save(user);
        LocalDateTime dateOfRecommendations = LocalDateTime.now();
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag = tagTestUtils.saveWithName("Tag");
        questionTagTestUtils.saveQuestionTags(question, asList(tag));
        tag.setNumberQuestionsAsked(BigDecimal.valueOf(10));
        tagRepository.save(tag);
        totalActivitySystemService.updateNumberByAction(PostClassificationType.TAG, UserActionType.QUESTION_ASKED, BigDecimal.valueOf(100));
        UserTag userTag = userTagTestUtils.save(user, tag);
        userTag.setNumberQuestionsAsked(BigDecimal.valueOf(6));
        userTagRepository.save(userTag);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.QUESTION_NOTIFICATION);
        recommendationSettings.put(MINIMUM_SCORE_TO_SEND_QUESTION_TO_USER, BigDecimal.valueOf(10));
        entityManager.flush();
        entityManager.clear();

        // Act
        List<UserToSendQuestionNotificationDto> users = questionService.findUsersToNotifyQuestion(question.getPostId(), 1, 20, recommendationSettings, dateOfRecommendations.minusDays(60));

        // Assert
        assertUserToSendQuestionNotificationDto(users.get(0), user.getIntegrationUserId(), BigDecimal.valueOf(10));
    }

    private void assertUserToSendQuestionNotificationDto(UserToSendQuestionNotificationDto user, String integrationUserId, BigDecimal score) {
        Assertions.assertEquals(user.getIntegrationUserId(), integrationUserId);
    }
}

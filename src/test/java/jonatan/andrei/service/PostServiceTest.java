package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.BestAnswerRequestDto;
import jonatan.andrei.dto.DuplicateQuestionRequestDto;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.dto.ViewsRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.QuestionCategoryFactory;
import jonatan.andrei.factory.QuestionTagFactory;
import jonatan.andrei.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class PostServiceTest extends AbstractServiceTest {

    @Inject
    PostService postService;

    @Test
    public void registerViews_updateTotalViews() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        ViewsRequestDto viewsRequestDto = ViewsRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .totalViews(10)
                .integrationUsersId(new ArrayList<>())
                .build();

        // Act
        postService.registerViews(viewsRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question result = questionRepository.findByIntegrationPostId("1");
        assertEquals(10, result.getViews());
    }

    @Test
    public void registerViews_updateQuestionCategoriesViews() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("100");

        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("a");
        questionCategoryRepository.save(QuestionCategoryFactory.newQuestionCategory(question, category1));
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("b");
        questionCategoryRepository.save(QuestionCategoryFactory.newQuestionCategory(question, category2));

        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        userCategoryTestUtils.saveWithQuestionViews(user1, category1, 15);
        userCategoryTestUtils.saveWithQuestionViews(user1, category2, 10);
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        userCategoryTestUtils.saveWithQuestionViews(user2, category1, 20);
        User user3 = userTestUtils.saveWithIntegrationUserId("3");

        ViewsRequestDto viewsRequestDto = ViewsRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .totalViews(10)
                .integrationUsersId(asList(user1.getIntegrationUserId(), user2.getIntegrationUserId(), user3.getIntegrationUserId()))
                .build();

        // Act
        postService.registerViews(viewsRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserCategory user1Category1 = userCategoryRepository.findByUserIdAndCategoryId(user1.getUserId(), category1.getCategoryId());
        assertEquals(16, user1Category1.getNumberQuestionsViewed());

        UserCategory user1Category2 = userCategoryRepository.findByUserIdAndCategoryId(user1.getUserId(), category2.getCategoryId());
        assertEquals(11, user1Category2.getNumberQuestionsViewed());

        UserCategory user2Category1 = userCategoryRepository.findByUserIdAndCategoryId(user2.getUserId(), category1.getCategoryId());
        assertEquals(21, user2Category1.getNumberQuestionsViewed());

        UserCategory user2Category2 = userCategoryRepository.findByUserIdAndCategoryId(user2.getUserId(), category2.getCategoryId());
        assertEquals(1, user2Category2.getNumberQuestionsViewed());

        UserCategory user3Category1 = userCategoryRepository.findByUserIdAndCategoryId(user3.getUserId(), category1.getCategoryId());
        assertEquals(1, user3Category1.getNumberQuestionsViewed());

        UserCategory user3Category2 = userCategoryRepository.findByUserIdAndCategoryId(user3.getUserId(), category2.getCategoryId());
        assertEquals(1, user3Category2.getNumberQuestionsViewed());
    }

    @Test
    public void registerViews_updateQuestionTagsViews() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("100");

        Tag tag1 = tagTestUtils.saveWithName("a");
        questionTagRepository.save(QuestionTagFactory.newQuestionTag(question, tag1));
        Tag tag2 = tagTestUtils.saveWithName("b");
        questionTagRepository.save(QuestionTagFactory.newQuestionTag(question, tag2));

        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        userTagTestUtils.saveWithQuestionViews(user1, tag1, 15);
        userTagTestUtils.saveWithQuestionViews(user1, tag2, 10);
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        userTagTestUtils.saveWithQuestionViews(user2, tag1, 20);
        User user3 = userTestUtils.saveWithIntegrationUserId("3");

        ViewsRequestDto viewsRequestDto = ViewsRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .totalViews(10)
                .integrationUsersId(asList(user1.getIntegrationUserId(), user2.getIntegrationUserId(), user3.getIntegrationUserId()))
                .build();

        // Act
        postService.registerViews(viewsRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserTag user1Tag1 = userTagRepository.findByUserIdAndTagId(user1.getUserId(), tag1.getTagId());
        assertEquals(16, user1Tag1.getNumberQuestionsViewed());

        UserTag user1Tag2 = userTagRepository.findByUserIdAndTagId(user1.getUserId(), tag2.getTagId());
        assertEquals(11, user1Tag2.getNumberQuestionsViewed());

        UserTag user2Tag1 = userTagRepository.findByUserIdAndTagId(user2.getUserId(), tag1.getTagId());
        assertEquals(21, user2Tag1.getNumberQuestionsViewed());

        UserTag user2Tag2 = userTagRepository.findByUserIdAndTagId(user2.getUserId(), tag2.getTagId());
        assertEquals(1, user2Tag2.getNumberQuestionsViewed());

        UserTag user3Tag1 =userTagRepository.findByUserIdAndTagId(user3.getUserId(), tag1.getTagId());
        assertEquals(1, user3Tag1.getNumberQuestionsViewed());

        UserTag user3Tag2 =userTagRepository.findByUserIdAndTagId(user3.getUserId(), tag2.getTagId());
        assertEquals(1, user3Tag2.getNumberQuestionsViewed());
    }

    @Test
    public void registerBestAnswer_registerBestAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Act
        postService.registerBestAnswer(bestAnswerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Answer result = answerRepository.findById(answer.getPostId()).get();
        assertTrue(result.isBestAnswer());
    }

    @Test
    public void registerBestAnswer_unRegisterBestAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        answer.setBestAnswer(true);
        answerRepository.save(answer);
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(false)
                .build();

        // Act
        postService.registerBestAnswer(bestAnswerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Answer result = answerRepository.findById(answer.getPostId()).get();
        assertFalse(result.isBestAnswer());
    }

    @Test
    public void registerBestAnswer_questionNotFound() {
        // Arrange
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId("1")
                .integrationAnswerId("2")
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_answerNotFound() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId("2")
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 2", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_bestAnswerAlreadyRegister() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        Answer otherAnswer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("3", question.getPostId());
        otherAnswer.setBestAnswer(true);
        answerRepository.save(otherAnswer);
        entityManager.flush();
        entityManager.clear();
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Question 1 already has a best answer selected", exception.getMessage());
    }

    @Test
    public void registerBestAnswer_notAnAnswerToQuestion() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Question otherQuestion = questionTestUtils.saveWithIntegrationPostId("2");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("3", otherQuestion.getPostId());
        BestAnswerRequestDto bestAnswerRequestDto = BestAnswerRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationAnswerId(answer.getIntegrationPostId())
                .selected(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerBestAnswer(bestAnswerRequestDto);
        });

        Assertions.assertEquals("Answer 3 is not an answer to question 1", exception.getMessage());
    }

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNotNull() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        Question duplicateQuestion = questionTestUtils.saveWithIntegrationPostId("1");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId(duplicateQuestion.getIntegrationPostId())
                .build();

        // Act
        postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertEquals(duplicateQuestion.getPostId(), updatedQuestion.getDuplicateQuestionId());
    }

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNull() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId(null)
                .build();

        // Act
        postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertEquals(null, updatedQuestion.getDuplicateQuestionId());
    }

    @Test
    public void registerDuplicateQuestion_questionNotFound() {
        // Arrange
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId("2")
                .integrationDuplicateQuestionId(null)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 2", exception.getMessage());
    }

    @Test
    public void registerDuplicateQuestion_duplicateQuestionNotFound() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("2");
        DuplicateQuestionRequestDto duplicateQuestionRequestDto = DuplicateQuestionRequestDto.builder()
                .integrationQuestionId(question.getIntegrationPostId())
                .integrationDuplicateQuestionId("1")
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void hideOrExposePost_hidePost() {
        // Arrange
        Question question = questionTestUtils.save();
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId(question.getIntegrationPostId())
                .hidden(true)
                .build();

        // Act
        postService.hideOrExposePost(hidePostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertTrue(updatedQuestion.isHidden());
    }

    @Test
    public void hideOrExposePost_exposePost() {
        // Arrange
        Question question = questionTestUtils.save();
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId(question.getIntegrationPostId())
                .hidden(false)
                .build();

        // Act
        postService.hideOrExposePost(hidePostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertFalse(updatedQuestion.isHidden());
    }

    @Test
    public void hideOrExposePost_validateIntegrationPostIdRequired() {
        // Arrange
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .hidden(false)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.hideOrExposePost(hidePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationPostId' is required", exception.getMessage());
    }

    @Test
    public void hideOrExposePost_notFoundPostWithIntegrationPostId() {
        // Arrange
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId("1")
                .hidden(false)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.hideOrExposePost(hidePostRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }


}

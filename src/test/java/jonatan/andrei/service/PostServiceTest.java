package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.PostType;
import jonatan.andrei.domain.VoteType;
import jonatan.andrei.domain.VoteTypeRequest;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.QuestionCategoryFactory;
import jonatan.andrei.factory.QuestionTagFactory;
import jonatan.andrei.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class PostServiceTest extends AbstractServiceTest {

    @Inject
    PostService postService;

    @Test
    public void save_saveQuestion() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .tags(asList("a", "b"))
                .integrationAnonymousUserId("1")
                .build();

        // Act
        Question question = (Question) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), question.getIntegrationPostId());
        assertEquals(createPostRequestDto.getPostType(), question.getPostType());
        assertEquals(createPostRequestDto.getPublicationDate(), question.getPublicationDate());
        assertEquals(createPostRequestDto.getTitle(), question.getTitle());
        assertEquals(createPostRequestDto.getContentOrDescription(), question.getDescription());
        assertEquals("a;b", question.getTags());
    }

    @Test
    public void save_saveAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationAnonymousUserId("11")
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        Answer answer = (Answer) postService.save(createPostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answer.getIntegrationPostId());
        assertEquals(createPostRequestDto.getPostType(), answer.getPostType());
        assertEquals(createPostRequestDto.getPublicationDate(), answer.getPublicationDate());
        assertEquals(createPostRequestDto.getContentOrDescription(), answer.getContent());
        assertEquals(question.getPostId(), answer.getQuestionId());
        question = questionRepository.findByIntegrationPostId("1");
        assertEquals(1, question.getAnswers());
    }

    @Test
    public void save_saveQuestionComment() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.QUESTION_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationAnonymousUserId("11")
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        QuestionComment questionComment = (QuestionComment) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), questionComment.getIntegrationPostId());
        assertEquals(createPostRequestDto.getPostType(), questionComment.getPostType());
        assertEquals(createPostRequestDto.getPublicationDate(), questionComment.getPublicationDate());
        assertEquals(createPostRequestDto.getContentOrDescription(), questionComment.getContent());
        assertEquals(question.getPostId(), questionComment.getQuestionId());
    }

    @Test
    public void save_saveAnswerComment() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("3")
                .postType(PostType.ANSWER_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationAnonymousUserId("11")
                .integrationParentPostId(answer.getIntegrationPostId())
                .build();

        // Act
        AnswerComment answerComment = (AnswerComment) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answerComment.getIntegrationPostId());
        assertEquals(createPostRequestDto.getPostType(), answerComment.getPostType());
        assertEquals(createPostRequestDto.getPublicationDate(), answerComment.getPublicationDate());
        assertEquals(createPostRequestDto.getContentOrDescription(), answerComment.getContent());
        assertEquals(answer.getPostId(), answerComment.getAnswerId());
    }

    @Test
    public void save_postAlreadyRegister() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .tags(asList("a"))
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("There is already a post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void save_parentPostNotFound() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationAnonymousUserId("1")
                .integrationParentPostId("11")
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 11", exception.getMessage());
    }

    @Test
    public void save_validateIntegrationPostIdInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .tags(asList("a"))
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationPostId' is required", exception.getMessage());
    }

    @Test
    public void save_validatePostTypeInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .tags(asList("a"))
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Attribute 'postType' is required", exception.getMessage());
    }

    @Test
    public void save_validateTitleInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .tags(asList("a"))
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Attribute 'title' is required to postType QUESTION", exception.getMessage());
    }

    @Test
    public void save_validateContentOrDescriptionInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Attribute 'contentOrDescription' is required to postType ANSWER", exception.getMessage());
    }

    @Test
    public void save_validateCategoriesOrTagsInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList())
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("At least one of the fields must be informed: 'integrationCategoriesIds' or 'tags'", exception.getMessage());
    }

    @Test
    public void save_validateIntegrationParentPostIdInformed() {
        // Arrange
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationAnonymousUserId("1")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.save(createPostRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationParentPostId' is required to postType ANSWER", exception.getMessage());
    }

    @Test
    public void save_saveQuestionAndUserCategories() {
        // Arrange
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("a");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .integrationCategoriesIds(asList(category.getIntegrationCategoryId()))
                .tags(asList())
                .integrationUserId("11")
                .build();

        // Act
        Question question = (Question) postService.save(createPostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), question.getIntegrationPostId());
        UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), userCategory.getNumberQuestionsAsked().stripTrailingZeros());
    }

    @Test
    public void save_saveQuestionAndUserTags() {
        // Arrange
        Tag tagA = tagTestUtils.saveWithName("a");
        Tag tagB = tagTestUtils.saveWithName("b");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .title("Title")
                .contentOrDescription("Description")
                .tags(asList(tagA.getName(), tagB.getName()))
                .integrationUserId("11")
                .build();

        // Act
        Question question = (Question) postService.save(createPostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), question.getIntegrationPostId());
        UserTag userTagA = userTagRepository.findByUserIdAndTagId(user.getUserId(), tagA.getTagId());
        assertEquals(BigDecimal.valueOf(1), userTagA.getNumberQuestionsAsked().stripTrailingZeros());
        UserTag userTagB = userTagRepository.findByUserIdAndTagId(user.getUserId(), tagB.getTagId());
        assertEquals(BigDecimal.valueOf(1), userTagB.getNumberQuestionsAsked().stripTrailingZeros());
    }

    @Test
    public void save_saveAnswerAndUserCategories() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("a");
        questionCategoryTestUtils.saveQuestionCategories(question, asList(category));
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId(user.getIntegrationUserId())
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        Answer answer = (Answer) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answer.getIntegrationPostId());
        UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), userCategory.getNumberQuestionsAnswered());
    }

    @Test
    public void save_saveAnswerAndUserTags() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag = tagTestUtils.saveWithName("a");
        questionTagTestUtils.saveQuestionTags(question, asList(tag));
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.ANSWER)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId(user.getIntegrationUserId())
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        Answer answer = (Answer) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answer.getIntegrationPostId());
        UserTag userTag = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag.getTagId());
        assertEquals(BigDecimal.valueOf(1), userTag.getNumberQuestionsAnswered());
    }

    @Test
    public void save_saveQuestionCommentAndUserCategories() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("a");
        questionCategoryTestUtils.saveQuestionCategories(question, asList(category));
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.QUESTION_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId(user.getIntegrationUserId())
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        QuestionComment questionComment = (QuestionComment) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), questionComment.getIntegrationPostId());
        UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), userCategory.getNumberQuestionsCommented());
    }

    @Test
    public void save_saveQuestionCommentAndUserTags() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag = tagTestUtils.saveWithName("a");
        questionTagTestUtils.saveQuestionTags(question, asList(tag));
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.QUESTION_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId(user.getIntegrationUserId())
                .integrationParentPostId(question.getIntegrationPostId())
                .build();

        // Act
        QuestionComment questionComment = (QuestionComment) postService.save(createPostRequestDto);

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), questionComment.getIntegrationPostId());
        UserTag userTag = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag.getTagId());
        assertEquals(BigDecimal.valueOf(1), userTag.getNumberQuestionsCommented());
    }

    @Test
    public void save_saveAnswerCommentAndUserCategories() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Category category = categoryTestUtils.saveWithIntegrationCategoryId("a");
        questionCategoryTestUtils.saveQuestionCategories(question, asList(category));
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("3")
                .postType(PostType.ANSWER_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId("11")
                .integrationParentPostId(answer.getIntegrationPostId())
                .build();

        // Act
        AnswerComment answerComment = (AnswerComment) postService.save(createPostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answerComment.getIntegrationPostId());
        UserCategory userCategory = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), userCategory.getNumberQuestionsCommented().stripTrailingZeros());
    }

    @Test
    public void save_saveAnswerCommentAndUserTags() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag = tagTestUtils.saveWithName("a");
        questionTagTestUtils.saveQuestionTags(question, asList(tag));
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        CreatePostRequestDto createPostRequestDto = CreatePostRequestDto.builder()
                .integrationPostId("3")
                .postType(PostType.ANSWER_COMMENT)
                .publicationDate(LocalDateTime.now().minusDays(1))
                .contentOrDescription("Content")
                .integrationUserId("11")
                .integrationParentPostId(answer.getIntegrationPostId())
                .build();

        // Act
        AnswerComment answerComment = (AnswerComment) postService.save(createPostRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(createPostRequestDto.getIntegrationPostId(), answerComment.getIntegrationPostId());
        UserTag userTag = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag.getTagId());
        assertEquals(BigDecimal.valueOf(1), userTag.getNumberQuestionsCommented().stripTrailingZeros());
    }

    @Test
    public void update_updateQuestion() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .title("Updated title")
                .contentOrDescription("Updated description")
                .tags(asList("a"))
                .build();

        // Act
        Question result = (Question) postService.update(updatePostRequestDto);

        // Assert
        assertEquals(question.getPostId(), result.getPostId());
        assertEquals(updatePostRequestDto.getTitle(), result.getTitle());
        assertEquals(updatePostRequestDto.getContentOrDescription(), result.getDescription());
    }

    @Test
    public void update_updateAnswer() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.ANSWER)
                .contentOrDescription("Updated content")
                .build();

        // Act
        Answer result = (Answer) postService.update(updatePostRequestDto);

        // Assert
        assertEquals(answer.getPostId(), result.getPostId());
        assertEquals(updatePostRequestDto.getContentOrDescription(), result.getContent());
    }

    @Test
    public void update_updateQuestionComment() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        QuestionComment questionComment = questionCommentTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("2")
                .postType(PostType.QUESTION_COMMENT)
                .contentOrDescription("Updated content")
                .build();

        // Act
        QuestionComment result = (QuestionComment) postService.update(updatePostRequestDto);

        // Assert
        assertEquals(questionComment.getPostId(), result.getPostId());
        assertEquals(updatePostRequestDto.getContentOrDescription(), result.getContent());
    }

    @Test
    public void update_updateAnswerComment() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Answer answer = answerTestUtils.saveWithIntegrationPostIdAndQuestionId("2", question.getPostId());
        AnswerComment answerComment = answerCommentTestUtils.saveWithIntegrationPostIdAndAnswerId("3", answer.getPostId());
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("3")
                .postType(PostType.ANSWER_COMMENT)
                .contentOrDescription("Updated content")
                .build();

        // Act
        AnswerComment result = (AnswerComment) postService.update(updatePostRequestDto);

        // Assert
        assertEquals(answerComment.getPostId(), result.getPostId());
        assertEquals(updatePostRequestDto.getContentOrDescription(), result.getContent());
    }

    @Test
    public void update_postNotFound() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .title("Updated title")
                .contentOrDescription("Updated description")
                .tags(asList("a"))
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void update_validateIntegrationPostIdInformed() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .postType(PostType.QUESTION)
                .title("Updated title")
                .contentOrDescription("Updated description")
                .tags(asList("a"))
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationPostId' is required", exception.getMessage());
    }

    @Test
    public void update_validatePostTypeInformed() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .title("Updated title")
                .contentOrDescription("Updated description")
                .tags(asList("a"))
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'postType' is required", exception.getMessage());
    }

    @Test
    public void update_validateTitleInformed() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .contentOrDescription("Updated description")
                .tags(asList("a"))
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'title' is required to postType QUESTION", exception.getMessage());
    }

    @Test
    public void update_validateContentOrDescriptionInformed() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.ANSWER)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("Attribute 'contentOrDescription' is required to postType ANSWER", exception.getMessage());
    }

    @Test
    public void update_validateCategoriesOrTagsInformed() {
        // Arrange
        UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .title("Updated title")
                .contentOrDescription("Updated description")
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.update(updatePostRequestDto);
        });

        Assertions.assertEquals("At least one of the fields must be informed: 'integrationCategoriesIds' or 'tags'", exception.getMessage());
    }

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
        userCategoryTestUtils.saveWithQuestionViews(user1, category1, BigDecimal.valueOf(15));
        userCategoryTestUtils.saveWithQuestionViews(user1, category2, BigDecimal.valueOf(10));
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        userCategoryTestUtils.saveWithQuestionViews(user2, category1, BigDecimal.valueOf(20));
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
        assertEquals(BigDecimal.valueOf(16), user1Category1.getNumberQuestionsViewed().stripTrailingZeros());

        UserCategory user1Category2 = userCategoryRepository.findByUserIdAndCategoryId(user1.getUserId(), category2.getCategoryId());
        assertEquals(BigDecimal.valueOf(11), user1Category2.getNumberQuestionsViewed().stripTrailingZeros());

        UserCategory user2Category1 = userCategoryRepository.findByUserIdAndCategoryId(user2.getUserId(), category1.getCategoryId());
        assertEquals(BigDecimal.valueOf(21), user2Category1.getNumberQuestionsViewed().stripTrailingZeros());

        UserCategory user2Category2 = userCategoryRepository.findByUserIdAndCategoryId(user2.getUserId(), category2.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), user2Category2.getNumberQuestionsViewed().stripTrailingZeros());

        UserCategory user3Category1 = userCategoryRepository.findByUserIdAndCategoryId(user3.getUserId(), category1.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), user3Category1.getNumberQuestionsViewed().stripTrailingZeros());

        UserCategory user3Category2 = userCategoryRepository.findByUserIdAndCategoryId(user3.getUserId(), category2.getCategoryId());
        assertEquals(BigDecimal.valueOf(1), user3Category2.getNumberQuestionsViewed().stripTrailingZeros());
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
        userTagTestUtils.saveWithQuestionViews(user1, tag1, BigDecimal.valueOf(15));
        userTagTestUtils.saveWithQuestionViews(user1, tag2, BigDecimal.valueOf(10));
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        userTagTestUtils.saveWithQuestionViews(user2, tag1, BigDecimal.valueOf(20));
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
        assertEquals(BigDecimal.valueOf(16), user1Tag1.getNumberQuestionsViewed().stripTrailingZeros());

        UserTag user1Tag2 = userTagRepository.findByUserIdAndTagId(user1.getUserId(), tag2.getTagId());
        assertEquals(BigDecimal.valueOf(11), user1Tag2.getNumberQuestionsViewed().stripTrailingZeros());

        UserTag user2Tag1 = userTagRepository.findByUserIdAndTagId(user2.getUserId(), tag1.getTagId());
        assertEquals(BigDecimal.valueOf(21), user2Tag1.getNumberQuestionsViewed().stripTrailingZeros());

        UserTag user2Tag2 = userTagRepository.findByUserIdAndTagId(user2.getUserId(), tag2.getTagId());
        assertEquals(BigDecimal.valueOf(1), user2Tag2.getNumberQuestionsViewed().stripTrailingZeros());

        UserTag user3Tag1 = userTagRepository.findByUserIdAndTagId(user3.getUserId(), tag1.getTagId());
        assertEquals(BigDecimal.valueOf(1), user3Tag1.getNumberQuestionsViewed().stripTrailingZeros());

        UserTag user3Tag2 = userTagRepository.findByUserIdAndTagId(user3.getUserId(), tag2.getTagId());
        assertEquals(BigDecimal.valueOf(1), user3Tag2.getNumberQuestionsViewed().stripTrailingZeros());
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
        question = questionRepository.findByIntegrationPostId("1");
        assertEquals(result.getPostId(), question.getBestAnswerId());
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
        question = questionRepository.findByIntegrationPostId("1");
        assertTrue(isNull(question.getBestAnswerId()));
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

    @Test
    public void registerVote_saveUpvote() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(post.getIntegrationPostId())
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        post = postService.findByIntegrationPostId(post.getIntegrationPostId());
        assertEquals(1, post.getUpvotes());
        assertEquals(0, post.getDownvotes());
        Vote vote = voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).get();
        assertEquals(VoteType.UPVOTE, vote.getVoteType());
        user = userRepository.findByIntegrationUserId("11").get();
        assertEquals(BigDecimal.valueOf(1), user.getNumberQuestionsUpvoted().stripTrailingZeros());
    }

    @Test
    public void registerVote_saveDownvote() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(post.getIntegrationPostId())
                .voteType(VoteTypeRequest.DOWNVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        post = postService.findByIntegrationPostId(post.getIntegrationPostId());
        assertEquals(0, post.getUpvotes());
        assertEquals(1, post.getDownvotes());
        Vote vote = voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).get();
        assertEquals(VoteType.DOWNVOTE, vote.getVoteType());
        user = userRepository.findByIntegrationUserId("11").get();
        assertEquals(BigDecimal.valueOf(1), user.getNumberQuestionsDownvoted().stripTrailingZeros());
    }

    @Test
    public void registerVote_NotRemoveVoteNotExist() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(post.getIntegrationPostId())
                .voteType(VoteTypeRequest.REMOVED)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);

        // Assert
        post = postService.findByIntegrationPostId(post.getIntegrationPostId());
        assertEquals(0, post.getUpvotes());
        assertEquals(0, post.getDownvotes());
        assertTrue(voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).isEmpty());
    }

    @Test
    public void registerVote_removeUpvote() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setUpvotes(10);
        question = questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Vote existingVote = voteRepository.save(Vote.builder()
                .postId(question.getPostId())
                .userId(user.getUserId())
                .voteType(VoteType.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build());
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(question.getIntegrationPostId())
                .voteType(VoteTypeRequest.REMOVED)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);

        // Assert
        Post post = postService.findByIntegrationPostId(question.getIntegrationPostId());
        assertEquals(9, post.getUpvotes());
        assertEquals(0, post.getDownvotes());
        assertTrue(voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).isEmpty());
    }

    @Test
    public void registerVote_removeDownvote() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setDownvotes(10);
        question = questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Vote existingVote = voteRepository.save(Vote.builder()
                .postId(question.getPostId())
                .userId(user.getUserId())
                .voteType(VoteType.DOWNVOTE)
                .voteDate(LocalDateTime.now())
                .build());
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(question.getIntegrationPostId())
                .voteType(VoteTypeRequest.REMOVED)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);

        // Assert
        Post post = postService.findByIntegrationPostId(question.getIntegrationPostId());
        assertEquals(0, post.getUpvotes());
        assertEquals(9, post.getDownvotes());
        assertTrue(voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).isEmpty());
    }

    @Test
    public void registerVote_validateIntegrationPostIdRequired() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.registerVote(voteRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationPostId' is required", exception.getMessage());
    }

    @Test
    public void registerVote_validateIntegrationUserIdRequired() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationPostId(post.getIntegrationPostId())
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.registerVote(voteRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationUserId' is required", exception.getMessage());
    }

    @Test
    public void registerVote_validateVoteTypeRequired() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(post.getIntegrationPostId())
                .voteDate(LocalDateTime.now())
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.registerVote(voteRequestDto);
        });

        Assertions.assertEquals("Attribute 'voteType' is required", exception.getMessage());
    }

    @Test
    public void registerVote_removeUpvoteAndSaveDownvote() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setUpvotes(10);
        question = questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Vote existingVote = voteRepository.save(Vote.builder()
                .postId(question.getPostId())
                .userId(user.getUserId())
                .voteType(VoteType.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build());
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(question.getIntegrationPostId())
                .voteType(VoteTypeRequest.DOWNVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);

        // Assert
        Post post = postService.findByIntegrationPostId(question.getIntegrationPostId());
        assertEquals(9, post.getUpvotes());
        assertEquals(1, post.getDownvotes());
        Vote vote = voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).get();
        assertEquals(VoteType.DOWNVOTE, vote.getVoteType());
    }

    @Test
    public void registerVote_removeDownvoteAndSaveUpvote() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setDownvotes(10);
        question = questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        Vote existingVote = voteRepository.save(Vote.builder()
                .postId(question.getPostId())
                .userId(user.getUserId())
                .voteType(VoteType.DOWNVOTE)
                .voteDate(LocalDateTime.now())
                .build());
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId(question.getIntegrationPostId())
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Act
        postService.registerVote(voteRequestDto);

        // Assert
        Post post = postService.findByIntegrationPostId(question.getIntegrationPostId());
        assertEquals(1, post.getUpvotes());
        assertEquals(9, post.getDownvotes());
        Vote vote = voteRepository.findByUserIdAndPostId(user.getUserId(), post.getPostId()).get();
        assertEquals(VoteType.UPVOTE, vote.getVoteType());
    }

    @Test
    public void registerVote_userNotFound() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId("11")
                .integrationPostId(post.getIntegrationPostId())
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerVote(voteRequestDto);
        });

        Assertions.assertEquals("Not found user with integrationUserId 11", exception.getMessage());
    }

    @Test
    public void registerVote_postNotFound() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        VoteRequestDto voteRequestDto = VoteRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationPostId("1")
                .voteType(VoteTypeRequest.UPVOTE)
                .voteDate(LocalDateTime.now())
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerVote(voteRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void registerQuestionFollower_follow() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setFollowers(10);
        questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationQuestionId(question.getIntegrationPostId())
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Act
        postService.registerQuestionFollower(questionFollowerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(11, questionRepository.findById(question.getPostId()).get().getFollowers());
        assertTrue(questionFollowerRepository.findByUserIdAndQuestionId(user.getUserId(), question.getPostId()).isPresent());
    }

    @Test
    public void registerQuestionFollower_unfollow() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        question.setFollowers(10);
        questionRepository.save(question);
        User user = userTestUtils.saveWithIntegrationUserId("11");
        questionFollowerRepository.save(QuestionFollower.builder()
                .questionId(question.getPostId())
                .userId(user.getUserId())
                .startDate(LocalDateTime.now())
                .build());
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationQuestionId(question.getIntegrationPostId())
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Act
        postService.registerQuestionFollower(questionFollowerRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertEquals(9, questionRepository.findById(question.getPostId()).get().getFollowers());
        assertTrue(questionFollowerRepository.findByUserIdAndQuestionId(user.getUserId(), question.getPostId()).isEmpty());
    }

    @Test
    public void registerQuestionFollower_userNotFound() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationUserId("11")
                .integrationQuestionId(post.getIntegrationPostId())
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerQuestionFollower(questionFollowerRequestDto);
        });

        Assertions.assertEquals("Not found user with integrationUserId 11", exception.getMessage());
    }

    @Test
    public void registerQuestionFollower_postNotFound() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationQuestionId("1")
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            postService.registerQuestionFollower(questionFollowerRequestDto);
        });

        Assertions.assertEquals("Not found post with integrationPostId 1", exception.getMessage());
    }

    @Test
    public void registerQuestionFollower_validateIntegrationQuestionIdRequired() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.registerQuestionFollower(questionFollowerRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationQuestionId' is required", exception.getMessage());
    }

    @Test
    public void registerQuestionFollower_validateIntegrationUserIdRequired() {
        // Arrange
        Post post = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");
        QuestionFollowerRequestDto questionFollowerRequestDto = QuestionFollowerRequestDto.builder()
                .integrationQuestionId(post.getIntegrationPostId())
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            postService.registerQuestionFollower(questionFollowerRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationUserId' is required", exception.getMessage());
    }

}

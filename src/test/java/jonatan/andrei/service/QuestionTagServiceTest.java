package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.Tag;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class QuestionTagServiceTest extends AbstractServiceTest {

    @Inject
    QuestionTagService questionTagService;

    @Test
    public void save_withoutQuestionTags() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("a");
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Act
        questionTagService.save(question, asList(), user);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void save_questionTagsToInsert() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        Tag tag2 = tagTestUtils.saveWithName("b");
        User user = userTestUtils.saveWithIntegrationUserId("11");

        // Act
        questionTagService.save(question, asList(tag1.getName(), tag2.getName()), user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag1.getTagId())));
        assertEquals(1, tagRepository.findByName("a").getQuestionCount());
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag2.getTagId())));
        assertEquals(1, tagRepository.findByName("b").getQuestionCount());
    }

    @Test
    public void save_questionTagsToDelete() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        Tag tag2 = tagTestUtils.saveWithName("b");
        Tag tag3 = tagTestUtils.saveWithName("c");
        Tag tag4 = tagTestUtils.saveWithName("d");
        questionTagTestUtils.saveQuestionTags(question, asList(tag1, tag2, tag3, tag4));
        User user = userTestUtils.saveWithIntegrationUserId("11");

        // Act
        questionTagService.save(question, asList(tag1.getName(), tag3.getName()), user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag1.getTagId())));
        assertEquals(1, tagRepository.findByName("a").getQuestionCount());
        assertTrue(isNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag2.getTagId())));
        assertEquals(0, tagRepository.findByName("b").getQuestionCount());
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag3.getTagId())));
        assertEquals(1, tagRepository.findByName("c").getQuestionCount());
        assertTrue(isNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag4.getTagId())));
        assertEquals(0, tagRepository.findByName("d").getQuestionCount());
    }

    @Test
    public void save_newTag() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("1");
        User user = userTestUtils.saveWithIntegrationUserId("11");

        // Act
        questionTagService.save(question, asList("a", "b"), user);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Tag tag1 = tagRepository.findByName("a");
        assertTrue(nonNull(tag1));
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag1.getTagId())));
        assertEquals(1, tagRepository.findByName("a").getQuestionCount());
        Tag tag2 = tagRepository.findByName("b");
        assertTrue(nonNull(tag2));
        assertTrue(nonNull(questionTagRepository.findByQuestionIdAndTagId(question.getPostId(), tag2.getTagId())));
        assertEquals(1, tagRepository.findByName("b").getQuestionCount());
    }

}

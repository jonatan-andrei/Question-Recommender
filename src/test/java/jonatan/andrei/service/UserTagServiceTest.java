package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.UserPreferenceType;
import jonatan.andrei.model.Tag;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserTag;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class UserTagServiceTest extends AbstractServiceTest {

    @Inject
    UserTagService userTagService;

    @Test
    public void saveUserPreferences_saveUserTagsExplicit() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        Tag tag2 = tagTestUtils.saveWithName("b");
        userTagTestUtils.save(user, tag2);

        // Act
        userTagService.saveUserPreferences(user, asList(tag1, tag2), UserPreferenceType.EXPLICIT);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserTag userTag1 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag1.getTagId());
        assertTrue(userTag1.isExplicitRecommendation());
        UserTag userTag2 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag2.getTagId());
        assertTrue(userTag2.isExplicitRecommendation());
    }

    @Test
    public void saveUserPreferences_updateUserTagsExplicit() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        userTagTestUtils.save(user, tag1);
        Tag tag2 = tagTestUtils.saveWithName("b");
        userTagTestUtils.save(user, tag2);
        Tag tag3 = tagTestUtils.saveWithName("c");
        userTagTestUtils.save(user, tag3);
        Tag tag4 = tagTestUtils.saveWithName("d");
        userTagTestUtils.save(user, tag4);
        Tag tag5 = tagTestUtils.saveWithName("e");
        Tag tag6 = tagTestUtils.saveWithName("f");

        // Act
        userTagService.saveUserPreferences(user, asList(tag1, tag2, tag5, tag6), UserPreferenceType.EXPLICIT);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserTag userTag1 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag1.getTagId());
        assertTrue(userTag1.isExplicitRecommendation());
        UserTag userTag2 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag2.getTagId());
        assertTrue(userTag2.isExplicitRecommendation());
        UserTag userTag3 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag3.getTagId());
        assertFalse(userTag3.isExplicitRecommendation());
        UserTag userTag4 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag4.getTagId());
        assertFalse(userTag4.isExplicitRecommendation());
        UserTag userTag5 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag5.getTagId());
        assertTrue(userTag5.isExplicitRecommendation());
        UserTag userTag6 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag6.getTagId());
        assertTrue(userTag6.isExplicitRecommendation());
    }

    @Test
    public void saveUserPreferences_saveUserTagsIgnored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        Tag tag2 = tagTestUtils.saveWithName("b");
        userTagTestUtils.save(user, tag2);

        // Act
        userTagService.saveUserPreferences(user, asList(tag1, tag2), UserPreferenceType.IGNORED);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserTag userTag1 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag1.getTagId());
        assertTrue(userTag1.isIgnored());
        UserTag userTag2 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag2.getTagId());
        assertTrue(userTag2.isIgnored());
    }

    @Test
    public void saveUserPreferences_updateUserTagsIgnored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Tag tag1 = tagTestUtils.saveWithName("a");
        userTagTestUtils.save(user, tag1);
        Tag tag2 = tagTestUtils.saveWithName("b");
        userTagTestUtils.save(user, tag2);
        Tag tag3 = tagTestUtils.saveWithName("c");
        userTagTestUtils.save(user, tag3);
        Tag tag4 = tagTestUtils.saveWithName("d");
        userTagTestUtils.save(user, tag4);
        Tag tag5 = tagTestUtils.saveWithName("e");
        Tag tag6 = tagTestUtils.saveWithName("f");

        // Act
        userTagService.saveUserPreferences(user, asList(tag1, tag2, tag5, tag6), UserPreferenceType.IGNORED);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserTag userTag1 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag1.getTagId());
        assertTrue(userTag1.isIgnored());
        UserTag userTag2 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag2.getTagId());
        assertTrue(userTag2.isIgnored());
        UserTag userTag3 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag3.getTagId());
        assertFalse(userTag3.isIgnored());
        UserTag userTag4 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag4.getTagId());
        assertFalse(userTag4.isIgnored());
        UserTag userTag5 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag5.getTagId());
        assertTrue(userTag5.isIgnored());
        UserTag userTag6 = userTagRepository.findByUserIdAndTagId(user.getUserId(), tag6.getTagId());
        assertTrue(userTag6.isIgnored());
    }
}

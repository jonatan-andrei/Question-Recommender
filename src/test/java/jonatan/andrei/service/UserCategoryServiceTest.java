package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.UserPreference;
import jonatan.andrei.model.Category;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserCategory;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestTransaction
public class UserCategoryServiceTest extends AbstractServiceTest {

    @Inject
    UserCategoryService userCategoryService;

    @Test
    public void saveUserPreferences_saveUserCategoriesExplicit() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("a");
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("b");
        userCategoryTestUtils.save(user, category2);

        // Act
        userCategoryService.saveUserPreferences(user, asList(category1, category2), UserPreference.EXPLICIT);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserCategory userCategory1 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category1.getCategoryId());
        assertTrue(userCategory1.isExplicitRecommendation());
        UserCategory userCategory2 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category2.getCategoryId());
        assertTrue(userCategory2.isExplicitRecommendation());
    }

    @Test
    public void saveUserPreferences_updateUserCategoriesExplicit() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("a");
        userCategoryTestUtils.save(user, category1);
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("b");
        userCategoryTestUtils.save(user, category2);
        Category category3 = categoryTestUtils.saveWithIntegrationCategoryId("c");
        userCategoryTestUtils.save(user, category3);
        Category category4 = categoryTestUtils.saveWithIntegrationCategoryId("d");
        userCategoryTestUtils.save(user, category4);
        Category category5 = categoryTestUtils.saveWithIntegrationCategoryId("e");
        Category category6 = categoryTestUtils.saveWithIntegrationCategoryId("f");

        // Act
        userCategoryService.saveUserPreferences(user, asList(category1, category2, category5, category6), UserPreference.EXPLICIT);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserCategory userCategory1 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category1.getCategoryId());
        assertTrue(userCategory1.isExplicitRecommendation());
        UserCategory userCategory2 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category2.getCategoryId());
        assertTrue(userCategory2.isExplicitRecommendation());
        UserCategory userCategory3 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category3.getCategoryId());
        assertFalse(userCategory3.isExplicitRecommendation());
        UserCategory userCategory4 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category4.getCategoryId());
        assertFalse(userCategory4.isExplicitRecommendation());
        UserCategory userCategory5 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category5.getCategoryId());
        assertTrue(userCategory5.isExplicitRecommendation());
        UserCategory userCategory6 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category6.getCategoryId());
        assertTrue(userCategory6.isExplicitRecommendation());
    }

    @Test
    public void saveUserPreferences_saveUserCategoriesIgnored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("a");
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("b");
        userCategoryTestUtils.save(user, category2);

        // Act
        userCategoryService.saveUserPreferences(user, asList(category1, category2), UserPreference.IGNORED);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserCategory userCategory1 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category1.getCategoryId());
        assertTrue(userCategory1.isIgnored());
        UserCategory userCategory2 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category2.getCategoryId());
        assertTrue(userCategory2.isIgnored());
    }

    @Test
    public void saveUserPreferences_updateUserCategoriesIgnored() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        Category category1 = categoryTestUtils.saveWithIntegrationCategoryId("a");
        userCategoryTestUtils.save(user, category1);
        Category category2 = categoryTestUtils.saveWithIntegrationCategoryId("b");
        userCategoryTestUtils.save(user, category2);
        Category category3 = categoryTestUtils.saveWithIntegrationCategoryId("c");
        userCategoryTestUtils.save(user, category3);
        Category category4 = categoryTestUtils.saveWithIntegrationCategoryId("d");
        userCategoryTestUtils.save(user, category4);
        Category category5 = categoryTestUtils.saveWithIntegrationCategoryId("e");
        Category category6 = categoryTestUtils.saveWithIntegrationCategoryId("f");

        // Act
        userCategoryService.saveUserPreferences(user, asList(category1, category2, category5, category6), UserPreference.IGNORED);
        entityManager.flush();
        entityManager.clear();

        // Assert
        UserCategory userCategory1 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category1.getCategoryId());
        assertTrue(userCategory1.isIgnored());
        UserCategory userCategory2 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category2.getCategoryId());
        assertTrue(userCategory2.isIgnored());
        UserCategory userCategory3 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category3.getCategoryId());
        assertFalse(userCategory3.isIgnored());
        UserCategory userCategory4 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category4.getCategoryId());
        assertFalse(userCategory4.isIgnored());
        UserCategory userCategory5 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category5.getCategoryId());
        assertTrue(userCategory5.isIgnored());
        UserCategory userCategory6 = userCategoryRepository.findByUserIdAndCategoryId(user.getUserId(), category6.getCategoryId());
        assertTrue(userCategory6.isIgnored());
    }
}

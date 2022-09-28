package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserFollower;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class UserServiceTest extends AbstractServiceTest {

    @Inject
    UserService userService;

    @Test
    public void save_saveUser() {
        // Arrange
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .integrationUserId("1")
                .integrationAnonymousUserId("abc")
                .username("username")
                .registrationDate(LocalDateTime.now())
                .userPreferences(UserPreferencesRequestDto.builder()
                        .emailNotificationEnable(true)
                        .emailNotificationHour(17)
                        .questionNotificationEnable(true)
                        .recommendationEnable(true)
                        .explicitIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .ignoredIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .explicitTags(Collections.EMPTY_LIST)
                        .ignoredTags(Collections.EMPTY_LIST)
                        .build())
                .build();

        // Act
        User user = userService.save(createUserRequestDto);

        // Assert
        assertEquals(createUserRequestDto.getIntegrationUserId(), user.getIntegrationUserId());
        assertEquals(createUserRequestDto.getUsername(), user.getUsername());
        assertEquals(createUserRequestDto.getRegistrationDate(), user.getRegistrationDate());
        assertEquals(createUserRequestDto.getUserPreferences().isEmailNotificationEnable(), user.isEmailNotificationEnable());
        assertEquals(createUserRequestDto.getUserPreferences().getEmailNotificationHour(), user.getEmailNotificationHour());
        assertEquals(createUserRequestDto.getUserPreferences().isQuestionNotificationEnable(), user.isQuestionNotificationEnable());
    }

    @Test
    public void save_saveUserWithIntegrationAnonymousUserId() {
        // Arrange
        User anonymousUser = userTestUtils.saveWithIntegrationAnonymousUserId("abc");

        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .integrationUserId("1")
                .integrationAnonymousUserId(anonymousUser.getIntegrationAnonymousUserId())
                .username("username")
                .registrationDate(LocalDateTime.now())
                .userPreferences(UserPreferencesRequestDto.builder()
                        .emailNotificationEnable(true)
                        .emailNotificationHour(17)
                        .questionNotificationEnable(true)
                        .recommendationEnable(true)
                        .explicitIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .ignoredIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .explicitTags(Collections.EMPTY_LIST)
                        .ignoredTags(Collections.EMPTY_LIST)
                        .build())
                .build();

        // Act
        User user = userService.save(createUserRequestDto);

        // Assert
        assertEquals(createUserRequestDto.getIntegrationUserId(), user.getIntegrationUserId());
        assertEquals(createUserRequestDto.getUsername(), user.getUsername());
        assertEquals(createUserRequestDto.getRegistrationDate(), user.getRegistrationDate());
        assertEquals(createUserRequestDto.getUserPreferences().isEmailNotificationEnable(), user.isEmailNotificationEnable());
        assertEquals(createUserRequestDto.getUserPreferences().getEmailNotificationHour(), user.getEmailNotificationHour());
        assertEquals(createUserRequestDto.getUserPreferences().isQuestionNotificationEnable(), user.isQuestionNotificationEnable());
        assertEquals(true, user.isActive());
        assertEquals(false, user.isAnonymous());
    }

    @Test
    public void save_userAlreadyExists() {
        // Arrange
        User existingUser = userTestUtils.saveWithIntegrationUserId("1");
        CreateUserRequestDto createUserRequestDto = CreateUserRequestDto.builder()
                .integrationUserId("1")
                .userPreferences(UserPreferencesRequestDto.builder().build())
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            User user = userService.save(createUserRequestDto);
        });

        Assertions.assertEquals("There is already a user with integrationUserId 1", exception.getMessage());
    }

    @Test
    public void update_updateUser() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .integrationUserId("1")
                .active(false)
                .userPreferences(UserPreferencesRequestDto.builder()
                        .explicitIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .ignoredIntegrationCategoriesIds(Collections.EMPTY_LIST)
                        .explicitTags(Collections.EMPTY_LIST)
                        .ignoredTags(Collections.EMPTY_LIST)
                        .build())
                .build();

        // Act
        User result = userService.update(updateUserRequestDto);

        // Assert
        assertFalse(result.isActive());
    }

    @Test
    public void update_integrationUserIdNotFound() {
        // Arrange
        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .integrationUserId("1")
                .userPreferences(UserPreferencesRequestDto.builder().build())
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            User user = userService.update(updateUserRequestDto);
        });

        Assertions.assertEquals("Not found user with integrationUserId 1", exception.getMessage());
    }

    @Test
    public void registerFollower_follow() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        User follower = userTestUtils.saveWithIntegrationUserId("2");
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationFollowerUserId(follower.getIntegrationUserId())
                .startDate(LocalDateTime.now())
                .followed(true)
                .build();

        // Act
        userService.registerFollower(followerRequestDto);

        // Assert
        UserFollower userFollower = userFollowerRepository.findByUserIdAndFollowerId(user.getUserId(), follower.getUserId());
        assertTrue(nonNull(userFollower));
        assertEquals(followerRequestDto.getStartDate(), userFollower.getStartDate());
    }

    @Test
    public void registerFollower_unfollow() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        User follower = userTestUtils.saveWithIntegrationUserId("2");
        userFollowerRepository.save(UserFollower.builder()
                .userId(user.getUserId())
                .userFollowerId(follower.getUserId())
                .startDate(LocalDateTime.now())
                .build());
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationFollowerUserId(follower.getIntegrationUserId())
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Act
        userService.registerFollower(followerRequestDto);

        // Assert
        UserFollower userFollower = userFollowerRepository.findByUserIdAndFollowerId(user.getUserId(), follower.getUserId());
        assertTrue(isNull(userFollower));
    }

    @Test
    public void registerFollower_validateIntegrationUserIdRequired() {
        // Arrange
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationFollowerUserId("2")
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            userService.registerFollower(followerRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationUserId' is required", exception.getMessage());
    }

    @Test
    public void registerFollower_validateIntegrationFollowerUserIdRequired() {
        // Arrange
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationUserId("2")
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            userService.registerFollower(followerRequestDto);
        });

        Assertions.assertEquals("Attribute 'integrationFollowerUserId' is required", exception.getMessage());
    }

    @Test
    public void registerFollower_userNotFound() {
        // Arrange
        User follower = userTestUtils.saveWithIntegrationUserId("2");
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationUserId("1")
                .integrationFollowerUserId(follower.getIntegrationUserId())
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            userService.registerFollower(followerRequestDto);
        });

        Assertions.assertEquals("Not found user with integrationUserId 1", exception.getMessage());
    }

    @Test
    public void registerFollower_followerNotFound() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        UserFollowerRequestDto followerRequestDto = UserFollowerRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .integrationFollowerUserId("2")
                .startDate(LocalDateTime.now())
                .followed(false)
                .build();

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            userService.registerFollower(followerRequestDto);
        });

        Assertions.assertEquals("Not found user with integrationUserId 2", exception.getMessage());
    }

    @Test
    public void findUserByIntegrationUserIdOrCreateByAnonymousId_findUserByIntegrationUserId() {
        // Arrange
        String integrationUserId = "1";
        String integrationAnonymousUserId = "abc";
        User user = userTestUtils.saveWithIntegrationUserId("1");

        // Act
        User result = userService.findUserByIntegrationUserIdOrCreateByAnonymousId(integrationUserId, integrationAnonymousUserId);

        // Assert
        Assertions.assertEquals(user.getUserId(), result.getUserId());
    }

    @Test
    public void findUserByIntegrationUserIdOrCreateByAnonymousId_integrationUserIdNotFound() {
        // Arrange
        String integrationUserId = "1";
        String integrationAnonymousUserId = "abc";

        // Assert
        Exception exception = assertThrows(InconsistentIntegratedDataException.class, () -> {
            // Act
            userService.findUserByIntegrationUserIdOrCreateByAnonymousId(integrationUserId, integrationAnonymousUserId);
        });

        Assertions.assertEquals("Not found user with integrationUserId 1", exception.getMessage());
    }

    @Test
    public void findUserByIntegrationUserIdOrCreateByAnonymousId_integrationUserIdNullAndIntegrationAnonymousUserIdNull() {
        // Arrange
        String integrationUserId = null;
        String integrationAnonymousUserId = null;

        // Assert
        Exception exception = assertThrows(RequiredDataException.class, () -> {
            // Act
            userService.findUserByIntegrationUserIdOrCreateByAnonymousId(integrationUserId, integrationAnonymousUserId);
        });

        Assertions.assertEquals("At least one of the fields must be informed: integrationUserId or integrationAnonymousUserId", exception.getMessage());
    }

    @Test
    public void findUserByIntegrationUserIdOrCreateByAnonymousId_findUserByIntegrationAnonymousUserId() {
        // Arrange
        String integrationUserId = null;
        String integrationAnonymousUserId = "abc";
        User user = userTestUtils.saveWithIntegrationUserIdAndIntegrationAnonymousUserId(null, integrationAnonymousUserId);

        // Act
        User result = userService.findUserByIntegrationUserIdOrCreateByAnonymousId(integrationUserId, integrationAnonymousUserId);

        // Assert
        Assertions.assertTrue(nonNull(result));
        Assertions.assertEquals(integrationAnonymousUserId, result.getIntegrationAnonymousUserId());
    }

    @Test
    public void findUserByIntegrationUserIdOrCreateByAnonymousId_createByIntegrationAnonymousUserId() {
        // Arrange
        String integrationUserId = null;
        String integrationAnonymousUserId = "abc";

        // Act
        User result = userService.findUserByIntegrationUserIdOrCreateByAnonymousId(integrationUserId, integrationAnonymousUserId);

        // Assert
        Assertions.assertTrue(nonNull(result));
        Assertions.assertEquals(integrationAnonymousUserId, result.getIntegrationAnonymousUserId());
    }

    @Test
    public void findQuestionsAnsweredInPeriod_findUsers() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("A", now.minusDays(1));
        Question question2 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("B", now.minusDays(2));
        Question question3 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("C", now.minusDays(3));
        LocalDateTime startDate = now;
        LocalDateTime endDate = now.plusDays(7);
        Integer minimumOfPreviousAnswers = 1;
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        Answer answer1User1 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("D", question1.getPostId(), user1.getUserId(), now.minusDays(1));
        Answer answer2User1 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("E", question2.getPostId(), user1.getUserId(), now.plusDays(2));
        Answer answer3User1 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("F", question3.getPostId(), user1.getUserId(), now.plusDays(10));
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        Answer answer1User2 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("G", question1.getPostId(), user2.getUserId(), now.minusDays(1));
        Answer answer2User2 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("H", question2.getPostId(), user2.getUserId(), now.plusDays(3));
        Answer answer3User2 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("I", question3.getPostId(), user2.getUserId(), now.plusDays(2));

        // Act
        List<QuestionsAnsweredByUserResponseDto> users = userService.findQuestionsAnsweredInPeriod(startDate, endDate, minimumOfPreviousAnswers);

        // Assert
        QuestionsAnsweredByUserResponseDto questionsAnsweredByUser1 = users.stream().filter(u -> u.getIntegrationUserId().equals(user1.getIntegrationUserId())).findFirst().get();
        assertEquals(1, questionsAnsweredByUser1.getQuestions().size());
        assertEquals(answer2User1.getPublicationDate().toLocalDate(), questionsAnsweredByUser1.getDateFirstAnswer().toLocalDate());
        QuestionsAnsweredByUserResponseDto questionsAnsweredByUser2 = users.stream().filter(u -> u.getIntegrationUserId().equals(user2.getIntegrationUserId())).findFirst().get();
        assertEquals(2, questionsAnsweredByUser2.getQuestions().size());
        assertEquals(answer3User2.getPublicationDate().toLocalDate(), questionsAnsweredByUser2.getDateFirstAnswer().toLocalDate());
    }

    @Test
    public void findQuestionsAnsweredInPeriod_userWithoutMinimumOfPreviousAnswers() {
        // Arrange
        LocalDateTime now = LocalDateTime.now();
        Question question1 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("A", now.minusDays(1));
        Question question2 = questionTestUtils.saveWithIntegrationPostIdAndPublicationDate("B", now.minusDays(2));
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        Answer answer1User1 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("C", question1.getPostId(), user1.getUserId(), now.minusDays(1));
        Answer answer2User1 = answerTestUtils.saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate("D", question2.getPostId(), user1.getUserId(), now.plusDays(2));
        LocalDateTime startDate = now;
        LocalDateTime endDate = now.plusDays(7);
        Integer minimumOfPreviousAnswers = 2;

        // Act
        List<QuestionsAnsweredByUserResponseDto> users = userService.findQuestionsAnsweredInPeriod(startDate, endDate, minimumOfPreviousAnswers);

        // Assert
        assertTrue(users.isEmpty());
    }

    @Test
    public void findUsersToSendRecommendedEmail_lastActivityDate() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        user.setLastActivityDate(LocalDateTime.now().minusYears(1));
        userRepository.save(user);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        Integer hour = startDate.getHour();
        boolean isDefaultHour = true;
        LocalDateTime minimumLastActivityDate = startDate.minusDays(60);
        Integer pageNumber = 1;
        Integer lengthPage = 10;

        // Act
        List<UserToSendRecommendedEmailDto> users = userService.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, pageNumber, lengthPage);

        // Assert
        assertEquals(0, users.size());
    }

    @Test
    public void findUsersToSendRecommendedEmail_emailNotificationEnableFalse() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        user.setEmailNotificationEnable(false);
        userRepository.save(user);
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        Integer hour = startDate.getHour();
        boolean isDefaultHour = true;
        LocalDateTime minimumLastActivityDate = startDate.minusDays(60);
        Integer pageNumber = 1;
        Integer lengthPage = 10;

        // Act
        List<UserToSendRecommendedEmailDto> users = userService.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, pageNumber, lengthPage);

        // Assert
        assertEquals(0, users.size());
    }

    @Test
    public void findUsersToSendRecommendedEmail_defaultHour() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("1");
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        Integer hour = startDate.getHour();
        boolean isDefaultHour = true;
        LocalDateTime minimumLastActivityDate = startDate.minusDays(60);
        Integer pageNumber = 1;
        Integer lengthPage = 10;

        // Act
        List<UserToSendRecommendedEmailDto> users = userService.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, pageNumber, lengthPage);

        // Assert
        assertEquals(1, users.size());
        assertEquals(user.getIntegrationUserId(), users.get(0).getIntegrationUserId());
    }

    @Test
    public void findUsersToSendRecommendedEmail_emailNotificationHour() {
        // Arrange
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        Integer hour = startDate.getHour();
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        user1.setEmailNotificationHour(hour);
        userRepository.save(user1);
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        user2.setEmailNotificationHour(hour + 2);
        userRepository.save(user2);
        boolean isDefaultHour = false;
        LocalDateTime minimumLastActivityDate = startDate.minusDays(60);
        Integer pageNumber = 1;
        Integer lengthPage = 10;

        // Act
        List<UserToSendRecommendedEmailDto> users = userService.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, pageNumber, lengthPage);

        // Assert
        assertEquals(1, users.size());
        assertEquals(user1.getIntegrationUserId(), users.get(0).getIntegrationUserId());
    }

}

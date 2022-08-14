package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserFollower;
import jonatan.andrei.repository.UserFollowerRepository;
import jonatan.andrei.utils.UserTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestTransaction
public class UserServiceTest {

    @Inject
    UserService userService;

    @Inject
    UserFollowerRepository userFollowerRepository;

    @Inject
    UserTestUtils userTestUtils;

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

}

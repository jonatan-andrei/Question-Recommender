package jonatan.andrei.factory;

import jonatan.andrei.dto.CreateUserRequestDto;
import jonatan.andrei.dto.UpdateUserRequestDto;
import jonatan.andrei.model.User;

import java.time.LocalDateTime;

public class UserFactory {

    public static User newUser(CreateUserRequestDto createUserRequestDto) {
        return User.builder()
                .integrationUserId(createUserRequestDto.getIntegrationUserId())
                .integrationAnonymousUserId(createUserRequestDto.getIntegrationAnonymousUserId())
                .registrationDate(createUserRequestDto.getRegistrationDate())
                .integrationDate(LocalDateTime.now())
                .anonymous(false)
                .active(true)
                .emailNotificationEnable(createUserRequestDto.isEmailNotificationEnable())
                .emailNotificationHour(createUserRequestDto.getEmailNotificationHour())
                .notificationEnable(createUserRequestDto.isNotificationEnable())
                .recommendationEnable(createUserRequestDto.isRecommendationEnable())
                .lastActivityDate(LocalDateTime.now())
                .build();
    }

    public static User overwriteWithAnonymousUser(User user, CreateUserRequestDto createUserRequestDto) {
        user.setIntegrationUserId(createUserRequestDto.getIntegrationUserId());
        user.setRegistrationDate(createUserRequestDto.getRegistrationDate());
        user.setAnonymous(false);
        user.setActive(true);
        user.setEmailNotificationEnable(createUserRequestDto.isEmailNotificationEnable());
        user.setEmailNotificationHour(createUserRequestDto.getEmailNotificationHour());
        user.setNotificationEnable(createUserRequestDto.isNotificationEnable());
        user.setRecommendationEnable(createUserRequestDto.isRecommendationEnable());
        user.setLastActivityDate(LocalDateTime.now());
        return user;
    }

    public static User overwrite(User user, UpdateUserRequestDto updateUserRequestDto) {
        user.setActive(updateUserRequestDto.isActive());
        user.setEmailNotificationEnable(updateUserRequestDto.isEmailNotificationEnable());
        user.setEmailNotificationHour(updateUserRequestDto.getEmailNotificationHour());
        user.setNotificationEnable(updateUserRequestDto.isNotificationEnable());
        user.setRecommendationEnable(updateUserRequestDto.isRecommendationEnable());
        user.setLastActivityDate(LocalDateTime.now());
        return user;
    }

    public static User newUserWithIntegrationAnonymousUserId(String integrationAnonymousUserId) {
        return User.builder()
                .integrationAnonymousUserId(integrationAnonymousUserId)
                .registrationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .anonymous(true)
                .active(true)
                .emailNotificationEnable(false)
                .notificationEnable(false)
                .recommendationEnable(true)
                .lastActivityDate(LocalDateTime.now())
                .build();
    }
}

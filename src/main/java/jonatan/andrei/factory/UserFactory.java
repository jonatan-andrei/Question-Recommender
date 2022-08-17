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
                .emailNotificationEnable(createUserRequestDto.getUserPreferences().isEmailNotificationEnable())
                .emailNotificationHour(createUserRequestDto.getUserPreferences().getEmailNotificationHour())
                .notificationEnable(createUserRequestDto.getUserPreferences().isNotificationEnable())
                .recommendationEnable(createUserRequestDto.getUserPreferences().isRecommendationEnable())
                .lastActivityDate(LocalDateTime.now())
                .build();
    }

    public static User overwriteWithAnonymousUser(User user, CreateUserRequestDto createUserRequestDto) {
        user.setIntegrationUserId(createUserRequestDto.getIntegrationUserId());
        user.setRegistrationDate(createUserRequestDto.getRegistrationDate());
        user.setAnonymous(false);
        user.setActive(true);
        user.setEmailNotificationEnable(createUserRequestDto.getUserPreferences().isEmailNotificationEnable());
        user.setEmailNotificationHour(createUserRequestDto.getUserPreferences().getEmailNotificationHour());
        user.setNotificationEnable(createUserRequestDto.getUserPreferences().isNotificationEnable());
        user.setRecommendationEnable(createUserRequestDto.getUserPreferences().isRecommendationEnable());
        user.setLastActivityDate(LocalDateTime.now());
        return user;
    }

    public static User overwrite(User user, UpdateUserRequestDto updateUserRequestDto) {
        user.setActive(updateUserRequestDto.isActive());
        user.setEmailNotificationEnable(updateUserRequestDto.getUserPreferences().isEmailNotificationEnable());
        user.setEmailNotificationHour(updateUserRequestDto.getUserPreferences().getEmailNotificationHour());
        user.setNotificationEnable(updateUserRequestDto.getUserPreferences().isNotificationEnable());
        user.setRecommendationEnable(updateUserRequestDto.getUserPreferences().isRecommendationEnable());
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
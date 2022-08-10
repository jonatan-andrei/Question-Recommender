package jonatan.andrei.factory;

import jonatan.andrei.model.User;

import java.time.LocalDateTime;

public class UserFactory {

    public static User newUserWithSessionId(String sessionId) {
        return User.builder()
                .sessionId(sessionId)
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

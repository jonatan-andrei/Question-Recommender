package jonatan.andrei.utils;

import jonatan.andrei.model.User;
import jonatan.andrei.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserTestUtils {

    @Inject
    UserRepository userRepository;

    public User save() {
        return saveWithIntegrationUserId("1");
    }

    public User saveWithIntegrationUserId(String integrationUserId) {
        return userRepository.save(User.builder()
                .integrationUserId(integrationUserId)
                .registrationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .anonymous(false)
                .active(true)
                .emailNotificationEnable(false)
                .emailNotificationHour(5)
                .notificationEnable(true)
                .recommendationEnable(true)
                .lastActivityDate(LocalDateTime.now())
                .build());
    }
}

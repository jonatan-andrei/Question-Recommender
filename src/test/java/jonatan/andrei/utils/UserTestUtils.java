package jonatan.andrei.utils;

import jonatan.andrei.model.User;
import jonatan.andrei.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApplicationScoped
public class UserTestUtils {

    @Inject
    UserRepository userRepository;

    public User save() {
        return saveWithIntegrationUserId("1");
    }

    public User saveWithIntegrationUserId(String integrationUserId) {
        return saveWithIntegrationUserIdAndIntegrationAnonymousUserId(integrationUserId, null);
    }

    public User saveWithIntegrationUserIdAndIntegrationAnonymousUserId(String integrationUserId, String integrationAnonymousUserId) {
        return userRepository.save(User.builder()
                .integrationUserId(integrationUserId)
                .integrationAnonymousUserId(integrationAnonymousUserId)
                .registrationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .anonymous(false)
                .active(true)
                .emailNotificationEnable(false)
                .emailNotificationHour(5)
                .notificationEnable(true)
                .recommendationEnable(true)
                .lastActivityDate(LocalDateTime.now())
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .build());
    }

    public User saveWithIntegrationAnonymousUserId(String integrationAnonymousUserId) {
        return userRepository.save(User.builder()
                .integrationAnonymousUserId(integrationAnonymousUserId)
                .registrationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .anonymous(false)
                .active(false)
                .emailNotificationEnable(false)
                .notificationEnable(false)
                .recommendationEnable(false)
                .lastActivityDate(LocalDateTime.now())
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .build());
    }
}

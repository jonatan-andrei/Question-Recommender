package jonatan.andrei.factory;

import jonatan.andrei.dto.CreateUserRequestDto;
import jonatan.andrei.dto.UpdateUserRequestDto;
import jonatan.andrei.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserFactory {

    public static User newUser(CreateUserRequestDto createUserRequestDto) {
        return User.builder()
                .integrationUserId(createUserRequestDto.getIntegrationUserId())
                .integrationAnonymousUserId(createUserRequestDto.getIntegrationAnonymousUserId())
                .username(createUserRequestDto.getUsername())
                .registrationDate(createUserRequestDto.getRegistrationDate())
                .integrationDate(LocalDateTime.now())
                .anonymous(false)
                .active(true)
                .emailNotificationEnable(createUserRequestDto.getUserPreferences().isEmailNotificationEnable())
                .emailNotificationHour(createUserRequestDto.getUserPreferences().getEmailNotificationHour())
                .questionNotificationEnable(createUserRequestDto.getUserPreferences().isQuestionNotificationEnable())
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
                .build();
    }

    public static User overwriteWithAnonymousUser(User user, CreateUserRequestDto createUserRequestDto) {
        user.setIntegrationUserId(createUserRequestDto.getIntegrationUserId());
        user.setUsername(createUserRequestDto.getUsername());
        user.setRegistrationDate(createUserRequestDto.getRegistrationDate());
        user.setAnonymous(false);
        user.setActive(true);
        user.setEmailNotificationEnable(createUserRequestDto.getUserPreferences().isEmailNotificationEnable());
        user.setEmailNotificationHour(createUserRequestDto.getUserPreferences().getEmailNotificationHour());
        user.setQuestionNotificationEnable(createUserRequestDto.getUserPreferences().isQuestionNotificationEnable());
        user.setLastActivityDate(LocalDateTime.now());
        return user;
    }

    public static User overwrite(User user, UpdateUserRequestDto updateUserRequestDto) {
        user.setUsername(updateUserRequestDto.getUsername());
        user.setActive(updateUserRequestDto.isActive());
        user.setEmailNotificationEnable(updateUserRequestDto.getUserPreferences().isEmailNotificationEnable());
        user.setEmailNotificationHour(updateUserRequestDto.getUserPreferences().getEmailNotificationHour());
        user.setQuestionNotificationEnable(updateUserRequestDto.getUserPreferences().isQuestionNotificationEnable());
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
                .questionNotificationEnable(false)
                .lastActivityDate(LocalDateTime.now())
                .numberQuestionsAsked(BigDecimal.ZERO)
                .numberQuestionsViewed(BigDecimal.ZERO)
                .numberQuestionsAnswered(BigDecimal.ZERO)
                .numberQuestionsCommented(BigDecimal.ZERO)
                .numberQuestionsFollowed(BigDecimal.ZERO)
                .numberQuestionsUpvoted(BigDecimal.ZERO)
                .numberAnswersUpvoted(BigDecimal.ZERO)
                .numberCommentsUpvoted(BigDecimal.ZERO)
                .numberQuestionsDownvoted(BigDecimal.ZERO)
                .numberAnswersDownvoted(BigDecimal.ZERO)
                .numberCommentsDownvoted(BigDecimal.ZERO)
                .build();
    }
}

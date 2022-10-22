package jonatan.andrei.service;

import io.quarkus.logging.Log;
import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.domain.QuestionViewType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.ListRecommendedEmailRequestDto;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.dto.UserToSendRecommendedEmailDto;
import jonatan.andrei.factory.RecommendedEmailFactory;
import jonatan.andrei.model.RecommendedEmail;
import jonatan.andrei.proxy.RecommendedEmailProxy;
import jonatan.andrei.repository.RecommendedEmailRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static jonatan.andrei.domain.RecommendationSettingsType.*;

@ApplicationScoped
public class RecommendedEmailService {

    @Inject
    RecommendedEmailQuestionService recommendedEmailQuestionService;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Inject
    UserService userService;

    @Inject
    QuestionViewService questionViewService;

    @Inject
    RecommendedEmailRepository recommendedEmailRepository;

    @Inject
    @RestClient
    RecommendedEmailProxy recommendedEmailProxy;

    @Transactional
    public void generateRecommendedEmails(IntegrationMethodType integrationMethodType) {
        Map<RecommendationSettingsType, BigDecimal> settings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_EMAIL);
        boolean channelEnabled = settings.get(ENABLE_CHANNEL).compareTo(BigDecimal.ZERO) > 0;
        if (!channelEnabled) {
            return;
        }
        LocalDateTime startDate = LocalDateTime.now();
        Integer hour = startDate.getHour();
        boolean isDefaultHour = hour.equals(settings.get(DEFAULT_HOUR_OF_THE_DAY_TO_SEND_RECOMMENDATIONS).intValue());
        List<UserToSendRecommendedEmailDto> users = new ArrayList<>();
        LocalDateTime minimumLastActivityDate = startDate.minusDays(settings.get(DAYS_OF_USER_INACTIVITY_TO_SUSPEND_RECOMMENDATIONS).intValue());
        Integer maximumNumberOfRows = settings.get(MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST).intValue();
        Integer lengthQuestionListEmail = settings.get(DEFAULT_LENGTH).intValue();
        Integer minimumLength = settings.get(MINIMUM_LENGTH).intValue();
        Integer maximumNumberOfPagesWithRecommendedQuestions = settings.get(MAXIMUM_NUMBER_OF_PAGES_WITH_RECOMMENDED_QUESTIONS).intValue();
        Integer pageNumber = 0;
        do {
            try {
                users = userService.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, ++pageNumber, maximumNumberOfRows);
                List<ListRecommendedEmailRequestDto.RecommendedEmailRequestDto> emails = new ArrayList<>();
                for (UserToSendRecommendedEmailDto user : users) {
                    List<RecommendedQuestionOfListDto> questions = recommendedEmailQuestionService.newEmail(user.getUserId(), lengthQuestionListEmail, settings, startDate, maximumNumberOfPagesWithRecommendedQuestions);
                    if (questions.size() >= minimumLength) {
                        RecommendedEmail recommendedEmail = save(user, startDate);
                        recommendedEmailQuestionService.save(questions, recommendedEmail.getRecommendedEmailId());
                        emails.add(RecommendedEmailFactory.newDto(user, questions));
                        saveQuestionsView(user, questions);
                    }
                }
                sendRecommendedEmailList(emails, integrationMethodType);
            } catch (Exception e) {
                Log.error("Error to send recommended emails: ", e);
            }
        } while (!users.isEmpty());
    }

    private RecommendedEmail save(UserToSendRecommendedEmailDto user, LocalDateTime sendDate) {
        return recommendedEmailRepository.save(RecommendedEmail.builder()
                .userId(user.getUserId())
                .integrationUserId(user.getIntegrationUserId())
                .sendDate(sendDate)
                .build());
    }

    private void saveQuestionsView(UserToSendRecommendedEmailDto
                                           user, List<RecommendedQuestionOfListDto> questions) {
        List<Long> questionsIds = questions.stream().map(RecommendedQuestionOfListDto::getQuestionId).collect(Collectors.toList());
        questionViewService.registerQuestionsViewInList(questionsIds, user.getUserId(), QuestionViewType.VIEW_IN_RECOMMENDED_EMAIL);
    }

    private void sendRecommendedEmailList(List<ListRecommendedEmailRequestDto.RecommendedEmailRequestDto> emails, IntegrationMethodType integrationMethodType) {
        if (emails.isEmpty()) {
            return;
        }
        switch (integrationMethodType) {
            case REST:
                recommendedEmailProxy.saveRecommendedEmailList(new ListRecommendedEmailRequestDto(emails));
                break;
            default:
                break;
        }
    }
}

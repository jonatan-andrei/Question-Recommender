package jonatan.andrei.service;

import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.domain.QuestionViewType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.ListQuestionNotificationRequestDto;
import jonatan.andrei.dto.UserToSendQuestionNotificationDto;
import jonatan.andrei.model.QuestionNotification;
import jonatan.andrei.model.QuestionNotificationQueue;
import jonatan.andrei.proxy.QuestionNotificationProxy;
import jonatan.andrei.repository.QuestionNotificationRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class QuestionNotificationService {

    @Inject
    QuestionNotificationQueueService questionNotificationQueueService;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Inject
    QuestionService questionService;

    @Inject
    QuestionViewService questionViewService;

    @Inject
    @RestClient
    QuestionNotificationProxy questionNotificationProxy;

    @Inject
    QuestionNotificationRepository questionNotificationRepository;

    @Transactional
    public void generateQuestionNotifications(IntegrationMethodType integrationMethodType) {
        List<QuestionNotificationQueue> pendingList = questionNotificationQueueService.findBySendDateIsNullAndDateWasIgnoredIsNull();
        Map<RecommendationSettingsType, BigDecimal> settings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.QUESTION_NOTIFICATION);
        boolean channelEnabled = settings.get(ENABLE_CHANNEL).compareTo(BigDecimal.ZERO) > 0;
        Integer maximumNumberOfRows = settings.get(MAXIMUM_SIZE_OF_INTEGRATED_USER_LIST).intValue();
        LocalDateTime minimumLastActivityDate = LocalDateTime.now().minusDays(settings.get(DAYS_OF_USER_INACTIVITY_TO_SUSPEND_RECOMMENDATIONS).intValue());
        for (QuestionNotificationQueue qnq : pendingList) {
            try {
                if (channelEnabled) {
                    generateQuestionNotifications(qnq.getQuestionId(), qnq.getIntegrationQuestionId(), integrationMethodType, maximumNumberOfRows, settings, minimumLastActivityDate);
                    qnq.setSendDate(LocalDateTime.now());
                } else {
                    qnq.setDateWasIgnored(LocalDateTime.now());
                }
            } catch (Exception e) {
                log.error("Error to send notifications for question " + qnq.getQuestionId(), e);
            }
        }
        questionNotificationQueueService.save(pendingList);
    }

    private void generateQuestionNotifications(Long questionId, String integrationQuestionId, IntegrationMethodType integrationMethodType, Integer maximumNumberOfRows, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime minimumLastActivityDate) {
        List<UserToSendQuestionNotificationDto> users = new ArrayList<>();
        Integer pageNumber = 0;
        do {
            users = questionService.findUsersToNotifyQuestion(questionId, ++pageNumber, maximumNumberOfRows, recommendationSettings, minimumLastActivityDate);
            List<QuestionNotification> notifications = saveQuestionNotifications(users, questionId, integrationQuestionId);
            List<ListQuestionNotificationRequestDto.QuestionNotificationRequestDto> notificationsRequest = notifications.stream()
                    .map(n -> ListQuestionNotificationRequestDto.QuestionNotificationRequestDto.builder()
                            .integrationQuestionId(integrationQuestionId)
                            .integrationUserId(n.getIntegrationUserId())
                            .build())
                    .collect(Collectors.toList());
            sendQuestionNotifications(notificationsRequest, integrationMethodType);
            saveQuestionView(users, questionId);
        } while (!users.isEmpty());
    }

    private void sendQuestionNotifications(List<ListQuestionNotificationRequestDto.QuestionNotificationRequestDto> notifications, IntegrationMethodType integrationMethodType) {
        if (notifications.isEmpty()) {
            return;
        }

        switch (integrationMethodType) {
            case REST:
                questionNotificationProxy.saveQuestionNotificationList(new ListQuestionNotificationRequestDto(notifications));
                break;
            default:
                break;
        }
    }

    private List<QuestionNotification> saveQuestionNotifications(List<UserToSendQuestionNotificationDto> users, Long questionId, String integrationQuestionId) {
        List<QuestionNotification> notifications = users.stream()
                .map(u -> QuestionNotification.builder()
                        .userId(u.getUserId())
                        .integrationUserId(u.getIntegrationUserId())
                        .questionId(questionId)
                        .integrationQuestionId(integrationQuestionId)
                        .sendDate(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        questionNotificationRepository.saveAll(notifications);
        return notifications;
    }

    private void saveQuestionView(List<UserToSendQuestionNotificationDto> users, Long questionId) {
        List<Long> usersIds = users.stream().map(UserToSendQuestionNotificationDto::getUserId).collect(Collectors.toList());
        questionViewService.registerQuestionViews(questionId, usersIds, QuestionViewType.VIEW_IN_NOTIFICATION);
    }
}

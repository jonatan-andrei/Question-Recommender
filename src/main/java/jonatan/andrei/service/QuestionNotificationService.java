package jonatan.andrei.service;

import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.model.QuestionNotificationQueue;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static jonatan.andrei.domain.RecommendationSettingsType.ENABLE_CHANNEL;

@ApplicationScoped
@Slf4j
public class QuestionNotificationService {

    @Inject
    QuestionNotificationQueueService questionNotificationQueueService;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Transactional
    public void generateQuestionNotifications(IntegrationMethodType integrationMethodType) {
        List<QuestionNotificationQueue> pendingList = questionNotificationQueueService.findBySendDateIsNullAndDateWasIgnoredIsNull();
        Map<RecommendationSettingsType, BigDecimal> settings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.QUESTION_NOTIFICATION);
        boolean channelEnabled = settings.get(ENABLE_CHANNEL).compareTo(BigDecimal.ZERO) > 0;
        for (QuestionNotificationQueue qnq : pendingList) {
            try {
                if (channelEnabled) {
                    generateQuestionNotifications(qnq.getQuestionId(), integrationMethodType);
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

    private void generateQuestionNotifications(Long questionId, IntegrationMethodType integrationMethodType) {

    }
}

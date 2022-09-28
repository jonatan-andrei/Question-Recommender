package jonatan.andrei.scheduled;

import io.quarkus.scheduler.Scheduled;
import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.service.QuestionNotificationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class QuestionNotificationScheduled {

    @Inject
    QuestionNotificationService questionNotificationService;

    @Scheduled(cron = "{cron.question-notification}")
    void questionNotificationScheduled() {
        questionNotificationService.generateQuestionNotifications(IntegrationMethodType.REST);
    }

}

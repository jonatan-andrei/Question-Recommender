package jonatan.andrei.scheduled;

import io.quarkus.scheduler.Scheduled;
import jonatan.andrei.domain.IntegrationMethodType;
import jonatan.andrei.service.RecommendedEmailQuestionService;
import jonatan.andrei.service.RecommendedEmailService;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RecommendedEmailScheduled {

    @Inject
    RecommendedEmailService recommendedEmailService;

    @Scheduled(cron = "{cron.recommended-email}")
    void recommendedEmailScheduled() {
        recommendedEmailService.generateRecommendedEmails(IntegrationMethodType.REST);
    }

}

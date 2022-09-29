package jonatan.andrei.service;

import jonatan.andrei.model.QuestionNotificationQueue;
import jonatan.andrei.repository.QuestionNotificationQueueRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class QuestionNotificationQueueService {

    @Inject
    QuestionNotificationQueueRepository questionNotificationQueueRepository;

    public void saveQuestionNotificationQueue(Long questionId, String integrationQuestionId, LocalDateTime publicationDate) {
        if (publicationDate.isAfter(LocalDateTime.now().minusDays(1))) {
            questionNotificationQueueRepository.save(QuestionNotificationQueue.builder()
                    .questionId(questionId)
                    .integrationQuestionId(integrationQuestionId)
                    .build());
        }
    }

    public List<QuestionNotificationQueue> findBySendDateIsNullAndDateWasIgnoredIsNull() {
        return questionNotificationQueueRepository.findBySendDateIsNullAndDateWasIgnoredIsNull();
    }

    public void save(List<QuestionNotificationQueue> list) {
        questionNotificationQueueRepository.saveAll(list);
    }
}

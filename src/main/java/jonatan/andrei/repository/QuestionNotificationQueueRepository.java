package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionNotificationQueue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionNotificationQueueRepository extends CrudRepository<QuestionNotificationQueue, Long> {

    List<QuestionNotificationQueue> findBySendDateIsNullAndDateWasIgnoredIsNull();

    QuestionNotificationQueue findByQuestionId(Long questionId);

}

package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionNotification;
import org.springframework.data.repository.CrudRepository;

public interface QuestionNotificationRepository extends CrudRepository<QuestionNotification, Long> {

    QuestionNotification findByQuestionIdAndUserId(Long questionId, Long userId);

}

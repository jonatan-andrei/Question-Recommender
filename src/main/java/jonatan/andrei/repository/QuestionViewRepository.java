package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionView;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionViewRepository extends JpaRepository<QuestionView, Long> {

    List<QuestionView> findByQuestionIdAndUserIdIn(Long questionId, List<Long> userIds);

    List<QuestionView> findByUserIdAndQuestionIdIn(Long userId, List<Long> questionIds);

    QuestionView findByQuestionIdAndUserId(Long questionId, Long userId);

}

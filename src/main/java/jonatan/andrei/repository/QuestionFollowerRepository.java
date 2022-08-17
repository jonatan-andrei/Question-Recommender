package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionFollower;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionFollowerRepository extends CrudRepository<QuestionFollower, Long> {

    Optional<QuestionFollower> findByUserIdAndQuestionId(Long userId, Long questionId);
}

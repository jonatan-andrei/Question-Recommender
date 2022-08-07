package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionComment;
import org.springframework.data.repository.CrudRepository;

public interface QuestionCommentRepository extends CrudRepository<QuestionComment, Long> {
}
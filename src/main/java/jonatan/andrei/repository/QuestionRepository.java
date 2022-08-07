package jonatan.andrei.repository;

import jonatan.andrei.model.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}

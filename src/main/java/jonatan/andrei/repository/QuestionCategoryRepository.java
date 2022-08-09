package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionCategoryRepository extends CrudRepository<QuestionCategory, Long> {

    List<QuestionCategory> findByQuestionId(Long questionId);
}

package jonatan.andrei.repository;

import jonatan.andrei.model.QuestionTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionTagRepository extends CrudRepository<QuestionTag, Long> {

    List<QuestionTag> findByQuestionId(Long questionId);

    QuestionTag findByQuestionIdAndTagId(Long questionId, Long tagId);

}

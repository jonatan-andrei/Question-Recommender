package jonatan.andrei.repository;

import jonatan.andrei.model.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Question findByIntegrationPostId(String integrationPostId);

    @Modifying
    @Query("UPDATE Question SET duplicateQuestionId = :duplicateQuestionId WHERE postId = :postId")
    int registerDuplicateQuestion(@Param("postId") Long postId, @Param("duplicateQuestionId") Long duplicateQuestionId);
}

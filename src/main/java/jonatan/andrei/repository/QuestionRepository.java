package jonatan.andrei.repository;

import jonatan.andrei.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Question findByIntegrationPostId(String integrationPostId);

    @Modifying
    @Query("UPDATE Question SET duplicateQuestionId = :duplicateQuestionId WHERE postId = :postId")
    int registerDuplicateQuestion(@Param("postId") Long postId, @Param("duplicateQuestionId") Long duplicateQuestionId);

    @Modifying
    @Query("UPDATE Question SET bestAnswerId = :bestAnswerId WHERE postId = :postId")
    int registerBestAnswer(@Param("postId") Long postId, @Param("bestAnswerId") Long bestAnswerId);

    @Modifying
    @Query("UPDATE Question SET answers = answers + 1 WHERE postId = :postId")
    int updateNumberOfAnswers(@Param("postId") Long postId);
}

package jonatan.andrei.repository;

import jonatan.andrei.model.Question;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    Question findByIntegrationPostId(String integrationPostId);

    @Modifying
    @Query("UPDATE Question SET duplicateQuestionId = :duplicateQuestionId, updateDate = :updateDate WHERE postId = :postId")
    int registerDuplicateQuestion(@Param("postId") Long postId, @Param("duplicateQuestionId") Long duplicateQuestionId, @Param("updateDate") LocalDateTime updateDate);

    @Modifying
    @Query("UPDATE Question SET bestAnswerId = :bestAnswerId, updateDate = :updateDate WHERE postId = :postId")
    int registerBestAnswer(@Param("postId") Long postId, @Param("bestAnswerId") Long bestAnswerId, @Param("updateDate") LocalDateTime updateDate);

    @Modifying
    @Query("UPDATE Question SET answers = answers + 1 WHERE postId = :postId")
    int updateNumberOfAnswers(@Param("postId") Long postId);
}

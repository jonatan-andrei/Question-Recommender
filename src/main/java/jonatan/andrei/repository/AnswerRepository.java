package jonatan.andrei.repository;

import jonatan.andrei.model.Answer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    @Modifying
    @Query("UPDATE Answer SET bestAnswer = :selected, updateDate = :updateDate WHERE postId = :postId")
    int registerBestAnswer(@Param("postId") Long postId, @Param("selected") boolean selected, @Param("updateDate") LocalDateTime updateDate);

    Optional<Answer> findByQuestionIdAndBestAnswer(Long questionId, boolean bestAnswer);
}

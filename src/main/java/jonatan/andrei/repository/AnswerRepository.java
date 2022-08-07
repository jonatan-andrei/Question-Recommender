package jonatan.andrei.repository;

import jonatan.andrei.model.Answer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends CrudRepository<Answer, Long> {

    @Modifying
    @Query("UPDATE Answer SET selected = :selected WHERE postId = :postId")
    int registerBestAnswer(@Param("postId") Long postId, @Param("selected") boolean selected);
}

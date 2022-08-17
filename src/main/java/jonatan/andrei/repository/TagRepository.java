package jonatan.andrei.repository;

import jonatan.andrei.model.Tag;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String name);

    List<Tag> findByNameIn(List<String> tags);

    @Modifying
    @Query("UPDATE Tag SET questionCount = questionCount + 1 WHERE tagId IN :tags")
    int incrementQuestionCount(@Param("tags") List<Long> tags);

    @Modifying
    @Query("UPDATE Tag SET questionCount = questionCount - 1 WHERE tagId IN :tags")
    int decrementQuestionCount(@Param("tags") List<Long> tags);
}

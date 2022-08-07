package jonatan.andrei.repository;

import jonatan.andrei.model.Word;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WordRepository extends CrudRepository<Word, Long> {

    @Query(value = "SELECT w.* FROM word w WHERE w.word IN (:words) ")
    List<Word> findByWord(List<String> words);
}

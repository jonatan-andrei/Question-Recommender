package jonatan.andrei.repository;

import jonatan.andrei.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByName(String name);

    List<Tag> findByNameIn(List<String> tags);

    List<Tag> findByTagIdIn(List<Long> tagsIds);

}

package jonatan.haas.repository;

import jonatan.haas.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {

    Tag findByIntegrationTagId(String integrationTagId);

    Tag findByName(String name);
}

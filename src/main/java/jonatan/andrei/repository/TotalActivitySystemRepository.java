package jonatan.andrei.repository;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.model.TotalActivitySystem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TotalActivitySystemRepository extends CrudRepository<TotalActivitySystem, Long> {

    Optional<TotalActivitySystem> findByPostClassificationType(PostClassificationType postClassificationType);
}

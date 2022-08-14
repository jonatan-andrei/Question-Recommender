package jonatan.andrei.repository;

import jonatan.andrei.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByIntegrationUserId(String integrationUserId);

    Optional<User> findByIntegrationUserIdAndIntegrationAnonymousUserId(String integrationUserId, String integrationAnonymousUserId);

}

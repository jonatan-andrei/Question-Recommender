package jonatan.andrei.repository;

import jonatan.andrei.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByIntegrationUserId(String integrationUserId);

    Optional<User> findByIntegrationAnonymousUserId(String integrationAnonymousUserId);

    Optional<User> findByIntegrationUserIdAndIntegrationAnonymousUserId(String integrationUserId, String integrationAnonymousUserId);

    List<User> findByintegrationUserIdIn(List<String> integrationUsersIds);
}

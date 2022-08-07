package jonatan.andrei.repository;

import jonatan.andrei.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

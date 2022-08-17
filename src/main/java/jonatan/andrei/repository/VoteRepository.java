package jonatan.andrei.repository;

import jonatan.andrei.model.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    Optional<Vote> findByUserIdAndPostId(Long userId, Long postId);
}

package jonatan.andrei.repository;

import jonatan.andrei.model.UserFollower;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface UserFollowerRepository extends CrudRepository<UserFollower, Long> {

    UserFollower findByUserIdAndFollowerId(Long userId, Long followerId);

    @Modifying
    void deleteByUserIdAndFollowerId(Long userId, Long followerId);
}

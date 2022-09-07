package jonatan.andrei.repository;

import jonatan.andrei.model.UserTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTagRepository extends CrudRepository<UserTag, Long> {

    List<UserTag> findByUserIdAndTagIdIn(Long userId, List<Long> tagsIds);

    UserTag findByUserIdAndTagId(Long userId, Long tagId);

    List<UserTag> findByUserId(Long userId);

    List<UserTag> findByUserIdAndExplicitRecommendation(Long userId, boolean explicitRecommendation);

    List<UserTag> findByUserIdAndIgnored(Long userId, boolean ignored);
}

package jonatan.andrei.repository;

import jonatan.andrei.model.UserTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserTagRepository extends CrudRepository<UserTag, Long> {

    List<UserTag> findByUserIdAndTagIdIn(Long userId, List<Long> tagsIds);

    UserTag findByUserIdAndTagId(Long userId, Long tagId);
}

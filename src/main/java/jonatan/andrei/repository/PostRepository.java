package jonatan.andrei.repository;

import jonatan.andrei.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findByIntegrationPostId(String integrationPostId);

    @Query("UPDATE post p SET p.hidden = :hidden WHERE p.postId = :postId")
    void hideOrExposePost(@Param("postId") Long postId, @Param("hidden") boolean hidden);


}

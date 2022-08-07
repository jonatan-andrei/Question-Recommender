package jonatan.andrei.repository;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findByIntegrationPostId(String integrationPostId);

    Optional<Post> findByIntegrationPostIdAndPostType(String integrationPostId, PostType postType);

    @Modifying
    @Query("UPDATE Post SET hidden = :hidden WHERE postId = :postId")
    int hideOrExposePost(@Param("postId") Long postId, @Param("hidden") boolean hidden);


}

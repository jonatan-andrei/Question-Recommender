package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Post;
import jonatan.andrei.repository.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

import static java.util.Objects.isNull;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    @Transactional
    public Post save(CreatePostRequestDto createPostRequestDto) {
        return null;
    }

    @Transactional
    public Post update(UpdatePostRequestDto updatePostRequestDto) {
        return null;
    }

    public Post findByIntegrationPostId(String integrationPostId) {
        return findOptionalByIntegrationPostId(integrationPostId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found post with integrationPostId " + integrationPostId));
    }

    public Optional<Post> findOptionalByIntegrationPostId(String integrationPostId) {
        return postRepository.findByIntegrationPostId(integrationPostId);
    }

    @Transactional
    public void hideOrExposePost(HidePostRequestDto hidePostRequestDto) {
        if (isNull(hidePostRequestDto.getIntegrationPostId())) {
            throw new RequiredDataException("Attribute 'integrationPostId' is required");
        }

        Post post = findByIntegrationPostId(hidePostRequestDto.getIntegrationPostId());
        postRepository.hideOrExposePost(post.getPostId(), hidePostRequestDto.isHidden());
    }
}

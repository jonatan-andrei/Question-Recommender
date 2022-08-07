package jonatan.andrei.resource;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.model.Post;
import jonatan.andrei.service.PostService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/post")
@ApplicationScoped
public class PostResource {

    @Inject
    PostService postService;

    @POST
    public ResponseEntity<Post> save(CreatePostRequestDto createPostRequestDto) {
        return ResponseEntity.ok(postService.save(createPostRequestDto));
    }

    @PUT
    public ResponseEntity<Post> update(UpdatePostRequestDto updatePostRequestDto) {
        return ResponseEntity.ok(postService.update(updatePostRequestDto));
    }

    @PUT()
    @Path("/hidden")
    public ResponseEntity hideOrExposePost(HidePostRequestDto hidePostRequestDto) {
        postService.hideOrExposePost(hidePostRequestDto);
        return ResponseEntity.ok().build();
    }
}

package jonatan.andrei.resource;

import jonatan.andrei.dto.*;
import jonatan.andrei.model.Post;
import jonatan.andrei.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;

@Path("/post")
@ApplicationScoped
@Slf4j
public class PostResource {

    @Inject
    PostService postService;

    @POST
    public ResponseEntity<Post> save(CreatePostRequestDto createPostRequestDto) {
        return ResponseEntity.ok(postService.save(createPostRequestDto));
    }

    @POST
    @Path("/list")
    public ResponseEntity<List<Post>> save(List<CreatePostRequestDto> posts) {
        return ResponseEntity.ok(postService.save(posts));
    }

    @PUT
    public ResponseEntity<Post> update(UpdatePostRequestDto updatePostRequestDto) {
        return ResponseEntity.ok(postService.update(updatePostRequestDto));
    }

    @POST
    @Path("/register-views")
    public ResponseEntity registerViews(ViewsRequestDto viewsRequestDto) {
        postService.registerViews(viewsRequestDto);
        return ResponseEntity.ok().build();
    }

    @PUT
    @Path("/register-best-answer")
    public ResponseEntity registerBestAnswer(BestAnswerRequestDto bestAnswerRequestDto) {
        postService.registerBestAnswer(bestAnswerRequestDto);
        return ResponseEntity.ok().build();
    }

    @PUT
    @Path("/register-best-answer/list")
    public ResponseEntity registerBestAnswer(List<BestAnswerRequestDto> bestAnswers) {
        postService.registerBestAnswer(bestAnswers);
        return ResponseEntity.ok().build();
    }

    @PUT
    @Path("/register-duplicate_question")
    public ResponseEntity registerDuplicateQuestion(DuplicateQuestionRequestDto duplicateQuestionRequestDto) {
        postService.registerDuplicateQuestion(duplicateQuestionRequestDto);
        return ResponseEntity.ok().build();
    }

    @PUT
    @Path("/register-duplicate_question/list")
    public ResponseEntity registerDuplicateQuestion(List<DuplicateQuestionRequestDto> duplicateQuestions) {
        postService.registerDuplicateQuestion(duplicateQuestions);
        return ResponseEntity.ok().build();
    }

    @PUT
    @Path("/hidden")
    public ResponseEntity hideOrExposePost(HidePostRequestDto hidePostRequestDto) {
        postService.hideOrExposePost(hidePostRequestDto);
        return ResponseEntity.ok().build();
    }

    @POST
    @Path("/register-vote")
    public ResponseEntity registerVote(VoteRequestDto voteRequestDto) {
        postService.registerVote(voteRequestDto);
        return ResponseEntity.ok().build();
    }

    @POST
    @Path("/register-vote/list")
    public ResponseEntity registerVote(List<VoteRequestDto> votes) {
        postService.registerVote(votes);
        return ResponseEntity.ok().build();
    }

    @POST
    @Path("/register-question-follower")
    public ResponseEntity registerQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto) {
        postService.registerQuestionFollower(questionFollowerRequestDto);
        return ResponseEntity.ok().build();
    }

    @POST
    @Path("/register-question-follower/list")
    public ResponseEntity registerQuestionFollower(List<QuestionFollowerRequestDto> questionFollowers) {
        postService.registerQuestionFollower(questionFollowers);
        return ResponseEntity.ok().build();
    }

    @GET
    @Path("/{integrationPostId}")
    public ResponseEntity<Post> findByIntegrationPostId(String integrationPostId) {
        return ResponseEntity.ok(postService.findByIntegrationPostId(integrationPostId));
    }

}

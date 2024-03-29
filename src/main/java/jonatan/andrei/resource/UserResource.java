package jonatan.andrei.resource;

import jonatan.andrei.dto.*;
import jonatan.andrei.model.User;
import jonatan.andrei.service.UserService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.time.LocalDateTime;
import java.util.List;

@Path("/user")
@ApplicationScoped
public class UserResource {

    @Inject
    UserService userService;

    @POST
    public ResponseEntity<User> save(CreateUserRequestDto createUserRequestDto) {
        return ResponseEntity.ok(userService.save(createUserRequestDto));
    }

    @POST
    @Path("/list")
    public ResponseEntity<List<User>> save(List<CreateUserRequestDto> users) {
        return ResponseEntity.ok(userService.save(users));
    }

    @PUT
    public ResponseEntity<User> update(UpdateUserRequestDto updateUserRequestDto) {
        return ResponseEntity.ok(userService.update(updateUserRequestDto));
    }

    @POST
    @Path("/register-follower")
    public ResponseEntity registerFollower(UserFollowerRequestDto userFollowerRequestDto) {
        userService.registerFollower(userFollowerRequestDto);
        return ResponseEntity.ok().build();
    }

    @POST
    @Path("/register-follower/list")
    public ResponseEntity registerFollower(List<UserFollowerRequestDto> followers) {
        userService.registerFollower(followers);
        return ResponseEntity.ok().build();
    }

    @GET
    @Path("/find-user-tags")
    public ResponseEntity<List<UserTagDto>> findUserTags(@QueryParam("integrationUserId") String integrationUserId) {
        return ResponseEntity.ok(userService.findTagsByUserId(integrationUserId));
    }

    @GET
    @Path("/find-questions-answered-in-period")
    public ResponseEntity<List<QuestionsAnsweredByUserResponseDto>> findQuestionsAnsweredInPeriod(@QueryParam("startDate") LocalDateTime startDate,
                                                                                                  @QueryParam("endDate") LocalDateTime endDate,
                                                                                                  @QueryParam("minimumOfPreviousAnswers") Integer minimumOfPreviousAnswers) {
        return ResponseEntity.ok(userService.findQuestionsAnsweredInPeriod(startDate, endDate, minimumOfPreviousAnswers));
    }

}

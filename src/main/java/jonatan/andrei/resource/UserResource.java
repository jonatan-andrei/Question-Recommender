package jonatan.andrei.resource;

import jonatan.andrei.dto.CreateUserRequestDto;
import jonatan.andrei.dto.UpdateUserRequestDto;
import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.model.User;
import jonatan.andrei.service.UserService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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

}

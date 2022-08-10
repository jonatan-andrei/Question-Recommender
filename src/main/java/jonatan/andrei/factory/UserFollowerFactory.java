package jonatan.andrei.factory;

import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserFollower;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserFollowerFactory {

    public static UserFollower newUserFollower(UserFollowerRequestDto userFollowerRequestDto, User user, User userFollower) {
        return UserFollower.builder()
                .userId(user.getUserId())
                .followerId(userFollower.getUserId())
                .startDate(Optional.ofNullable(userFollowerRequestDto.getStartDate()).orElse(LocalDateTime.now()))
                .build();
    }
}

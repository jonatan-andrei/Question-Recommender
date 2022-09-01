package jonatan.andrei.service;

import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.factory.UserFollowerFactory;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserFollower;
import jonatan.andrei.repository.UserFollowerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserFollowerService {

    @Inject
    UserFollowerRepository userFollowerRepository;

    public void registerFollower(UserFollowerRequestDto userFollowerRequestDto, User user, User userFollower) {
        UserFollower existingUserFollower = userFollowerRepository.findByUserIdAndFollowerId(user.getUserId(), userFollower.getUserId());

        if (!userFollowerRequestDto.isFollowed()) {
            userFollowerRepository.deleteByUserIdAndFollowerId(user.getUserId(), userFollower.getUserId());
        } else if (isNull(existingUserFollower)) {
            userFollowerRepository.save(UserFollowerFactory.newUserFollower(userFollowerRequestDto, user, userFollower));
        }
    }

    public void clear() {
        userFollowerRepository.deleteAll();
    }
}

package jonatan.andrei.service;

import jonatan.andrei.dto.CreateUserRequestDto;
import jonatan.andrei.dto.UpdateUserRequestDto;
import jonatan.andrei.dto.UserFollowerRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.UserFactory;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserFollowerService userFollowerService;

    @Transactional
    public User save(CreateUserRequestDto createUserRequestDto) {
        return null;
    }

    @Transactional
    public List<User> save(List<CreateUserRequestDto> users) {
        return users.stream().map(u -> save(u)).collect(Collectors.toList());
    }

    @Transactional
    public User update(UpdateUserRequestDto updateUserRequestDto) {
        return null;
    }

    @Transactional
    public void registerFollower(UserFollowerRequestDto userFollowerRequestDto) {
        if (isNull(userFollowerRequestDto.getIntegrationUserId())) {
            throw new RequiredDataException("Attribute 'integrationUserId' is required");
        }
        if (isNull(userFollowerRequestDto.getIntegrationFollowerUserId())) {
            throw new RequiredDataException("Attribute 'integrationFollowerUserId' is required");
        }
        User user = findByIntegrationUserId(userFollowerRequestDto.getIntegrationUserId());
        User followerUser = findByIntegrationUserId(userFollowerRequestDto.getIntegrationFollowerUserId());

        userFollowerService.registerFollower(userFollowerRequestDto, user, followerUser);
    }

    @Transactional
    public void registerFollower(List<UserFollowerRequestDto> followers) {
        followers.forEach(f -> registerFollower(f));
    }

    public User findByIntegrationUserId(String integrationUserId) {
        return findOptionalByIntegrationUserId(integrationUserId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found user with integrationUserId " + integrationUserId));
    }

    public Optional<User> findOptionalByIntegrationUserId(String integrationUserId) {
        return userRepository.findByIntegrationUserId(integrationUserId);
    }

    public User findUserByIntegrationUserIdOrCreateByAnonymousId(String integrationUserId, String integrationAnonymousUserId) {
        if (nonNull(integrationUserId)) {
            return findByIntegrationUserId(integrationUserId);
        } else if (nonNull(integrationAnonymousUserId)) {
            Optional<User> anonymousUser = userRepository.findByIntegrationUserIdAndIntegrationAnonymousUserId(null, integrationAnonymousUserId);
            return anonymousUser.orElse(userRepository.save(UserFactory.newUserWithIntegrationAnonymousUserId(integrationAnonymousUserId)));
        } else {
            throw new RequiredDataException("At least one of the fields must be informed: integrationUserId or integrationAnonymousUserId");
        }
    }
}

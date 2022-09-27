package jonatan.andrei.service;

import io.netty.util.internal.StringUtil;
import jonatan.andrei.domain.PostType;
import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.UserPreferenceType;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.factory.QuestionsAnsweredByUserFactory;
import jonatan.andrei.factory.UserFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.UserRepository;
import jonatan.andrei.repository.custom.UserCustomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
    UserCustomRepository userCustomRepository;

    @Inject
    UserFollowerService userFollowerService;

    @Inject
    CategoryService categoryService;

    @Inject
    TagService tagService;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserTagService userTagService;

    @Transactional
    public User save(CreateUserRequestDto createUserRequestDto) {
        validateIntegrationUserIdInformed(createUserRequestDto.getIntegrationUserId());
        validateUserPreferencesInformed(createUserRequestDto.getUserPreferences());
        if (userRepository.findByIntegrationUserId(createUserRequestDto.getIntegrationUserId()).isPresent()) {
            throw new InconsistentIntegratedDataException("There is already a user with integrationUserId " + createUserRequestDto.getIntegrationUserId());
        }
        Optional<User> anonymousUser = nonNull(createUserRequestDto.getIntegrationAnonymousUserId())
                ? userRepository.findByIntegrationAnonymousUserId(createUserRequestDto.getIntegrationAnonymousUserId())
                : Optional.empty();

        User user = anonymousUser.isPresent()
                ? UserFactory.overwriteWithAnonymousUser(anonymousUser.get(), createUserRequestDto)
                : UserFactory.newUser(createUserRequestDto);
        user = userRepository.save(user);
        saveUserPreferences(user, createUserRequestDto.getUserPreferences());
        return user;
    }

    @Transactional
    public List<User> save(List<CreateUserRequestDto> users) {
        return users.stream().map(u -> save(u)).collect(Collectors.toList());
    }

    @Transactional
    public User update(UpdateUserRequestDto updateUserRequestDto) {
        validateIntegrationUserIdInformed(updateUserRequestDto.getIntegrationUserId());
        validateUserPreferencesInformed(updateUserRequestDto.getUserPreferences());
        User user = findByIntegrationUserId(updateUserRequestDto.getIntegrationUserId());
        user = userRepository.save(UserFactory.overwrite(user, updateUserRequestDto));
        saveUserPreferences(user, updateUserRequestDto.getUserPreferences());
        return user;
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

    public List<User> findByIntegrationUserIdIn(List<String> integrationUsersIds) {
        return userRepository.findByintegrationUserIdIn(integrationUsersIds);
    }

    public User findUserByIntegrationUserIdOrCreateByAnonymousId(String integrationUserId, String integrationAnonymousUserId) {
        if (!StringUtil.isNullOrEmpty(integrationUserId)) {
            return findByIntegrationUserId(integrationUserId);
        } else if (!StringUtil.isNullOrEmpty(integrationAnonymousUserId)) {
            Optional<User> anonymousUser = userRepository.findByIntegrationUserIdAndIntegrationAnonymousUserId(null, integrationAnonymousUserId);
            return anonymousUser.orElseGet(() -> userRepository.save(UserFactory.newUserWithIntegrationAnonymousUserId(integrationAnonymousUserId)));
        } else {
            throw new RequiredDataException("At least one of the fields must be informed: integrationUserId or integrationAnonymousUserId");
        }
    }

    public List<UserToSendRecommendedEmailDto> findUsersToSendRecommendedEmail(LocalDateTime startDate, Integer hour, boolean isDefaultHour, LocalDateTime minimumLastActivityDate, Integer pageNumber, Integer lengthPage) {
        return userCustomRepository.findUsersToSendRecommendedEmail(startDate, hour, isDefaultHour, minimumLastActivityDate, pageNumber, lengthPage);
    }

    private void validateIntegrationUserIdInformed(String integrationUserId) {
        if (isNull(integrationUserId)) {
            throw new RequiredDataException("Attribute 'integrationUserId' is required");
        }
    }

    private void validateUserPreferencesInformed(UserPreferencesRequestDto userPreferences) {
        if (isNull(userPreferences)) {
            throw new RequiredDataException("Attribute 'userPreferences' is required");
        }
    }

    public void updateQuestionCategoriesViewed(List<User> users, List<QuestionCategory> categories) {
        userCategoryService.updateNumberQuestionsViewed(users, categories);
    }

    public void updateQuestionTagsViewed(List<User> users, List<QuestionTag> tags) {
        userTagService.updateNumberQuestionsViewed(users, tags);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    private void saveUserPreferences(User user, UserPreferencesRequestDto userPreferences) {
        List<Category> explicitCategories = categoryService.findByIntegrationCategoriesIds(userPreferences.getExplicitIntegrationCategoriesIds());
        userCategoryService.saveUserPreferences(user, explicitCategories, UserPreferenceType.EXPLICIT);
        List<Category> ignoredCategories = categoryService.findByIntegrationCategoriesIds(userPreferences.getIgnoredIntegrationCategoriesIds());
        userCategoryService.saveUserPreferences(user, ignoredCategories, UserPreferenceType.IGNORED);
        List<Tag> explicitTags = tagService.findOrCreateTags(userPreferences.getExplicitTags());
        userTagService.saveUserPreferences(user, explicitTags, UserPreferenceType.EXPLICIT);
        List<Tag> ignoredTags = tagService.findOrCreateTags(userPreferences.getIgnoredTags());
        userTagService.saveUserPreferences(user, ignoredTags, UserPreferenceType.IGNORED);
    }

    public void updateQuestionViewed(List<User> users) {
        users.forEach(u -> updateByAction(u, UserActionType.QUESTION_VIEWED, UserActionUpdateType.INCREASE, LocalDateTime.now()));
    }

    public void updateByActionAndPostType(User user, UserActionUpdateType userActionUpdateType, PostType postType, LocalDateTime actionDate) {
        UserActionType userActionType = switch (postType) {
            case QUESTION -> UserActionType.QUESTION_ASKED;
            case ANSWER -> UserActionType.QUESTION_ANSWERED;
            case QUESTION_COMMENT, ANSWER_COMMENT -> UserActionType.QUESTION_COMMENTED;
        };

        updateByAction(user, userActionType, userActionUpdateType, actionDate);
    }

    public void updateVotesByActionAndPostType(User user, UserActionUpdateType userActionUpdateType, PostType postType, boolean upvoted, LocalDateTime voteDate) {
        UserActionType userActionType = switch (postType) {
            case QUESTION -> upvoted ? UserActionType.QUESTION_UPVOTED : UserActionType.QUESTION_DOWNVOTED;
            case ANSWER -> upvoted ? UserActionType.ANSWER_UPVOTED : UserActionType.ANSWER_DOWNVOTED;
            case QUESTION_COMMENT, ANSWER_COMMENT ->
                    upvoted ? UserActionType.COMMENT_UPVOTED : UserActionType.COMMENT_DOWNVOTED;
        };

        updateByAction(user, userActionType, userActionUpdateType, voteDate);
    }

    public void updateByAction(User user, UserActionType userActionType, UserActionUpdateType userActionUpdateType, LocalDateTime actionDate) {
        switch (userActionType) {
            case QUESTION_ASKED ->
                    user.setNumberQuestionsAsked(user.getNumberQuestionsAsked().add(userActionUpdateType.getValue()));
            case QUESTION_VIEWED ->
                    user.setNumberQuestionsViewed(user.getNumberQuestionsViewed().add(userActionUpdateType.getValue()));
            case QUESTION_ANSWERED ->
                    user.setNumberQuestionsAnswered(user.getNumberQuestionsAnswered().add(userActionUpdateType.getValue()));
            case QUESTION_COMMENTED ->
                    user.setNumberQuestionsCommented(user.getNumberQuestionsCommented().add(userActionUpdateType.getValue()));
            case QUESTION_FOLLOWED ->
                    user.setNumberQuestionsFollowed(user.getNumberQuestionsFollowed().add(userActionUpdateType.getValue()));
            case QUESTION_UPVOTED ->
                    user.setNumberQuestionsUpvoted(user.getNumberQuestionsUpvoted().add(userActionUpdateType.getValue()));
            case QUESTION_DOWNVOTED ->
                    user.setNumberQuestionsDownvoted(user.getNumberQuestionsDownvoted().add(userActionUpdateType.getValue()));
            case ANSWER_UPVOTED ->
                    user.setNumberAnswersUpvoted(user.getNumberAnswersUpvoted().add(userActionUpdateType.getValue()));
            case ANSWER_DOWNVOTED ->
                    user.setNumberAnswersDownvoted(user.getNumberAnswersDownvoted().add(userActionUpdateType.getValue()));
            case COMMENT_UPVOTED ->
                    user.setNumberCommentsUpvoted(user.getNumberCommentsUpvoted().add(userActionUpdateType.getValue()));
            case COMMENT_DOWNVOTED ->
                    user.setNumberCommentsDownvoted(user.getNumberCommentsDownvoted().add(userActionUpdateType.getValue()));
        }
        user.setLastActivityDate(Optional.ofNullable(actionDate).orElse(LocalDateTime.now()));
        userRepository.save(user);
    }

    public List<UserTagDto> findTagsByUserId(String integrationUserId) {
        return userTagService.findByIntegrationUserId(integrationUserId);
    }

    @Transactional
    public List<QuestionsAnsweredByUserResponseDto> findQuestionsAnsweredInPeriod(LocalDateTime startDate, LocalDateTime endDate, Integer minimumOfPreviousAnswers) {
        return userCustomRepository.findQuestionsAnsweredInPeriod(startDate, endDate, minimumOfPreviousAnswers)
                .stream().collect(Collectors.groupingBy(q -> q.getIntegrationUserId()))
                .entrySet().stream().map(u -> QuestionsAnsweredByUserFactory.newQuestionsAnsweredByUser(u.getKey(), u.getValue()))
                .collect(Collectors.toList());
    }

    public void clear() {
        userRepository.deleteAll();
    }
}

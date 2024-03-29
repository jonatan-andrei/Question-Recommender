package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.UserPreferenceType;
import jonatan.andrei.dto.UserTagDto;
import jonatan.andrei.factory.UserTagFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.UserTagRepository;
import jonatan.andrei.repository.custom.UserTagCustomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserTagService {

    @Inject
    UserTagRepository userTagRepository;

    @Inject
    UserTagCustomRepository userTagCustomRepository;

    @Inject
    TagService tagService;

    public void updateNumberQuestionsViewed(List<User> users, List<QuestionTag> tags) {
        for (User user : users) {
            updateNumberQuestionsByAction(user, tags, UserActionType.QUESTION_VIEWED, UserActionUpdateType.INCREASE);
        }
    }

    public void updateNumberQuestionsVoted(User user, Post post, List<QuestionTag> tags, UserActionUpdateType userActionUpdateType, boolean upvoted) {
        UserActionType userActionType = switch (post.getPostType()) {
            case QUESTION -> upvoted ? UserActionType.QUESTION_UPVOTED : UserActionType.QUESTION_DOWNVOTED;
            case ANSWER -> upvoted ? UserActionType.ANSWER_UPVOTED : UserActionType.ANSWER_DOWNVOTED;
            case QUESTION_COMMENT, ANSWER_COMMENT ->
                    upvoted ? UserActionType.COMMENT_UPVOTED : UserActionType.COMMENT_DOWNVOTED;
        };

        updateNumberQuestionsByAction(user, tags, userActionType, userActionUpdateType);
    }

    public void updateNumberQuestionsByAction(User user, List<QuestionTag> tags, UserActionType userActionType, UserActionUpdateType userActionUpdateType) {
        List<Long> tagsIds = tags.stream().map(QuestionTag::getTagId).collect(Collectors.toList());
        List<UserTag> userTags = userTagRepository.findByUserIdAndTagIdIn(user.getUserId(), tagsIds);
        BigDecimal value = tags.isEmpty() ? BigDecimal.ZERO : userActionUpdateType.getValue().divide(new BigDecimal(tags.size()), 2, RoundingMode.HALF_UP);
        for (Long tag : tagsIds) {
            UserTag userTag = findOrCreateUserTag(userTags, user.getUserId(), tag);
            switch (userActionType) {
                case QUESTION_ASKED -> userTag.setNumberQuestionsAsked(userTag.getNumberQuestionsAsked().add(value));
                case QUESTION_VIEWED -> userTag.setNumberQuestionsViewed(userTag.getNumberQuestionsViewed().add(value));
                case QUESTION_ANSWERED ->
                        userTag.setNumberQuestionsAnswered(userTag.getNumberQuestionsAnswered().add(value));
                case QUESTION_COMMENTED ->
                        userTag.setNumberQuestionsCommented(userTag.getNumberQuestionsCommented().add(value));
                case QUESTION_FOLLOWED ->
                        userTag.setNumberQuestionsFollowed(userTag.getNumberQuestionsFollowed().add(value));
                case QUESTION_UPVOTED ->
                        userTag.setNumberQuestionsUpvoted(userTag.getNumberQuestionsUpvoted().add(value));
                case QUESTION_DOWNVOTED ->
                        userTag.setNumberQuestionsDownvoted(userTag.getNumberQuestionsDownvoted().add(value));
                case ANSWER_UPVOTED -> userTag.setNumberAnswersUpvoted(userTag.getNumberAnswersUpvoted().add(value));
                case ANSWER_DOWNVOTED ->
                        userTag.setNumberAnswersDownvoted(userTag.getNumberAnswersDownvoted().add(value));
                case COMMENT_UPVOTED -> userTag.setNumberCommentsUpvoted(userTag.getNumberCommentsUpvoted().add(value));
                case COMMENT_DOWNVOTED ->
                        userTag.setNumberCommentsDownvoted(userTag.getNumberCommentsDownvoted().add(value));
            }
        }
        userTagRepository.saveAll(userTags);
        tagService.updateNumberQuestionsByAction(tags, userActionType, userActionUpdateType, value);
    }

    public void saveUserPreferences(User user, List<Tag> tags, UserPreferenceType userPreference) {
        List<UserTag> userTags = findOrCreateUserTags(tags, user.getUserId());
        userTags.forEach(ut -> updateUserPreference(ut, userPreference, true));

        List<UserTag> userTagsToUpdateToFalse = findUserTagsToUpdateToFalse(user, userPreference, tags);
        userTagsToUpdateToFalse.forEach(ut -> updateUserPreference(ut, userPreference, false));
        userTags.addAll(userTagsToUpdateToFalse);

        userTagRepository.saveAll(userTags);
    }

    public List<UserTagDto> findByIntegrationUserId(String integrationUserId) {
        return userTagCustomRepository.findByUserId(integrationUserId);
    }

    private void updateUserPreference(UserTag userTag, UserPreferenceType userPreference, boolean active) {
        if (userPreference.equals(UserPreferenceType.EXPLICIT)) {
            userTag.setExplicitRecommendation(active);
        } else {
            userTag.setIgnored(active);
        }
    }

    private UserTag findOrCreateUserTag(List<UserTag> userTags, Long userId, Long tagId) {
        UserTag userTag = userTags.stream().filter(
                        ut -> ut.getTagId().equals(tagId)).findFirst()
                .orElse(null);
        if (isNull(userTag)) {
            userTag = UserTagFactory.newUserTag(userId, tagId);
            userTags.add(userTag);
        }
        return userTag;
    }

    private List<UserTag> findOrCreateUserTags(List<Tag> tags, Long userId) {
        List<Long> tagsIds = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        List<UserTag> existingUserTags = userTagRepository.findByUserIdAndTagIdIn(userId, tagsIds);
        return tags.stream().map(t -> findOrCreateUserTag(existingUserTags, userId, t.getTagId()))
                .collect(Collectors.toList());
    }

    private List<UserTag> findUserTagsToUpdateToFalse(User user, UserPreferenceType userPreference, List<Tag> tags) {
        List<UserTag> existingUserTagsByType = UserPreferenceType.EXPLICIT.equals(userPreference)
                ? userTagRepository.findByUserIdAndExplicitRecommendation(user.getUserId(), true)
                : userTagRepository.findByUserIdAndIgnored(user.getUserId(), true);
        List<Long> tagsIds = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        return existingUserTagsByType.stream()
                .filter(ut -> !tagsIds.contains(ut.getTagId()))
                .collect(Collectors.toList());
    }

    public void clear() {
        userTagRepository.deleteAll();
    }
}

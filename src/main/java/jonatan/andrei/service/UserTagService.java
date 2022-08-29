package jonatan.andrei.service;

import jonatan.andrei.domain.UserActionType;
import jonatan.andrei.domain.UserActionUpdateType;
import jonatan.andrei.domain.UserPreference;
import jonatan.andrei.factory.UserTagFactory;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.UserTagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ApplicationScoped
public class UserTagService {

    @Inject
    UserTagRepository userTagRepository;

    public void updateNumberQuestionsViewed(List<User> users, List<QuestionTag> tags) {
        for (User user : users) {
            updateNumberQuestionsByAction(user, tags, UserActionType.QUESTION_VIEWED, UserActionUpdateType.INCREASE);
        }
    }

    public void updateNumberQuestionsVoted(User user, Post post, List<QuestionTag> tags, UserActionUpdateType userActionUpdateType) {
        UserActionType userActionType = switch (post.getPostType()) {
            case QUESTION -> UserActionType.QUESTION_VOTED;
            case ANSWER -> UserActionType.ANSWER_VOTED;
            case QUESTION_COMMENT, ANSWER_COMMENT -> UserActionType.COMMENT_VOTED;
        };

        updateNumberQuestionsByAction(user, tags, userActionType, userActionUpdateType);
    }

    public void updateNumberQuestionsByAction(User user, List<QuestionTag> tags, UserActionType userActionType, UserActionUpdateType userActionUpdateType) {
        List<Long> tagsIds = tags.stream().map(QuestionTag::getTagId).collect(Collectors.toList());
        List<UserTag> userTags = userTagRepository.findByUserIdAndTagIdIn(user.getUserId(), tagsIds);
        for (Long tag : tagsIds) {
            UserTag userTag = findOrCreateUserTag(userTags, user.getUserId(), tag);
            switch (userActionType) {
                case QUESTION_ASKED ->
                        userTag.setNumberQuestionsAsked(userTag.getNumberQuestionsAsked() + userActionUpdateType.getValue());
                case QUESTION_VIEWED ->
                        userTag.setNumberQuestionsViewed(userTag.getNumberQuestionsViewed() + userActionUpdateType.getValue());
                case QUESTION_VOTED ->
                        userTag.setNumberQuestionsVoted(userTag.getNumberQuestionsVoted() + userActionUpdateType.getValue());
                case QUESTION_ANSWERED ->
                        userTag.setNumberQuestionsAnswered(userTag.getNumberQuestionsAnswered() + userActionUpdateType.getValue());
                case QUESTION_COMMENTED ->
                        userTag.setNumberQuestionsCommented(userTag.getNumberQuestionsCommented() + userActionUpdateType.getValue());
                case QUESTION_FOLLOWED ->
                        userTag.setNumberQuestionsFollowed(userTag.getNumberQuestionsFollowed() + userActionUpdateType.getValue());
                case ANSWER_VOTED ->
                        userTag.setNumberAnswersVoted(userTag.getNumberAnswersVoted() + userActionUpdateType.getValue());
                case COMMENT_VOTED ->
                        userTag.setNumberCommentsVoted(userTag.getNumberCommentsVoted() + userActionUpdateType.getValue());
            }
        }
        userTagRepository.saveAll(userTags);
    }

    public void saveUserPreferences(User user, List<Tag> tags, UserPreference userPreference) {
        List<UserTag> userTags = findOrCreateUserTags(tags, user.getUserId());
        userTags.forEach(ut -> updateUserPreference(ut, userPreference, true));

        List<UserTag> userTagsToUpdateToFalse = findUserTagsToUpdateToFalse(user, userPreference, tags);
        userTagsToUpdateToFalse.forEach(ut -> updateUserPreference(ut, userPreference, false));
        userTags.addAll(userTagsToUpdateToFalse);

        userTagRepository.saveAll(userTags);
    }

    private void updateUserPreference(UserTag userTag, UserPreference userPreference, boolean active) {
        if (userPreference.equals(UserPreference.EXPLICIT)) {
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

    private List<UserTag> findUserTagsToUpdateToFalse(User user, UserPreference userPreference, List<Tag> tags) {
        List<UserTag> existingUserTagsByType = UserPreference.EXPLICIT.equals(userPreference)
                ? userTagRepository.findByUserIdAndExplicitRecommendation(user.getUserId(), true)
                : userTagRepository.findByUserIdAndIgnored(user.getUserId(), true);
        List<Long> tagsIds = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        return existingUserTagsByType.stream()
                .filter(ut -> !tagsIds.contains(ut.getTagId()))
                .collect(Collectors.toList());
    }
}

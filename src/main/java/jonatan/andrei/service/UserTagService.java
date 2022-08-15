package jonatan.andrei.service;

import jonatan.andrei.factory.UserTagFactory;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserTag;
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

    public void updateQuestionViews(List<User> users, List<QuestionTag> tags) {
        List<Long> tagsIds = tags.stream().map(QuestionTag::getTagId).collect(Collectors.toList());
        for (User user : users) {
            List<UserTag> userTags = userTagRepository.findByUserIdAndTagIdIn(user.getUserId(), tagsIds);
            for (Long tag : tagsIds) {
                UserTag userTag = findOrCreateUserTag(userTags, user.getUserId(), tag);
                userTag.setNumberQuestionsViewed(userTag.getNumberQuestionsViewed() + 1);
            }
            userTagRepository.saveAll(userTags);
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
}

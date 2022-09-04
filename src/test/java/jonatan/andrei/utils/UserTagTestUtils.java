package jonatan.andrei.utils;

import jonatan.andrei.factory.UserTagFactory;
import jonatan.andrei.model.Tag;
import jonatan.andrei.model.User;
import jonatan.andrei.model.UserTag;
import jonatan.andrei.repository.UserTagRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;

@ApplicationScoped
public class UserTagTestUtils {

    @Inject
    UserTagRepository userTagRepository;

    public UserTag save(UserTag userTag) {
        return userTagRepository.save(userTag);
    }

    public UserTag build(User user, Tag tag) {
        return UserTagFactory.newUserTag(user.getUserId(), tag.getTagId());
    }

    public UserTag save(User user, Tag tag) {
        return userTagRepository.save(build(user, tag));
    }

    public UserTag saveWithQuestionViews(User user, Tag tag, BigDecimal questionViews) {
        UserTag userTag = build(user, tag);
        userTag.setNumberQuestionsViewed(questionViews);
        return save(userTag);
    }
}

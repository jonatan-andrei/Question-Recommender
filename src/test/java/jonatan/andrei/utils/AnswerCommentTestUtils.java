package jonatan.andrei.utils;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.model.AnswerComment;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.AnswerCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class AnswerCommentTestUtils {

    @Inject
    AnswerCommentRepository answerCommentRepository;

    @Inject
    UserTestUtils userTestUtils;

    public AnswerComment saveWithIntegrationPostIdAndAnswerId(String integrationPostId, Long answerId) {
        User user = userTestUtils.saveWithIntegrationUserId(integrationPostId);
        return saveWithIntegrationPostIdAndAnswerIdAndUserId(integrationPostId, answerId, user.getUserId());
    }

    public AnswerComment saveWithIntegrationPostIdAndAnswerIdAndUserId(String integrationPostId, Long answerId, Long userId) {
        return answerCommentRepository.save(AnswerComment.builder()
                .answerId(answerId)
                .content("Answer Comment content")
                .integrationPostId(integrationPostId)
                .publicationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .postType(PostType.ANSWER_COMMENT)
                .userId(userId)
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build());
    }
}

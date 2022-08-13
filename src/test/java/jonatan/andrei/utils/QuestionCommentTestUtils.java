package jonatan.andrei.utils;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.QuestionComment;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionCommentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class QuestionCommentTestUtils {

    @Inject
    QuestionCommentRepository questionCommentRepository;

    @Inject
    UserTestUtils userTestUtils;

    public QuestionComment saveWithIntegrationPostIdAndQuestionId(String integrationPostId, Long questionId) {
        User user = userTestUtils.saveWithIntegrationUserId(integrationPostId);
        return questionCommentRepository.save(QuestionComment.builder()
                .questionId(questionId)
                .content("Question Comment content")
                .integrationPostId(integrationPostId)
                .publicationDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .postType(PostType.QUESTION_COMMENT)
                .userId(user.getUserId())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build());
    }
}

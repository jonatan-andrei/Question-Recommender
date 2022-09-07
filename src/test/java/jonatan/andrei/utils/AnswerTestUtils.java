package jonatan.andrei.utils;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.model.Answer;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.AnswerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class AnswerTestUtils {

    @Inject
    AnswerRepository answerRepository;

    @Inject
    UserTestUtils userTestUtils;

    public Answer saveWithIntegrationPostIdAndQuestionId(String integrationPostId, Long questionId) {
        User user = userTestUtils.saveWithIntegrationUserId(integrationPostId);
        return saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate(integrationPostId, questionId, user.getUserId(), LocalDateTime.now());
    }

    public Answer saveWithIntegrationPostIdAndQuestionIdAndUserIdAndPublicationDate(String integrationPostId, Long questionId, Long userId, LocalDateTime publicationDate) {
        return answerRepository.save(Answer.builder()
                .questionId(questionId)
                .bestAnswer(false)
                .content("Answer content")
                .integrationPostId(integrationPostId)
                .publicationDate(publicationDate)
                .integrationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .postType(PostType.ANSWER)
                .userId(userId)
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .build());
    }
}

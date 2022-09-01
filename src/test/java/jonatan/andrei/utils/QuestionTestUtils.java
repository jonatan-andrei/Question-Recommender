package jonatan.andrei.utils;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

@ApplicationScoped
public class QuestionTestUtils {

    @Inject
    QuestionRepository questionRepository;

    @Inject
    UserTestUtils userTestUtils;

    public Question save() {
        return saveWithIntegrationPostId("1");
    }

    public Question saveWithIntegrationPostId(String integrationPostId) {
        return saveWithIntegrationPostIdAndPublicationDate(integrationPostId, LocalDateTime.now());
    }

    public Question saveWithIntegrationPostIdAndPublicationDate(String integrationPostId, LocalDateTime publicationDate) {
        User user = userTestUtils.saveWithIntegrationUserId(integrationPostId);
        return questionRepository.save(Question.builder()
                .integrationPostId(integrationPostId)
                .postType(PostType.QUESTION)
                .userId(user.getUserId())
                .publicationDate(publicationDate)
                .updateDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .views(0)
                .title("Question title")
                .description("Question description")
                .followers(0)
                .answers(0)
                .build());
    }
}

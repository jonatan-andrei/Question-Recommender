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
        User user = userTestUtils.save();
        return questionRepository.save(Question.builder()
                .integrationPostId("1")
                .postType(PostType.QUESTION)
                .userId(user.getUserId())
                .publicationDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .integrationDate(LocalDateTime.now())
                .hidden(false)
                .upvotes(0)
                .downvotes(0)
                .views(0)
                .title("Question title")
                .description("Question description")
                .followers(0)
                .duplicateQuestion(false)
                .build());
    }
}

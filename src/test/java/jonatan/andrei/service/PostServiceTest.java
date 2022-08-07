package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.HidePostRequestDto;
import jonatan.andrei.model.Question;
import jonatan.andrei.repository.PostRepository;
import jonatan.andrei.repository.QuestionRepository;
import jonatan.andrei.utils.QuestionTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
@TestTransaction
public class PostServiceTest {

    @Inject
    PostService postService;

    @Inject
    PostRepository postRepository;

    @Inject
    QuestionTestUtils questionTestUtils;

    @Inject
    QuestionRepository questionRepository;

    @Test
    public void hideOrExposePost_hidePost() {
        // Arrange
        Question question = questionTestUtils.save();
        HidePostRequestDto hidePostRequestDto = HidePostRequestDto.builder()
                .integrationPostId(question.getIntegrationPostId())
                .hidden(true)
                .build();

        // Act
        postService.hideOrExposePost(hidePostRequestDto);

        // Assert
        Question updatedQuestion = questionRepository.findById(question.getPostId()).get();
        Assertions.assertTrue(updatedQuestion.isHidden());
    }

}

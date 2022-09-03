package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.domain.QuestionViewType;
import jonatan.andrei.factory.QuestionViewFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionView;
import jonatan.andrei.model.User;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class QuestionViewServiceTest extends AbstractServiceTest {

    @Inject
    QuestionViewService questionViewService;

    @Test
    public void registerQuestionViews() {
        // Arrange
        Question question = questionTestUtils.saveWithIntegrationPostId("A");
        User user1 = userTestUtils.saveWithIntegrationUserId("1");
        questionViewRepository.save(QuestionViewFactory.newQuestionView(question.getPostId(), user1.getUserId()));
        User user2 = userTestUtils.saveWithIntegrationUserId("2");
        User user3 = userTestUtils.saveWithIntegrationUserId("3");
        QuestionView questionViewUser3 = questionViewRepository.save(QuestionViewFactory.newQuestionView(question.getPostId(), user3.getUserId()));
        questionViewUser3.setNumberOfViews(2);
        questionViewRepository.save(questionViewUser3);
        User user4 = userTestUtils.saveWithIntegrationUserId("4");
        List<User> users = asList(user1, user2, user3, user4);

        // Act
        questionViewService.registerQuestionViews(question, users, QuestionViewType.VIEW);
        entityManager.flush();
        entityManager.clear();

        // Assert
        QuestionView questionViewUser1 = questionViewRepository.findByQuestionIdAndUserId(question.getPostId(), user1.getUserId());
        assertEquals(1, questionViewUser1.getNumberOfViews());
        QuestionView questionViewUser2 = questionViewRepository.findByQuestionIdAndUserId(question.getPostId(), user2.getUserId());
        assertEquals(1, questionViewUser2.getNumberOfViews());
        questionViewUser3 = questionViewRepository.findByQuestionIdAndUserId(question.getPostId(), user3.getUserId());
        assertEquals(3, questionViewUser3.getNumberOfViews());
        QuestionView questionViewUser4 = questionViewRepository.findByQuestionIdAndUserId(question.getPostId(), user4.getUserId());
        assertEquals(1, questionViewUser4.getNumberOfViews());
    }

    @Test
    public void registerQuestionsViewInList() {
        // Arrange
        User user = userTestUtils.saveWithIntegrationUserId("A");
        Question question1 = questionTestUtils.saveWithIntegrationPostId("1");
        questionViewRepository.save(QuestionViewFactory.newQuestionView(question1.getPostId(), user.getUserId()));
        Question question2 = questionTestUtils.saveWithIntegrationPostId("2");
        Question question3 = questionTestUtils.saveWithIntegrationPostId("3");
        QuestionView questionViewQuestion3 = questionViewRepository.save(QuestionViewFactory.newQuestionView(question3.getPostId(), user.getUserId()));
        questionViewQuestion3.setNumberOfRecommendationsInList(3);
        questionViewRepository.save(questionViewQuestion3);
        Question question4 = questionTestUtils.saveWithIntegrationPostId("4");
        List<Long> questions = asList(question1.getPostId(), question2.getPostId(), question3.getPostId(), question4.getPostId());

        // Act
        questionViewService.registerQuestionsViewInList(questions, user.getUserId(), QuestionViewType.VIEW_IN_LIST);
        entityManager.flush();
        entityManager.clear();

        // Assert
        QuestionView questionViewQuestion1 = questionViewRepository.findByQuestionIdAndUserId(question1.getPostId(), user.getUserId());
        assertEquals(1, questionViewQuestion1.getNumberOfRecommendationsInList());
        QuestionView questionViewQuestion2 = questionViewRepository.findByQuestionIdAndUserId(question2.getPostId(), user.getUserId());
        assertEquals(1, questionViewQuestion2.getNumberOfRecommendationsInList());
        questionViewQuestion3 = questionViewRepository.findByQuestionIdAndUserId(question3.getPostId(), user.getUserId());
        assertEquals(4, questionViewQuestion3.getNumberOfRecommendationsInList());
        QuestionView questionViewQuestion4 = questionViewRepository.findByQuestionIdAndUserId(question4.getPostId(), user.getUserId());
        assertEquals(1, questionViewQuestion4.getNumberOfRecommendationsInList());
    }
}

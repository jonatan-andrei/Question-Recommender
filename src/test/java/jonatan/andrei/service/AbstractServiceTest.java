package jonatan.andrei.service;

import io.quarkus.test.junit.QuarkusMock;
import jonatan.andrei.dto.VoteRequestDto;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.repository.*;
import jonatan.andrei.utils.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class AbstractServiceTest {

    @Inject
    QuestionTestUtils questionTestUtils;

    @Inject
    UserTestUtils userTestUtils;

    @Inject
    CategoryTestUtils categoryTestUtils;

    @Inject
    TagTestUtils tagTestUtils;

    @Inject
    AnswerTestUtils answerTestUtils;

    @Inject
    QuestionCommentTestUtils questionCommentTestUtils;

    @Inject
    AnswerCommentTestUtils answerCommentTestUtils;

    @Inject
    UserCategoryTestUtils userCategoryTestUtils;

    @Inject
    UserTagTestUtils userTagTestUtils;

    @Inject
    QuestionCategoryTestUtils questionCategoryTestUtils;

    @Inject
    QuestionTagTestUtils questionTagTestUtils;

    @Inject
    UserRepository userRepository;

    @Inject
    PostRepository postRepository;

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnswerRepository answerRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    TagRepository tagRepository;

    @Inject
    UserCategoryRepository userCategoryRepository;

    @Inject
    UserTagRepository userTagRepository;

    @Inject
    QuestionCategoryRepository questionCategoryRepository;

    @Inject
    QuestionTagRepository questionTagRepository;

    @Inject
    UserFollowerRepository userFollowerRepository;

    @Inject
    QuestionFollowerRepository questionFollowerRepository;

    @Inject
    VoteRepository voteRepository;

    @Inject
    RecommendedListRepository recommendedListRepository;

    @Inject
    RecommendedListPageRepository recommendedListPageRepository;

    @Inject
    RecommendedListPageQuestionRepository recommendedListPageQuestionRepository;

    @Inject
    QuestionViewRepository questionViewRepository;

    @Inject
    TestResultRepository testResultRepository;

    @Inject
    TestResultUserRepository testResultUserRepository;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Inject
    EntityManager entityManager;
}

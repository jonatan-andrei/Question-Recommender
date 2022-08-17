package jonatan.andrei.service;

import jonatan.andrei.dto.VoteRequestDto;
import jonatan.andrei.repository.*;
import jonatan.andrei.utils.*;

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
    UserCategoryTestUtils userCategoryTestUtils;

    @Inject
    UserTagTestUtils userTagTestUtils;

    @Inject
    QuestionCategoryTestUtils questionCategoryTestUtils;

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
    EntityManager entityManager;
}

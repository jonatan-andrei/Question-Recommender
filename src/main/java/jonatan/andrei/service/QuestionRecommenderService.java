package jonatan.andrei.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class QuestionRecommenderService {

    @Inject
    AnswerCommentService answerCommentService;

    @Inject
    AnswerService answerService;

    @Inject
    CategoryService categoryService;

    @Inject
    QuestionCategoryService questionCategoryService;

    @Inject
    QuestionFollowerService questionFollowerService;

    @Inject
    QuestionService questionService;

    @Inject
    QuestionCommentService questionCommentService;

    @Inject
    QuestionTagService questionTagService;

    @Inject
    RecommendedListPageQuestionService recommendedListPageQuestionService;

    @Inject
    RecommendedListPageService recommendedListPageService;

    @Inject
    RecommendedListService recommendedListService;

    @Inject
    TagService tagService;

    @Inject
    UserCategoryService userCategoryService;

    @Inject
    UserFollowerService userFollowerService;

    @Inject
    UserService userService;

    @Inject
    UserTagService userTagService;

    @Inject
    VoteService voteService;

    @Inject
    QuestionViewService questionViewService;

    @Inject
    TotalActivitySystemService totalActivitySystemService;

    @Transactional
    public void clear() {
        recommendedListPageQuestionService.clear();
        recommendedListPageService.clear();
        recommendedListService.clear();
        voteService.clear();
        questionViewService.clear();
        userCategoryService.clear();
        userTagService.clear();
        questionFollowerService.clear();
        questionCategoryService.clear();
        questionTagService.clear();
        questionCommentService.clear();
        answerCommentService.clear();
        answerService.clear();
        questionService.clear();
        categoryService.clear();
        tagService.clear();
        userFollowerService.clear();
        userService.clear();
        totalActivitySystemService.clear();
    }

    @Transactional
    public void clearRecommendations() {
        recommendedListPageQuestionService.clear();
        recommendedListPageService.clear();
        recommendedListService.clear();
    }
}

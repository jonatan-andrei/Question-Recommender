package jonatan.andrei.service;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.PostRepository;
import org.springframework.util.CollectionUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@ApplicationScoped
public class PostService {

    @Inject
    PostRepository postRepository;

    @Inject
    QuestionService questionService;

    @Inject
    AnswerService answerService;

    @Inject
    QuestionCommentService questionCommentService;

    @Inject
    AnswerCommentService answerCommentService;

    @Inject
    UserService userService;

    @Transactional
    public Post save(CreatePostRequestDto createPostRequestDto) {
        validateRequiredDataToSave(createPostRequestDto);
        PostType postType = createPostRequestDto.getPostType();
        Optional<Post> post = findOptionalByIntegrationPostIdAndPostType(createPostRequestDto.getIntegrationPostId(), postType);
        if (post.isPresent()) {
            throw new InconsistentIntegratedDataException("There is already a post with integrationPostId " + post.get().getIntegrationPostId());
        }
        Post parentPost = nonNull(postType.getParentPostType())
                ? findByIntegrationPostIdAndPostType(createPostRequestDto.getIntegrationParentPostId(), postType.getParentPostType())
                : null;

        User user = userService.findUserByIntegrationUserIdOrCreateByAnonymousId(createPostRequestDto.getIntegrationUserId(), createPostRequestDto.getIntegrationAnonymousUserId());

        return switch (createPostRequestDto.getPostType()) {
            case QUESTION -> questionService.save(createPostRequestDto, user);
            case ANSWER -> answerService.save(createPostRequestDto, user, (Question) parentPost);
            case QUESTION_COMMENT -> questionCommentService.save(createPostRequestDto, user, (Question) parentPost);
            case ANSWER_COMMENT -> answerCommentService.save(createPostRequestDto, user, (Answer) parentPost);
        };
    }

    @Transactional
    public List<Post> save(List<CreatePostRequestDto> posts) {
        return posts.stream().map(p -> save(p)).collect(Collectors.toList());
    }

    @Transactional
    public Post update(UpdatePostRequestDto updatePostRequestDto) {
        validateRequiredDataToUpdate(updatePostRequestDto);
        Post post = findByIntegrationPostIdAndPostType(updatePostRequestDto.getIntegrationPostId(), updatePostRequestDto.getPostType());

        return switch (updatePostRequestDto.getPostType()) {
            case QUESTION -> questionService.update((Question) post, updatePostRequestDto);
            case ANSWER -> answerService.update((Answer) post, updatePostRequestDto);
            case QUESTION_COMMENT -> questionCommentService.update((QuestionComment) post, updatePostRequestDto);
            case ANSWER_COMMENT -> answerCommentService.update((AnswerComment) post, updatePostRequestDto);
        };
    }

    public Post findByIntegrationPostId(String integrationPostId) {
        return findOptionalByIntegrationPostId(integrationPostId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found post with integrationPostId " + integrationPostId));
    }

    public Optional<Post> findOptionalByIntegrationPostId(String integrationPostId) {
        return postRepository.findByIntegrationPostId(integrationPostId);
    }

    public Post findByIntegrationPostIdAndPostType(String integrationPostId, PostType postType) {
        return findOptionalByIntegrationPostIdAndPostType(integrationPostId, postType)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found post with integrationPostId " + integrationPostId));
    }

    public Optional<Post> findOptionalByIntegrationPostIdAndPostType(String integrationPostId, PostType postType) {
        return postRepository.findByIntegrationPostIdAndPostType(integrationPostId, postType);
    }

    @Transactional
    public void registerViews(ViewsRequestDto viewsRequestDto) {
        Question question = (Question) findByIntegrationPostIdAndPostType(viewsRequestDto.getIntegrationQuestionId(), PostType.QUESTION);
        question.setViews(viewsRequestDto.getTotalViews());
        postRepository.save(question);
        List<QuestionTag> tags = questionService.findQuestionTags(question.getPostId());
        List<QuestionCategory> categories = questionService.findQuestionCategories(question.getPostId());
        List<User> users = userService.findByIntegrationUserIdIn(viewsRequestDto.getIntegrationUsersId());
        userService.updateQuestionCategoriesViewed(users, categories);
        userService.updateQuestionTagsViewed(users, tags);
    }

    @Transactional
    public void registerBestAnswer(BestAnswerRequestDto bestAnswerRequestDto) {
        Question question = (Question) findByIntegrationPostIdAndPostType(bestAnswerRequestDto.getIntegrationQuestionId(), PostType.QUESTION);
        Answer answer = (Answer) findByIntegrationPostIdAndPostType(bestAnswerRequestDto.getIntegrationAnswerId(), PostType.ANSWER);
        answerService.registerBestAnswer(question, answer, bestAnswerRequestDto.isSelected());
    }

    @Transactional
    public void registerBestAnswer(List<BestAnswerRequestDto> bestAnswers) {
        bestAnswers.forEach(ba -> registerBestAnswer(ba));
    }

    @Transactional
    public void registerDuplicateQuestion(DuplicateQuestionRequestDto duplicateQuestionRequestDto) {
        Post question = findByIntegrationPostIdAndPostType(duplicateQuestionRequestDto.getIntegrationQuestionId(), PostType.QUESTION);
        Post duplicateQuestion = nonNull(duplicateQuestionRequestDto.getIntegrationDuplicateQuestionId())
                ? findByIntegrationPostIdAndPostType(duplicateQuestionRequestDto.getIntegrationDuplicateQuestionId(), PostType.QUESTION)
                : null;
        questionService.registerDuplicateQuestion(question.getPostId(), Optional.ofNullable(duplicateQuestion).map(Post::getPostId).orElse(null));
    }

    @Transactional
    public void registerDuplicateQuestion(List<DuplicateQuestionRequestDto> duplicateQuestions) {
        duplicateQuestions.stream().forEach(dq -> registerDuplicateQuestion(duplicateQuestions));
    }

    @Transactional
    public void hideOrExposePost(HidePostRequestDto hidePostRequestDto) {
        if (isNull(hidePostRequestDto.getIntegrationPostId())) {
            throw new RequiredDataException("Attribute 'integrationPostId' is required");
        }

        Post post = findByIntegrationPostId(hidePostRequestDto.getIntegrationPostId());
        postRepository.hideOrExposePost(post.getPostId(), hidePostRequestDto.isHidden());
    }

    @Transactional
    public void registerVote(VoteRequestDto voteRequestDto) {
        // Verifica se usuário já votou, se sim exclui e se não for do tipo REMOVED insere de novo
        // Atualiza número de votos no post
    }

    @Transactional
    public void registerVote(List<VoteRequestDto> votes) {
        votes.forEach(v -> registerVote(v));
    }

    @Transactional
    public void registerQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto) {
        // Adiciona ou remove
        // Valida se usuário e pergunta existem
        // Atualiza o número de seguidores na pergunta
    }

    @Transactional
    public void registerQuestionFollower(List<QuestionFollowerRequestDto> questionFollowers) {
        questionFollowers.forEach(qf -> registerQuestionFollower(qf));
    }

    private void validateRequiredDataToSave(CreatePostRequestDto createPostRequestDto) {
        validateIntegrationPostIdInformed(createPostRequestDto.getIntegrationPostId());
        validatePostTypeInformed(createPostRequestDto.getPostType());
        validateTitleInformed(createPostRequestDto.getPostType(), createPostRequestDto.getTitle());
        validateContentOrDescriptionInformed(createPostRequestDto.getPostType(), createPostRequestDto.getContentOrDescription());
        validateCategoriesOrTagsInformed(createPostRequestDto.getPostType(), createPostRequestDto.getIntegrationCategoriesIds(), createPostRequestDto.getTags());
        validateIntegrationParentPostIdInformed(createPostRequestDto.getPostType(), createPostRequestDto.getIntegrationParentPostId());

    }

    private void validateRequiredDataToUpdate(UpdatePostRequestDto updatePostRequestDto) {
        validateIntegrationPostIdInformed(updatePostRequestDto.getIntegrationPostId());
        validatePostTypeInformed(updatePostRequestDto.getPostType());
        validateTitleInformed(updatePostRequestDto.getPostType(), updatePostRequestDto.getTitle());
        validateContentOrDescriptionInformed(updatePostRequestDto.getPostType(), updatePostRequestDto.getContentOrDescription());
        validateCategoriesOrTagsInformed(updatePostRequestDto.getPostType(), updatePostRequestDto.getIntegrationCategoriesIds(), updatePostRequestDto.getTags());
    }

    private void validateIntegrationPostIdInformed(String integrationPostId) {
        if (isNull(integrationPostId)) {
            throw new RequiredDataException("Attribute 'integrationPostId' is required");
        }
    }

    private void validateIntegrationParentPostIdInformed(PostType postType, String integrationParentPostId) {
        if (!postType.equals(PostType.QUESTION) && isNull(integrationParentPostId)) {
            throw new RequiredDataException("Attribute 'integrationParentPostId' is required to postType " + postType);
        }
    }

    private void validatePostTypeInformed(PostType postType) {
        if (isNull(postType)) {
            throw new RequiredDataException("Attribute 'postType' is required");
        }
    }

    private void validateTitleInformed(PostType postType, String title) {
        if (postType.equals(PostType.QUESTION) && isNull(title)) {
            throw new RequiredDataException("Attribute 'title' is required to postType " + postType);
        }
    }

    private void validateContentOrDescriptionInformed(PostType postType, String contentOrDescription) {
        if (!postType.equals(PostType.QUESTION) && isNull(contentOrDescription)) {
            throw new RequiredDataException("Attribute 'contentOrDescription' is required to postType " + postType);
        }
    }

    private void validateCategoriesOrTagsInformed(PostType postType, List<String> integrationCategoriesIds, List<String> tags) {
        if (postType.equals(PostType.QUESTION)
                && CollectionUtils.isEmpty(integrationCategoriesIds)
                && CollectionUtils.isEmpty(tags)) {
            throw new RequiredDataException("At least one of the fields must be informed: integrationCategoriesIds or tags");
        }
    }
}

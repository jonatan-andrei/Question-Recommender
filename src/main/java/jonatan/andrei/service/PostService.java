package jonatan.andrei.service;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.*;
import jonatan.andrei.repository.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Post save(CreatePostRequestDto createPostRequestDto) {
        // Validar informações obrigatórias
        Optional<Post> post = findOptionalByIntegrationPostIdAndPostType(createPostRequestDto.getIntegrationPostId(), createPostRequestDto.getPostType());
        if (post.isPresent()) {
            throw new InconsistentIntegratedDataException("There is already a post with integrationPostId " + post.get().getIntegrationPostId());
        }

        // Se integrationUserId não for nulo, validar se existe. Se for nulo validar sessionUserId não nulo
        // Criar usuário com sessionUserId se não existir
        User user = null;

        return switch (createPostRequestDto.getPostType()) {
            case QUESTION -> questionService.save(createPostRequestDto, user);
            case ANSWER -> answerService.save(createPostRequestDto, user);
            case QUESTION_COMMENT -> questionCommentService.save(createPostRequestDto, user);
            case ANSWER_COMMENT -> answerCommentService.save(createPostRequestDto, user);
        };
    }

    @Transactional
    public Post update(UpdatePostRequestDto updatePostRequestDto) {
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

    }

    @Transactional
    public void registerBestAnswer(BestAnswerRequestDto bestAnswerRequestDto) {
        // Verifica se pergunta existe
        // Verifica se já existe uma melhor resposta para a pergunta (caso seja true o selected)
        // Verifica se resposta existe
        // Verifica se é resposta da pergunta
        answerService.registerBestAnswer(null, bestAnswerRequestDto.isSelected());
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

    }

    @Transactional
    public void registerQuestionFollower(QuestionFollowerRequestDto questionFollowerRequestDto) {
        // Adiciona ou remove
        // Valida se usuário e pergunta existem
        // Atualiza o número de seguidores na pergunta
    }
}

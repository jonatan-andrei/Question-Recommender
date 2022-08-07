package jonatan.andrei.service;

import jonatan.andrei.domain.PostType;
import jonatan.andrei.dto.*;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.exception.RequiredDataException;
import jonatan.andrei.model.Post;
import jonatan.andrei.repository.PostRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
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

    @Transactional
    public Post save(CreatePostRequestDto createPostRequestDto) {
        return null;
    }

    @Transactional
    public Post update(UpdatePostRequestDto updatePostRequestDto) {
        return null;
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
    public void hideOrExposePost(HidePostRequestDto hidePostRequestDto) {
        if (isNull(hidePostRequestDto.getIntegrationPostId())) {
            throw new RequiredDataException("Attribute 'integrationPostId' is required");
        }

        Post post = findByIntegrationPostId(hidePostRequestDto.getIntegrationPostId());
        postRepository.hideOrExposePost(post.getPostId(), hidePostRequestDto.isHidden());
    }
}

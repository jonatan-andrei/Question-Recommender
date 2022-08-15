package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.factory.QuestionFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class QuestionService {

    @Inject
    QuestionRepository questionRepository;

    @Inject
    QuestionCategoryService questionCategoryService;

    @Inject
    QuestionTagService questionTagService;

    public Question save(CreatePostRequestDto createPostRequestDto, User user) {
        Question question = QuestionFactory.newQuestion(createPostRequestDto, user.getUserId());
        question = questionRepository.save(question);
        questionCategoryService.save(question, createPostRequestDto.getIntegrationCategoriesIds());
        questionTagService.save(question, createPostRequestDto.getTags());
        return question;
    }

    public Question update(Question existingQuestion, UpdatePostRequestDto updatePostRequestDto) {
        Question question = QuestionFactory.overwrite(existingQuestion, updatePostRequestDto);
        question = questionRepository.save(question);
        questionCategoryService.save(question, updatePostRequestDto.getIntegrationCategoriesIds());
        questionTagService.save(question, updatePostRequestDto.getTags());
        return question;
    }

    @Transactional
    public void registerDuplicateQuestion(Long postId, Long duplicateQuestionId) {
        questionRepository.registerDuplicateQuestion(postId, duplicateQuestionId);
    }

    public List<QuestionCategory> findQuestionCategories(Long postId) {
        return questionCategoryService.findByQuestionId(postId);
    }

    public List<QuestionTag> findQuestionTags(Long postId) {
        return questionTagService.findByQuestionId(postId);
    }
}

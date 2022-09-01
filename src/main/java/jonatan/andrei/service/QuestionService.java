package jonatan.andrei.service;

import jonatan.andrei.dto.CreatePostRequestDto;
import jonatan.andrei.dto.RecommendedQuestionOfPageDto;
import jonatan.andrei.dto.SettingsDto;
import jonatan.andrei.dto.UpdatePostRequestDto;
import jonatan.andrei.factory.QuestionFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionRepository;
import jonatan.andrei.repository.custom.QuestionCustomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class QuestionService {

    @Inject
    QuestionRepository questionRepository;

    @Inject
    QuestionCustomRepository questionCustomRepository;

    @Inject
    QuestionCategoryService questionCategoryService;

    @Inject
    QuestionTagService questionTagService;

    public Question save(CreatePostRequestDto createPostRequestDto, User user) {
        Question question = QuestionFactory.newQuestion(createPostRequestDto, user.getUserId());
        question = questionRepository.save(question);
        questionCategoryService.save(question, createPostRequestDto.getIntegrationCategoriesIds(), user);
        questionTagService.save(question, createPostRequestDto.getTags(), user);
        return question;
    }

    public List<QuestionCategory> findCategoriesByQuestionId(Long questionId) {
        return questionCategoryService.findByQuestionId(questionId);
    }

    public List<QuestionTag> findTagsByQuestionId(Long questionId) {
        return questionTagService.findByQuestionId(questionId);
    }

    public Question update(Question existingQuestion, UpdatePostRequestDto updatePostRequestDto, User user) {
        Question question = QuestionFactory.overwrite(existingQuestion, updatePostRequestDto);
        question = questionRepository.save(question);
        questionCategoryService.save(question, updatePostRequestDto.getIntegrationCategoriesIds(), user);
        questionTagService.save(question, updatePostRequestDto.getTags(), user);
        return question;
    }

    @Transactional
    public void registerDuplicateQuestion(Long postId, Long duplicateQuestionId) {
        questionRepository.registerDuplicateQuestion(postId, duplicateQuestionId);
    }

    public void registerBestAnswer(Long postId, Long bestAnswerId) {
        questionRepository.registerBestAnswer(postId, bestAnswerId);
    }

    public void updateNumberOfAnswers(Long postId) {
        questionRepository.updateNumberOfAnswers(postId);
    }

    public List<QuestionCategory> findQuestionCategories(Long postId) {
        return questionCategoryService.findByQuestionId(postId);
    }

    public List<QuestionTag> findQuestionTags(Long postId) {
        return questionTagService.findByQuestionId(postId);
    }

    public List<RecommendedQuestionOfPageDto> findRecommendedList(Long userId, Integer pageNumber, Integer lengthQuestionListPage, Long recommendedListId, SettingsDto settings, LocalDateTime dateOfRecommendations) {
        return questionCustomRepository.findRecommendedList(userId, pageNumber, lengthQuestionListPage, recommendedListId, settings, dateOfRecommendations);
    }

    public Integer countForRecommendedList(Long userId, LocalDateTime dateOfRecommendations) {
        return questionCustomRepository.countForRecommendedList(userId, dateOfRecommendations);
    }

    public void clear() {
        questionRepository.deleteAll();
    }

}

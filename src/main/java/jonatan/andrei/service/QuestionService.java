package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.*;
import jonatan.andrei.factory.QuestionFactory;
import jonatan.andrei.factory.RecommendedQuestionScoreFactory;
import jonatan.andrei.model.Question;
import jonatan.andrei.model.QuestionCategory;
import jonatan.andrei.model.QuestionTag;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.QuestionRepository;
import jonatan.andrei.repository.custom.QuestionCustomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Inject
    QuestionNotificationQueueService questionNotificationQueueService;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    public Question save(CreatePostRequestDto createPostRequestDto, User user) {
        Question question = QuestionFactory.newQuestion(createPostRequestDto, user.getUserId());
        question = questionRepository.save(question);
        questionCategoryService.save(question, createPostRequestDto.getIntegrationCategoriesIds(), user);
        questionTagService.save(question, createPostRequestDto.getTags(), user);
        questionNotificationQueueService.saveQuestionNotificationQueue(question.getPostId(), question.getIntegrationPostId(), question.getPublicationDate());
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
        questionRepository.registerDuplicateQuestion(postId, duplicateQuestionId, LocalDateTime.now());
    }

    public void registerBestAnswer(Long postId, Long bestAnswerId) {
        questionRepository.registerBestAnswer(postId, bestAnswerId, LocalDateTime.now());
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

    public List<UserToSendQuestionNotificationDto> findUsersToNotifyQuestion(Long questionId, Integer pageNumber, Integer lengthUsersList, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime minimumLastActivityDate) {
        return questionCustomRepository.findUsersToNotifyQuestion(questionId, pageNumber, lengthUsersList, recommendationSettings, minimumLastActivityDate);
    }

    public List<RecommendedQuestionOfListDto> findRecommendedList(Long userId, Integer realPageNumber, Integer pageNumber, Integer lengthQuestionList, Long recommendedListId, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime dateOfRecommendations, LocalDateTime minimumDateForRecommendedQuestions, boolean pageWithRecommendedQuestions) {
        if (pageWithRecommendedQuestions) {
            return questionCustomRepository.findRecommendedList(userId, realPageNumber, lengthQuestionList, recommendedListId, recommendationSettings, dateOfRecommendations, minimumDateForRecommendedQuestions);
        } else {
            return questionCustomRepository.findQuestionsList(userId, pageNumber, lengthQuestionList, dateOfRecommendations);
        }
    }

    public RecommendedQuestionScoreResponseDto calculateQuestionScoreToUser(Long userId, Long questionId, LocalDateTime dateOfRecommendations) {
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        RecommendedQuestionScoreDto recommendedQuestionScoreDto = questionCustomRepository.calculateQuestionScoreToUser(userId, questionId, recommendationSettings, dateOfRecommendations);
        List<QuestionTagScoreDto> tagsScore = questionCustomRepository.calculateQuestionTagsScoreToUser(userId, questionId, recommendationSettings);
        return RecommendedQuestionScoreFactory.newResponseDto(recommendedQuestionScoreDto, tagsScore);
    }

    public Integer countForRecommendedList(Long userId, LocalDateTime dateOfRecommendations) {
        return questionCustomRepository.countForRecommendedList(userId, dateOfRecommendations);
    }

    public LocalDateTime findMinimumDateForRecommendedQuestions(Long userId, LocalDateTime dateOfRecommendations, Integer maximumQuestions) {
        return questionCustomRepository.findMinimumDateForRecommendedQuestions(userId, dateOfRecommendations, maximumQuestions);
    }

    public void clear() {
        questionRepository.deleteAll();
    }

}

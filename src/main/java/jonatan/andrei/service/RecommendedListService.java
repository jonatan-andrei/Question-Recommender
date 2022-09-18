package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.RecommendedListFactory;
import jonatan.andrei.model.RecommendedList;
import jonatan.andrei.model.User;
import jonatan.andrei.repository.RecommendedListRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;

import static java.util.Objects.isNull;
import static jonatan.andrei.domain.RecommendationSettingsType.DEFAULT_LENGTH;

@ApplicationScoped
public class RecommendedListService {

    @Inject
    RecommendedListRepository recommendedListRepository;

    @Inject
    UserService userService;

    @Inject
    QuestionService questionService;

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @Inject
    RecommendedListPageService recommendedListPageService;

    @Transactional
    public RecommendedListResponseDto findRecommendedList(Integer lengthQuestionListPage,
                                                          String integrationUserId,
                                                          Long recommendedListId,
                                                          Integer pageNumber,
                                                          LocalDateTime dateOfRecommendations) {
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = recommendationSettingsService.findRecommendationSettingsByChannel(RecommendationChannelType.RECOMMENDED_LIST);
        User user = userService.findByIntegrationUserId(integrationUserId);
        lengthQuestionListPage = isNull(lengthQuestionListPage) ? recommendationSettings.get(DEFAULT_LENGTH).intValue() : lengthQuestionListPage;
        dateOfRecommendations = isNull(dateOfRecommendations) ? LocalDateTime.now() : dateOfRecommendations;
        RecommendedList recommendedList = isNull(recommendedListId)
                ? createRecommendedList(lengthQuestionListPage, user.getUserId(), dateOfRecommendations)
                : findByRecommendedListId(recommendedListId);

        return recommendedListPageService.findOrCreatePage(user.getUserId(), recommendedList, pageNumber, lengthQuestionListPage, recommendationSettings, dateOfRecommendations);
    }

    private RecommendedList findByRecommendedListId(Long recommendedListId) {
        return recommendedListRepository.findById(recommendedListId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found recommended list with id " + recommendedListId));
    }

    private RecommendedList createRecommendedList(Integer lengthQuestionListPage, Long userId, LocalDateTime dateOfRecommendations) {
        Integer totalQuestions = questionService.countForRecommendedList(userId, dateOfRecommendations);
        Integer totalPages = calculateTotalNumberOfPages(totalQuestions, lengthQuestionListPage);
        return recommendedListRepository.save(RecommendedListFactory.newRecommendedList(
                lengthQuestionListPage, userId, totalPages, totalQuestions, dateOfRecommendations));
    }

    private Integer calculateTotalNumberOfPages(Integer totalQuestions, Integer lengthQuestionListPage) {
        if (totalQuestions.equals(0)) {
            return 1;
        }
        return new BigDecimal(totalQuestions).divide(new BigDecimal(lengthQuestionListPage)).setScale(0, RoundingMode.CEILING).intValue();
    }

    public void clear() {
        recommendedListRepository.deleteAll();
    }
}

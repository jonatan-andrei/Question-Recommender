package jonatan.andrei.service;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.dto.SettingsDto;
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

import static java.util.Objects.isNull;

@ApplicationScoped
public class RecommendedListService {

    @Inject
    RecommendedListRepository recommendedListRepository;

    @Inject
    UserService userService;

    @Inject
    QuestionService questionService;

    @Inject
    SettingsService settingsService;

    @Inject
    RecommendedListPageService recommendedListPageService;

    @Transactional
    public RecommendedListResponseDto findRecommendedList(Integer lengthQuestionListPage,
                                                          String integrationUserId,
                                                          Long recommendedListId,
                                                          Integer pageNumber,
                                                          LocalDateTime dateOfRecommendations) {
        SettingsDto settings = settingsService.getSettings();
        User user = userService.findByIntegrationUserId(integrationUserId);
        lengthQuestionListPage = isNull(lengthQuestionListPage) ? settings.getDefaultLengthQuestionListPage() : lengthQuestionListPage;
        dateOfRecommendations = isNull(dateOfRecommendations) ? LocalDateTime.now() : dateOfRecommendations;
        RecommendedList recommendedList = isNull(recommendedListId)
                ? createRecommendedList(lengthQuestionListPage, user.getUserId(), dateOfRecommendations)
                : findByRecommendedListId(recommendedListId);

        return recommendedListPageService.findOrCreatePage(user.getUserId(), recommendedList, pageNumber, lengthQuestionListPage, settings, dateOfRecommendations);
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

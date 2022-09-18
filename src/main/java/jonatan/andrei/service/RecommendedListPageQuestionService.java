package jonatan.andrei.service;

import jonatan.andrei.domain.QuestionViewType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.factory.RecommendedListPageFactory;
import jonatan.andrei.model.RecommendedListPage;
import jonatan.andrei.model.RecommendedListPageQuestion;
import jonatan.andrei.repository.RecommendedListPageQuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecommendedListPageQuestionService {

    @Inject
    RecommendedListPageQuestionRepository recommendedListPageQuestionRepository;

    @Inject
    QuestionService questionService;

    @Inject
    QuestionViewService questionViewService;

    public List<RecommendedListResponseDto.RecommendedQuestionResponseDto> findByRecommendedListPageId(Long recommendedListPageId) {
        return recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPageId)
                .stream().map(RecommendedListPageFactory::newDto)
                .collect(Collectors.toList());
    }

    public List<RecommendedListResponseDto.RecommendedQuestionResponseDto> newPage(Long userId, RecommendedListPage recommendedListPage, Integer lengthQuestionListPage, Integer realPageNumber, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime dateOfRecommendations) {
        List<RecommendedListPageQuestion> recommendedQuestions = questionService.findRecommendedList(userId, realPageNumber,
                        lengthQuestionListPage, recommendedListPage.getRecommendedListId(), recommendationSettings, dateOfRecommendations)
                .stream().map(rq -> RecommendedListPageFactory.newRecommendedQuestion(rq, recommendedListPage.getRecommendedListPageId()))
                .collect(Collectors.toList());
        questionViewService.registerQuestionsViewInList(recommendedQuestions.stream().map(RecommendedListPageQuestion::getQuestionId).collect(Collectors.toList()), userId, QuestionViewType.VIEW_IN_LIST);
        recommendedListPageQuestionRepository.saveAll(recommendedQuestions);
        return recommendedQuestions.stream()
                .map(rq -> RecommendedListResponseDto.RecommendedQuestionResponseDto.builder()
                        .integrationQuestionId(rq.getIntegrationQuestionId())
                        .score(rq.getScore())
                        .build())
                .collect(Collectors.toList());
    }

    public void clear() {
        recommendedListPageQuestionRepository.deleteAll();
    }

}

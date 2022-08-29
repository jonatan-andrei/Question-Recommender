package jonatan.andrei.service;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.factory.RecommendedListPageFactory;
import jonatan.andrei.model.RecommendedListPageQuestion;
import jonatan.andrei.repository.RecommendedListPageQuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecommendedListPageQuestionService {

    @Inject
    RecommendedListPageQuestionRepository recommendedListPageQuestionRepository;

    @Inject
    QuestionService questionService;

    public List<RecommendedListResponseDto.RecommendedQuestionResponseDto> findByRecommendedListPageId(Long recommendedListPageId) {
        return recommendedListPageQuestionRepository.findByRecommendedListPageId(recommendedListPageId)
                .stream().map(RecommendedListPageFactory::newDto)
                .collect(Collectors.toList());
    }

    public List<RecommendedListResponseDto.RecommendedQuestionResponseDto> newPage(Long recommendedListPageId) {
        List<RecommendedListPageQuestion> recommendedQuestions = questionService.findRecommendedList()
                .stream().map(rq -> RecommendedListPageFactory.newRecommendedQuestion(rq, recommendedListPageId))
                .collect(Collectors.toList());
        recommendedListPageQuestionRepository.saveAll(recommendedQuestions);
        return recommendedQuestions.stream()
                .map(rq -> RecommendedListResponseDto.RecommendedQuestionResponseDto.builder()
                        .integrationQuestionId(rq.getIntegrationQuestionId())
                        .score(rq.getScore())
                        .build())
                .collect(Collectors.toList());
    }

}

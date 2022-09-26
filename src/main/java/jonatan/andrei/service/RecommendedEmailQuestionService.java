package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.factory.RecommendedEmailQuestionFactory;
import jonatan.andrei.model.RecommendedEmailQuestion;
import jonatan.andrei.repository.RecommendedEmailQuestionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecommendedEmailQuestionService {

    @Inject
    QuestionService questionService;

    @Inject
    RecommendedEmailQuestionRepository recommendedEmailQuestionRepository;

    public List<RecommendedQuestionOfListDto> newEmail(Long userId, Integer lengthQuestionListEmail, Map<RecommendationSettingsType, BigDecimal> recommendationSettings, LocalDateTime dateOfRecommendations) {
        return questionService.findRecommendedList(userId, 1,
                lengthQuestionListEmail, null, recommendationSettings, dateOfRecommendations);
    }

    public void save(List<RecommendedQuestionOfListDto> questionsDto, Long recommendedEmailId) {
        List<RecommendedEmailQuestion> questions = questionsDto.stream()
                .map(q -> RecommendedEmailQuestionFactory.newRecommendedEmailQuestion(q, recommendedEmailId))
                .collect(Collectors.toList());
        recommendedEmailQuestionRepository.saveAll(questions);
    }
}

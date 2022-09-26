package jonatan.andrei.factory;

import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.model.RecommendedEmailQuestion;

public class RecommendedEmailQuestionFactory {

    public static RecommendedEmailQuestion newRecommendedEmailQuestion(RecommendedQuestionOfListDto dto, Long recommendedEmailId) {
        return RecommendedEmailQuestion.builder()
                .recommendedEmailId(recommendedEmailId)
                .questionId(dto.getQuestionId())
                .integrationQuestionId(dto.getIntegrationQuestionId())
                .score(dto.getScore())
                .build();
    }
}

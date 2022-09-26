package jonatan.andrei.factory;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.model.RecommendedListPageQuestion;

import java.math.RoundingMode;

public class RecommendedListPageFactory {

    public static RecommendedListResponseDto.RecommendedQuestionResponseDto newDto(RecommendedListPageQuestion recommendedListPageQuestion) {
        return RecommendedListResponseDto.RecommendedQuestionResponseDto.builder()
                .integrationQuestionId(recommendedListPageQuestion.getIntegrationQuestionId())
                .score(recommendedListPageQuestion.getScore())
                .build();
    }

    public static RecommendedListPageQuestion newRecommendedQuestion(RecommendedQuestionOfListDto recommendedQuestionOfListDto, Long recommendedListPageId) {
        return RecommendedListPageQuestion.builder()
                .recommendedListPageId(recommendedListPageId)
                .questionId(recommendedQuestionOfListDto.getQuestionId())
                .integrationQuestionId(recommendedQuestionOfListDto.getIntegrationQuestionId())
                .score(recommendedQuestionOfListDto.getScore().setScale(2, RoundingMode.HALF_UP))
                .build();
    }
}

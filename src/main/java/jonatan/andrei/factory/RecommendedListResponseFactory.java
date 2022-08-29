package jonatan.andrei.factory;

import jonatan.andrei.dto.RecommendedListResponseDto;
import jonatan.andrei.model.RecommendedList;

import java.util.List;

public class RecommendedListResponseFactory {

    public static RecommendedListResponseDto newRecommendedListResponseDto(RecommendedList recommendedList, Integer pageNumber,
                                                                           List<RecommendedListResponseDto.RecommendedQuestionResponseDto> questions) {
        return RecommendedListResponseDto.builder()
                .recommendedListId(recommendedList.getRecommendedListId())
                .pageNumber(pageNumber)
                .totalNumberOfPages(recommendedList.getTotalNumberOfPages())
                .questions(questions)
                .build();
    }
}

package jonatan.andrei.factory;

import jonatan.andrei.dto.ListRecommendedEmailRequestDto;
import jonatan.andrei.dto.RecommendedQuestionOfListDto;
import jonatan.andrei.dto.UserToSendRecommendedEmailDto;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendedEmailFactory {

    public static ListRecommendedEmailRequestDto.RecommendedEmailRequestDto newDto(UserToSendRecommendedEmailDto user, List<RecommendedQuestionOfListDto> questions) {
        return ListRecommendedEmailRequestDto.RecommendedEmailRequestDto.builder()
                .integrationUserId(user.getIntegrationUserId())
                .questions(questions.stream()
                        .map(q -> ListRecommendedEmailRequestDto.RecommendedEmailRequestDto.RecommendedEmailQuestionRequestDto.builder()
                                .integrationQuestionId(q.getIntegrationQuestionId())
                                .score(q.getScore())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}

package jonatan.andrei.factory;

import jonatan.andrei.dto.QuestionTagScoreDto;
import jonatan.andrei.dto.RecommendedQuestionScoreDto;
import jonatan.andrei.dto.RecommendedQuestionScoreResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class RecommendedQuestionScoreFactory {

    public static RecommendedQuestionScoreResponseDto newResponseDto(RecommendedQuestionScoreDto dto, List<QuestionTagScoreDto> tagsScore) {
        return RecommendedQuestionScoreResponseDto.builder()
                .questionId(dto.getQuestionId())
                .integrationQuestionId(dto.getIntegrationQuestionId())
                .score(dto.getScore())
                .publicationDateRecentScore(dto.getPublicationDateRecentScore())
                .publicationDateRelevantScore(dto.getPublicationDateRelevantScore())
                .hasAnswerScore(dto.getHasAnswerScore())
                .perAnswerScore(dto.getPerAnswerScore())
                .hasBestAnswerScore(dto.getHasBestAnswerScore())
                .questionNumberViewsScore(dto.getQuestionNumberViewsScore())
                .questionNumberFollowersScore(dto.getQuestionNumberFollowersScore())
                .userAlreadyAnsweredScore(dto.getUserAlreadyAnsweredScore())
                .userAlreadyCommentedScore(dto.getUserAlreadyCommentedScore())
                .userTagScore(dto.getUserTagScore())
                .tags(tagsScore.stream()
                        .map(t -> RecommendedQuestionScoreResponseDto.QuestionTagScoreResponseDto.builder()
                                .name(t.getName())
                                .score(t.getScore())
                                .numberQuestionsAskedInTag(t.getNumberQuestionsAskedInTag())
                                .numberQuestionsAskedInUserTag(t.getNumberQuestionsAskedInUserTag())
                                .numberQuestionsAskedPercent(t.getNumberQuestionsAskedPercent())
                                .numberQuestionsAskedSystemPercent(t.getNumberQuestionsAskedSystemPercent())
                                .numberQuestionsAskedScore(t.getNumberQuestionsAskedScore())
                                .numberQuestionsAnsweredInTag(t.getNumberQuestionsAnsweredInTag())
                                .numberQuestionsAnsweredInUserTag(t.getNumberQuestionsAnsweredInUserTag())
                                .numberQuestionsAnsweredPercent(t.getNumberQuestionsAnsweredPercent())
                                .numberQuestionsAnsweredSystemPercent(t.getNumberQuestionsAnsweredSystemPercent())
                                .numberQuestionsAnsweredScore(t.getNumberQuestionsAnsweredScore())
                                .numberQuestionsCommentedInTag(t.getNumberQuestionsCommentedInTag())
                                .numberQuestionsCommentedInUserTag(t.getNumberQuestionsCommentedInUserTag())
                                .numberQuestionsCommentedPercent(t.getNumberQuestionsCommentedPercent())
                                .numberQuestionsCommentedSystemPercent(t.getNumberQuestionsCommentedSystemPercent())
                                .numberQuestionsCommentedScore(t.getNumberQuestionsCommentedScore())
                                .numberQuestionsFollowedInTag(t.getNumberQuestionsFollowedInTag())
                                .numberQuestionsFollowedInUserTag(t.getNumberQuestionsFollowedInUserTag())
                                .numberQuestionsFollowedPercent(t.getNumberQuestionsFollowedPercent())
                                .numberQuestionsFollowedSystemPercent(t.getNumberQuestionsFollowedSystemPercent())
                                .numberQuestionsFollowedScore(t.getNumberQuestionsFollowedScore())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}

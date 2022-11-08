package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedQuestionScoreResponseDto {

    private Long questionId;

    private String integrationQuestionId;

    private BigDecimal score;

    private BigDecimal publicationDateRecentScore;

    private BigDecimal publicationDateRelevantScore;

    private BigDecimal hasAnswerScore;

    private BigDecimal perAnswerScore;

    private BigDecimal hasBestAnswerScore;

    private BigDecimal questionNumberViewsScore;

    private BigDecimal questionNumberFollowersScore;

    private BigDecimal userAlreadyAnsweredScore;

    private BigDecimal userAlreadyCommentedScore;

    private BigDecimal userTagScore;

    private List<QuestionTagScoreResponseDto> tags;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionTagScoreResponseDto {

        private String name;

        private BigDecimal score;

        private BigDecimal numberQuestionsAskedInTag;

        private BigDecimal numberQuestionsAskedInUserTag;

        private BigDecimal numberQuestionsAskedPercent;

        private BigDecimal numberQuestionsAskedSystemPercent;

        private BigDecimal numberQuestionsAskedScore;

        private BigDecimal numberQuestionsAnsweredInTag;

        private BigDecimal numberQuestionsAnsweredInUserTag;

        private BigDecimal numberQuestionsAnsweredPercent;

        private BigDecimal numberQuestionsAnsweredSystemPercent;

        private BigDecimal numberQuestionsAnsweredScore;

        private BigDecimal numberQuestionsCommentedInTag;

        private BigDecimal numberQuestionsCommentedInUserTag;

        private BigDecimal numberQuestionsCommentedPercent;

        private BigDecimal numberQuestionsCommentedSystemPercent;

        private BigDecimal numberQuestionsCommentedScore;

        private BigDecimal numberQuestionsFollowedInTag;

        private BigDecimal numberQuestionsFollowedInUserTag;

        private BigDecimal numberQuestionsFollowedPercent;

        private BigDecimal numberQuestionsFollowedSystemPercent;

        private BigDecimal numberQuestionsFollowedScore;

    }
}

package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedQuestionScoreDto {

    private Long questionId;

    private String integrationQuestionId;

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

    private BigDecimal score;

}

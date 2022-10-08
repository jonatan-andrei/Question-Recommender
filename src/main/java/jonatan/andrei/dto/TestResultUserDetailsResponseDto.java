package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultUserDetailsResponseDto {

    private Long testResultUserId;

    private String integrationUserId;

    private Integer numberOfQuestions;

    private Integer numberOfRecommendedQuestions;

    private BigDecimal percentageOfCorrectRecommendations;

    private String userTags;

    private String questions;

    private String recommendedQuestions;

    private TestResultDetailsResponseDto testResult;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestResultDetailsResponseDto {

        private Long testResultId;

        private String dumpName;

        private BigDecimal integratedDumpPercentage;

        private Integer daysAfterDumpConsidered;

        private Integer numberOfUsers;

        private Integer numberOfQuestions;

        private Integer numberOfRecommendedQuestions;

        private BigDecimal percentageOfCorrectRecommendations;

        private LocalDateTime testDate;

        private String settings;

        private String totalActivitySystem;
    }

}

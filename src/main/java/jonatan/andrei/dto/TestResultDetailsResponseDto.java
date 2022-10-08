package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultDetailsResponseDto {

    private Long testResultId;

    private String dumpName;

    private BigDecimal integratedDumpPercentage;

    private Integer daysAfterDumpConsidered;

    private Integer numberOfUsers;

    private Integer numberOfQuestions;

    private Integer numberOfRecommendedQuestions;

    private BigDecimal percentageOfCorrectRecommendations;

    private LocalDateTime testDate;

    private List<TestResultUserResponseDto> users;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestResultUserResponseDto {

        private Long testResultUserId;

        private String integrationUserId;

        private Integer numberOfQuestions;

        private Integer numberOfRecommendedQuestions;

        private BigDecimal percentageOfCorrectRecommendations;

    }

}

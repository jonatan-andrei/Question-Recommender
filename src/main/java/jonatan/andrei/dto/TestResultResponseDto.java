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
public class TestResultResponseDto {

    private Long testResultId;

    private String dumpName;

    private BigDecimal integratedDumpPercentage;

    private Integer daysAfterDumpConsidered;

    private Integer numberOfUsers;

    private Integer numberOfQuestions;

    private Integer numberOfRecommendedQuestions;

    private BigDecimal percentageOfCorrectRecommendations;

    private String testDate;

}

package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResultRequestDto {

    @NotNull
    private String dumpName;

    @NotNull
    private BigDecimal integratedDumpPercentage;

    @NotNull
    private Integer daysAfterDumpConsidered;

    @NotNull
    private String settings;

    @NotNull
    private String totalActivitySystem;

    @NotNull
    private Integer numberOfUsers;

    @NotNull
    private Integer numberOfQuestions;

    @NotNull
    private Integer numberOfRecommendedQuestions;

    @NotNull
    private BigDecimal percentageOfCorrectRecommendations;

    @NotNull
    private LocalDateTime testDate;

    private List<TestResultUserRequestDto> users;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestResultUserRequestDto {

        @NotNull
        private String integrationUserId;

        @NotNull
        private Integer numberOfQuestions;

        @NotNull
        private Integer numberOfRecommendedQuestions;

        @NotNull
        private BigDecimal percentageOfCorrectRecommendations;

        @NotNull
        private boolean error;

        @NotNull
        private String userTags;

        @NotNull
        private String questions;

        @NotNull
        private String recommendedQuestions;
    }

}

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
public class TestInformationResponseDto {

    private String dumpName;

    private LocalDateTime dumpEndDate;

    private LocalDateTime endDateTestInformation;

    private Integer daysAfterPartialEndDate;

    private Integer minimumOfPreviousAnswers;

    private BigDecimal percentage;

    private List<RecommendationSettingsRequestDto> settings;
}

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
public class RecommendedListResponseDto {

    private Long recommendedListId;

    private Integer totalNumberOfPages;

    private List<RecommendedQuestionResponseDto> questions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedQuestionResponseDto {

        private String integrationQuestionId;

        private BigDecimal score;

    }

}

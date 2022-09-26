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
public class ListRecommendedEmailRequestDto {

    private List<RecommendedEmailRequestDto> emails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecommendedEmailRequestDto {

        private String integrationUserId;

        private List<RecommendedEmailQuestionRequestDto> questions;

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class RecommendedEmailQuestionRequestDto {

            private String integrationQuestionId;

            private BigDecimal score;

        }
    }
}

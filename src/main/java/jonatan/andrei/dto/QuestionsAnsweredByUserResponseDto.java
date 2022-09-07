package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAnsweredByUserResponseDto {

    private String integrationUserId;

    private List<QuestionAnsweredResponseDto> questions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnsweredResponseDto {

        private String integrationQuestionId;

    }
}

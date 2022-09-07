package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionsAnsweredByUserResponseDto {

    private String integrationUserId;

    private LocalDateTime dateFirstAnswer;

    private List<QuestionAnsweredResponseDto> questions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionAnsweredResponseDto {

        private String integrationQuestionId;

        private Integer followers;

        private Long bestAnswerId;

        private Integer answers;

        private String tags;

        private LocalDateTime answerDate;

    }
}

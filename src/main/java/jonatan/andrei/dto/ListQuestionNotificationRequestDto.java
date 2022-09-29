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
public class ListQuestionNotificationRequestDto {

    private List<QuestionNotificationRequestDto> notifications;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionNotificationRequestDto {

        private String integrationUserId;

        private String integrationQuestionId;

    }
}

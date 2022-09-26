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
public class ListNotificationRequestDto {

    private List<NotificationRequestDto> notifications;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationRequestDto {

        private String integrationUserId;

        private String integrationQuestionId;

        private BigDecimal score;

    }
}

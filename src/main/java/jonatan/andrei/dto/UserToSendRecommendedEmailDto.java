package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserToSendRecommendedEmailDto {

    private Long userId;

    private String integrationUserId;

}

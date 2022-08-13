package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotBlank
    private String integrationUserId;

    private String sessionId;

    @NotNull
    private LocalDateTime registrationDate;

    private boolean emailNotificationEnable;

    private Integer emailNotificationTime;

    private boolean notificationEnable;

    private boolean recommendationEnable;

    private List<String> explicitIntegrationCategoriesIds;

    private List<String> explicitTags;

    private List<String> ignoredIntegrationCategoriesIds;

    private List<String> ignoredTags;

}

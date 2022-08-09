package jonatan.andrei.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {

    @NotBlank
    private String integrationUserId;

    private boolean active;

    private boolean emailNotificationEnable;

    private Integer emailNotificationTime;

    private boolean notificationEnable;

    private boolean recommendationEnable;

    private List<String> explicitIntegrationCategoriesIds;

    private List<String> explicitTags;

    private List<String> ignoredIntegrationCategoriesIds;

    private List<String> ignoredTags;

}

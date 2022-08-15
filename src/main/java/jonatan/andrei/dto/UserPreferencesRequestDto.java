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
public class UserPreferencesRequestDto {

    private boolean emailNotificationEnable;

    private Integer emailNotificationHour;

    private boolean notificationEnable;

    private boolean recommendationEnable;

    private List<String> explicitIntegrationCategoriesIds;

    private List<String> explicitTags;

    private List<String> ignoredIntegrationCategoriesIds;

    private List<String> ignoredTags;

}

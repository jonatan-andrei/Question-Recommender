package jonatan.andrei.resource;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import jonatan.andrei.service.RecommendationSettingsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

@Path("/recommendation-settings")
@ApplicationScoped
public class RecommendationSettingsResource {

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @PUT
    public void save(List<RecommendationSettingsRequestDto> recommendationSettings) {
        recommendationSettingsService.save(recommendationSettings);
    }
}

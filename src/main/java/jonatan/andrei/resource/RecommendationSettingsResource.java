package jonatan.andrei.resource;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.service.RecommendationSettingsService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Map;

@Path("/recommendation-settings")
@ApplicationScoped
public class RecommendationSettingsResource {

    @Inject
    RecommendationSettingsService recommendationSettingsService;

    @POST
    public void save(Map<RecommendationSettingsType, Integer> recommendationSettings) {
        recommendationSettingsService.save(recommendationSettings);
    }
}

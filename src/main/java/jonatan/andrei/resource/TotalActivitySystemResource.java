package jonatan.andrei.resource;

import jonatan.andrei.domain.PostClassificationType;
import jonatan.andrei.model.TotalActivitySystem;
import jonatan.andrei.service.TotalActivitySystemService;
import org.springframework.http.ResponseEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/total-activity-system")
@ApplicationScoped
public class TotalActivitySystemResource {

    @Inject
    TotalActivitySystemService totalActivitySystemService;

    @GET
    public ResponseEntity<TotalActivitySystem> findByPostClassificationType(@QueryParam("postClassificationType") PostClassificationType postClassificationType) {
        return ResponseEntity.ok(totalActivitySystemService.findOrCreateByType(postClassificationType));
    }
}

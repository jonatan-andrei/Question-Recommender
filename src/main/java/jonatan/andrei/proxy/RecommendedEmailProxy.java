package jonatan.andrei.proxy;

import jonatan.andrei.dto.ListRecommendedEmailRequestDto;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient(configKey = "recommended-email-proxy")
public interface RecommendedEmailProxy {

    @POST
    @Path("/recommended-email")
    void saveRecommendedEmailList(ListRecommendedEmailRequestDto recommendedEmailRequestDtoList);

}

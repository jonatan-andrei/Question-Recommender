package jonatan.andrei.resource;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.service.TestResultService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/test-result")
@ApplicationScoped
public class TestResultResource {

    @Inject
    TestResultService testResultService;

    @POST
    public void save(TestResultRequestDto testResultRequestDto) {
        testResultService.save(testResultRequestDto);
    }
}

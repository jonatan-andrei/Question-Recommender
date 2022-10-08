package jonatan.andrei.resource;

import jonatan.andrei.dto.TestResultDetailsResponseDto;
import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.dto.TestResultResponseDto;
import jonatan.andrei.dto.TestResultUserDetailsResponseDto;
import jonatan.andrei.service.TestResultService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Path("/test-result")
@ApplicationScoped
public class TestResultResource {

    @Inject
    TestResultService testResultService;

    @POST
    public void save(TestResultRequestDto testResultRequestDto) {
        testResultService.save(testResultRequestDto);
    }

    @GET
    public List<TestResultResponseDto> findAll() {
        return testResultService.findAll();
    }

    @GET
    @Path("/{testResultId}")
    public TestResultDetailsResponseDto findByTestResultId(Long testResultId) {
        return testResultService.findByTestResultId(testResultId);
    }

    @GET
    @Path("/user/{testResultUserId}")
    public TestResultUserDetailsResponseDto findByTestResultUserId(Long testResultUserId) {
        return testResultService.findByTestResultUserId(testResultUserId);
    }

}

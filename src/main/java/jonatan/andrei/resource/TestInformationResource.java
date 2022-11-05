package jonatan.andrei.resource;

import jonatan.andrei.domain.SettingsModelType;
import jonatan.andrei.domain.TestInformation;
import jonatan.andrei.dto.TestInformationResponseDto;
import jonatan.andrei.service.TestInformationService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/test-information")
@ApplicationScoped
public class TestInformationResource {

    @Inject
    TestInformationService testInformationService;

    @GET
    public TestInformationResponseDto findTestInformation(@QueryParam("testInformation") TestInformation testInformation, @QueryParam("settingsModel") SettingsModelType settingsModel) {
        return testInformationService.findTestInformation(testInformation, settingsModel);
    }
}

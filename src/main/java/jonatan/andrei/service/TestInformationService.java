package jonatan.andrei.service;

import jonatan.andrei.domain.SettingsModelType;
import jonatan.andrei.domain.TestInformation;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import jonatan.andrei.dto.TestInformationResponseDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TestInformationService {

    @Inject
    SettingsModelService settingsModelService;

    @Transactional
    public TestInformationResponseDto findTestInformation(TestInformation testInformation, SettingsModelType settingsModel) {
        List<RecommendationSettingsRequestDto> settings = settingsModelService.findSettings(settingsModel);
        return TestInformationResponseDto.builder()
                .dumpName(testInformation.getDump().getDumpName())
                .dumpEndDate(testInformation.getDump().getEndDate())
                .endDateTestInformation(testInformation.getEndDate())
                .daysAfterPartialEndDate(testInformation.getDaysAfterPartialEndDate())
                .minimumOfPreviousAnswers(testInformation.getMinimumOfPreviousAnswers())
                .percentage(testInformation.getPercentage())
                .settings(settings)
                .settingsModel(settingsModel)
                .build();
    }
}

package jonatan.andrei.factory;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.model.TestResult;

public class TestResultFactory {

    public static TestResult newTestResult(TestResultRequestDto testResultRequestDto) {
        return TestResult.builder()
                .dumpName(testResultRequestDto.getDumpName())
                .integratedDumpPercentage(testResultRequestDto.getIntegratedDumpPercentage())
                .daysAfterDumpConsidered(testResultRequestDto.getDaysAfterDumpConsidered())
                .settings(testResultRequestDto.getSettings())
                .numberOfUsers(testResultRequestDto.getNumberOfUsers())
                .numberOfQuestions(testResultRequestDto.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResultRequestDto.getNumberOfRecommendedQuestions())
                .percentageOfCorrectRecommendations(testResultRequestDto.getPercentageOfCorrectRecommendations())
                .testDate(testResultRequestDto.getTestDate())
                .build();
    }
}

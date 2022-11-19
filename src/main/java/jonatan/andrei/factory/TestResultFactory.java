package jonatan.andrei.factory;

import jonatan.andrei.dto.TestResultDetailsResponseDto;
import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.dto.TestResultResponseDto;
import jonatan.andrei.dto.TestResultUserDetailsResponseDto;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.model.TestResultUser;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class TestResultFactory {

    public static TestResult newTestResult(TestResultRequestDto testResultRequestDto) {
        return TestResult.builder()
                .dumpName(testResultRequestDto.getDumpName())
                .integratedDumpPercentage(testResultRequestDto.getIntegratedDumpPercentage())
                .daysAfterDumpConsidered(testResultRequestDto.getDaysAfterDumpConsidered())
                .settings(testResultRequestDto.getSettings())
                .settingsModel(testResultRequestDto.getSettingsModel())
                .totalActivitySystem(testResultRequestDto.getTotalActivitySystem())
                .numberOfUsers(testResultRequestDto.getNumberOfUsers())
                .numberOfQuestions(testResultRequestDto.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResultRequestDto.getNumberOfRecommendedQuestions())
                .percentageOfCorrectRecommendations(testResultRequestDto.getPercentageOfCorrectRecommendations())
                .testDate(testResultRequestDto.getTestDate())
                .build();
    }

    public static TestResultResponseDto toDto(TestResult testResult) {
        return TestResultResponseDto.builder()
                .testResultId(testResult.getTestResultId())
                .dumpName(testResult.getDumpName())
                .integratedDumpPercentage(testResult.getIntegratedDumpPercentage())
                .daysAfterDumpConsidered(testResult.getDaysAfterDumpConsidered())
                .numberOfUsers(testResult.getNumberOfUsers())
                .numberOfQuestions(testResult.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResult.getNumberOfRecommendedQuestions())
                .percentageOfCorrectRecommendations(testResult.getPercentageOfCorrectRecommendations())
                .percentageIncreaseOfCorrectRecommendations(calculatePercentageIncreaseOfCorrectRecommendations(testResult))
                .testDate(LocalDateTimeFormatterFactory.formatLocalDateTimeToString(testResult.getTestDate()))
                .settingsModel(testResult.getSettingsModel())
                .build();
    }

    public static TestResultDetailsResponseDto toDto(TestResult testResult, List<TestResultUser> users) {
        return TestResultDetailsResponseDto.builder()
                .testResultId(testResult.getTestResultId())
                .dumpName(testResult.getDumpName())
                .integratedDumpPercentage(testResult.getIntegratedDumpPercentage())
                .settingsModel(testResult.getSettingsModel())
                .daysAfterDumpConsidered(testResult.getDaysAfterDumpConsidered())
                .numberOfUsers(testResult.getNumberOfUsers())
                .numberOfQuestions(testResult.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResult.getNumberOfRecommendedQuestions())
                .percentageOfQuestionsAnsweredWithoutRecommendations(testResult.getPercentageOfQuestionsAnsweredWithoutRecommendations())
                .percentageOfCorrectRecommendations(testResult.getPercentageOfCorrectRecommendations())
                .percentageIncreaseOfCorrectRecommendations(calculatePercentageIncreaseOfCorrectRecommendations(testResult))
                .testDate(LocalDateTimeFormatterFactory.formatLocalDateTimeToString(testResult.getTestDate()))
                .users(users.stream()
                        .map(u -> TestResultDetailsResponseDto.TestResultUserResponseDto.builder()
                                .testResultUserId(u.getTestResultUserId())
                                .integrationUserId(u.getIntegrationUserId())
                                .numberOfQuestions(u.getNumberOfQuestions())
                                .numberOfRecommendedQuestions(u.getNumberOfRecommendedQuestions())
                                .percentageOfCorrectRecommendations(u.getPercentageOfCorrectRecommendations())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static TestResultUserDetailsResponseDto toDto(TestResultUser testResultUser, TestResult testResult) {

        return TestResultUserDetailsResponseDto.builder()
                .testResultUserId(testResultUser.getTestResultUserId())
                .integrationUserId(testResultUser.getIntegrationUserId())
                .numberOfQuestions(testResultUser.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResultUser.getNumberOfRecommendedQuestions())
                .percentageOfCorrectRecommendations(testResultUser.getPercentageOfCorrectRecommendations())
                .userTags(testResultUser.getUserTags())
                .questions(testResultUser.getQuestions())
                .recommendedQuestions(testResultUser.getRecommendedQuestions())
                .testResult(TestResultUserDetailsResponseDto.TestResultDetailsResponseDto.builder()
                        .testResultId(testResult.getTestResultId())
                        .dumpName(testResult.getDumpName())
                        .integratedDumpPercentage(testResult.getIntegratedDumpPercentage())
                        .daysAfterDumpConsidered(testResult.getDaysAfterDumpConsidered())
                        .numberOfUsers(testResult.getNumberOfUsers())
                        .numberOfQuestions(testResult.getNumberOfQuestions())
                        .numberOfRecommendedQuestions(testResult.getNumberOfRecommendedQuestions())
                        .percentageOfQuestionsAnsweredWithoutRecommendations(testResult.getPercentageOfQuestionsAnsweredWithoutRecommendations())
                        .percentageOfCorrectRecommendations(testResult.getPercentageOfCorrectRecommendations())
                        .percentageIncreaseOfCorrectRecommendations(calculatePercentageIncreaseOfCorrectRecommendations(testResult))
                        .testDate(LocalDateTimeFormatterFactory.formatLocalDateTimeToString(testResult.getTestDate()))
                        .settings(testResult.getSettings())
                        .settingsModel(testResult.getSettingsModel())
                        .totalActivitySystem(testResult.getTotalActivitySystem())
                        .build())
                .build();
    }


    public static BigDecimal calculatePercentageIncreaseOfCorrectRecommendations(TestResult testResult) {
        if (isNull(testResult.getPercentageOfQuestionsAnsweredWithoutRecommendations())){
            return null;
        }

        BigDecimal percent = testResult.getPercentageOfCorrectRecommendations()
                .multiply(BigDecimal.valueOf(100))
                .divide(testResult.getPercentageOfQuestionsAnsweredWithoutRecommendations(), 2, RoundingMode.HALF_UP);

        return percent.subtract(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);
    }

}

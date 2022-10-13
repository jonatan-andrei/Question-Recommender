package jonatan.andrei.factory;

import jonatan.andrei.dto.TestResultDetailsResponseDto;
import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.dto.TestResultResponseDto;
import jonatan.andrei.dto.TestResultUserDetailsResponseDto;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.model.TestResultUser;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class TestResultFactory {

    public static TestResult newTestResult(TestResultRequestDto testResultRequestDto) {
        return TestResult.builder()
                .dumpName(testResultRequestDto.getDumpName())
                .integratedDumpPercentage(testResultRequestDto.getIntegratedDumpPercentage())
                .daysAfterDumpConsidered(testResultRequestDto.getDaysAfterDumpConsidered())
                .settings(testResultRequestDto.getSettings())
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
                .testDate(LocalDateTimeFormatterFactory.formatLocalDateTimeToString(testResult.getTestDate()))
                .build();
    }

    public static TestResultDetailsResponseDto toDto(TestResult testResult, List<TestResultUser> users) {
        return TestResultDetailsResponseDto.builder()
                .testResultId(testResult.getTestResultId())
                .dumpName(testResult.getDumpName())
                .integratedDumpPercentage(testResult.getIntegratedDumpPercentage())
                .daysAfterDumpConsidered(testResult.getDaysAfterDumpConsidered())
                .numberOfUsers(testResult.getNumberOfUsers())
                .numberOfQuestions(testResult.getNumberOfQuestions())
                .numberOfRecommendedQuestions(testResult.getNumberOfRecommendedQuestions())
                .percentageOfCorrectRecommendations(testResult.getPercentageOfCorrectRecommendations())
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
                        .percentageOfCorrectRecommendations(testResult.getPercentageOfCorrectRecommendations())
                        .testDate(LocalDateTimeFormatterFactory.formatLocalDateTimeToString(testResult.getTestDate()))
                        .settings(testResult.getSettings())
                        .totalActivitySystem(testResult.getTotalActivitySystem())
                        .build())
                .build();
    }

}

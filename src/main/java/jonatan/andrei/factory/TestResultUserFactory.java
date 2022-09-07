package jonatan.andrei.factory;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.model.TestResultUser;

import java.util.List;
import java.util.stream.Collectors;

public class TestResultUserFactory {

    public static List<TestResultUser> newTestResultUser(List<TestResultRequestDto.TestResultUserRequestDto> users, Long testResultId) {
        return users.stream()
                .map(u -> TestResultUser.builder()
                        .testResultId(testResultId)
                        .integrationUserId(u.getIntegrationUserId())
                        .numberOfQuestions(u.getNumberOfQuestions())
                        .numberOfRecommendedQuestions(u.getNumberOfRecommendedQuestions())
                        .percentageOfCorrectRecommendations(u.getPercentageOfCorrectRecommendations())
                        .error(u.isError())
                        .userTags(u.getUserTags())
                        .questions(u.getQuestions())
                        .build())
                .collect(Collectors.toList());
    }
}

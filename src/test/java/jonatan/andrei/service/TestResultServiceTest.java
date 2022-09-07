package jonatan.andrei.service;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.model.TestResultUser;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestTransaction
public class TestResultServiceTest extends AbstractServiceTest {

    @Inject
    TestResultService testResultService;

    @Test
    public void save() {
        // Arrange
        TestResultRequestDto testResultRequestDto = TestResultRequestDto.builder()
                .dumpName("dump name")
                .integratedDumpPercentage(BigDecimal.valueOf(50))
                .daysAfterDumpConsidered(7)
                .settings("settings")
                .numberOfUsers(15)
                .numberOfQuestions(50)
                .numberOfRecommendedQuestions(40)
                .percentageOfCorrectRecommendations(BigDecimal.valueOf(80))
                .testDate(LocalDateTime.now())
                .users(asList(TestResultRequestDto.TestResultUserRequestDto.builder()
                        .integrationUserId("1")
                        .numberOfQuestions(50)
                        .numberOfRecommendedQuestions(40)
                        .percentageOfCorrectRecommendations(BigDecimal.valueOf(80))
                        .error(false)
                        .build()))
                .build();

        // Act
        Long testResultId = testResultService.save(testResultRequestDto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        TestResult testResult = testResultRepository.findById(testResultId).get();
        assertEquals(testResultRequestDto.getDumpName(), testResult.getDumpName());
        assertEquals(testResultRequestDto.getIntegratedDumpPercentage().stripTrailingZeros(), testResult.getIntegratedDumpPercentage().stripTrailingZeros());
        assertEquals(testResultRequestDto.getDaysAfterDumpConsidered(), testResult.getDaysAfterDumpConsidered());
        assertEquals(testResultRequestDto.getSettings(), testResult.getSettings());
        assertEquals(testResultRequestDto.getNumberOfUsers(), testResult.getNumberOfUsers());
        assertEquals(testResultRequestDto.getNumberOfQuestions(), testResult.getNumberOfQuestions());
        assertEquals(testResultRequestDto.getNumberOfRecommendedQuestions(), testResult.getNumberOfRecommendedQuestions());
        assertEquals(testResultRequestDto.getPercentageOfCorrectRecommendations().stripTrailingZeros(), testResult.getPercentageOfCorrectRecommendations().stripTrailingZeros());
        assertEquals(testResultRequestDto.getTestDate().toLocalDate(), testResult.getTestDate().toLocalDate());
        TestResultUser testResultUser = testResultUserRepository.findByTestResultId(testResultId).get(0);
        assertEquals(testResultRequestDto.getUsers().get(0).getIntegrationUserId(), testResultUser.getIntegrationUserId());
        assertEquals(testResultRequestDto.getUsers().get(0).getNumberOfQuestions(), testResultUser.getNumberOfQuestions());
        assertEquals(testResultRequestDto.getUsers().get(0).getNumberOfRecommendedQuestions(), testResultUser.getNumberOfRecommendedQuestions());
        assertEquals(testResultRequestDto.getUsers().get(0).getPercentageOfCorrectRecommendations().stripTrailingZeros(), testResultUser.getPercentageOfCorrectRecommendations().stripTrailingZeros());
        assertEquals(testResultRequestDto.getUsers().get(0).isError(), testResultUser.isError());
    }
}

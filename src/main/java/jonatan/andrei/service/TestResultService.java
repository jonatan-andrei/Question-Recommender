package jonatan.andrei.service;

import jonatan.andrei.dto.TestResultDetailsResponseDto;
import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.dto.TestResultResponseDto;
import jonatan.andrei.dto.TestResultUserDetailsResponseDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.TestResultFactory;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.model.TestResultUser;
import jonatan.andrei.repository.TestResultRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TestResultService {

    @Inject
    TestResultUserService testResultUserService;

    @Inject
    TestResultRepository testResultRepository;

    @Transactional
    public Long save(TestResultRequestDto testResultRequestDto) {
        TestResult testResult = testResultRepository.save(TestResultFactory.newTestResult(testResultRequestDto));
        testResultUserService.saveAll(testResultRequestDto.getUsers(), testResult.getTestResultId());
        return testResult.getTestResultId();
    }

    @Transactional
    public List<TestResultResponseDto> findAll() {
        return testResultRepository.findAll()
                .stream().map(tr -> TestResultFactory.toDto(tr))
                .collect(Collectors.toList());
    }

    @Transactional
    public TestResultDetailsResponseDto findByTestResultId(Long testResultId) {
        TestResult testResult = findById(testResultId);
        List<TestResultUser> users = testResultUserService.findByTestResultId(testResultId);
        return TestResultFactory.toDto(testResult, users);
    }

    @Transactional
    public TestResultUserDetailsResponseDto findByTestResultUserId(Long testResultUserId) {
        TestResultUser testResultUser = testResultUserService.findByTestResultUserId(testResultUserId);
        TestResult testResult = findById(testResultUser.getTestResultId());
        return TestResultFactory.toDto(testResultUser, testResult);
    }

    private TestResult findById(Long testResultId) {
        return testResultRepository.findById(testResultId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found TestResult with id " + testResultId));
    }
}

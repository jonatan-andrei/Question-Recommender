package jonatan.andrei.service;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.factory.TestResultFactory;
import jonatan.andrei.model.TestResult;
import jonatan.andrei.repository.TestResultRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
}

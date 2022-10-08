package jonatan.andrei.service;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.exception.InconsistentIntegratedDataException;
import jonatan.andrei.factory.TestResultUserFactory;
import jonatan.andrei.model.TestResultUser;
import jonatan.andrei.repository.TestResultUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class TestResultUserService {

    @Inject
    TestResultUserRepository testResultUserRepository;

    public void saveAll(List<TestResultRequestDto.TestResultUserRequestDto> users, Long testResultId) {
        testResultUserRepository.saveAll(TestResultUserFactory.newTestResultUser(users, testResultId));
    }

    public List<TestResultUser> findByTestResultId(Long testResultId) {
        return testResultUserRepository.findByTestResultId(testResultId);
    }

    public TestResultUser findByTestResultUserId(Long testResultUserId) {
        return testResultUserRepository.findById(testResultUserId)
                .orElseThrow(() -> new InconsistentIntegratedDataException("Not found TestResultUser with id " + testResultUserId));
    }
}

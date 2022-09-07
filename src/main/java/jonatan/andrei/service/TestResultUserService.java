package jonatan.andrei.service;

import jonatan.andrei.dto.TestResultRequestDto;
import jonatan.andrei.factory.TestResultUserFactory;
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
}

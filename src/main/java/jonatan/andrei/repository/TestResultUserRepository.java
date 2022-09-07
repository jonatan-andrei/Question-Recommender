package jonatan.andrei.repository;

import jonatan.andrei.model.TestResultUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestResultUserRepository extends CrudRepository<TestResultUser, Long> {

    List<TestResultUser> findByTestResultId(@Param("testResultId") Long testResultId);
}

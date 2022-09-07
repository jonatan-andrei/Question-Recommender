package jonatan.andrei.repository;

import jonatan.andrei.model.TestResult;
import org.springframework.data.repository.CrudRepository;

public interface TestResultRepository extends CrudRepository<TestResult, Long> {
}

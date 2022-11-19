package jonatan.andrei.repository;

import jonatan.andrei.model.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    List<TestResult> findAllByOrderByTestResultIdAsc();

}

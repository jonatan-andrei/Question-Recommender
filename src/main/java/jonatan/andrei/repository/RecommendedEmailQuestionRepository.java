package jonatan.andrei.repository;

import jonatan.andrei.model.RecommendedEmailQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendedEmailQuestionRepository extends JpaRepository<RecommendedEmailQuestion, Long> {

    List<RecommendedEmailQuestion> findByRecommendedEmailId(Long recommendedEmailId);

}

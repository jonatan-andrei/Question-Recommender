package jonatan.andrei.repository;

import jonatan.andrei.model.RecommendedEmail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendedEmailRepository extends JpaRepository<RecommendedEmail, Long> {

    RecommendedEmail findByIntegrationUserId(String integrationUserId);
}

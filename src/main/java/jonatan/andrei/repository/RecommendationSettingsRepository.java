package jonatan.andrei.repository;

import jonatan.andrei.model.RecommendationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationSettingsRepository extends JpaRepository<RecommendationSettings, Long> {
}

package jonatan.andrei.repository;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.model.RecommendationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendationSettingsRepository extends JpaRepository<RecommendationSettings, Long> {

    List<RecommendationSettings> findByChannel(RecommendationChannelType channel);
}

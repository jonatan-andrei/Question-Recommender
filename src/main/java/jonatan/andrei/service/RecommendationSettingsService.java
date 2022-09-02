package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.model.RecommendationSettings;
import jonatan.andrei.repository.RecommendationSettingsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.*;

@ApplicationScoped
public class RecommendationSettingsService {

    @Inject
    RecommendationSettingsRepository recommendationSettingsRepository;

    @Transactional
    public void save(Map<RecommendationSettingsType, Integer> recommendationSettingsMap) {
        recommendationSettingsRepository.deleteAll();
        List<RecommendationSettings> recommendationSettingsList = new ArrayList<>();

        for (RecommendationSettingsType recommendationSettingsType : RecommendationSettingsType.values()) {
            Integer value = Optional.ofNullable(recommendationSettingsMap.get(recommendationSettingsType))
                    .orElse(recommendationSettingsType.getDefaultValue());

            recommendationSettingsList.add(RecommendationSettings.builder()
                    .name(recommendationSettingsType)
                    .value(value)
                    .build());
        }

        recommendationSettingsRepository.saveAll(recommendationSettingsList);
    }

    @Transactional
    public Map<RecommendationSettingsType, Integer> findRecommendationSettings() {
        List<RecommendationSettings> allRecommendationSettings = recommendationSettingsRepository.findAll();
        Map<RecommendationSettingsType, Integer> recommendationSettingsMap = new HashMap<>();

        for (RecommendationSettingsType recommendationSettingsType : RecommendationSettingsType.values()) {
            Optional<RecommendationSettings> settings = allRecommendationSettings.stream()
                    .filter(s -> s.getName().equals(recommendationSettingsType)).findFirst();
            recommendationSettingsMap.put(recommendationSettingsType,
                    settings.map(RecommendationSettings::getValue).orElse(recommendationSettingsType.getDefaultValue()));
        }

        return recommendationSettingsMap;
    }
}

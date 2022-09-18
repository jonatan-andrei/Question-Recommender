package jonatan.andrei.service;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import jonatan.andrei.dto.RecommendationSettingsRequestDto;
import jonatan.andrei.model.RecommendationSettings;
import jonatan.andrei.repository.RecommendationSettingsRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class RecommendationSettingsService {

    @Inject
    RecommendationSettingsRepository recommendationSettingsRepository;

    @Transactional
    public void save(List<RecommendationSettingsRequestDto> recommendationSettings) {
        recommendationSettingsRepository.deleteAll();
        List<RecommendationSettings> recommendationSettingsList = new ArrayList<>();
        for (RecommendationChannelType recommendationChannelType : RecommendationChannelType.values()) {
            Map<RecommendationSettingsType, BigDecimal> recommendationSettingsByChannel = recommendationChannelType.getSettings();
            for (var recommendationSettingsType : recommendationSettingsByChannel.entrySet()) {
                BigDecimal value = recommendationSettings.stream().filter(
                                rs -> recommendationChannelType.equals(rs.getChannel())
                                        && recommendationSettingsType.getKey().equals(rs.getName())
                        ).findFirst()
                        .map(RecommendationSettingsRequestDto::getValue)
                        .orElse(recommendationSettingsType.getValue());

                recommendationSettingsList.add(RecommendationSettings.builder()
                        .name(recommendationSettingsType.getKey())
                        .channel(recommendationChannelType)
                        .value(value)
                        .build());
            }
        }

        recommendationSettingsRepository.saveAll(recommendationSettingsList);
    }

    @Transactional
    public Map<RecommendationSettingsType, BigDecimal> findRecommendationSettingsByChannel(RecommendationChannelType channel) {
        List<RecommendationSettings> recommendationSettingsByChannel = recommendationSettingsRepository.findByChannel(channel);
        Map<RecommendationSettingsType, BigDecimal> recommendationSettingsMap = new HashMap<>();
        Map<RecommendationSettingsType, BigDecimal> recommendationSettings = RecommendationChannelType.findSettingsByChannel(channel);

        for (var recommendationSettingsType : recommendationSettings.entrySet()) {
            Optional<RecommendationSettings> settings = recommendationSettingsByChannel.stream()
                    .filter(s -> s.getName().equals(recommendationSettingsType.getKey())).findFirst();
            recommendationSettingsMap.put(recommendationSettingsType.getKey(),
                    settings.map(RecommendationSettings::getValue).orElse(recommendationSettingsType.getValue()));
        }

        return recommendationSettingsMap;
    }
}

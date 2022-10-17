package jonatan.andrei.dto;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationSettingsRequestDto {

    private RecommendationSettingsType name;

    private BigDecimal value;

    private RecommendationChannelType channel;

}

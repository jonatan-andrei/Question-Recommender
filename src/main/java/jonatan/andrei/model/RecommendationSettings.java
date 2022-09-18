package jonatan.andrei.model;

import jonatan.andrei.domain.RecommendationChannelType;
import jonatan.andrei.domain.RecommendationSettingsType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "recommendation_settings")
public class RecommendationSettings {

    @Id
    @SequenceGenerator(name = "recommendationSettingsSeq", sequenceName = "recommendation_settings_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendationSettingsSeq")
    @NotNull
    @Column(name = "recommendation_settings_id")
    private Long recommendationSettingsId;

    @NotNull
    @Column(name = "channel", length = 100)
    private RecommendationChannelType channel;

    @NotNull
    @Column(name = "name", length = 100)
    private RecommendationSettingsType name;

    @NotNull
    @Column(name = "value", length = 100)
    private BigDecimal value;

}

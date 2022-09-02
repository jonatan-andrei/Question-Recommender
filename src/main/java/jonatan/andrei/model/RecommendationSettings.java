package jonatan.andrei.model;

import jonatan.andrei.domain.RecommendationSettingsType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name = "name", unique = true, length = 100)
    private RecommendationSettingsType name;

    @NotNull
    @Column(name = "value", length = 100)
    private Integer value;

}
